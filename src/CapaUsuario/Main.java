package CapaUsuario;

import java.time.LocalDate;

import javax.swing.JOptionPane;

import CapaLogica.Banco;
import CapaLogica.CuentaAdministrador;
import CapaLogica.CuentaBancaria;
import CapaLogica.CuentaCliente;
import CapaLogica.Rol;
import CapaLogica.Usuario;

public class Main {
	public static void main(String[] args) {
		String [] opciones = {"Crear Cuenta Bancaria", "Iniciar Sesion", "Salir"};
		String [] opcionesCliente = {"Depositar Dinero", "Retirar Dinero", "Transferir Dinero", "Ver Movimientos", "Salir"}; 
		String [] opcionesAdministrador = {"Depositar Dinero", "Retirar Dinero", "Transferir Dinero", "Ver Movimientos", "Administrar Cuentas" ,"Salir"};
		String [] opcionesMovimiento = {"Ver general", "Mas Recientes", "Por mayor monto", "Por menor monto", "Salir"};
		
		Banco banco = new Banco("Galicia");
		
		banco.agregarCuentasBancarias(new CuentaAdministrador(new Usuario("maco", "di filippo", LocalDate.of(2007, 6, 26), "35", "479"), "maco@gmail.com", "maco"));
		
		banco.agregarCuentasBancarias(new CuentaCliente(new Usuario("marcos", "di filippo", LocalDate.of(2007, 6, 26), "3525", "47"), "marcos@gmail.com", "marcos"));
		banco.agregarCuentasBancarias(new CuentaCliente(new Usuario("martin", "juncos", LocalDate.of(2005, 5, 5), "35253", "4734"), "martin@gmail.com", "martin"));
		
		int opcionElegida;
		
		do {
			opcionElegida = JOptionPane.showOptionDialog(null, "Bienvenido", null, 0, 0, null, opciones, opciones[0]);
			switch (opcionElegida) {
			case 0:
				banco.agregarCuentasBancarias(CuentaBancaria.crearCuentaBancaria(Usuario.crearUsuario(banco), banco));
				break;
			case 1:
				CuentaBancaria cuentaBancaria = Usuario.iniciarSesion(banco);
				if (cuentaBancaria != null) {
					JOptionPane.showMessageDialog(null, "Sesion iniciada con exito");
					if (cuentaBancaria.getRol().equals(Rol.CLIENTE)) {
						cuentaBancaria.realizarAcciones(opcionesCliente, opcionesMovimiento, banco);
					}
					else {
						cuentaBancaria.realizarAcciones(opcionesAdministrador, opcionesMovimiento, banco);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "No se ha encontrado la sesion");
				}
				break;
			default:
					JOptionPane.showMessageDialog(null, "Has salido");
				break;
			}
		} while (opcionElegida != opciones.length - 1);	
	}
}