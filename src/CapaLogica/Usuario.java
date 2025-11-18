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
		String telefonoAux;
		String dni;
		String dniAux;
		
		boolean campoVacio = false;
		boolean existeDni = false;
		boolean existeTelefono = false;
		boolean tieneLetras = false;
		boolean fechaDespuesDeHoy = false;
		boolean esNumeroNegativo = false;
		boolean esMayorAlLimite = false;
		
		do {
			nombre = JOptionPane.showInputDialog("Ingrese su nombre");
			campoVacio = Validacion.validarCampoVacio(nombre, "nombre");
		} while (campoVacio == true);
		
		campoVacio = false;
		do {
			apellido = JOptionPane.showInputDialog("Ingrese su apellido");
			
			campoVacio = Validacion.validarCampoVacio(apellido, "apellido");
		} while (campoVacio == true);
		
		campoVacio = false;
		do {
			anio = JOptionPane.showInputDialog("Ingrese su anio de nacimiento");
			
			campoVacio = Validacion.validarCampoVacio(anio, "anio");
			if (campoVacio == true) {
				continue;
			}

			tieneLetras = Validacion.validarLetrasCampo(anio, "anio");
			if (tieneLetras == true) {
				continue;
			}
			
			if (hoy.getYear() < Integer.parseInt(anio)) {
				JOptionPane.showMessageDialog(null, "El anio ingresado es posterior al actual, por favor vuelva ingresar");
				fechaDespuesDeHoy = true;
				continue;
			}
			fechaDespuesDeHoy = false;
		} while (campoVacio == true || fechaDespuesDeHoy == true || tieneLetras == true);
		
		Mes mes = (Mes) JOptionPane.showInputDialog(null, "Elija su mes de nacimiento", "", 0, null, Mes.values(), Mes.values()[0]);
		
		for (Integer i = 0; i < mes.getDias().length; i++) {
			mes.getDias()[i] = String.valueOf(i + 1);
		}
		
		int dia = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Elija su dia de nacimiento", "", 0, null, mes.getDias(), mes.getDias()[0]));
		
		do {
			dniAux = JOptionPane.showInputDialog("Ingrese su dni");
			
			dni = dniAux.replaceAll("\\s+", "");
			
			campoVacio = Validacion.validarCampoVacio(dni, "dni");
			if (campoVacio == true) {
				continue;
			}
			
			tieneLetras = Validacion.validarLetrasCampo(dni, "dni");
			if (tieneLetras == true) {
				continue;
			}
			
			esMayorAlLimite = Validacion.validarTamanioCadena(dni, "dni", 10);
			if (esMayorAlLimite == true) {
				continue;
			}
			
			esNumeroNegativo = Validacion.verificarNumeroNegativo(Double.parseDouble(dni), "dni");
			if (esNumeroNegativo == true) {
				continue;
			}
			for (CuentaBancaria cuentaBancariaCoincidente : banco.getCuentasBancarias()) {
				String dniCoincidente = cuentaBancariaCoincidente.getUsuario().getDni();
				if (dniAux.equals(dniCoincidente)) {
					JOptionPane.showMessageDialog(null, "El dni ingresado ya existe");
					existeDni = true;
					break;
				}
				existeDni = false;
			}
		} while (campoVacio == true || esNumeroNegativo == true || existeDni == true || tieneLetras == true || esMayorAlLimite == true);
		
		do {
			telefonoAux = JOptionPane.showInputDialog("Ingrese su numero de telefono");
			
			telefono = telefonoAux.replaceAll("\\s+", "");
			
			campoVacio = Validacion.validarCampoVacio(telefono, "telefono");
			if (campoVacio == true) {
				continue;
			}
			
			tieneLetras = Validacion.validarLetrasCampo(telefono, "telefono");
			if (tieneLetras == true) {
				continue;
			}
			
			esNumeroNegativo = Validacion.validarLetrasCampo(telefono, "telefono");
			
			if (esNumeroNegativo == true) {
				continue;
			}
			
			esMayorAlLimite = Validacion.validarTamanioCadena(telefono, "telefono", 15);
			
			if (esMayorAlLimite == true) {
				continue;
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
		} while (campoVacio == true || esNumeroNegativo == true || existeTelefono == true || tieneLetras == true || esMayorAlLimite == true);
		
		int opcion = Validacion.confirmarIngreso();
		
		if (opcion == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(null, "Has cancelado la creacion de la cuenta");
			return null;
		} 
		
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
