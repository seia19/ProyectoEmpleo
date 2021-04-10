package com.eia.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eia.model.Categoria;

public interface ICategoriaService {
	
	public void guardarCategoria(Categoria categoria);
	
	public List<Categoria> buscarTodasCategorias();
	
	public Categoria buscarCategoriaPorId(Integer id);
	
	public void eliminarCategoria(Integer idCategoria);
	
	public Page<Categoria> buscarTodasPorCategoria(Pageable pag);
}
