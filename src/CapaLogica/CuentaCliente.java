package CapaLogica;

import javax.swing.JOptionPane;

public class CuentaCliente extends CuentaBancaria  {
	private String [] permisos = {"Ver perfil", "Cambiar email", "Cambiar contrasenia", "Salir"};
	
	public CuentaCliente(Usuario usuario, String email, String contrasenia) {
		super(usuario, email, contrasenia);
		setRol(Rol.CLIENTE);
	}
	
	@Override
	public void realizarOpcionesCuenta(Banco banco) {
		int opcion = JOptionPane.showOptionDialog(null, "Opciones de cuenta", "", 0, 0, null, permisos, permisos[0]);
		
		if (opcion == 0) {
			JOptionPane.showMessageDialog(null, toString());
		}
		else if (opcion == 1) {		
			if (validarEmailActual() == false) {
				return;
			}
			
			String email = ingresarEmail(banco);
			
			setEmail(email);
		}
		else if (opcion == 2) {
			if (validarContraseniaActual() == false) {
				return;
			}
			
			String contrasenia = ingresarContrasenia();
			
			setContrasenia(contrasenia);
		}
		else {
			return;
		}
	}
}
