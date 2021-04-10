package com.eia.config.recursosestaticos;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigImgs implements WebMvcConfigurer {

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		//agrega (de forma temporal) al directorio /images/* los recursos estáticos [imgs] que se encuentren en nuestra carpeta img-vacantes
		registry.addResourceHandler("/images/**").addResourceLocations("file:c:/empleos/img-vacantes/");
		
		
	}
}
