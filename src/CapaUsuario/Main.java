package CapaUsuario;

import java.time.LocalDate;

import javax.swing.JOptionPane;

import CapaLogica.Banco;
import CapaLogica.CuentaBancaria;
import CapaLogica.Usuario;

public class Main {
	public static void main(String[] args) {
		
		String [] opciones = {"Crear Cuenta Bancaria", "Iniciar Sesion", "Salir"};
		String [] opcionesCuentaBancaria = {"Depositar Dinero", "Retirar Dinero", "Transferir Dinero", "Ver Movimientos", "Salir"}; 
				
		Banco banco = new Banco("Galicia");
		
		banco.agregarCuentasBancarias(new CuentaBancaria(new Usuario("marcos", "di filippo", LocalDate.of(2007, 6, 26), "3525", "47"), "marcos@gmail.com", "marcos"));
		banco.agregarCuentasBancarias(new CuentaBancaria(new Usuario("martin", "juncos", LocalDate.of(2005, 5, 5), "3525", "47"), "martin@gmail.com", "martin"));
		
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
					
					do {
						opcionElegida = JOptionPane.showOptionDialog(null, "Panel Usuario - " + cuentaBancaria.toString(), null, 0, 0, null, opcionesCuentaBancaria, opcionesCuentaBancaria[0]);
					switch (opcionElegida) {
					case 0:
						cuentaBancaria.getMovimientos().add(cuentaBancaria.depositarDinero());
						break;
					case 1:
						cuentaBancaria.retirarDinero();
						break;
					case 2:
						cuentaBancaria.transferirDinero(banco);
						break;
					case 3:
						JOptionPane.showMessageDialog(null, cuentaBancaria.getMovimientos().size() != 0 ? cuentaBancaria.verMovimentos() : "No hay movimientos cargados");
						break;
					default:
						
						break;
						}	
					} while (opcionElegida != 4);
				}
				else {
					JOptionPane.showMessageDialog(null, "No se ha encontrado la sesion");
				}
				break;
			default:
				JOptionPane.showMessageDialog(null, "Has salido");
				break;
			}
		} while (opcionElegida != 2);
	}
}
