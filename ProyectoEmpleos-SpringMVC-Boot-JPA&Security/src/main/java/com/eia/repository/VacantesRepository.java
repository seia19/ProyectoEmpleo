package com.eia.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eia.model.Vacante;

public interface VacantesRepository extends JpaRepository<Vacante, Integer>{
	
	//       select * from Vacantes where estatus = ?
	//Query Method . keyWord findBy+AtributoClase (tipoDato atributoClase) Nota: el parametro puede llamarse como sea
	public List<Vacante> findByEstatus (String estatus);
	
	public List<Vacante> findByDestacadoAndEstatus(Integer destacada, String estatus);
	
	public List<Vacante> findByDestacadoAndEstatusOrderByIdDesc (Integer destacado, String estatus);
	
	public List<Vacante> findBySalarioBetween(double salario1, double salario2);
	
	public List<Vacante> findBySalarioBetweenOrderBySalarioDesc(double salario1, double salario2);
	
	public List<Vacante> findByEstatusIn(String[] estatus); //In requiere que se evalue mediante un arreglo [] de la propiedad
}
