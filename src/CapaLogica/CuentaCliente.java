package CapaLogica;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;

public class CuentaCliente extends CuentaBancaria  {
	private String [] permisos = {"Ver perfil", "Cambiar email", "Cambiar contrasenia", "Salir"};
	
	public CuentaCliente(Usuario usuario, String email, String contrasenia) {
		super(usuario, email, contrasenia);
		this.notificacionesPropias.add("Se ha creado la cuenta exitosamente! |" + " Fecha: " + LocalDate.now() + " | Horario: " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond());
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
			
			String email = cambiarEmail(banco);
			
			setEmail(email);
		}
		else if (opcion == 2) {
			if (validarContraseniaActual() == false) {
				return;
			}
			
			String contrasenia = cambiarContrasenia();
			
			setContrasenia(contrasenia);
		}
		else {
			return;
		}
	}
}
