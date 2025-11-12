package CapaLogica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

public abstract class CuentaBancaria {
	protected static int numeroCuentaBancaria = 0;
	protected double saldo;
	protected String alias;
	protected String email;
	protected String contrasenia;
	protected Usuario usuario;
	protected LocalDate fechaCreacion;
	protected LinkedList <Movimiento> movimientos;
	protected LinkedList <CuentaBancaria> contactos;
	protected Rol rol;
	protected static ArrayList <String> notificacionesGenerales = new ArrayList<String>();
	protected ArrayList <String> notificacionesPropias;
	
	public CuentaBancaria(Usuario usuario, String email, String contrasenia) {
		this.saldo = 0;
		this.usuario = usuario;
		this.fechaCreacion = LocalDate.now();
		this.movimientos = new LinkedList<Movimiento>();
		this.contactos = new LinkedList<CuentaBancaria>();
		this.email = email;
		this.contrasenia = contrasenia;
		numeroCuentaBancaria++;
		this.alias = usuario.getNombre() + numeroCuentaBancaria;
		this.notificacionesPropias = new ArrayList<String>();
	}
	
	//constructor para cuando un admin cambia el rol de una cuenta
	public CuentaBancaria(CuentaBancaria cuentaBancaria) {
		this.saldo = cuentaBancaria.getSaldo();
		this.usuario = cuentaBancaria.getUsuario();
		this.fechaCreacion = cuentaBancaria.getFechaCreacion();
		this.movimientos = cuentaBancaria.getMovimientos();
		this.contactos = cuentaBancaria.getContactos();
		this.email = cuentaBancaria.getEmail();
		this.contrasenia = cuentaBancaria.getContrasenia();
		this.alias = cuentaBancaria.getAlias();
		this.rol = cuentaBancaria.getRol();
		this.notificacionesPropias = cuentaBancaria.getNotificacionesPropias();
	}
	
	public abstract void realizarOpcionesCuenta(Banco banco);
	
