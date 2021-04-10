package com.eia.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eia.model.Vacante;

@Service
public class VacanteServiceImp implements IVacanteService{

	private List <Vacante> listaVacantes = null;
	private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
	
	//constructor - los constructores no devuelven valor, solo lo inicializan
	public VacanteServiceImp() {
		
		listaVacantes = new LinkedList<Vacante>();
		
		try {	
			//Realizando Listas de vacantes
			Vacante vacante1 = new Vacante();
			vacante1.setId(1);
			vacante1.setNombre("Ingeniero en Sistemas");
			vacante1.setDescripcion("Se necesita un Programador en Spring Boot n.n");
			vacante1.setFechaPublicacion(formatoFecha.parse("01-01-2019"));
			vacante1.setSalario(25000.00);
			vacante1.setDestacado(1);
			vacante1.setImagen("e1.png");
			
			
			
			listaVacantes.add(vacante1);
			
			Vacante vacante2 = new Vacante();
			vacante2.setId(2);
			vacante2.setNombre("Administrador");
			vacante2.setDescripcion("Se necesita un Administrador de Proyectos PMI");
			vacante2.setFechaPublicacion(formatoFecha.parse("22-02-2019"));
			vacante2.setSalario(26000.00);
			vacante2.setDestacado(0);
			vacante2.setImagen("e2.png");
			vacante2.setEstatus("En espera");
			

			listaVacantes.add(vacante2);
		
			Vacante vacante3 = new Vacante();
			vacante3.setId(3);
			vacante3.setNombre("Scrum Master");
			vacante3.setDescripcion("Se necesita un Product Manager en base a Scrum");
			vacante3.setFechaPublicacion(formatoFecha.parse("15-04-2019"));
			vacante3.setSalario(30000.00);
			vacante3.setDestacado(0);
			vacante3.setEstatus("En espera");
			
			listaVacantes.add(vacante3);
			
			Vacante vacante4 = new Vacante();
			vacante4.setId(4);
			vacante4.setNombre("Product Owner - Kanban");
			vacante4.setDescripcion("Se necesita un Product Owner con conocimientos en Kanban");
			vacante4.setFechaPublicacion(formatoFecha.parse("15-06-2019"));
			vacante4.setSalario(33000.00);
			vacante4.setDestacado(1);
			vacante4.setImagen("e4.png");
			
			listaVacantes.add(vacante4);

			/*Imprimiendo lista de vacantes en consola - solo para verificar datos -*/
			
			Iterator<Vacante> iter = listaVacantes.iterator();
			
			while (iter.hasNext()) {
			  System.out.println(iter.next());
			}
		
			} catch (ParseException e) {
				
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
	}
	
	@Override
	public List<Vacante> buscarTodas() {
		
		return listaVacantes; //proveniente de nuestro constructor [que ya inicializó los valores]
	}

	@Override
	//buscarPorId() recibe un objeto de tipo Vacante y su función es recorre la lista de los objetos Vacante 'listaVacantes'
	//para comparalos y en caso de que un objeto Vacante sea igual al id esperado, obtiene sus propiedades, 
	//devolviéndolo en un objeto v, y caso contrario devuelve null
	public Vacante buscarPorId(Integer VacanteId) {

		for (Vacante v : listaVacantes){
			
			if (v.getId() == VacanteId) {
				
				return v;
			}
		}
		return null;
	}

	@Override
	/** agrega un objeto vacante y sus propiedades a una lista dinámica utilizando la función DataBinding de Spring*/
	public void guardar(Vacante vacante) {
		
		listaVacantes.add(vacante);
	}

	@Override
	public List<Vacante> buscarDestacadas() {
		
		return null;
	}

	@Override
	public void eliminar(int idVacante) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> exaVacante) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Vacante> buscarTodasPorPaginacion(Pageable pag) {
		// TODO Auto-generated method stub
		return null;
	}

}
