package CapaLogica;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class CuentaInversion {
	private CuentaBancaria cuentaBancaria;
	private LocalDate fechaCreacion;
	private ArrayList <Inversion> historialInversiones;
	private double saldo;
	
	public CuentaInversion(CuentaBancaria cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
		this.fechaCreacion = LocalDate.now();
		this.historialInversiones = new ArrayList<Inversion>();
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
		
		this.cuentaBancaria.restarSaldo(Double.parseDouble(monto));
		
		this.actualizarSaldo(Double.parseDouble(monto));
		
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
		
		double saldoInicial = this.saldo;
		double comision = 0;
		double montoCalculado = 0;
		float porcentajePromedio = 0;
		double montoGanado = 0;
		double montoPerdido = 0;
		
		
		for (int i = 0; i < Integer.parseInt(cantidadDias); i++) {
			comision = Math.random() * 10 - 5;
			
			porcentajePromedio += comision;
			
			montoCalculado = (this.saldo * comision) / 100;
			
			if (montoCalculado < 0) {
				this.restarSaldo(montoCalculado);
				montoPerdido -= montoCalculado;
			}
			else {
				this.actualizarSaldo(montoCalculado);
				montoGanado += montoCalculado;
			}
		}
		
		TipoInversion tipoInversion = null;

		double montoTotal = this.saldo;

		if (montoTotal < saldoInicial) {
			tipoInversion = TipoInversion.PERDIDA;
		}
		else {
			tipoInversion = TipoInversion.GANANCIA;
		}
		
		Inversion inversion = new Inversion(Double.parseDouble(monto), montoTotal, montoGanado, montoPerdido, porcentajePromedio, tipoInversion);
		
		agregarInversionHistorial(inversion);
		
		JOptionPane.showMessageDialog(null, "Saldo final: " + this.saldo
				+ "\nPorcentaje promedio de los " + cantidadDias + " dias: " + porcentajePromedio);
	}
	
	public void agregarInversionHistorial(Inversion inversion) {
		this.historialInversiones.add(inversion);
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

	public ArrayList<Inversion> getHistorialInversiones() {
		return historialInversiones;
	}

	public void setHistorialInversiones(ArrayList<Inversion> historialInversiones) {
		this.historialInversiones = historialInversiones;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	public void restarSaldo(double monto) {
		this.saldo -= monto;
	}
	
	public void actualizarSaldo(double monto) {
		this.saldo += monto;
	}
}
