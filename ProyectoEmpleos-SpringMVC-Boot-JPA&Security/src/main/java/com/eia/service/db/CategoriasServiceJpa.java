package com.eia.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eia.model.Categoria;
import com.eia.repository.CategoriasRepository;
import com.eia.service.ICategoriaService;

@Service
@Primary
public class CategoriasServiceJpa implements ICategoriaService {

	@Autowired
	private CategoriasRepository categoriasRepo;
	
	@Override
	public void guardarCategoria(Categoria categoria) {
		categoriasRepo.save(categoria);

	}

	@Override
	public List<Categoria> buscarTodasCategorias() {
		
		List<Categoria> listaBusCat = categoriasRepo.findAll();
		
		return listaBusCat;
	}

	
	@Override
	public Categoria buscarCategoriaPorId(Integer idCategoria) {
		
		Categoria cat = new Categoria();
		
		Optional <Categoria> optionalBusId = categoriasRepo.findById(idCategoria);
		
		if(optionalBusId.isPresent()==true) {
			
			cat = optionalBusId.get();
			
		return cat;
		}
		else {
			System.out.println("\n Registro de Categoria con id " + idCategoria + " no encontrado ¬¬' \n");
		return null;
		}
	}

	@Override
	public void eliminarCategoria(Integer idCategoria) {
		
		categoriasRepo.deleteById(idCategoria);
	}

	@Override
	public Page<Categoria> buscarTodasPorCategoria(Pageable pag) {
		
		Page<Categoria> pagCat = categoriasRepo.findAll(pag);
		return pagCat;
	}
}
