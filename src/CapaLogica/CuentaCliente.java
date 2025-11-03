package CapaLogica;

import javax.swing.JOptionPane;

public class CuentaCliente extends CuentaBancaria  {

	public CuentaCliente(Usuario usuario, String email, String contrasenia) {
		super(usuario, email, contrasenia);
		setRol(Rol.CLIENTE);
	}
	
	@Override
	public void realizarAcciones(String[] opciones, String [] opcionesMovimiento, String [] movimientosCategoria ,Banco banco) {
		int opcion;
		
		do {
			opcion = JOptionPane.showOptionDialog(null, toString(), "", 0, 0, null, opciones, opciones[0]);
			
			switch (opcion) {
			case 0:
				getMovimientos().add(depositarDinero());
				break;	
			case 1:
				retirarDinero();
				break;
			case 2:
				transferirDinero(banco);
				break;
			case 3:
				if (getMovimientos().size() == 0) {
					JOptionPane.showMessageDialog(null, "No hay movimientos cargados");
					break;
				}
				opcion = JOptionPane.showOptionDialog(null, "Que movimiento quiere ver?", "", 0, 0, null, opcionesMovimiento, opcionesMovimiento[0]);
				switch (opcion) {
				case 0:
					JOptionPane.showMessageDialog(null, verMovimentos());
					break;
				case 1:
					JOptionPane.showMessageDialog(null, verMovimientosRecientes());
					break;
				case 2:
					JOptionPane.showMessageDialog(null, verMovimientosMayorMonto());
					break;
				case 3:
					JOptionPane.showMessageDialog(null, verMovimientosMenorMonto());
					break;
				case 4:
					opcion = JOptionPane.showOptionDialog(null, "Movimientos", "", 0, 0, null, movimientosCategoria, movimientosCategoria[0]);
					Tipo_Movimiento tipo_Movimiento = null;
					if (opcion == 0) {
						tipo_Movimiento = Tipo_Movimiento.DEPOSITO;
					}
					else if (opcion == 1) {
						tipo_Movimiento = Tipo_Movimiento.RETIRO;
					}
					else if (opcion == 2) {
						tipo_Movimiento = Tipo_Movimiento.TRANSFERENCIA;
					}
					else if (opcion == 3) {
						tipo_Movimiento = Tipo_Movimiento.TRANSFERENCIA_RECIBIDA;
					}
					else {
						break;
					}
					verMovimientosPorCategoria(tipo_Movimiento);
					break;
				default:
					
					break;
				}
				break;
			default:
				JOptionPane.showMessageDialog(null, "Has cerrado sesion");
				break;
				}	
		} while (opcion !=  opciones.length - 1);
	}
	
	@Override
	public void transferirDinero(Banco banco) {
		super.transferirDinero(banco);
	}
}
