package com.eia.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table (name="Usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nombre;
	private String email;
	private String username;
	private String password;
	private int estatus;
	private Date fechaRegistro;	
	 	
	
	@ManyToMany(fetch = FetchType.EAGER) //EAGER --> retrae toda la info de la BD
	@JoinTable(name="UsuarioPerfil",
			   joinColumns =  @JoinColumn(name="idUsuario"),
			   inverseJoinColumns = @JoinColumn(name="idPerfil"))
	private List<Perfil> perfiles;
	
	/** UsuarioPerfil --> nombre de la Tabla de la Bd
    joinColumns =  @JoinColumn(name="idUsuario") --> nombre del campo de la tabla UsuarioPerfil. Primero en declararse x que la relación @ManyToMany la declaramos en nuestra Clase Usuario
inverseJoinColumns = @JoinColumn(name="idPerfil") nombre del campo de la tabla UsuarioPerfil. debe ser la segunda en declararse 
SE DEBE RESPETAR ESTE ORDEN. primero se declara aquella llave foranea proveniente de la clase que declara el tipo de relación con joinColumns, 
y depués aquellas llaves foráneas de las otras Clases con inverseJoinColumns **/ 
	
	
	public void agregarPerfil_AlUsuario(Perfil tempPerfil) {
		
		if(perfiles == null) {//si no hay lista la crea y agrega el obj Perfil a la lista
			
			perfiles = new LinkedList<Perfil>();
			perfiles.add(tempPerfil);
			
		} else {
			
			perfiles.add(tempPerfil); //si hay lista de Perfiles, agrega el obj Perfil a la lista
			
		}
		
	}
	
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getEstatus() {
		return estatus;
	}
	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public List<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", username=" + username
				+ ", password=" + password + ", estatus=" + estatus + ", fechaRegistro=" + fechaRegistro + ", perfiles="
				+ perfiles + "]";
	}
	
	
	
}
