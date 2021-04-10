package com.eia.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.eia.model.Usuario;
import com.eia.repository.UsuariosRepository;
import com.eia.service.IUsuarioService;

@Service
public class UsuarioServiceJpa implements IUsuarioService {

	@Autowired
	private UsuariosRepository userRepository;
	
	@Override
	public void guardarUsuario(Usuario usuario) {
		
		userRepository.save(usuario);
	}

	@Override
	public void eliminarUsuario(Integer idUsuario) {
		
		userRepository.deleteById(idUsuario);
	}

	@Override
	public List<Usuario> buscarTodosUsuarios() {
		
		List<Usuario> listaUsuarios = userRepository.findAll();
		
		return listaUsuarios;
	}
	


	@Override
	public Usuario buscarUsuarioPorUserName(String username) {
		
		Usuario user = userRepository.findByUsername(username);

		return user;
	}

}
