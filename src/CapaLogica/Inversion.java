package CapaLogica;

import java.time.LocalDate;

public class Inversion {
	private double montoInicial;
	private double montoTotal;
	private LocalDate fecha;
	private double montoGanado;
	private double montoPerdido;
	private double porcentajePromedio;
	private TipoInversion tipoInversion;
	
	public Inversion(double montoInicial, double montoTotal, double montoGanado, double montoPerdido, double porcentajePromedio, TipoInversion tipoInversion) {
		this.montoInicial = montoInicial;
		this.montoTotal = montoTotal;
		this.fecha = LocalDate.now();
		this.montoGanado = montoGanado;
		this.montoPerdido = montoPerdido;
		this.porcentajePromedio = porcentajePromedio;
		this.tipoInversion = tipoInversion;
	}

	public double getMontoInicial() {
		return montoInicial;
	}

	public void setMontoInicial(double montoInicial) {
		this.montoInicial = montoInicial;
	}

	public double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public double getMontoGanado() {
		return montoGanado;
	}

	public void setMontoGanado(double montoGanado) {
		this.montoGanado = montoGanado;
	}

	public double getMontoPerdido() {
		return montoPerdido;
	}

	public void setMontoPerdido(double montoPerdido) {
		this.montoPerdido = montoPerdido;
	}

	public double getPorcentajePromedio() {
		return porcentajePromedio;
	}

	public void setPorcentajePromedio(double porcentajePromedio) {
		this.porcentajePromedio = porcentajePromedio;
	}
	
	public TipoInversion getTipoInversion() {
		return tipoInversion;
	}
	
	public void setTipoInversion(TipoInversion tipoInversion) {
		this.tipoInversion = tipoInversion;
	}

	@Override
	public String toString() {
		return "Fecha: " + fecha
				+ "\nMonto Inicial: " + montoInicial 
				+ "\nMonto Ganado: " + montoGanado
				+ "\nMonto Perdido: " + montoPerdido 
				+ "\nMonto Total: " + montoTotal
				+ "\nPorcentaje Promedio: " + porcentajePromedio;
	}
}