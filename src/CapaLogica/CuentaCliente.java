package CapaLogica;

import javax.swing.JOptionPane;

public class CuentaCliente extends CuentaBancaria  {

	public CuentaCliente(Usuario usuario, String email, String contrasenia) {
		super(usuario, email, contrasenia);
		setRol(Rol.CLIENTE);
	}
	
	@Override
	public void realizarAcciones(String[] opciones, String [] opcionesMovimiento, Banco banco) {
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
				default:
					
					break;
				}
				break;
			default:
				
				break;
				}	
		} while (opcion !=  opciones.length - 1);
	}
}
