package com.eia.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eia.model.Vacante;
import com.eia.repository.VacantesRepository;
import com.eia.service.IVacanteService;

@Service
@Primary
public class VacantesServiceJpa implements IVacanteService {

	@Autowired
	private VacantesRepository vacantesRepo;
	
	@Override
	public List<Vacante> buscarTodas() {
		
		
		
		return vacantesRepo.findAll();
	}

	@Override
	public Vacante buscarPorId(Integer vacanteId) {
	
	Optional<Vacante> optionalVac = vacantesRepo.findById(vacanteId);
	
		if (optionalVac.isPresent()==true) {
			
			Vacante Objvac = (Vacante) optionalVac.get();
			
			return  Objvac;
		}
		
		else {
			System.out.println("\n Registro de Categoria con id " + vacanteId + " no encontrado ¬¬' \n");
		return null;
		}
	}

	@Override
	public void guardar(Vacante vacante) {
	
		vacantesRepo.save(vacante);
	}

	@Override
	public List<Vacante> buscarDestacadas() {
		
		return vacantesRepo.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
	}

	@Override
	public void eliminar(int idVacante) {
		
		vacantesRepo.deleteById(idVacante);
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> exaVacante) {
		
		System.out.println("\n  Valor de exaVacante: \n" + exaVacante.toString() +  " \n");

		return vacantesRepo.findAll(exaVacante);
	}

	@Override
	public Page<Vacante> buscarTodasPorPaginacion(Pageable pag) {
				
		Page<Vacante> pagVac =  vacantesRepo.findAll(pag);
		
		return pagVac;
	}

}
