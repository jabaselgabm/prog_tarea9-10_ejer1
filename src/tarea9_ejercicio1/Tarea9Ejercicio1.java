package tarea9_ejercicio1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

import utilidades.Leer;

public class Tarea9Ejercicio1 {
	
	public void mostrarFicheroBytes (RandomAccessFile fichero) throws IOException {
		int n;
		String linea = "";
		char c;
		fichero.seek(0);
		
		try {
			while (true) {
					c = (char)fichero.read();
					System.out.print(c);
				}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	public void mostrarFichero (RandomAccessFile fichero) throws IOException {
		int n;
		String linea = "";
		fichero.seek(0);
		
		try {
			while ((linea = fichero.readLine()) != null) {
					System.out.print(linea + "\r\n");
				}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void reeplazarPalabra (RandomAccessFile fichero, String palabra) {
		int n;
		String linea = "";
		int posicionFichero = 0;
		try {
			fichero.seek(0); // nos situamos al principio del fichero
			
			while ((linea = fichero.readLine ()) != null) {
				if (linea.indexOf(palabra) != -1) { // palabra encontrada
					//System.out.println(linea.replace(palabra, palabra.toUpperCase()));
					fichero.seek(posicionFichero);
					fichero.writeBytes (linea.replace(palabra, palabra.toUpperCase()) + "\n");
				} else {
					//System.out.println(linea);
					fichero.writeBytes (linea + "\n");
				}
				posicionFichero += linea.length() + 1;
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	public void reeplazarPalabra2 (RandomAccessFile fichero, String palabra) {
		int n, c;
		StringBuilder linea = new StringBuilder ();
		int posicionFichero = 0;
		try {
			fichero.seek(0); // nos situamos al principio del fichero
			
			while ((c = fichero.read ()) != -1) {
					linea.append((char)c);
					posicionFichero++;
					
					if ((char) c == '\n' ) {  // hemos leido una linea
						//System.out.print(linea.toString());
						
						int indice = linea.indexOf(palabra); 

						while (indice != -1) {
						    linea.replace(indice, indice + palabra.length(), palabra.toUpperCase());
						    // Buscamos la siguiente aparición a partir de donde terminamos el reemplazo
						    indice = linea.indexOf(palabra, indice + palabra.length());
						}
						
						fichero.seek(posicionFichero - linea.length());
						for (int i = 0; i < linea.length(); i++) {
							System.out.print(linea.charAt(i));
							fichero.writeByte(linea.charAt(i));
						}
						linea.setLength(0);
					}
			}
			System.out.println("Fichero leido");
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		
		
		public void reeplazarPalabra3 (RandomAccessFile fichero, String palabra) {
			int n, c;
			char caracter;
			StringBuilder linea = new StringBuilder ();
			int posicionFichero = 0;
			boolean esPalabra = false;
			boolean comienzoPalabra = true;
			
		    int indicePalagra = 0;
			try {
				fichero.seek(0); // nos situamos al principio del fichero
				
				while ((c = fichero.read ()) != -1) {
					
						caracter = (char)c;
						linea.append(caracter);
						posicionFichero++;
						
						if (caracter == '\n' ) {  // hemos leido una linea
							//System.out.print(linea.toString());
							
							int indice = linea.indexOf(palabra); 

							while (indice != -1) {
								if ((indice == 0 && !Character.isLetter(linea.charAt(palabra.length()))) || 
									((!Character.isLetter(linea.charAt(indice - 1)) && !Character.isLetter(linea.charAt(indice + palabra.length()))))) {
									linea.replace(indice, indice + palabra.length(), palabra.toUpperCase());
									// Buscamos la siguiente aparición a partir de donde terminamos el reemplazo
								}
								indice = linea.indexOf(palabra, indice + palabra.length());
							}
							fichero.seek(posicionFichero - linea.length());
							for (int i = 0; i < linea.length(); i++) {
								System.out.print(linea.charAt(i));
								fichero.writeByte(linea.charAt(i));
							}
							linea.setLength(0);
						}
				}
				System.out.println("Fichero leido");
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		
	}

    public static void main(String[] args) throws IOException {
        
    	String palabra = "";
    	RandomAccessFile fichero = null;
    	Tarea9Ejercicio1 t9ej1 = new Tarea9Ejercicio1();
    	
    	
    	// Pedir por teclado 1 palabra
    	palabra = Leer.pedirCadena ("Introduce una palabra: ");
    	
    	try {
            //se abre el fichero para lectura y escritura
    		System.out.println("abriendo el fichero ...");
            fichero = new RandomAccessFile("ejer1.txt", "rw");
            // t9ej1.mostrarFichero(fichero); 
            
            t9ej1.reeplazarPalabra3(fichero, palabra);
            
            t9ej1.mostrarFichero(fichero);
            
           //t9ej1.mostrarFicheroBytes(fichero);
            
            
            
    		
    	} catch (FileNotFoundException ex) {
    		System.out.println(ex.getMessage());
    	} catch (IOException ex) {
    		System.out.println(ex.getMessage());
    	} finally {
    		if (fichero!= null) {
    			System.out.println("Cerrando el fichero ...");
    			fichero.close();
    		}
    		
		}
        

    }

}
