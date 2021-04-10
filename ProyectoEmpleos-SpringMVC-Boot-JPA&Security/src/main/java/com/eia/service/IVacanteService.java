package com.eia.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eia.model.Vacante;

public interface IVacanteService {

	public List<Vacante> buscarTodas();
	
	public Vacante buscarPorId(Integer vacanteId);
	
	public void guardar (Vacante vacante);
	
	public List<Vacante> buscarDestacadas();
	
	public void eliminar(int idVacante);
	
	List<Vacante> buscarByExample(Example<Vacante> exaVacante);
	
	public Page<Vacante> buscarTodasPorPaginacion(Pageable pag);
}