	public static String ingresarEmail(Banco banco) {
		String email; 
		boolean emailVacio;
		boolean existeEmail = false;
		
		do {
			email = JOptionPane.showInputDialog("Ingrese el email");
			emailVacio = Validacion.validarCampoVacio(email, "email");
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
		
		return email;
	}
	
	public static String ingresarContrasenia() {
		String contrasenia;
		String contraseniaConfirmada = null;
		boolean esVacio;
		
		do {
			contrasenia = JOptionPane.showInputDialog("Ingrese la contrasenia");
			esVacio = Validacion.validarCampoVacio(contrasenia, "contrasenia");
			
			if (esVacio == true) {
				continue;
			}
			
			contraseniaConfirmada = JOptionPane.showInputDialog("Ingrese nuevamente la contrasenia");
			if (!contraseniaConfirmada.equals(contrasenia)) {					
				JOptionPane.showMessageDialog(null, "Las contrasenias no coinciden por favor vuelva ingresar");
			}
		} while (contrasenia.isEmpty() || !contraseniaConfirmada.equals(contrasenia));
		
		return contrasenia;
	}
	
	public String cambiarEmail(Banco banco) {
		String email; 
		boolean emailVacio;
		boolean existeEmail = false;
		
		String emailActual = this.email;
		
		do {
			email = JOptionPane.showInputDialog("Ingrese el nuevo email");
			emailVacio = Validacion.validarCampoVacio(email, "email");
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
		
		this.notificacionesPropias.add("Has cambiado el email de " + emailActual + ", a " + email + " | " + LocalDate.now());
		
		return email;
	}
	
	public String cambiarContrasenia() {
		String contrasenia;
		String contraseniaConfirmada = null;
		boolean esVacio;
		
		do {
			contrasenia = JOptionPane.showInputDialog("Ingrese una nueva contrasenia");
			esVacio = Validacion.validarCampoVacio(contrasenia, "contrasenia");
			
			if (esVacio == true) {
				continue;
			}
			
			contraseniaConfirmada = JOptionPane.showInputDialog("Ingrese nuevamente la contrasenia");
			if (!contraseniaConfirmada.equals(contrasenia)) {					
				JOptionPane.showMessageDialog(null, "Las contrasenias no coinciden por favor vuelva ingresar");
			}
		} while (contrasenia.isEmpty() || !contraseniaConfirmada.equals(contrasenia));
		
		this.notificacionesPropias.add("Has cambiado la contrasenia | " + LocalDate.now());
		
		return contrasenia;
	}
	
	public boolean validarEmailActual() {
		String email = JOptionPane.showInputDialog("Ingrese su email actual para poder cambiarlo");
		if (email.equals(this.email)) {
			JOptionPane.showMessageDialog(null, "El email ingresado es correcto");
			return true;
		}
		JOptionPane.showMessageDialog(null, "El email ingresado es incorrecto");
		return false;
	}
	
	public boolean validarContraseniaActual() {
		String contrasenia = JOptionPane.showInputDialog("Ingrese su contrasenia actual para poder cambiarla");
		if (contrasenia.equals(this.contrasenia)) {
			JOptionPane.showMessageDialog(null, "La contrasenia ingresada es correcto");
			return true;
		}
		JOptionPane.showMessageDialog(null, "La contrasenia ingresada es incorrecto");
		return false;
	}
	
	public static CuentaBancaria crearCuentaBancaria(Usuario usuario, Banco banco) {
		String email = ingresarEmail(banco);
		
		String contrasenia = ingresarContrasenia();
		
		return new CuentaCliente(usuario, email, contrasenia);
	}
	
	public Movimiento depositarDinero() {
		if (Cajero.getCajeros().size() == 0) {
			JOptionPane.showMessageDialog(null, "Lo sentimos no hay cajeros disponibles, vuelva a intentar mas tarde");
			return null;
		}
		
		String monto;
		boolean esVacio;
		boolean esNegativo = false;
		boolean tieneLetras = false;
		boolean mayorAlLimite = false;
		boolean estaEnRango = false;
		String ubicacionCajero = (String) JOptionPane.showInputDialog(null, "Ingrese el cajero en el cual se realizara el deposito", "", 0, null, Cajero.incluirCajeros(), Cajero.incluirCajeros()[0]);
		
		Cajero cajeroSeleccionado = null;
		
		for (Cajero cajero : Cajero.getCajeros()) {
			if (cajero.getUbicacion().equals(ubicacionCajero)) {
				cajeroSeleccionado = cajero;
				break;
			}
		}
		
		do {
			monto = JOptionPane.showInputDialog("Ingrese el monto "
					+ "de deposito (solo numeros)");
			esVacio = Validacion.validarCampoVacio(monto, "monto");
			if (esVacio == true) {
				continue;
			}
			
			tieneLetras = Validacion.validarLetrasCampo(monto, "monto");
			if (tieneLetras == true) {
				continue;
			}
			
			esNegativo = verificarMontoNegativo(Double.parseDouble(monto));
			if (esNegativo == true) {
				continue;
			}
			
			if (Double.parseDouble(monto) == 0 || Double.parseDouble(monto) < 1000) {
				JOptionPane.showMessageDialog(null, "El monto a ingresar tiene que ser por lo menos mayor o igual a 1000");
				estaEnRango = false;
				continue;
			}
			else {
				estaEnRango = true;
			}
			
			mayorAlLimite = validarDeposito(monto);
			if (mayorAlLimite == true) {
				continue;
			}
		} while (tieneLetras == true || esVacio == true || esNegativo == true || mayorAlLimite == true || estaEnRango == false);
		
		String detalles = JOptionPane.showInputDialog("Desea agregar detalles sobre el deposito (opcional)");
		
		double comision = 0;
		double total = 0;
		
		if (Double.parseDouble(monto) > 100000) {
			comision = calcularComision(cajeroSeleccionado.getMedioOperacion().getNombreMedio(), Double.parseDouble(monto));

			total = Double.parseDouble(monto) - comision;
		}
		if (total > 0) {
			JOptionPane.showMessageDialog(null, "El monto depositado --" + Double.parseDouble(monto) + "-- es mayor a 100000, asique se ha aplicado una comision de $" + comision + "\nTotal: " + total);			
		}
		else {
			total = Double.parseDouble(monto);
			JOptionPane.showMessageDialog(null, "El monto depositado --" + Double.parseDouble(monto) + "-- es menor a 100000, asique no se ha aplicado ninguna comision" 
			+ "\nTotal: " + total);	
		}
		
		cajeroSeleccionado.sumarSaldo(Double.parseDouble(monto) + comision);
		
		actualizarSaldo(total);
		
		Movimiento movimiento = new Movimiento(incluirTernaria(detalles), Double.parseDouble(monto), Tipo_Movimiento.DEPOSITO);
		
		cajeroSeleccionado.agregarMovimientos(movimiento);
		
		return movimiento;
	}
	
	public boolean validarDeposito(String campo) {
		if (Double.parseDouble(campo) > 200000) {
			JOptionPane.showMessageDialog(null, "El monto ingresado es mayor al limite (200000), por favor vuelva ingresar");
			return true;
		}
		return false;
	}
	
	public Movimiento retirarDinero() {
		boolean saldoNulo = verificarSaldo();
		
		if (saldoNulo == true) {
			return null;
		}

		if (Cajero.getCajeros().size() == 0) {
			JOptionPane.showMessageDialog(null, "Lo sentimos no hay cajeros disponibles, vuelva a intentar mas tarde");
			return null;
		}
		
		String ubicacionCajero = (String) JOptionPane.showInputDialog(null, "Ingrese el cajero en el cual se realizara el retiro", "", 0, null, Cajero.incluirCajeros(), Cajero.incluirCajeros()[0]);
		
		Cajero cajeroSeleccionado = null;
		
		for (Cajero cajero : Cajero.getCajeros()) {
			if (cajero.getUbicacion().equals(ubicacionCajero)) {
				cajeroSeleccionado = cajero;
				break;
			}
		}
		
		String monto = "";
		String detalles = "";
		
		boolean esVacio = false;
		boolean esNegativo = false;
		boolean tieneLetras = false;
		boolean esMayorAlSaldo = false;
		boolean esMontoMayorSaldo = false;
		
		
		do {
			monto = JOptionPane.showInputDialog("Ingrese el monto a retirar de la cuenta");
			
			esVacio = Validacion.validarCampoVacio(monto, "monto");
			if (esVacio == true) {
				continue;
			}
			
			tieneLetras = Validacion.validarLetrasCampo(monto, "monto");
			if (tieneLetras == true) {
				continue;
			}
			else {
				esNegativo = verificarMontoNegativo(Double.parseDouble(monto));
				if (esNegativo == true) {
					continue;
				}
			}
			
			esMayorAlSaldo = cajeroSeleccionado.validarMontoMayorSaldo(Double.parseDouble(monto));
			if (esMayorAlSaldo == true) {
				continue;
			}
			
			esMontoMayorSaldo = validarMontoMayorSaldo(Double.parseDouble(monto));
			if (esMontoMayorSaldo == true) {
				continue;
			}
			
		} while (esNegativo == true || esVacio == true || tieneLetras == true || esMayorAlSaldo == true || esMontoMayorSaldo == true);
		
		detalles = JOptionPane.showInputDialog("Desea agregar detalles sobre el deposito (opcional)");
		
		restarSaldo(Double.parseDouble(monto));
		
		cajeroSeleccionado.restarSaldo(Double.parseDouble(monto));
		
		Movimiento movimiento = new Movimiento(incluirTernaria(detalles), Double.parseDouble(monto), Tipo_Movimiento.RETIRO);
		
		cajeroSeleccionado.agregarMovimientos(movimiento);
		
		return movimiento;
	}
	
	public String[] incluirContactos() {
		String [] contactos = new String[this.contactos.size()];
		
		for (int i = 0; i < contactos.length; i++) {
			String nombre = this.contactos.get(i).getUsuario().getNombre();
			String apellido = this.contactos.get(i).getUsuario().getApellido();
			String alias = this.contactos.get(i).getAlias();
			contactos[i] = nombre + " " + apellido + " - Alias: " + alias;
		}
		
		return contactos;
	}
	
	public Movimiento transferirDinero(Banco banco) {
		if (verificarSaldo() == true) {
			return null;
		}
		String [] contactos = incluirContactos();
		
		String [] opciones = {"Lista contactos", "Por alias"};
		
		List <CuentaBancaria> cuentasBancarias = banco.filtrarCuentas(this.alias);

		CuentaBancaria cuentaTransferida = null;
		String monto = "";
		boolean esMenorACero = false;
		boolean esVacio = false;
		boolean tieneLetras = false;
		boolean montoMayorSaldo = false;
		String detalles = "";
		Movimiento transferencia = null;	
		
		int opcionElegida = JOptionPane.showOptionDialog(null, "Como quiere realizar la transferencia?", "", 0, 0, null, opciones, opciones[0]);
		
		if (opcionElegida == 0) {
			if (contactos.length == 0) {
				JOptionPane.showMessageDialog(null, "No tienes un historial de contactos");
				return null;
			}
			String cuentaString = (String) JOptionPane.showInputDialog(null, "Contactos", "", 0, null, contactos, contactos[0]);
			
			for (CuentaBancaria contacto : this.contactos) {
				String nombre = contacto.getUsuario().getNombre();
				String apellido = contacto.getUsuario().getApellido();
				String alias = contacto.getAlias();
				String usuario = nombre + " " + apellido + " - Alias: " + alias;
				
				if (cuentaString.equals(usuario)) {
					cuentaTransferida = contacto;
					do {
						monto = JOptionPane.showInputDialog("Ingrese el monto (solo numeros) para la transferencia hacia " + cuentaTransferida.getUsuario().getNombre() + " " + cuentaTransferida.getUsuario().getApellido() + "\nAlias: " + cuentaTransferida.getAlias());
						
						esVacio = Validacion.validarCampoVacio(monto, "monto");
						if (esVacio == true) {
							continue;
						}
						
						esMenorACero = verificarMontoNegativo(Double.parseDouble(monto));
						if (esMenorACero == true) {
							continue;
						}
						
						montoMayorSaldo = validarMontoMayorSaldo(Double.parseDouble(monto));
						if (montoMayorSaldo == true) {
							continue;
						}
					} 
					while (esVacio == true || esMenorACero == true || montoMayorSaldo == true);
					saldo = saldo - Double.parseDouble(monto);
					cuentaTransferida.actualizarSaldo(Double.parseDouble(monto));
					
					detalles = JOptionPane.showInputDialog("Desea agregar detalles sobre la transferencia (opcional)");
					
					transferencia = new Movimiento(incluirTernaria(detalles), Double.parseDouble(monto), Tipo_Movimiento.TRANSFERENCIA); 
					
					cuentaTransferida.getMovimientos().add(new Movimiento(incluirTernaria(detalles), Double.parseDouble(monto), Tipo_Movimiento.TRANSFERENCIA_RECIBIDA));
					
					break;
				}
			}
			
			return transferencia;
		}
		
		String aliasBuscado;
		boolean seEncontroAlias = false;
		
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
				
				esVacio = Validacion.validarCampoVacio(monto, "monto");
				if (esVacio == true) {
					continue;
				}

				tieneLetras = Validacion.validarLetrasCampo(monto, "monto");
				if (tieneLetras == true) {
					continue;
				}
				
				esMenorACero = Validacion.verificarNumeroNegativo(Double.parseDouble(monto), "monto");
				if (esMenorACero == true) {
					continue;
				}
				
				montoMayorSaldo = validarMontoMayorSaldo(Double.parseDouble(monto));
				if (montoMayorSaldo == true) {
					continue;
				}
			} while (esMenorACero == true || tieneLetras == true || montoMayorSaldo == true || esVacio == true);
			
			this.restarSaldo(Double.parseDouble(monto));
			
			break;
		} while (seEncontroAlias == false);
		
		cuentaTransferida.actualizarSaldo(Double.parseDouble(monto));
		
		detalles = JOptionPane.showInputDialog("Desea agregar detalles sobre la transferencia (opcional)");
		
		this.contactos.add(cuentaTransferida);
		
		transferencia = new Movimiento(incluirTernaria(detalles), Double.parseDouble(monto), Tipo_Movimiento.TRANSFERENCIA);
		
		JOptionPane.showMessageDialog(null, "---Se ha realizado la transferencia---\n"
				+ "\n➡️Tu Cuenta: \n" + toString()
				+ "\n--------------------------------------------------------------------------\n"
				+ "\n➡️Cuenta transferida: \n"
				+ cuentaTransferida.toString());
		
		cuentaTransferida.getMovimientos().add(new Movimiento(incluirTernaria(detalles), Double.parseDouble(monto), Tipo_Movimiento
				.TRANSFERENCIA_RECIBIDA));
		
		return transferencia;
	}
	
