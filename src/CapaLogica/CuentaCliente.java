package CapaLogica;

public class CuentaCliente extends CuentaBancaria {

	public CuentaCliente(Usuario usuario, String email, String contrasenia) {
		super(usuario, email, contrasenia);
		setRol(Rol.CLIENTE);
	}
}
