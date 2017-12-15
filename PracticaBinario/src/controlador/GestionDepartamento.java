package controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.departamento.Departamento;
import modelo.departamento.Empleado;
import modelo.departamento.Empresa;
import modelo.revista.Articulo;
import modelo.revista.Autor;
import modelo.revista.Revista;

public class GestionDepartamento {
	private List<Empresa> empresas;
	private List<Departamento> departamentos;
	private List<Empleado> empleados;
	private String pathEmpresa = "src/archivos/empresa.txt";
	private String pathDepartamento = "src/archivos/Departamento.txt";

	public GestionDepartamento() {
		empresas = new ArrayList<Empresa>();
		departamentos = new ArrayList<Departamento>();
		empleados = new ArrayList<Empleado>();
	}
//metodo para agragar datos del departamento y de el trabajador
	public void agregarDepartamento(String nombreEm, String apellidoEm, String cedula, String nombreDepa, String codigo) {
		try {
			Empleado em = new Empleado();
			em.setNombreEm(nombreEm);
			em.setApellidoEm(apellidoEm);
			em.setCedula(cedula);
			empleados.add(em);

			Departamento depa = new Departamento();
			depa.setNombredepa(nombreDepa);
			depa.setCodigo(codigo);
			depa.setEmpleados(em);
			departamentos.add(depa);

			FileWriter file = new FileWriter(pathDepartamento,true);
			BufferedWriter escr = new BufferedWriter(file);
			String registro = "Nombre del departamento :"+depa.getNombredepa()+" codigo del departamento :"+depa.getCodigo()+" "+depa.getEmpleados();
			escr.append(registro + "\n");
			escr.close();
			file.close();

		} catch (Exception e) {
		}
	}
//metodo para agragar empresa
	public void agregarEmpresa(String nombre, String ruc, String direccion, Departamento departamento) {
		try {
			Empresa emp = new Empresa();
			emp.setNombre(nombre);
			emp.setRuc(ruc);
			emp.setDireccion(direccion);
			emp.setDepartamentos(departamento);
			empresas.add(emp);
			FileWriter file = new FileWriter(pathEmpresa, true);
			BufferedWriter escr = new BufferedWriter(file);
			String registro = emp.getNombre() + " " + emp.getRuc() + " " + emp.getDireccion() + " "
					+ emp.getDepartamentos();
			escr.append(registro);
			escr.close();
			file.close();

		} catch (Exception e) {
		}
	}

	//metodo para validar error del choose
	public boolean isChoose(Departamento departamento) throws Exception{
		try {
			
		}catch(Exception e){
			throw new Exception("Formato incorrecto, contiene caracteres");
		}
		if(departamento==null )
			throw new Exception("NO A LLENADO EL DEPARTAMENTO");
		return true;
	}
	//metodo de validacion de espacios en blanco
	public boolean isEsenci(String nombre,String ruc,String direccion) throws Exception{
		try {
			
		}catch(Exception e){
			throw new Exception("Formato incorrecto, contiene caracteres");
		}
		if(nombre.equals("")|| ruc.equals("") || direccion.equals(""))
			throw new Exception("ERROR UN COMPONENTE SE ENCUENTRA VACIO");
		return true;
	}
	//validacion de cedula
	public boolean isCedulaValida(String cedula) throws Exception{
		try {
			int a = Integer.parseInt(cedula);
		}catch(NumberFormatException e){
			throw new Exception("Formato incorrecto, contiene caracteres");
		}
		if(cedula.length()!=10)
			throw new Exception("Debe ser de 10 dígitos");
		
		return true;
	}
	//validacion de espacios vacios
	public boolean isEsenci2(String nombreEm, String apellidoEm, String cedula, String nombreDepa, String codigo) throws Exception{
		try {
			
		}catch(Exception e){
			throw new Exception("Formato incorrecto, contiene caracteres");
		}
		if(nombreEm.equals("")|| apellidoEm.equals("") || cedula.equals("")|| nombreDepa.equals("")|| codigo.equals(""))
			throw new Exception("ERROR UN COMPONENTE SE ENCUENTRA VACIO");
		return true;
	}
	

	
	public List<Empresa> getEmpresas() {
		return empresas;
	}
	public List<Departamento> getDepartamentos() {
		return departamentos;
	}
	public List<Empleado> getEmpleados() {
		return empleados;
	}
	public String leerDepartamento() throws IOException {
		String aux = "";
		try {
			FileReader l = new FileReader(pathDepartamento);
			BufferedReader es = new BufferedReader(l);
			String linea = "";
			while (linea != null) {
				linea = es.readLine();
				aux = aux + "" + linea + "\n";

			}
			es.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return aux;

	}
	
	public String leerEmpresa() throws IOException {
		String aux = "";
		try {
			FileReader l = new FileReader(pathEmpresa);
			BufferedReader es = new BufferedReader(l);
			String linea = "";
			while (linea != null) {
				linea = es.readLine();
				aux = aux + "" + linea + "\n";

			}
			es.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return aux;

	}
	
}