	public double calcularComision(NombreMedio nombreMedio, double monto) {
		double comision = 0;
		
		if (nombreMedio == NombreMedio.RAPIPAGO) {
			comision = monto / 20;
		}
		else if (nombreMedio == NombreMedio.PAGOFACIL) {
			comision = monto / 30;
		}
		else {
			comision = monto / 40;
		}
		
		return comision;
	}
	
	public String verMovimentos() {
		String string = "";
		
		for (Movimiento movimiento : movimientos) {
			string += "\n" + movimiento;
		}
		
		return string;
	}
	
	public String verMovimientosRecientes() {
		List <Movimiento> movimientosRecientes = this.movimientos.stream()
				.sorted(Comparator.comparing(Movimiento::getFecha).reversed())
				.sorted(Comparator.comparing(Movimiento::getHora).reversed())
				.collect(Collectors.toList());
		
		String string = "";
		
		for (Movimiento movimiento : movimientosRecientes) {
			string += "\n" + movimiento;
		}
		
		return string;
	}
	
	public String verMovimientosMayorMonto() {
		List <Movimiento> movimientosMontoMayor = this.movimientos.stream()
				.sorted(Comparator.comparing(Movimiento::getMonto).reversed())
				.collect(Collectors.toList());
		
		String string = "";
		
		for (Movimiento movimiento : movimientosMontoMayor) {
			string += "\n" + movimiento;
		}
		
		return string;
	}
	
