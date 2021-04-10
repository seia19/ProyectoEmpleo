package com.eia.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class DataBaseWebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;// --> template.properties
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource) //config de autenticación mediante nuestro DataSource -Mysql&Jdbc-
														.usersByUsernameQuery("select username, password, estatus from Usuarios where username=?")
														.authoritiesByUsernameQuery("select u.username, p.perfil from UsuarioPerfil up " +
																					"inner join Usuarios u on u.id = up.idUsuario " +
																					"inner join Perfiles p on p.id = up.idPerfil " +
																					"where u.username = ?"); //mustra el usuario y sus roles de nuestra Tabla Usuario
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Los recursos estáticos y las vistas no requieren autenticación
		http.authorizeRequests().antMatchers(
											  "/bootstrap/**",
											  "/images/**",
											  "/tinymce/**",
											  "/logos/**").permitAll().antMatchers("/", "/signup","/search","/vacantes/view/**")
		
		.permitAll()
		
		// Asignar permisos a URLs por ROLES - El tipo de rol es casesensitive -
		.antMatchers("/vacantes/**").hasAnyAuthority("Supervisor")
		.antMatchers("/categorias/**").hasAnyAuthority("Supervisor")
		.antMatchers("/usuarios/**").hasAnyAuthority("Supervisor","Administrador")
		.antMatchers("/encrip/**").hasAnyAuthority("Supervisor","Administrador")
		
		// Todas las demás URLs de la Aplicación requieren autenticación
	.anyRequest().authenticated()
	// El formulario de Login (de spring-security) no requiere autenticacion
	.and().formLogin().loginPage("/login").permitAll();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		
		PasswordEncoder passEnc = new BCryptPasswordEncoder();//--> algoritmo BCrypt
		
		return passEnc;
	}
}
