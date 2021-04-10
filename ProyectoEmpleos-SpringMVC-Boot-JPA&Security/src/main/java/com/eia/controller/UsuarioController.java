package com.eia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eia.model.Usuario;
import com.eia.service.IUsuarioService;
import com.eia.service.db.UsuarioServiceJpa;

@Controller
@RequestMapping(value="/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioServiceJpa usuarioJpa;
	
	
	
	@GetMapping(value="/delete/{id}")
	public String eliminarUsuario(@PathVariable(value="id")Integer idUsuario, RedirectAttributes attributes) {
		
		String strIdUser = Integer.toString(idUsuario);
		try {
			// Eliminamos el Usuario.
			usuarioJpa.eliminarUsuario(idUsuario);			
			attributes.addFlashAttribute("msjFlashCat", "El usuario fue eliminado exitosamente!.");
			
		}catch(Exception e) {
				
				attributes.addFlashAttribute("msgImpossible", "La categoría con Id " + strIdUser + " no puede ser eliminada, porque su registro se encuentra asociado a alguna vacante.");
				System.out.println("\n Problema de Integridad en Base de Datos: La categoría con Id" + idUsuario +  " no puede ser eliminada, porque su registro se encuentra asociado a alguna vacante. \n"  + e.getMessage()  + e.getCause());
			}
		return "redirect:/usuarios/index";
		
	}
	
	@GetMapping(value="/index")
	public String buscarTodosUsuarios(Model modelo){
		
		List<Usuario> listaTotUsuarios = usuarioJpa.buscarTodosUsuarios();
		modelo.addAttribute("usuarios", listaTotUsuarios);
		
		return "usuarios/listUsuarios";
	}
	
	//los usuarios se dan de alta en HomeController


}
