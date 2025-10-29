package CapaLogica;

public class CuentaAdministrador extends CuentaBancaria {

	public CuentaAdministrador(Usuario usuario, String email, String contrasenia) {
		super(usuario, email, contrasenia);
		setRol(Rol.ADMINISTRADOR);
	}
}
