package CapaUsuario;

import java.time.LocalDate;

import javax.swing.JOptionPane;

import CapaLogica.Banco;
import CapaLogica.CuentaAdministrador;
import CapaLogica.CuentaBancaria;
import CapaLogica.CuentaCliente;
import CapaLogica.Movimiento;
import CapaLogica.Usuario;

public class Main {
	public static void main(String[] args) {
		String [] opciones = {"Crear Cuenta Bancaria", "Iniciar Sesion", "Salir"};
		String [] opcionesGenerales = {"Depositar Dinero", "Retirar Dinero", "Transferir Dinero", "Ver Movimientos", "Opciones Cuenta" ,"Salir"};
		String [] opcionesMovimiento = {"Ver general", "Mas Recientes", "Por mayor monto", "Por menor monto", "Por Categoria","Salir"};
		String [] movimientosCategoria = {"Depositos", "Retiros", "Transferencias", "Transferencias Recibidas" ,"Salir"};
		
		Banco banco = new Banco("Galicia");
		
		banco.agregarCuentasBancarias(new CuentaAdministrador(new Usuario("maco", "di filippo", LocalDate.of(2007, 6, 26), "35", "479"), "maco@gmail.com", "maco"));
		
		banco.agregarCuentasBancarias(new CuentaCliente(new Usuario("marcos", "di filippo", LocalDate.of(2007, 6, 26), "3525", "47"), "marcos@gmail.com", "marcos"));
		banco.agregarCuentasBancarias(new CuentaCliente(new Usuario("martin", "juncos", LocalDate.of(2005, 5, 5), "35253", "4734"), "martin@gmail.com", "martin"));
		
		int opcionElegida;
		
		do {
			opcionElegida = JOptionPane.showOptionDialog(null, "Bienvenido", null, 0, 0, null, opciones, opciones[0]);
			switch (opcionElegida) {
			case 0:
				Usuario usuarioCreado = Usuario.crearUsuario(banco);
				CuentaBancaria cuentaCreada = CuentaBancaria.crearCuentaBancaria(usuarioCreado, banco);
				banco.agregarCuentasBancarias(cuentaCreada);
				break;
			case 1:
				CuentaBancaria cuentaBancaria = Usuario.iniciarSesion(banco);
				if (cuentaBancaria != null) {
					JOptionPane.showMessageDialog(null, "Sesion iniciada con exito");
					do {
						opcionElegida = JOptionPane.showOptionDialog(null, cuentaBancaria.toString(), "", 0, 0, null, opcionesGenerales, opcionesGenerales[0]);
						
						switch (opcionElegida) {
						case 0:
							Movimiento deposito = cuentaBancaria.depositarDinero();
							if (deposito != null) {								
								cuentaBancaria.getMovimientos().add(deposito);
							}
							break;	
						case 1:
							cuentaBancaria.retirarDinero(); 
							break;
						case 2:
							cuentaBancaria.transferirDinero(banco);
							break;
						case 3:
							if (cuentaBancaria.getMovimientos().size() == 0) {
								JOptionPane.showMessageDialog(null, "No hay movimientos cargados");
								break;
							}
							opcionElegida = JOptionPane.showOptionDialog(null, "Que movimiento quiere ver?", "", 0, 0, null, opcionesMovimiento, opcionesMovimiento[0]);
							switch (opcionElegida) {
							case 0:
								JOptionPane.showMessageDialog(null, cuentaBancaria.verMovimentos());
								break;
							case 1:
								JOptionPane.showMessageDialog(null, cuentaBancaria.verMovimientosRecientes());
								break;
							case 2:
								JOptionPane.showMessageDialog(null, cuentaBancaria.verMovimientosMayorMonto());
								break;
							case 3:
								JOptionPane.showMessageDialog(null, cuentaBancaria.verMovimientosMenorMonto());
								break;
							case 4:
								cuentaBancaria.verMovimientosPorCategoria(movimientosCategoria);
								break;
							}
							break;
						case 4:
							cuentaBancaria.realizarOpcionesCuenta(banco);
							break;
						default:
							JOptionPane.showMessageDialog(null, "Has cerrado sesion");
							break;
						}	
					} while (opcionElegida != opcionesGenerales.length - 1);
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