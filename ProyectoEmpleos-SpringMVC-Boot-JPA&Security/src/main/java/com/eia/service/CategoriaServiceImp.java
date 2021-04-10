package com.eia.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.eia.model.Categoria;

@Service
public class CategoriaServiceImp implements ICategoriaService{

	
	List<Categoria> listaCategoria = null;
	
	public CategoriaServiceImp() {
		
		listaCategoria = new LinkedList<Categoria>();
		// Creamos algunas Categorias para poblar la lista ...
		
		// Categoria 1
		Categoria cat1 = new Categoria();
		cat1.setId(1);
		cat1.setNombre("Contabilidad");
		cat1.setDescripcionCategoria("Descripcion de la categoria Contabilidad");
		
		// Categoria 2
		Categoria cat2 = new Categoria();
		cat2.setId(2);
		cat2.setNombre("Ventas");
		cat2.setDescripcionCategoria("Trabajos relacionados con Ventas");
		
					
		// Categoria 3
		Categoria cat3 = new Categoria();
		cat3.setId(3);
		cat3.setNombre("Comunicaciones");
		cat3.setDescripcionCategoria("Trabajos relacionados con Comunicaciones");
		
		// Categoria 4
		Categoria cat4 = new Categoria();
		cat4.setId(4);
		cat4.setNombre("Arquitectura");
		cat4.setDescripcionCategoria("Trabajos de Arquitectura");
		
		// Categoria 5
		Categoria cat5 = new Categoria();
		cat5.setId(5);
		cat5.setNombre("Educacion");
		cat5.setDescripcionCategoria("Maestros, tutores, etc");
		
		// Categoria 6
		Categoria cat6 = new Categoria();
		cat6.setId(6);
		cat6.setNombre("Programacion");
		cat6.setDescripcionCategoria("Empleo Programadores Spring");
		
		/**
		 * Agregamos los 5 objetos de tipo Categoria a la lista ...
		 */
		listaCategoria.add(cat1);			
		listaCategoria.add(cat2);
		listaCategoria.add(cat3);
		listaCategoria.add(cat4);
		listaCategoria.add(cat5);
		listaCategoria.add(cat6);

	}
	

	@Override
	public List<Categoria> buscarTodasCategorias() {
		
		return listaCategoria;
	}
	
	@Override
	public void guardarCategoria(Categoria categoria) {

		listaCategoria.add(categoria);		
	}


	@Override
	public Categoria buscarCategoriaPorId(Integer id) {
		
		for(Categoria c : listaCategoria) {
			
			if (c.getId()==id) {
				System.out.println("id Categoría encontrado: " + id );
				return c;
			}
		}
		
		return null;
	}


	@Override
	public void eliminarCategoria(Integer idCategoria) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Page<Categoria> buscarTodasPorCategoria(Pageable pag) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
