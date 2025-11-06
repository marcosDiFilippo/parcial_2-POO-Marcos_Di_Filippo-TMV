package CapaLogica;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

public class CuentaAdministrador extends CuentaBancaria {
	private String [] permisos = {"Eliminar cuenta", "Cambiar roles", "Agregar cajero", "Ver Cajeros Disponibles" ,"Salir"};

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
		
		for (CuentaBancaria cuenta : cuentasBancarias) {
			if (cuenta.getEmail().equals(email)) {
				int opcion = JOptionPane.showConfirmDialog(null, "Realmente quiere eliminar a este usuario?", "", JOptionPane.YES_NO_OPTION, 0,null);
				if (opcion == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "Se ha eliminado al usuario " + cuenta.getEmail());
					banco.getCuentasBancarias().remove(cuenta);
					return;
				}
				JOptionPane.showMessageDialog(null, "No se ha eliminado a ningun usuario");
				return;
			}
		}
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
		
		String [] usuarios = new String[cuentasFiltradas.size()];
		
		for (int i = 0; i < usuarios.length; i++) {
			usuarios[i] = cuentasFiltradas.get(i).getEmail();
		}
		
		String email = (String) JOptionPane.showInputDialog(null, "A que usuario desea modificar los roles?", "", 0, null, usuarios, usuarios[0]);
		
		int index = 0;
		
		for (CuentaBancaria cuenta : cuentasFiltradas) {
			index++;
			if (cuenta.getEmail().equals(email)) {
				Rol rol;
				int opcionRol = JOptionPane.showOptionDialog(null, 
					"Elija el rol para el usuario: \n" 
					+ cuenta.toString() 
					+ "\n-----------------------------------"
					+ "\nROL ACTUAL: " + cuenta.getRol()
				, "", 0, 0, null, Rol.values(), Rol.values()[0]);
				
				if (opcionRol == 0) {
					rol = Rol.CLIENTE;
				}
				else {
					rol = Rol.ADMINISTRADOR;
				}
				
				int opcion = JOptionPane.showConfirmDialog(null, "Realmente quiere modificar el rol de este usuario? \n" + cuenta.toString(), "", JOptionPane.YES_NO_OPTION, 0, null);
				
				if (opcion == JOptionPane.YES_OPTION) {
					mensaje += "Cuenta modificada: \n" + cuenta.toString();
					
					cuenta.setRol(rol);
					
					banco.getCuentasBancarias().set(index, CuentaAdministrador.crearNuevoAdministrador(cuenta));
					
					mensaje += "\nRol asignado: " + cuenta.getRol();
					
					JOptionPane.showMessageDialog(null, mensaje);
					return;
				}
				JOptionPane.showMessageDialog(null, "No se ha realizado ningun cambio de roles");
				return;
			}
		}
	}

	public CuentaAdministrador(CuentaBancaria cuentaBancaria) {
		super(cuentaBancaria);
	}

	public static CuentaAdministrador crearNuevoAdministrador(CuentaBancaria cuentaBancaria) {
		
		return new CuentaAdministrador(cuentaBancaria);
	}
}
