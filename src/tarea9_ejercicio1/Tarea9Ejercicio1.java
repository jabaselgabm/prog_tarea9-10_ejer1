package tarea9_ejercicio1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import utilidades.Leer;

public class Tarea9Ejercicio1 {
	
	/**
	 * mostarFichero muestra en pantalla el contenido de un fichero con los metodos
	 * de acceso aleatorio. Para mostarlo iremos leyendo linea a linea.
	 * 
	 * @param fichero es el puntero al fichero que queremos mostraer
	 * @throws IOException devuelve una excepción si se produce un eror
	 */
	public void mostrarFichero(RandomAccessFile fichero) throws IOException {
		String linea = "";
		fichero.seek(0);	// nos posicionamos al principio del fichero
		
		// Protegemos el código por si se produce una excepción 
		try {
			// Leemos linea a linea y mostramos en pantalla.
			while ((linea = fichero.readLine()) != null) {
				System.out.print(linea + "\r\n");
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		// En este caso no lo cerramos porque nos viene de la ejcución principal
	}

	/**
	 * reemplazarPalabra, va leyendo de un fichero y reemplaza la palabra dada por
	 * la misma palabra en mayúsculas
	 * @param fichero es el puntunto al fichero con el objeto RadomAccessFile
	 * @param palabra es la palabra que vamos a buscar.
	 */
	public void reeplazarPalabra(RandomAccessFile fichero, String palabra) {
		int c;	// vamos a ir leyendo caracter a caracter, pero read () lee integer
		char caracter;  // que luego convertiremos a char
		StringBuilder linea = new StringBuilder(); // aquí iremos construyendo la linea que leemos
		int posicionFichero = 0;  // vamos guardando la posición del fichero, para saber donde tenemos que escribir.

		/* Protegemos la lectura-escritura en el fichero */
		try {
			fichero.seek(0); // nos situamos al principio del fichero

			// vamos leyendo caracter a caracter y el EOF nos lo da el -1 
			while ((c = fichero.read()) != -1) {

				caracter = (char) c;  // hacemos el cast del int leido a character
				linea.append(caracter); // lo añadimos al cadena
				posicionFichero++;	    // incrementamos en 1 la posición que hemos leido en el fichero

				if (caracter == '\n') { // hemos leido una linea
					// Ya hemos leido una linea así que procedemos a verificar si esta la palabra

					int indice = linea.indexOf(palabra); // con este metodo detectamos si esta la palabra

					// pero la palabra puede estar 1 o más veces o ninguna (-1)
					while (indice != -1) {
						// aquí esta la clave de la deteción de la palabra, tiene que tener algo que
						// no sea una letra por deleante y por detras.
						if ((indice == 0 && !Character.isLetter(linea.charAt(palabra.length())))  // por si es principio de linea
								|| ((!Character.isLetter(linea.charAt(indice - 1)) // cuando esta en medio o al final
										&& !Character.isLetter(linea.charAt(indice + palabra.length()))))) {
							linea.replace(indice, indice + palabra.length(), palabra.toUpperCase()); 
							// reemplazamos la palabra, por la palabra en mayusculas
						}
						// Buscamos la siguiente aparición a partir de donde terminamos el reemplazo
						indice = linea.indexOf(palabra, indice + palabra.length());
					}
					// como hemos encontrada una palabra, posicionamos el puntero justo al comienzo de la linea
					fichero.seek(posicionFichero - linea.length());
					for (int i = 0; i < linea.length(); i++) {
						// escribimos byte a byte linea
						fichero.write(linea.charAt(i));
					}
					// restablecemos o ponemos a cero la linea para volver a llenarla
					linea.setLength(0);
				}
			}
			System.out.println("------ Fichero procesado ------");
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Llamada principal.
	 */
	public static void main(String[] args) throws IOException {

		String palabra = "";		// la palabra a reemplazar
		RandomAccessFile fichero = null; // el fichero que usaremos
		// en modo acceso aleatorio como pide el enunciado
		
		// Instanciamos un objeto para poder ejecutar los metodos
		Tarea9Ejercicio1 t9ej1 = new Tarea9Ejercicio1();

		// Pedir por teclado 1 palabra
		palabra = Leer.pedirCadena("Introduce una palabra: ");

		try {
			// se abre el fichero para lectura y escritura
			System.out.println("------ Abriendo el fichero ...");
			fichero = new RandomAccessFile("ejer1.txt", "rw");

			// Mostramos el fichero antes de modificarlo.
			t9ej1.mostrarFichero(fichero);
			
			// Reemplazamos en fichero la palabra introducida
			t9ej1.reeplazarPalabra(fichero, palabra);

			// Lo mostramos en pantalla para ver el resultado.
			t9ej1.mostrarFichero(fichero);

		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception ex) {
			System.out.println("algo fue mal ...");
			System.exit(1);
		}
		finally {
			if (fichero != null) {
				System.out.println("------ Cerrando el fichero ...");
				fichero.close();
			}
		}
	}
}
