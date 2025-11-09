package CapaLogica;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

public class CuentaAdministrador extends CuentaBancaria {
	private String [] permisos = {"Eliminar cuenta", "Cambiar roles", "Agregar cajero", "Ver cajeros disponibles", "Dar de baja cajero", "Ver cuentas", "Salir"};

	public CuentaAdministrador(Usuario usuario, String email, String contrasenia) {
		super(usuario, email, contrasenia);
		setRol(Rol.ADMINISTRADOR);
	}
	
	@Override
	public void realizarOpcionesCuenta(Banco banco) {
		int opcion;
		
		opcion = JOptionPane.showOptionDialog(null, "Elija la accion a realizar", "", 0, 0, null, permisos, permisos[0]);
	
		if (opcion == 0) {
			eliminarUsuarios(banco);
		}
		else if (opcion == 1) {
			cambiarRoles(banco);
		}
		else if (opcion == 2) {
			String ubicacion;
			do {
				ubicacion = JOptionPane.showInputDialog("Ingrese la ubicacion del cajero");
				if (ubicacion.isEmpty()) {
					JOptionPane.showMessageDialog(null, "La ubicacion es obligatoria, por favor vuelva ingresar");
				}
			} while (ubicacion.isEmpty());
			
			NombreMedio nombreMedio = (NombreMedio) JOptionPane.showInputDialog(null, "Ingrese el medio de operacion en el cual se encuentra el cajero", "", 0, null, NombreMedio.values(), NombreMedio.values()[0]);
			
			Cajero.agregarCajero(new Cajero(ubicacion, new MedioOperacion(nombreMedio)));
		}
		else if (opcion == 3) {
			String mensaje = "";
			
			for (Cajero cajero : Cajero.getCajeros()) {
				mensaje += cajero;
			}
			JOptionPane.showMessageDialog(null, mensaje.isEmpty() ? "No hay cajeros cargados" : mensaje);
		}
		else if (opcion == 4) {
			darBajaCajero();
		}
		else if (opcion == 5) {
			verCuentas(banco);
		}
	}
	
	public void eliminarUsuarios(Banco banco) {
		if (banco.getCuentasBancarias().size() == 0) {
			return;
		}
		List <CuentaBancaria> cuentasBancarias = banco.filtrarCuentas(this.getAlias());
		
		String [] usuarios = new String[cuentasBancarias.size()];
		
		for (int i = 0; i < usuarios.length; i++) {
			usuarios[i] = cuentasBancarias.get(i).getEmail();
		}
		
		String email = (String) JOptionPane.showInputDialog(null, "Que usuario desea eliminar del banco?", "", 0 ,null, usuarios, usuarios[0]);
		
		
		CuentaBancaria cuentaEliminada = null;
		
		for (CuentaBancaria cuenta : cuentasBancarias) {
			if (cuenta.getEmail().equals(email)) {
				cuentaEliminada = cuenta;
				int opcion = JOptionPane.showConfirmDialog(null, "Realmente quiere eliminar a este usuario?", "", JOptionPane.YES_NO_OPTION, 0,null);
				if (opcion == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "Se ha eliminado al usuario " + cuentaEliminada.getEmail());
					banco.getCuentasBancarias().remove(cuentaEliminada);
					return;
				}
				JOptionPane.showMessageDialog(null, "No se ha eliminado a ningun usuario");
				return;
			}
		}
		
