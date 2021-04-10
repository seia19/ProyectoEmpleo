package com.eia.controller;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.eia.model.Vacante;
import com.eia.service.IVacanteService;

import java.util.List;

@Controller
public class ListaVacantesController {
	
	@Autowired
	private IVacanteService serviceVacantes;
	
	/**Agregando la lista de Vacantes al modelo */
	
	@GetMapping(value="/tablavacantes")
	private String mostrarVacantes (Model modelo)  {
		
		//Establezco la lísta de vacantes en el método buscarTodas() (de la Intefaz 'IVacanteService') que devuelve  
		// la lista de objetos que creamos en el Constructor de la clase VacanteServiceImp()
		List<Vacante> modLista =  serviceVacantes.buscarTodas();

		//agrego la lista al modelo
		modelo.addAttribute("vacante", modLista);
		
		return "tablavacantes";
	} 
	
}
