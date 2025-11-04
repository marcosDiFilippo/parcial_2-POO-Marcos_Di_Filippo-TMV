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
		String email = "";
		boolean emailVacio;
		boolean existeEmail = false;
		boolean esVacio;
		String contrasenia = "";
		String contraseniaConfirmada = "";
		
		if (opcion == 0) {
			JOptionPane.showMessageDialog(null, toString());
		}
		else if (opcion == 1) {
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
			
			setEmail(email);
		}
		else if (opcion == 2) {
			do {
				contrasenia = JOptionPane.showInputDialog("Ingrese su contrasenia actual");
				if (!contrasenia.equals(this.contrasenia)) {
					JOptionPane.showMessageDialog(null, "La contrasenia ingresada no coincide con la actual");
					continue;
				}
				
				contrasenia = JOptionPane.showInputDialog("Ingrese una nueva contrasenia");
				esVacio = validarCamposVacios(contrasenia, "contrasenia");
				
				if (esVacio == true) {
					continue;
				}
				
				contraseniaConfirmada = JOptionPane.showInputDialog("Ingrese nuevamente la contrasenia");
				if (!contraseniaConfirmada.equals(contrasenia)) {					
					JOptionPane.showMessageDialog(null, "Las contrasenias no coinciden por favor vuelva ingresar");
				}
			} while (contrasenia.isEmpty() || !contraseniaConfirmada.equals(contrasenia));
			
			setContrasenia(contrasenia);
		}
		else {
			return;
		}
	}
	
	@Override
	public void transferirDinero(Banco banco) {
		super.transferirDinero(banco);
	}
}
