package CapaLogica;

import java.time.LocalDate;
import java.time.Period;

import javax.swing.JOptionPane;

public class Usuario {

	private static int idUsuario = 0;
	private String nombre;
	private String apellido;
	private LocalDate fechaNacimiento;
	private String telefono;
	private String dni;
	private int edad;
	
	public Usuario(String nombre, String apellido, LocalDate fechaNacimiento, String telefono, String dni) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
		this.dni = dni;
		this.edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
	}
	
	public static Usuario crearUsuario(Banco banco) {
		LocalDate hoy = LocalDate.now();
		String nombre;
		String apellido;
		
		String anio;
		
		String telefono;
		String dni;
		
		boolean campoVacio;
		boolean existeDni = false;
		boolean existeTelefono = false;
		boolean fechaTieneLetras = false;
		boolean fechaDespuesDeHoy = false;
		
		do {
			nombre = JOptionPane.showInputDialog("Ingrese su nombre");
			campoVacio = validarCamposVacios(nombre, "nombre");
		} while (campoVacio == true);
		
		campoVacio = false;
		do {
			apellido = JOptionPane.showInputDialog("Ingrese su apellido");
			
			campoVacio = validarCamposVacios(apellido, "apellido");
		} while (campoVacio == true);
		
		campoVacio = false;
		do {
			anio = JOptionPane.showInputDialog("Ingrese su anio de nacimiento");
			
			campoVacio = validarCamposVacios(anio, "anio");
			if (campoVacio == true) {
				continue;
			}

			fechaTieneLetras = verificarLetrasCampos(anio, "anio");
			
			if (fechaTieneLetras == true) {
				continue;
			}
			
			if (hoy.getYear() < Integer.parseInt(anio)) {
				JOptionPane.showMessageDialog(null, "El anio ingresado es posterior al actual, por favor vuelva ingresar");
				fechaDespuesDeHoy = true;
				continue;
			}
		} while (campoVacio == true || fechaDespuesDeHoy == true || fechaTieneLetras == true);
		
		Mes mes = (Mes) JOptionPane.showInputDialog(null, "Elija su mes de nacimiento", "", 0, null, Mes.values(), Mes.values()[0]);
		
		for (Integer i = 0; i < mes.getDias().length; i++) {
			mes.getDias()[i] = String.valueOf(i + 1);
		}
		
		int dia = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Elija su dia de nacimiento", "", 0, null, mes.getDias(), mes.getDias()[0]));
		
		do {
			dni = JOptionPane.showInputDialog("Ingrese su dni");
			if (dni.isEmpty()) {
				JOptionPane.showMessageDialog(null, "El dni es obligatorio");
			}
			else {				
				if (Integer.parseInt(dni) < 0) {
					JOptionPane.showMessageDialog(null, "El dni ingresado es invalido (no puede ser menor a 0)");
				}
			}
			for (CuentaBancaria cuentaBancariaCoincidente : banco.getCuentasBancarias()) {
				String dniCoincidente = cuentaBancariaCoincidente.getUsuario().getDni();
				if (dni.equals(dniCoincidente)) {
					JOptionPane.showMessageDialog(null, "El dni ingresado ya existe");
					existeDni = true;
					break;
				}
				existeDni = false;
			}
		} while (dni.isEmpty() || Integer.parseInt(dni) < 0 || existeDni == true);
		
		do {
			telefono = JOptionPane.showInputDialog("Ingrese su numero de telefono");
			if (telefono.isEmpty()) {
				JOptionPane.showMessageDialog(null, "El telefono es obligatorio");
			}
			else {				
				if (Integer.parseInt(telefono) < 0) {
					JOptionPane.showMessageDialog(null, "El telefono ingresado es invalido (no puede ser menor a 0)");
				}
			}
			for (CuentaBancaria cuentaBancariaCoincidente : banco.getCuentasBancarias()) {
				String telefonoCoincidente = cuentaBancariaCoincidente.getUsuario().getTelefono();
				if (telefono.equals(telefonoCoincidente)) {
					JOptionPane.showMessageDialog(null, "El telefono ingresado ya existe");
					existeTelefono = true;
					break;
				}
				existeTelefono = false;
			}
		} while (telefono.isEmpty() || Integer.parseInt(telefono) < 0 || existeTelefono == true);
		
		return new Usuario(nombre, apellido, LocalDate.of(Integer.parseInt(anio), mes.getNumero(), dia), telefono, dni);
	}
	
	public static CuentaBancaria iniciarSesion(Banco banco) {
		if (banco.getCuentasBancarias().size() == 0) {
			JOptionPane.showMessageDialog(null, "No hay cuentas registradas");
			return null;
		}
		String email = JOptionPane.showInputDialog("Ingrese el email");
		String contrasenia = JOptionPane.showInputDialog("Ingrese la contrasenia");
		
		for (CuentaBancaria cuentaBancaria : banco.getCuentasBancarias()) {
			
			String emails = cuentaBancaria.getEmail();			
			String contrasenias = cuentaBancaria.getContrasenia();

			if (email.equals(emails) && contrasenia.equals(contrasenias)) {
				
				return cuentaBancaria;
			}
		}
		return null;
	}
	
	public boolean validarCampoVacio(String campo, String nombreCampo) {
		if (campo.isEmpty()) {
			JOptionPane.showMessageDialog(null, "El campo de " + nombreCampo + " es obligatorio, por favor vuelva ingresar");
			return true;
		}
		return false;
	}
	
	public static boolean validarCamposVacios(String campo, String nombreCampo) {
		if (campo.isEmpty()) {
			JOptionPane.showMessageDialog(null, "El campo de " + nombreCampo + " es obligatorio, por favor vuelva ingresar");
			return true;
		}
		return false;
	}
	
	public static boolean verificarLetrasCampos(String campo, String nombreCampo) {
		for (int i = 0; i < campo.length(); i++) {
			if (!Character.isDigit(campo.charAt(i))) {
				JOptionPane.showMessageDialog(null, "El " + nombreCampo + " no puede contener letras, por favor vuelva ingresar");
				return true;
			}
		}
		return false;
	}
	
	public String verInformacion() {
		return "Nombre Completo: " + nombre + " " + apellido 
				+ "\nFecha Nacimiento: " + fechaNacimiento
				+ "\nEdad: " + edad
				+ "\nDni: " + dni
				+ "\nTelefono: " + telefono;
	}

	public static int getIdUsuario() {
		return idUsuario;
	}

	public static void setIdUsuario(int idUsuario) {
		Usuario.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}
}
