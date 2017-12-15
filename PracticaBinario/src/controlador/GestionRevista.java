package controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.departamento.Departamento;
import modelo.revista.Articulo;
import modelo.revista.Autor;
import modelo.revista.Revista;

public class GestionRevista {
	private List<Revista> revistas;
	private List<Articulo> articulos;
	private List<Autor> autores;

	protected String pathRevistas = "PracticaBinario/src/archivos/Articulos.dat";
	private String pathArticulos = "PracticaBinario/src/archivos/Articulos.dat";

	public GestionRevista() {
		revistas = new ArrayList<Revista>();
		articulos = new ArrayList<Articulo>();
		autores = new ArrayList<Autor>();

	}

	public void agregarArticulo(String nombreA, String apellido, String nacionalidad, String temaAr, String idioma) {

		Autor au = new Autor();
		au.setNombre(nombreA);
		au.setApellido(apellido);
		au.setNacionalidad(nacionalidad);
		autores.add(au);

		Articulo ar = new Articulo();
		ar.setIdioma(idioma);
		ar.setTema(temaAr);
		ar.setAutor(au);
		articulos.add(ar);
		try {
			FileOutputStream file = new FileOutputStream(pathArticulos, true);
			DataOutputStream escr = new DataOutputStream(file);
			escr.writeUTF(nombreA);
			escr.writeUTF(apellido);
			escr.writeUTF(nacionalidad);
			escr.writeUTF(temaAr);
			escr.writeUTF(idioma);
			escr.close();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void agregarRevista(String nombreR, String editorial, Articulo ar) {
		try {
			Revista re = new Revista();
			re.setNombre(nombreR);
			re.setEditorial(editorial);
			re.setArticulo(ar);
			FileOutputStream file = new FileOutputStream(pathRevistas, true);
			DataOutputStream escr = new DataOutputStream(file);
			escr.writeUTF(nombreR);
			escr.writeUTF(editorial);
			
			escr.close();
			file.close();

			revistas.add(re);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Articulo> leerArticulo() throws IOException {
		
		String aux = "";
		FileInputStream lec = null;
		DataInputStream entrada = null;
		try {
			String ruta = pathArticulos;
			String line = "";
			lec = new FileInputStream(ruta);
			entrada = new DataInputStream(lec);
			while (true) {
				String nombre = entrada.readUTF();
				String apellido = entrada.readUTF();
				String nacionalidad = entrada.readUTF();
				String tema = entrada.readUTF();
				String idioma= entrada.readUTF();
				Articulo art = new Articulo();
				Autor au= new Autor();
				au.setNombre(nombre);
				au.setNacionalidad(nacionalidad);
				au.setApellido(apellido);
				art.setAutor(au);
				art.setIdioma(idioma);
				art.setTema(tema);
				articulos.add(art);

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			entrada.close();
		}
		return articulos;
	}
	public List<Autor> leerAutor() throws IOException {
		
		String aux = "";
		FileInputStream lec = null;
		DataInputStream entrada = null;
		try {
			String ruta = pathArticulos;
			String line = "";
			lec = new FileInputStream(ruta);
			entrada = new DataInputStream(lec);
			while (true) {
				String nombre = entrada.readUTF();
				String apellido = entrada.readUTF();
				String nacionalidad = entrada.readUTF();
				Autor au= new Autor();
				au.setNombre(nombre);
				au.setNacionalidad(nacionalidad);
				au.setApellido(apellido);
				autores.add(au);

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			entrada.close();
		}
		return autores;
	}

	// leer revistas
	public String leerRevista() throws IOException {
		String aux = "";
		FileInputStream lec = null;
		DataInputStream entrada = null;
		try {
			String ruta = pathRevistas;
			String line = "";
			lec = new FileInputStream(ruta);
			entrada = new DataInputStream(lec);
			while (true) {
				line = entrada.readUTF();
				aux = aux + " " + entrada.readUTF() + "\n";
				System.out.println(line);

			}
		} catch (FileNotFoundException e) {
			System.out.println("¡El fichero no existe!");
		} catch (IOException e) {
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
			entrada.close();
		}
		return aux;
	}

	// validar autor
	public boolean validarAutor(String nombre, String apellido, String nacionalidad) throws Exception {
		int n = 1;
		if (autores.size() > 0) {
			for (int i = 0; i < autores.size(); i++) {
				if (nombre.equals(autores.get(i).getNombre()) && apellido.equals(autores.get(i).getApellido())
						&& nacionalidad.equals(autores.get(i).getNacionalidad())) {
					n++;
				}
			}
			if (n > 1) {
				throw new Exception("El autor ya se encuentra inscrito");
			}
		}
		return true;
	}

	// metodo de validar articulo
	public boolean validarArticulo(String nombre, String idioma) throws Exception {
		int n = 1;
		if (articulos.size() > 0) {
			for (Articulo ar : articulos) {
				if (ar.getTema().equals(nombre) && ar.getTema().equals(idioma)) {
					n++;
				}
			}
			if (n > 1) {
				throw new Exception("El articulo ya se encuentra inscrito");
			}
		}
		return true;
	}

	// metodo para validar la revista
	public boolean validarRevista(String nombre, String editorial, Articulo articulo) throws Exception {
		int n = 1;
		if (revistas.size() > 0) {
			for (Revista r : revistas) {
				if (r.getNombre().equals(nombre) && r.getEditorial().equals(editorial)) {
					n++;
				}
			}
			if (n > 1) {
				throw new Exception("La revista ya se encuentra inscrita");
			}
		}
		return true;
	}
	// metodo para validar los espacion en blanco

	public boolean validarEspacio(String nombre, String nombre2, String nombre3, String nombre4, String nombre5)
			throws Exception {
		try {

		} catch (Exception e) {
			throw new Exception("Formato incorrecto, contiene caracteres");
		}
		if (nombre.equals("") || nombre2.equals("") || nombre3.equals("") || nombre4.equals("") || nombre5.equals("")) {
			throw new Exception("ERROR UN COMPONENTE SE ENCUENTRA VACIO");
		}
		return true;
	}
	// validar choose

	public boolean validarChoose(Articulo articulo) throws Exception {
		try {

		} catch (Exception e) {
			throw new Exception("Formato incorrecto, contiene caracteres");
		}
		if (articulo == null)
			throw new Exception("no ha escojidoun articulo");
		return true;
	}

	// validar
	public boolean validarEspacio1(String nombre, String nombre2) throws Exception {
		try {

		} catch (Exception e) {
			throw new Exception("Formato incorrecto, contiene caracteres");
		}
		if (nombre.equals("") || nombre2.equals("")) {
			throw new Exception("ERROR UN COMPONENTE SE ENCUENTRA VACIO");
		}
		return true;
	}

	public List<Revista> getRevistas() {
		return revistas;
	}

	public List<Articulo> getArticulos() {
		return articulos;
	}

	public List<Autor> getAutores() {
		return autores;
	}

}
