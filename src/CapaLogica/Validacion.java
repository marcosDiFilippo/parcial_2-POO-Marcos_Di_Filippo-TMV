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
				JOptionPane.showMessageDialog(null, "El campo de " + nombreCampo + " no puede contener letras, por favor vuelva ingresar");
				return true;
			}
		}
		return false;
	}
	
	public static boolean verificarNumeroNegativo (double campo, String nombreCampo) {
		if (campo < 0) {
			JOptionPane.showMessageDialog(null, "El campo de " + nombreCampo + " no puede ser negativo, por favor vuelva ingresar");
			return true;
		}
		return false;
	}
	public static boolean validarTamanioCadena(String campo, String nombreCampo, int cantidadMaxima) {
		if (campo.length() > cantidadMaxima) {
			JOptionPane.showMessageDialog(null, "El campo de " + nombreCampo + " no puede tener mas de " + cantidadMaxima + " caracteres, por favor vuelva ingresar");
			return true;
		}
		
		return false;
	}
	public static int confirmarIngreso () {
		int opcion = JOptionPane.showConfirmDialog(null, "Desea seguir ingresando los datos?"
				+ "\nSi presiona cancelar lo redireccionara a la interfaz anterior", "Eleccion", JOptionPane.YES_NO_OPTION, 0, null);
		
		return opcion;
	}
}
