package com.eia.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eia.model.Categoria;
import com.eia.model.Vacante;
import com.eia.service.ICategoriaService;
import com.eia.service.IVacanteService;
import com.eia.utilimg.Utileria;

@Controller
@RequestMapping(value="/vacantes")
public class VacantesController {

	@Autowired
	private IVacanteService serviceVacantes;//Variable clave para el Data Bindig (@Autowired)
	
	@Autowired
	//@Qualifier(value = "categoriasServiceJpa")
	private ICategoriaService serviceCategoria;
	
	@Value(value="${empleosapp.images.ruta}")
	private String ruta;
	
	@RequestMapping(value="view/{id}")
	public String verDetalle (@PathVariable(value="id") Integer vacanteId, Model modelo) {
		
		//Almaceno el valor en un objeto Vacante para obtener sus propiedades a través del 
		//método buscarPorId() de la intefaz IVacanteService heredado VacanteServiceImp
	    Vacante vacante = serviceVacantes.buscarPorId(vacanteId);
		
		modelo.addAttribute("vacantesHome", vacante);
		
		System.out.println("el id mostrado en pantalla es: " + vacante.getId() + " el nombre de la vacante es: " + vacante.getNombre());
				
		return "detalles";
	}


 @GetMapping(value="/delete/{id}") 
 public String eliminar(@PathVariable(name="id") int idVacante, Model model) { 
	 
	 //model.addAttribute("modeloIndex", id);
	 
	 serviceVacantes.eliminar(idVacante);
	 
	 return "redirect:/vacantes/index";
 }
 
 //metodo Establece genéricos
 @ModelAttribute 
 public Model agregarListaDatosCategoriaAlModelo(Model model) {
	 
	 List<Categoria> listaCategoria = serviceCategoria.buscarTodasCategorias();
	 
	 Model modeloCrearCat = model.addAttribute("modelCategorias", listaCategoria);
	 
	return modeloCrearCat;
 }
 
 /** Nota: se tuvieron que cambiar el nombre del model de modeloIndex a vacante en métodos 
 
 mostrarIndex()
 editar()
 mostrarVacantes() - de la clase ListaVacantesController
 
y en las pág. html se cambión en el ciclo forEach:

antes - th:each="tempVacante : ${modeloIndex}"
ahora - th:each="tempVacante : ${vacante}" 

en:
listVacantes.html
tablavacantes.html

para que al momento de editar un objeto, este pase un objeto de tipo vacante
al formulario de la vista formVacante. pues este formulario espera un objeto
de tipo Vacante th:object="${vacante}"

*/
 @GetMapping(value="/edit/{id}")
 public String editar(@PathVariable(name="id") int idVacante, Model model) {
	 
	 Vacante v = serviceVacantes.buscarPorId(idVacante);
	 
	 model.addAttribute("vacante",v);
	 
	 //desplegando la Lista de Categorias, para editarla en la vista formVacante
	 agregarListaDatosCategoriaAlModelo(model);
	 	 
	 return"vacantes/formVacante";
 }
 

 @GetMapping(value="/create")
 public String crear(Model model, Vacante vacante) {
	 
	 agregarListaDatosCategoriaAlModelo(model); 
	 
	 return "vacantes/formVacante";
 }
 

 
 
 //                              -DataBinding & Manejo de errores-
 
