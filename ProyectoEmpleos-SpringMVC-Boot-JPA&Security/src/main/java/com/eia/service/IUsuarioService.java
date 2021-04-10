package com.eia.service;

import java.util.List;

import com.eia.model.Usuario;

public interface IUsuarioService {

	public void guardarUsuario(Usuario usuario);
	
	public void eliminarUsuario(Integer idUsuario);
	
	public List<Usuario> buscarTodosUsuarios();
	
	public Usuario buscarUsuarioPorUserName(String username);
}
