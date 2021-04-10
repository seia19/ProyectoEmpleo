package com.eia.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name="Vacantes")
public class Vacante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Id autoincremental. GenerationType - IDENTITY (Mysql)
	private Integer id;
	private String nombre;
	private String descripcion;
	
	@Column(name="fecha")
	private Date fechaPublicacion;
	
	
	private Double salario;
	
	private Integer destacado;
	private String imagen="no-image.png";
	private String estatus="Aprobada";
	
	@Column(name="detalles")
	private String detalle;
	
	//@Transient //indica que el mapeo de objetos no existentes en la tabla sean ignorados
	@OneToOne
	@JoinColumn(name="idCategoria") //idCategoria es el nombre de la llave foránea en la BD
	private Categoria categoria;
	

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

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}
	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	public Double getSalario() {
		return salario;
	}
	public void setSalario(Double salario) {
		this.salario = salario;
	}
	
	public Integer getDestacado() {
		return destacado;
	}
	public void setDestacado(Integer destacado) {
		this.destacado = destacado;
	}
	
	
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String status) {
		this.estatus = status;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public void resetImg() {
		this.imagen = null;
	}
	
	public void resetEstatus() {
		this.estatus=null;
	}
	

	
	@Override
	public String toString() {
		return "Vacante [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fechaPublicacion="
				+ fechaPublicacion + ", salario=" + salario + ", destacado=" + destacado + ", imagen=" + imagen
				+ ", estatus=" + estatus + ", detalle=" + detalle + ", categoria=" + categoria + "]";
	}
	
	
	

	
	
}
