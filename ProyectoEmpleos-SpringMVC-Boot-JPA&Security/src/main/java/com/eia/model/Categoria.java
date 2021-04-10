package com.eia.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;





@Entity
@Table(name="categorias")
public class Categoria {
	
	

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY) //Id autoincremental. GenerationType cambia dependiendo de la BD - IDENTITY con MYSQL -
	private Integer id;
	//private Integer id=0;
	
	private String nombre;
	
	@Column(name="descripcion")
	private String descripcionCategoria;
	

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	public String getDescripcionCategoria() {
		return descripcionCategoria;
	}
	public void setDescripcionCategoria(String descripcionCategoria) {
		this.descripcionCategoria = descripcionCategoria;
	}
	@Override
	public String toString() {
		return "Categorias [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcionCategoria + "]";
	}
	
	
	
}