	public String verMovimientosMenorMonto() {
		List <Movimiento> movimientosMontoMayor = this.movimientos.stream()
				.sorted(Comparator.comparing(Movimiento::getMonto))
				.collect(Collectors.toList());
		
		String string = "";
		
		for (Movimiento movimiento : movimientosMontoMayor) {
			string += "\n" + movimiento;
		}
		
		return string;
	}
	
	public void verMovimientosPorCategoria(String [] movimientosCategoria) {
		int opcionElegida = JOptionPane.showOptionDialog(null, "Movimientos", "", 0, 0, null, movimientosCategoria, movimientosCategoria[0]);
		
		Tipo_Movimiento tipo_Movimiento;
		if (opcionElegida == 0) {
			tipo_Movimiento = Tipo_Movimiento.DEPOSITO;
		}
		else if (opcionElegida == 1) {
			tipo_Movimiento = Tipo_Movimiento.RETIRO;
		}
		else if (opcionElegida == 2) {
			tipo_Movimiento = Tipo_Movimiento.TRANSFERENCIA;
		}
		else if (opcionElegida == 3) {
			tipo_Movimiento = Tipo_Movimiento.TRANSFERENCIA_RECIBIDA;
		}
		else {
			return;
		}
		
		String mensaje = "";
		
		List <Movimiento> movimientosFiltrados = this.movimientos.stream()
			.filter(movimiento -> movimiento.getTipo_Movimiento().equals(tipo_Movimiento))
			.collect(Collectors.toList());
		
		if (movimientosFiltrados.size() == 0) {
			JOptionPane.showMessageDialog(null, "No hay " + tipo_Movimiento + " realizados");
			return;
		}
		
		for (int i = 0; i < movimientosFiltrados.size(); i++) {
			mensaje += movimientosFiltrados.get(i).toString() + "\n";
		}
		
		JOptionPane.showMessageDialog(null, mensaje);
	}
	
