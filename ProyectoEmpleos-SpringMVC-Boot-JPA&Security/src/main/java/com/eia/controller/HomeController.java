package com.eia.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.eia.model.Perfil;
import com.eia.model.Usuario;
import com.eia.model.Vacante;
import com.eia.service.ICategoriaService;
import com.eia.service.IUsuarioService;
import com.eia.service.IVacanteService;
import com.eia.service.db.UsuarioServiceJpa;


@Controller
@ControllerAdvice
public class HomeController {
	
	@Autowired
	private ICategoriaService iCategoriasService;
	
	@Autowired
	private IVacanteService iVacanteService;
	
	@Autowired
	private UsuarioServiceJpa usuarioJpa;
	
	@Autowired
	private IUsuarioService iUsuarioService;
	
	@Autowired
	private PasswordEncoder passEncoder; //PasswordEncoder() --> de nuestra clase DataBaseWebSecurity
	
	
	
	@GetMapping(value="/")
	public String mostrarHome(Model modelo) {
		
		return "home";
	}
	
	@GetMapping(value="/login")
	public String mostrarFormLogin() {
		return "login/formLogin";
	}
	
	@GetMapping("/logout")
	public String cerrarSesionLogout(HttpServletRequest requestOut, RedirectAttributes redirAttr) {
		
		SecurityContextLogoutHandler logoutHandlerManager = new SecurityContextLogoutHandler();
		
		logoutHandlerManager.logout(requestOut, null, null);
		
		String mensajeLogout = "La sesión ha finalizado correctamente.";
		
		redirAttr.addFlashAttribute("msgLogout", mensajeLogout);
		
		
		return "redirect:/login";
	}
	
	@GetMapping(value="/index")
	public String mostrarIndexHome(Authentication auth, HttpSession session) {
		//obteniendo el nombre del usuario autenticado (loggeado)
		String nombreUser = auth.getName();
		
		System.out.println("\n"+ "Nombre de usuario loggeado:" + nombreUser + "\n" );
		
		
		/** Agregando datos a la sesión HttpSession & findByUsername() **/
		
		if(session.getAttribute("username") == null) {
			
			Usuario usuario = usuarioJpa.buscarUsuarioPorUserName(nombreUser);
			usuario.setPassword(null); //protejo el pass para que no salga durante la retracción de las propiedades del obj usuario
		    session.setAttribute("username", usuario); //si el atributo de la sesión es nulo, agrega el obj usuario
			
			System.out.println("\n Datos de Sesión del Usuario " + usuario.getNombre() + ": \n" + usuario.toString() + "\n" );
		
		}
		
		//obteniendo roles del usuario autenticado
		//Collection<GrantedAuthority> coleccionRolesUsuario =  (Collection<GrantedAuthority>) auth.getAuthorities();
		
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
		
		for(GrantedAuthority listaRoles: roles) {
			System.out.println("Roles asignados: " +  listaRoles.toString()+ "\n");
		}
		
		
		
		return "redirect:/";
	}
							
	
	
	@ModelAttribute
	public void setGenericos(Model modelo) {
		
		Vacante vacanteSearch = new Vacante();
		vacanteSearch.resetImg();//reset defalult 'no-image.png' -no es necesaria para la búsqueda-
		vacanteSearch.resetEstatus(); //reset default 'Aprobada' - caso contrario aparecerá este valor como parte del select durante la busqueda SQL del repositorio

		
		//Adquiriendo lista de vacantes destacadas
		List<Vacante> homeListDestacadas = iVacanteService.buscarDestacadas();

		//Agregando la lista al modelo
		modelo.addAttribute("vacantesHome", homeListDestacadas);
		
		//Agregando lista de Categorias al Modelo
		modelo.addAttribute("categoriasHome", iCategoriasService.buscarTodasCategorias());
		
		//Agregando una búsqueda de Vacantes al Modelo
		modelo.addAttribute("buscarVacanteHome", vacanteSearch); //map --> th:object en formulario home
		
	}
	