		this.notificacionesPropias.add("Eliminaste a la cuenta: " + cuentaEliminada.getUsuario().getNombre() + " " + cuentaEliminada.getUsuario().getApellido() + " - " + cuentaEliminada.getEmail());
	}
	
	public void cambiarRoles(Banco banco) {
		if (banco.getCuentasBancarias().size() == 0) {
			return;
		}
		String mensaje = "";
		
		List <CuentaBancaria> cuentasBancarias = banco.filtrarCuentas(this.getAlias());

		List <CuentaBancaria> cuentasFiltradas = cuentasBancarias.stream()
				.filter(cuenta -> !cuenta.getRol().equals(Rol.ADMINISTRADOR))
				.collect(Collectors.toList());
		if (cuentasFiltradas.size() == 0) {
			JOptionPane.showMessageDialog(null, "No hay cuentas de clientes registradas");
			return;
		}
		
		String [] usuarios = new String[cuentasFiltradas.size()];
		
		for (int i = 0; i < usuarios.length; i++) {
			usuarios[i] = cuentasFiltradas.get(i).getEmail();
		}
		
		String email = (String) JOptionPane.showInputDialog(null, "A que usuario desea modificar los roles?", "", 0, null, usuarios, usuarios[0]);
		
		int index = 0;
		
		CuentaBancaria cuentaModificada = null;
		for (CuentaBancaria cuenta : cuentasFiltradas) {
			index++;
			if (cuenta.getEmail().equals(email)) {
				cuentaModificada = cuenta;
				Rol rol;
				int opcionRol = JOptionPane.showOptionDialog(null, 
					"Elija el rol para el usuario: \n" 
					+ cuentaModificada.toString() 
					+ "\n-----------------------------------"
					+ "\nROL ACTUAL: " + cuentaModificada.getRol()
				, "", 0, 0, null, Rol.values(), Rol.values()[0]);
				
				if (opcionRol == 0) {
					rol = Rol.CLIENTE;
				}
				else {
					rol = Rol.ADMINISTRADOR;
				}
				
				int opcion = JOptionPane.showConfirmDialog(null, "Realmente quiere modificar el rol de este usuario? \n" + cuenta.toString(), "", JOptionPane.YES_NO_OPTION, 0, null);
				
				if (opcion == JOptionPane.YES_OPTION) {
					mensaje += "Cuenta modificada: \n" + cuentaModificada.toString();
					
					cuentaModificada.setRol(rol);
					
					banco.getCuentasBancarias().set(index, CuentaAdministrador.crearNuevoAdministrador(cuentaModificada));
					
					mensaje += "\nRol asignado: " + cuentaModificada.getRol();
					
					JOptionPane.showMessageDialog(null, mensaje);
					return;
				}
				JOptionPane.showMessageDialog(null, "No se ha realizado ningun cambio de roles");
				return;
			}
		}
	}
	
	public void darBajaCajero() {
		if (Cajero.getCajeros().size() == 0) {
			JOptionPane.showMessageDialog(null, "No hay ningun cajero cargado");
			return;
		}
		if (Cajero.getCajeros().size() == 1 && Cajero.getCajeros().get(0).getSaldo() > 0) {
			JOptionPane.showMessageDialog(null, "No se puede dar de baja el cajero"
					+ "\nYa que contiene saldo y no hay ningun otro cajero en el sistema para poder transferir ese saldo"
					+ "\nSolucion: agregue un nuevo cajero y podra dar de baja a este");
			return;
		}
		String [] cajeros = Cajero.incluirCajeros();
		
		String cajero = (String) JOptionPane.showInputDialog(null, "Elija el cajero que quiere dar de baja", "Cajeros", 0, null, cajeros, cajeros[0]);
		
		Cajero cajeroBaja = null;
		Cajero cajeroTransferido = null;
		
		for (int i = 0; i < cajeros.length; i++) {
			if (cajero.equals(cajeros[i])) {
				cajeroBaja = Cajero.getCajeros().get(i);
				break;
			}
		}
		if (cajeroBaja.getSaldo() == 0) {
			Cajero.getCajeros().remove(cajeroBaja);
			return;
		}
		ArrayList <Cajero> cajerosTransferibles = Cajero.getCajeros();
		cajerosTransferibles.remove(cajeroBaja);
		
		String [] cajerosTransferiblesArray = new String[cajerosTransferibles.size()];
		
		for (int i = 0; i < cajerosTransferiblesArray.length; i++) {
			cajerosTransferiblesArray[i] = cajerosTransferibles.get(i).getUbicacion();
		}
		
		String cajeroTransferidoString = (String) JOptionPane.showInputDialog(null, "Elija el cajero al cual se va transferir el dinero", "Cajeros disponibles", 0, null, cajerosTransferiblesArray, cajerosTransferiblesArray[0]);
		
		for (int i = 0; i < cajerosTransferiblesArray.length; i++) {
			if (cajerosTransferiblesArray[i].equals(cajeroTransferidoString)) {
				cajeroTransferido = cajerosTransferibles.get(i);
				break;
			}
		}
		double saldoAntesBaja = cajeroTransferido.getSaldo();
		cajeroTransferido.sumarSaldo(cajeroBaja.getSaldo());
		JOptionPane.showMessageDialog(null, "Se ha realizado la transaccion de dinero"
				+ "\nCajero Eliminado"
				+ cajeroBaja.toString()
				+ "\n----------------------------------------------------"
				+ "\nCajero transferido\n"
				+ cajeroTransferido.toString()
				+ "\nSaldo antes de la baja: " + saldoAntesBaja);
		
		Cajero.getCajeros().remove(cajeroBaja);
	}
	
	public void verCuentas(Banco banco) {
		if (banco.getCuentasBancarias().size() == 0) {
			JOptionPane.showMessageDialog(null, "No hay cuentas cargadas");
			return;
		}
		
		String mensaje = "---Cuentas---"
				+ "\nNombre completo | Correo electronico | Saldo \n";
		
		for (int i = 0; i < banco.getCuentasBancarias().size(); i++) {
			CuentaBancaria cuentaBancaria = banco.getCuentasBancarias().get(i);
			Usuario usuario = cuentaBancaria.getUsuario();
			mensaje += usuario.getNombre() + " " + usuario.getApellido() + " | " + cuentaBancaria.getEmail() + " | " + cuentaBancaria.getSaldo() + "\n";
		}
		
		JOptionPane.showMessageDialog(null, mensaje);
	}

	public CuentaAdministrador(CuentaBancaria cuentaBancaria) {
		super(cuentaBancaria);
	}

	public static CuentaAdministrador crearNuevoAdministrador(CuentaBancaria cuentaBancaria) {
		
		return new CuentaAdministrador(cuentaBancaria);
	}
}