	public String incluirTernaria(String string) {
		return string.isEmpty() ? "Ninguno" : string;
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
			JOptionPane.showMessageDialog(null, "El monto ingresado: " + monto + " es mayor al saldo actual -" + saldo + "-");
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
	
	public void verNotificaciones() {
		if (CuentaBancaria.notificacionesGenerales.size() == 0 && this.notificacionesPropias.size() == 0) {
			JOptionPane.showMessageDialog(null, "No tienes ninguna notificacion");
			return;
		}
		String mensaje = "";
		if (CuentaBancaria.notificacionesGenerales.size() > 0) {			
			mensaje += "---General---\n";
			
			for (int i = 0; i < notificacionesGenerales.size(); i++) {
				mensaje += "- " + notificacionesGenerales.get(i) + "\n";
			}
		}
		if (this.notificacionesPropias.size() > 0) {			
			mensaje += "---Propias---\n";
			for (int i = 0; i < this.notificacionesPropias.size(); i++) {
				mensaje += "- " + this.notificacionesPropias.get(i) + "\n";
			}
		}

		JOptionPane.showMessageDialog(null, mensaje);
	}
	
	@Override
	public String toString() {
		return " 💵 Saldo: " + saldo 
				+ "\n 💬 Alias: " + alias 
				+ "\n 📩 Email: " + email 
				+ "\n 📅 Fecha creacion cuenta: " + fechaCreacion
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
	
	public void restarSaldo(double monto) {
		this.saldo -= monto;
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
	
	public Rol getRol() {
		return rol;
	}
	
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public LinkedList<CuentaBancaria> getContactos() {
		return contactos;
	}
	
	public void setContactos(LinkedList<CuentaBancaria> contactos) {
		this.contactos = contactos;
	}
	
	public static ArrayList<String> getNotificacionesGenerales() {
		return notificacionesGenerales;
	}
	
	public static void setNotificacionesGenerales(ArrayList<String> notificacionesGenerales) {
		CuentaBancaria.notificacionesGenerales = notificacionesGenerales;
	}
	
	public ArrayList<String> getNotificacionesPropias() {
		return notificacionesPropias;
	}
	
	public void setNotificacionesPropias(ArrayList<String> notificacionesPropias) {
		this.notificacionesPropias = notificacionesPropias;
	}
}