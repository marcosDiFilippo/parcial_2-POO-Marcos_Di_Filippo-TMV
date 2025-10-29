package CapaLogica;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

public class CuentaBancaria {
	private static int numeroCuentaBancaria = 0;
	private double saldo;
	private String alias;
	private String email;
	private String contrasenia;
	private Usuario usuario;
	private LocalDate fechaCreacion;
	private LinkedList <Movimiento> movimientos;
	
	public CuentaBancaria(Usuario usuario, String email, String contrasenia) {
		this.saldo = 0;
		this.usuario = usuario;
		this.fechaCreacion = LocalDate.now();
		this.movimientos = new LinkedList<Movimiento>();
		this.email = email;
		this.contrasenia = contrasenia;
		numeroCuentaBancaria++;
		this.alias = usuario.getNombre() + numeroCuentaBancaria;
	}
	
	public static CuentaBancaria crearCuentaBancaria(Usuario usuario, Banco banco) {
		String email;
		boolean emailVacio;
		boolean existeEmail = false;
		
		do {
			email = JOptionPane.showInputDialog("Ingrese su email");
			emailVacio = validarCamposVacios(email, "email");
			if (emailVacio == true) {
				continue;
			}
			if (!email.contains("@")) {
				JOptionPane.showMessageDialog(null, "El email no contiene @, por favor vuelva ingresar");
			}
			for (CuentaBancaria cuentaBancaria : banco.getCuentasBancarias()) {
				String emailCoincidente = cuentaBancaria.getEmail();
				if (email.equals(emailCoincidente)) {
					JOptionPane.showMessageDialog(null, "El email ingresado ya existe por favor ingrese otro");
					existeEmail = true;
					break;
				}
				else {
					existeEmail = false;
				}
			}
		} while (emailVacio == true || !email.contains("@") || existeEmail == true);
		
		String contrasenia;
		String contraseniaConfirmada = null;
		boolean esVacio;
		
		do {
			contrasenia = JOptionPane.showInputDialog("Ingrese una contrasenia");
			esVacio = validarCamposVacios(contrasenia, "contrasenia");
			
			if (esVacio == true) {
				continue;
			}
			
			contraseniaConfirmada = JOptionPane.showInputDialog("Ingrese nuevamente la contrasenia");
			if (!contraseniaConfirmada.equals(contrasenia)) {					
				JOptionPane.showMessageDialog(null, "Las contrasenias no coinciden por favor vuelva ingresar");
			}
		} while (contrasenia.isEmpty() || !contraseniaConfirmada.equals(contrasenia));
		
		return new CuentaBancaria(usuario, email, contrasenia);
	}
	
	
	public Movimiento depositarDinero() {
		String monto;
		boolean esVacio;
		boolean esNegativo = false;
		boolean tieneLetras = false;
		
		NombreMedio nombreMedio = (NombreMedio) JOptionPane.showInputDialog(null, "Elija el medio para realizar el deposito", "", 0, null, NombreMedio.values(), NombreMedio.values()[0]);

		do {
			monto = JOptionPane.showInputDialog("Ingrese el monto de deposito (solo numeros)");
			
			esVacio = validarCampoVacio(monto, "monto");
			if (esVacio == true) {
				continue;
			}
			
			tieneLetras = verificarLetrasMonto(monto);
			if (tieneLetras == true) {
				continue;
			}
			
			esNegativo = verificarMontoNegativo(Double.parseDouble(monto));
			if (esNegativo == true) {
				continue;
			}
		} while (tieneLetras == true || esVacio == true || esNegativo == true);
		
		String detalles = JOptionPane.showInputDialog("Desea agregar detalles sobre el deposito (opcional)");
		
		double comision = calcularComision(nombreMedio, Double.parseDouble(monto));
		
		return new Movimiento(incluirTernaria(detalles), Double.parseDouble(monto), Tipo_Movimiento.DEPOSITO, new MedioOperacion(nombreMedio, comision));
	}
	
	public void retirarDinero() {
		String monto = "";
		String detalles = "";
		
		boolean esVacio = false;
		boolean esNegativo = false;
		boolean tieneLetras = false;
		boolean montoMayorAlSaldo = false;
		
		boolean saldoNulo = verificarSaldo();
				
		if (saldoNulo == true) {
			return;
		}
		
		NombreMedio nombreMedio = (NombreMedio) JOptionPane.showInputDialog(null, "Elija el medio por donde va realizar el deposito", "", 0, null, NombreMedio.values(), NombreMedio.values()[0]);
		
		do {
			monto = JOptionPane.showInputDialog("Ingrese el monto a retirar de la cuenta");
			
			esVacio = validarCampoVacio(monto, "monto");
			if (esVacio == true) {
				continue;
			}
			tieneLetras = verificarLetrasMonto(monto);
			if (tieneLetras == true) {
				continue;
			}
			montoMayorAlSaldo = validarMontoMayorSaldo(Double.parseDouble(monto));
			if (montoMayorAlSaldo == true) {
				JOptionPane.showMessageDialog(null, "El monto ingresado es mayor al saldo, por favor vuelva ingresar");
				continue;
			}
			else {
				esNegativo = verificarMontoNegativo(Double.parseDouble(monto));
				if (esNegativo == true) {
					continue;
				}
			}
			
		} while (esNegativo == true || esVacio == true || tieneLetras == true || montoMayorAlSaldo == true);
		
		detalles = JOptionPane.showInputDialog("Desea agregar detalles sobre el deposito (opcional)");
		
		saldo = saldo - Double.parseDouble(monto);
		
		double comision = calcularComision(nombreMedio, Double.parseDouble(monto));
		
		this.movimientos.add(new Movimiento(incluirTernaria(detalles), Double.parseDouble(monto), Tipo_Movimiento.RETIRO, new MedioOperacion(nombreMedio, comision)));
	}
	
