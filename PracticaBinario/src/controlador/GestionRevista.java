package controlador;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.revista.Articulo;
import modelo.revista.Autor;
import modelo.revista.Revista;

public class GestionRevista {
	private List<Revista> revistas;
	private List<Articulo> articulos;
	private List<Autor> autores;
    private  int c,c2;
    
	protected String pathRevistas = "PracticaBinario/src/archivos/Revistas.dat";
	private String pathArticulos = "PracticaBinario/src/archivos/Articulos.dat";
	private String pathAutores = "PracticaBinario/src/archivos/Autores.dat";

	public GestionRevista() {
		revistas = new ArrayList<Revista>();
		articulos = new ArrayList<Articulo>();
		autores = new ArrayList<Autor>();
		c=0;
		c2=0;

	}
	

	public void agregarRevista(String nombreR, String editorial, int codigo) {
		try {
			Revista re = new Revista();
			re.setNombre(nombreR);
			re.setEditorial(editorial);
			FileOutputStream file = new FileOutputStream(pathRevistas, true);
			DataOutputStream escr = new DataOutputStream(file);
			escr.writeUTF(nombreR);
			escr.writeUTF(editorial);
			escr.writeInt(codigo);
			escr.close();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Revista> leerRevista() throws IOException {

		FileInputStream lec = null;
		DataInputStream entrada = null;
		try {
			String ruta = pathRevistas;
			lec = new FileInputStream(ruta);
			entrada = new DataInputStream(lec);

			while (true) {

				String nombre = entrada.readUTF();
				String editorial = entrada.readUTF();
				int codigo = entrada.readInt();
				Revista re = new Revista();
				re.setNombre(nombre);
				re.setEditorial(editorial);
				re.setCodigo(codigo);

				revistas.add(re);

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			entrada.close();
		}
		return revistas;
	}

	public void agregarArticulo(String tema, String pagina, int codigo, int codigoR) {
		c=codigoR;
		try {
			FileOutputStream file = new FileOutputStream(pathArticulos, true);
			DataOutputStream escr = new DataOutputStream(file);

			Articulo ar = new Articulo();
			ar.setCodigo(codigo);
			ar.setTema(tema);
			ar.setPagina(pagina);

			escr.writeUTF(tema);
			escr.writeUTF(pagina);
			escr.writeInt(codigo);
			for (int i = 0; i < revistas.size(); i++) {
				if (codigoR == revistas.get(i).getCodigo()) {
					ar.setRevistas(revistas.get(i));
					escr.writeUTF(revistas.get(i).getNombre());
				}
			}

			escr.close();
			file.close();

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
				String tema = entrada.readUTF();
				String pagina = entrada.readUTF();
				int codigo = entrada.readInt();
				String revista = entrada.readUTF();

				Articulo art = new Articulo();
				art.setTema(tema);
				art.setPagina(pagina);
				art.setCodigo(codigo);

				for(int i=0;i<revistas.size();i++){
					if(c==revistas.get(i).getCodigo()){
						art.setRevistas(revistas.get(i));
					}
				}
				articulos.add(art);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			entrada.close();
		}
		return articulos;
	}

	public void agregarAutor(String nombre, String apellido, String nacionalidad, int codigoAr) {
		try {
			c2=codigoAr;
			Autor au = new Autor();
			au.setNombre(nombre);
			au.setNacionalidad(nacionalidad);
			au.setApellido(apellido);

			FileOutputStream file = new FileOutputStream(pathAutores, true);
			DataOutputStream escr = new DataOutputStream(file);
			escr.writeUTF(nombre);
			escr.writeUTF(apellido);
			escr.writeUTF(nacionalidad);

			for (Articulo ar : articulos) {
				if (ar.getCodigo() == articulos.get(0).getCodigo()) {
					au.setArticulo(ar);
					escr.writeUTF(articulos.get(0).getTema());
				}
			}

			escr.close();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
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
				Autor au = new Autor();
				au.setNombre(nombre);
				au.setNacionalidad(nacionalidad);
				au.setApellido(apellido);
				
				for(int i=0;i<articulos.size();i++){
					if(c2==articulos.get(i).getCodigo()){
					au.setArticulo(articulos.get(i));
					}
				}
				autores.add(au);

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			entrada.close();
		}
		return autores;
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
	public boolean validarArticulo(String nombre, int codigo) throws Exception {
		int n = 1;
		if (articulos.size() > 0) {
			for (Articulo ar : articulos) {
				if (ar.getTema().equals(nombre) && ar.getCodigo() == codigo) {
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
	public boolean validarRevista(String nombre, String editorial, int codigo) throws Exception {
		int n = 1;
		if (revistas.size() > 0) {
			for (Revista r : revistas) {
				if (r.getNombre().equals(nombre) && r.getEditorial().equals(editorial) && codigo == r.getCodigo()) {
					n++;
				}
			}
			if (n > 1) {
				throw new Exception("La revista ya se encuentra inscrita");
			}
		}
		return true;
	}

	// validar codigo articulo
	public boolean validarCodigoArticulo(int codigo) throws Exception {
		int n = 1;
		if (articulos.size() > 0) {
			for (Articulo ar : articulos) {
				if (ar.getCodigo() == codigo) {
					n++;
				}
			}
			if (n == 1) {
				throw new Exception("El Articulo no existe");
			}
		}
		return true;
	}

	// validar codigo revista
	public boolean validarCodigoRevista(int codigo) throws Exception {
		int n = 1;
		if (revistas.size() > 0) {
			for (Revista re : revistas) {
				if (re.getCodigo() == codigo) {
					n++;
				}
			}
			if (n == 1) {
				throw new Exception("La revista no existe");
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
