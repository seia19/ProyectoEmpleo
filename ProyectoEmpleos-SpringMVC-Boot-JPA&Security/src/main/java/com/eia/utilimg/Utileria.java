package com.eia.utilimg;

import java.io.*;

import org.springframework.web.multipart.MultipartFile;

public class Utileria {

	public static String guardarArchivoImg(MultipartFile multipart, String ruta) {
		
		String nombreImg = multipart.getOriginalFilename();
		
		/** Reemplazando espacios del nombre de la imagen por guiones */
		nombreImg = nombreImg.replace(" ", "-");
		try {
	
			//Formamos el nombre del archvio junto con su ruta de dirección, para posteriormente guardarlo	
			File imageFile = new File(ruta + nombreImg); 
			System.out.println("Archivo: " + imageFile.getAbsolutePath());
			
			// Alamacenamos físicamente dicha imagen en el disco duro
			multipart.transferTo(imageFile);			
			System.out.println("La imagen: " + nombreImg + " se almacenó correctamente");
			
			return nombreImg;
		}
		
		catch (IOException e){
			System.out.println("error al intentar cargar la dirección o nombre de la imagen ¬¬': " + e.getMessage());
			return null;
		}
		
	}
}