	public void transferirDinero(Banco banco) {
		if (verificarSaldo() == true) {
			return;
		}
		
		List <CuentaBancaria> cuentasBancarias = banco.getCuentasBancarias().stream()
			.filter(cuenta -> !cuenta.getAlias().equals(this.alias))
			.collect(Collectors.toList());
		
		String aliasBuscado;
		String monto = "";
		String detalles = "";
		boolean seEncontroAlias = false;
		boolean tieneLetras = false;
		boolean esMenorACero = false;
		boolean esVacio = false;
		CuentaBancaria cuentaTransferida = null;
		
		do {
			aliasBuscado = JOptionPane.showInputDialog("Ingrese el alias para realizar la transferencia");
			
			for (CuentaBancaria cuentaBancaria : cuentasBancarias) {
				if (!cuentaBancaria.getAlias().equals(aliasBuscado)) {
					seEncontroAlias = false;
				}
				else {
					cuentaTransferida = cuentaBancaria;
					seEncontroAlias = true;
					break;
				}
			}
			if (seEncontroAlias == false) {
				JOptionPane.showMessageDialog(null, "No se ha encontrado usuario correspondiente al alias: " + aliasBuscado + ", porfavor vuelva ingresar");
				continue;
			}
			do {
				monto = JOptionPane.showInputDialog("Ingrese el monto (solo numeros) para la transferencia hacia " + cuentaTransferida.getUsuario().getNombre() + " " + cuentaTransferida.getUsuario().getApellido() + "\nAlias: " + cuentaTransferida.getAlias());
				
				esVacio = validarCampoVacio(monto, "monto");
				if (esVacio == true) {
					continue;
				}
				
				esMenorACero = verificarMontoNegativo(Double.parseDouble(monto));
				if (esMenorACero == true) {
					continue;
				}
				
				tieneLetras = verificarLetrasMonto(monto);
				if (tieneLetras == true) {
					continue;
				}
			} while (esMenorACero == true || tieneLetras == true || Double.parseDouble(monto) > saldo);
			saldo = saldo - Double.parseDouble(monto);
			break;
		} while (seEncontroAlias == false);
		
		cuentaTransferida.actualizarSaldo(Double.parseDouble(monto));
		
		detalles = JOptionPane.showInputDialog("Desea agregar detalles sobre la transferencia (opcional)");
		
		this.movimientos.add(new Movimiento(incluirTernaria(detalles), Double.parseDouble(monto), Tipo_Movimiento.TRANSFERENCIA));
	}
	
	public double calcularComision(NombreMedio nombreMedio, double monto) {
		double comision = 0;
		double total = 0;
		
		if (nombreMedio == NombreMedio.RAPIPAGO) {
			comision = monto / 5;
		}
		else if (nombreMedio == NombreMedio.PAGOFACIL) {
			comision = monto / 10;
		}
		else {
			comision = monto / 15;
		}
		
		total = monto - comision;
		
		actualizarSaldo(total);
		
		return comision;
	}
	
	public String verMovimentos() {
		String string = "";
		
		for (Movimiento movimiento : movimientos) {
			string += "\n" + movimiento;
		}
		
		return string;
	}
	
	public String incluirTernaria(String string) {
		return string.isEmpty() ? "Ninguno" : string;
	}
	
	public boolean verificarLetrasMonto(String monto) {
		for (int i = 0; i < monto.length(); i++) {
			if (!Character.isDigit(monto.charAt(i))) {
				JOptionPane.showMessageDialog(null, "El monto no puede contener letras, por favor vuelva ingresar");
				return true;
			}
		}
		return false;
	}
	
	public boolean verificarMontoNegativo(double monto) {
		if (monto < 0) {
			JOptionPane.showMessageDialog(null, "El monto no puede ser menor a 0, por favor vuelva ingresar");
			return true;
		}
		return false;
	}
	
	public boolean validarMontoMayorSaldo(double monto) {
		if (monto > saldo) {
			return true;
		}
		return false;
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

	public boolean verificarSaldo() {
		if (saldo == 0) {
			JOptionPane.showMessageDialog(null, "El saldo de su cuenta es 0");
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Saldo: " + saldo 
				+ "\nAlias: " + alias 
				+ "\nEmail: " + email 
				+ "\nFecha creacion cuenta: " + fechaCreacion
				+ "\n--------------------------------------------------------------------------"
				+ "\n" + usuario.verInformacion() ;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	public void actualizarSaldo(double monto) {
		this.saldo += monto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LinkedList<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(LinkedList<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
	
	public String getContrasenia() {
		return contrasenia;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public static int getNumeroCuentaBancaria() {
		return numeroCuentaBancaria;
	}

	public static void setNumeroCuentaBancaria(int numeroCuentaBancaria) {
		CuentaBancaria.numeroCuentaBancaria = numeroCuentaBancaria;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