	@GetMapping(value="/search")
	public String busqueda(@ModelAttribute(value="buscarVacanteHome" ) Vacante vacante, Model model) { //databinding con @ModelAttribute --> modelo buscarVacanteHome
		
		
		System.out.println("buscando por: " + vacante.toString());
		
		//busqueda por partes coincidentes en <th:field="*{descripcion}"> - ExampleMatcher 
		GenericPropertyMatcher matchDescripcionParcial = ExampleMatcher.GenericPropertyMatchers.contains();
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion", matchDescripcionParcial); //SQL: like %?%
		
			/**  ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains()); */	
				
		
		
		Example exaVacante = Example.of(vacante,matcher);  // Example - hace match de las propiedades del obj vacante
		List<Vacante> listaVac = iVacanteService.buscarByExample(exaVacante);
		
		//Agregando al Modelo la lista
		model.addAttribute("vacantesHome", listaVac);
		
		return "home";

	}
	
	//Método en caso de que el usuario no agregue en th:field="*{descripcion}" algún String durante la búsqueda, convirte a null su valor  
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		
		StringTrimmerEditor recortadorString = new StringTrimmerEditor(true);
		webDataBinder.registerCustomEditor(String.class, recortadorString);
		
	}
	
	/** Almacenando Usuario*/
	
	@GetMapping(value="/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {
	/*	usuario.setEstatus(1); // Activado por defecto
		usuario.setFechaRegistro(new Date()); 
		usuario.setEmail("eia@hotmail.com");
		usuario.setNombre("seia");
		usuario.setUsername("eia");
		usuario.setPassword("1234");
		
		// Creamos el Perfil que le asignaremos al usuario nuevo
		Perfil perfil = new Perfil();
		perfil.setId(3); // Perfil USUARIO
		usuario.agregarPerfil_AlUsuario(perfil);*/
		


		
		/**
		 * Guardamos el usuario en la base de datos. El Perfil se guarda automaticamente
		 */
		
		try {
		
			usuario.setEstatus(1); // Activado por defecto
			usuario.setFechaRegistro(new Date()); 
			
			//Encriptando Password 
			String passwordPlano = usuario.getPassword();
			String passwordCod = passEncoder.encode(passwordPlano);
			usuario.setPassword(passwordCod);
			
			// Creamos el Perfil que le asignaremos al usuario nuevo
			Perfil perfil = new Perfil();
			perfil.setId(2); // Perfil Usuario -adm-
			usuario.agregarPerfil_AlUsuario(perfil);
			
			//guardando registro de nuevo usuario
			usuarioJpa.guardarUsuario(usuario);
		
				
		attributes.addFlashAttribute("msgSaveUser", "El registro del nuevo Usuario fue guardado correctamente!");
		return "redirect:/listUsuarios"; //envía petición al método mostrarListaUsuarios() QUE DEBE ESTAR UBICADO EN ESTA MISMA CLASE
		}
		
		catch(Exception e) {
			System.out.println("error en registro de nuevo usuario:" + e.getMessage());
			return "formRegistro";
		}
		
		/**
		 Se debe indicar que hacer en caso de errores durante el DataBinding, esto debido al redirect,
		 ya que si no lo hacemos, automáticamente tratará de guardar el objeto 'usuario',
		 pero como este tiene todos sus valores en null, generará un error de integridad en BD
		 haciendo que el sistema se caiga. 
		 */
		
	}

	
	@GetMapping(value="/listUsuarios")
	public String mostrarListaUsuarios(Model modelo) {
		
		
		List<Usuario> listaTempUsuarios = iUsuarioService.buscarTodosUsuarios();
		modelo.addAttribute("usuarios",listaTempUsuarios);
		
		return "usuarios/listUsuarios";
		
		/*se debe colocar este método en esta clase, pues atiende el redirect del método guardarRegistro()*/
	}
	
	
	//Ver algoritmos encriptados con bcrypt
	@GetMapping(value="/encrip/temp/{text}")
	@ResponseBody //crea vista temporal, mostrando el contenido del return
	private String verEncriptado(@PathVariable("text") String text){ //var text debe llamarse igual en todos los parámetros
		
		String textoEncriptado = passEncoder.encode(text);
		
		return "El valor de " + text + " En encriptado dinámico con bcrypt es: " + " " + textoEncriptado;
	}
}
