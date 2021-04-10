package com.eia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eia.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

	public Usuario findByUsername(String username);
}
