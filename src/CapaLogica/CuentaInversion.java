package CapaLogica;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class CuentaInversion {
	private CuentaBancaria cuentaBancaria;
	private LocalDate fechaCreacion;
	private ArrayList <String> historialInversiones;
	private double saldo;
	
	public CuentaInversion(CuentaBancaria cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
		this.fechaCreacion = LocalDate.now();
		this.historialInversiones = new ArrayList<String>();
		this.saldo = 0;
	}
	
	public void realizarInversion() {
		if (this.cuentaBancaria.getSaldo() <= 0) {
			JOptionPane.showMessageDialog(null, "El saldo de su cuenta bancaria es 0");
			return;
		}
		
		String monto;
		boolean esVacio;
		boolean esNegativo = false;
		boolean tieneLetras = false;
		boolean esDecimal = false;
		
		do {
			monto = JOptionPane.showInputDialog("Ingrese el monto de inversion (sin comas ni puntos, solo numeros)");
			esVacio = Validacion.validarCampoVacio(monto, "monto");
			if (esVacio == true) {
				continue;
			}
			
			tieneLetras = Validacion.validarLetrasCampo(monto, "monto");
			if (tieneLetras == true) {
				continue;
			}
			
			esNegativo = Validacion.verificarNumeroNegativo(Double.parseDouble(monto), "monto");
			if (esNegativo == true) {
				continue;
			}
			
			if (Double.parseDouble(monto) > this.cuentaBancaria.getSaldo()) {
				JOptionPane.showMessageDialog(null, "Ha ingresado un monto mayor al de su cuenta bancaria");
				continue;
			}
			
		} while (tieneLetras == true || esVacio == true || esNegativo == true);
		
		String cantidadDias;
		
		do {
			cantidadDias = JOptionPane.showInputDialog("Ingrese el plazo de dias para realizar la inversion");
			
			esVacio = Validacion.validarCampoVacio(cantidadDias, "cantidad de dias");
			if (esVacio == true) {
				continue;
			}
			
			tieneLetras = Validacion.validarLetrasCampo(cantidadDias, "cantidad de dias");
			if (tieneLetras == true) {
				continue;
			}
			
			esNegativo = Validacion.verificarNumeroNegativo(Integer.parseInt(cantidadDias), "cantidad de dias");
			if (esNegativo == true) {
				continue;
			}
			
			for (int i = 0; i < cantidadDias.length(); i++) {
				if (cantidadDias.charAt(i) == ',' || cantidadDias.charAt(i) == '.') {
					esDecimal = true;
					break;
				}
				esDecimal = false;
			}
			if (esDecimal == true) {
				JOptionPane.showMessageDialog(null, "La cantidad de dias no puede ser decimal");
				continue;
			}
			
		} while (esVacio == true || tieneLetras == true || esNegativo == true);
		
		for (int i = 0; i < Integer.parseInt(cantidadDias); i++) {
			
		}
	}

	public CuentaBancaria getCuentaBancaria() {
		return cuentaBancaria;
	}

	public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public ArrayList<String> getHistorialInversiones() {
		return historialInversiones;
	}

	public void setHistorialInversiones(ArrayList<String> historialInversiones) {
		this.historialInversiones = historialInversiones;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
}