 /**Método para almacenar datos proveniente del formulario formVacante tag <input> propiedad name */
 @PostMapping(value="/save")
 public String guardarDatos(
		 					Vacante vacante, 
		 					BindingResult enlaceResultante,  
		 					RedirectAttributes redirAttribute,
		 					@RequestParam(value="archivoImagen") MultipartFile multiPart) { 
	
	  
	 
	 if(enlaceResultante.hasErrors()==true){
		 
		 System.out.println("ocurrió un error en el DataBinding del formulario"); 
		 
		 List<ObjectError> listaErrores = enlaceResultante.getAllErrors();	 
		 
		 for(ObjectError e: listaErrores) {
			 
			 System.out.println(e.getDefaultMessage());
		 }
		 
		 return "vacantes/formVacante";
	 }
	 
	//Data Binding - spring instancia e inyecta los objetos de forma automática [en este caso gracias a nuestro modelo "modeloIndex"
	//declarado en el método mostrarIndex(Model model) de esta misma clase]. y mapea los datos en este caso con @PostMapping
	 System.out.println("Datos del objeto vacante, proveniente del formulario: " + "\n" + vacante.toString());
	 
	 //Agregando propiedades del objeto vacante en una lista
	 serviceVacantes.guardar(vacante);
	 
	 
	 //Almacena msj al usuario, para enviarlo a la vista después del redirect, 
	 redirAttribute.addFlashAttribute("msjFlash","El registro ha sido almacenado con éxito n.n");
	 
	 
	 /* -- Almacenando la imagen dentro del objeto vacante -- */
	 
	 if (multiPart.isEmpty()!=true) {
		 
	//	 String ruta = "c:/empleos/img-vacantes/";
		 
		 String img = Utileria.guardarArchivoImg(multiPart, ruta); //multiPart obtiene su valor por la img introducida en formVacantes y es identificada con @RequestParam(value="archivoImagen")
	     
		    // - Agregando el nombre y la ruta de la imagen. -
		 
		 if(img != null) { //la imagen si se guardo
			 
			 vacante.setImagen(img);
			 
		 }
		 
	 
	 }
	 
	//redirección. Llamando método mostrarIndex para que se ejecute y una vez agregado el objeto y sus 
	 //propiedades a la lista de forma dinámica, este sea dirija a la vista listVacantes y se pueda ver el resultado.
	 return "redirect:/vacantes/index";
	 
 }
 

 
 /** Método para establecer el formato de fecha para poder hacer el parse de Date a String durante 
     DataBinding proveniente de instanciar objetos tipo Vacante. método guardarDatos()*/
 @InitBinder()
 public void initBinderFormatoFecha(WebDataBinder webDataBinder) {
	 
	 SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
	 
	 CustomDateEditor editor = new CustomDateEditor(formatoFecha,false);
	 
	 webDataBinder.registerCustomEditor(Date.class, editor);
 }
 
 
 
 @GetMapping(value="/index")
 public String mostrarIndex(Model modelo) {
	 
	List<Vacante> indexVacantes = serviceVacantes.buscarTodas();
	
	modelo.addAttribute("vacante", indexVacantes); //Este modelo se utilizará para realizar el Data Bindg
	
	//Iteración en consola - prueba -
	Iterator<Vacante> iteradorIndex = indexVacantes.iterator() ;
	while (iteradorIndex.hasNext()) {
		System.out.println(iteradorIndex.next());
	}
	 
	 return "vacantes/listVacantes";
 }
 
 
	//Metodo para paginación
	@GetMapping(value="/indexPaginate")
	public String mostrarIndexPaginado(Pageable pag, Model modelo) {
		
		Page<Vacante> paginacionIndexVacante = serviceVacantes.buscarTodasPorPaginacion(pag);
		modelo.addAttribute("vacante",paginacionIndexVacante);
		
		return "vacantes/listVacantes";
	}
 
 
 
 /*@RequestMapping(value="/save",  method=RequestMethod.POST)
 @PostMapping(value="/save")
 public String guardarDatos(@RequestParam(value="nombre") String nombre,
		 					@RequestParam(value="descripcion") String descripcion,
		 					@RequestParam(value="categoria") String categoria,
		 					@RequestParam(value="estatus") String estatus,
		 					@RequestParam(value="fechaPublicacion") String fecha,
		 					@RequestParam(value="destacado") int destacado,
		 					@RequestParam(value="salario") double salario,
		 					@RequestParam(value="archivoImagen") String archivoImagen,
		 					@RequestParam(value="detalles") String detalles){
	 
	 System.out.println("Nombre. " + nombre);
	 System.out.println("Descripción. " + descripcion);
	 System.out.println("Estatus. " + estatus);
	 System.out.println("Fecha. " + fecha);
	 System.out.println("Destacado. " + destacado);
	 System.out.println("Salario. " + salario);
	 System.out.println("Detalles. " + detalles);
	 
	 return "vacantes/listVacantes";
 }*/
 
}
