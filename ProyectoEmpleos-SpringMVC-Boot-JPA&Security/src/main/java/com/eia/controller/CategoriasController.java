package com.eia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eia.model.Categoria;
import com.eia.service.ICategoriaService;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {
	
	@Autowired
	//@Qualifier(value = "categoriasServiceJpa")
	private ICategoriaService categoriaService;
	
	// @GetMapping("/create")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear(Categoria categoria) {
		
		
		return "categorias/formCategoria";
	}

	// @GetMapping("/index")
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String mostrarIndex(Model model) {//obtendrá su parámetro, para el modelo, desde formCategoria.html
	   
		List<Categoria> lista = categoriaService.buscarTodasCategorias();
    	model.addAttribute("categorias", lista);
		
		return "categorias/listCategorias";
	}
	
	//Método para Paginar el index de categorias
	@GetMapping(value="/indexPaginate")
	public String mostrarIndexPaginado(Pageable pag, Model model) {
		
		Page<Categoria> pagCategorias = categoriaService.buscarTodasPorCategoria(pag);
		model.addAttribute("categorias",pagCategorias);
		
		return "categorias/listCategorias";
	}
	
	
	// @PostMapping("/save") 
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String guardarDatosCategoria(   
									    Categoria categoria, 
										BindingResult resultadoEnlace, 
										RedirectAttributes redirAtt
										) { 
	
		if(resultadoEnlace.hasErrors()==true) {
			System.out.println("Error durante captura de categoría: " + "\n");
			
			List<ObjectError> listaErrores = resultadoEnlace.getAllErrors();
			
			for(ObjectError e: listaErrores) {
				System.out.println(e.getDefaultMessage());
			}
			return "categorias/formCategoria";
		}
		
		// Data Binding Automático de Spring, mediante el método mostrarIndex(Model model), mapeado con @RequestMapping(method=RequestMethod.POST)	
		categoriaService.guardarCategoria(categoria);
		
		redirAtt.addFlashAttribute("msjFlashCat", "registro de categoría almacenado correctamente n.n");
		
		System.out.println("datos categoria: " + categoria.getNombre() + " & "+ categoria.getDescripcionCategoria());
		
		return "redirect:/categorias/index";
	}
	
	
	@GetMapping(value="/edit/{id}")
	public String editarCategoria(Model model,@PathVariable(value="id") Integer id) {
	
		
	Categoria c =  categoriaService.buscarCategoriaPorId(id);
	model.addAttribute("categoria", c); //enviamos el modelo categoria al fomulario de formCategoria [que recibe un object="categoria"]
		
		return "categorias/formCategoria";
	}
	
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idCategoria, RedirectAttributes attributes) {		
		
		String strIdCat = Integer.toString(idCategoria);
		
		try {
		// Eliminamos la categoria.
		categoriaService.eliminarCategoria(idCategoria);			
		attributes.addFlashAttribute("msjFlashCat", "La categoría fue eliminada!.");
		}
		catch(Exception e) {
			attributes.addFlashAttribute("msgImpossible", "La categoría con Id " + strIdCat + " no puede ser eliminada, porque su registro se encuentra asociado a alguna vacante.");
			System.out.println("\n Problema de Integridad en Base de Datos: La categoría con Id" + idCategoria +  " no puede ser eliminada, porque su registro se encuentra asociado a alguna vacante. \n"  + e.getMessage()  + e.getCause());
		}
		return "redirect:/categorias/index";
	}
	
	
}
