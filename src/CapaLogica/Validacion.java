package CapaLogica;

import javax.swing.JOptionPane;

public class Validacion {
	
	public static boolean validarCampoVacio(String campo, String nombreCampo) {
		if (campo.isEmpty()) {
			JOptionPane.showMessageDialog(null, "El campo de " + nombreCampo + " es obligatorio, por favor vuelva ingresar");
			return true;
		}
		return false;
	}
	public static boolean validarLetrasCampo(String campo, String nombreCampo) {
		for (int i = 0; i < campo.length(); i++) {
			if (!Character.isDigit(campo.charAt(i))) {
				if (campo.charAt(i) == '.') {
					continue;
				}
				JOptionPane.showMessageDialog(null, "El " + nombreCampo + " no puede contener letras, por favor vuelva ingresar");
				return true;
			}
		}
		return false;
	}
	
	public static boolean verificarNumeroNegativo (double campo, String nombreCampo) {
		if (campo < 0) {
			JOptionPane.showMessageDialog(null, "El " + nombreCampo + " no puede ser menor a 0, por favor vuelva ingresar");
			return true;
		}
		return false;
	}
}
