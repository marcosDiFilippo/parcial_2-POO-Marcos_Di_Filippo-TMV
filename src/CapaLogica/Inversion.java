package CapaLogica;

import java.time.LocalDate;

public class Inversion {
	private static int idStatic = 0;
	private double montoInicial;
	private double montoTotal;
	private LocalDate fecha;
	private double montoGanado;
	private double montoPerdido;
	private double porcentajePromedio;
	private int plazoDias;
	private TipoInversion tipoInversion;
	private int idInversion;
	
	public Inversion(double montoInicial, double montoTotal, double montoGanado, double montoPerdido, double porcentajePromedio, TipoInversion tipoInversion, int plazoDias) {
		this.montoInicial = montoInicial;
		this.montoTotal = montoTotal;
		this.fecha = LocalDate.now();
		this.montoGanado = montoGanado;
		this.montoPerdido = montoPerdido;
		this.porcentajePromedio = porcentajePromedio;
		this.tipoInversion = tipoInversion;
		idStatic++;
		this.idInversion = idStatic;
		this.plazoDias = plazoDias;
	}
	
	public int getIdInversion() {
		return idInversion;
	}
	
	public void setIdInversion(int idInversion) {
		this.idInversion = idInversion;
	}
	
	public int getPlazoDias() {
		return plazoDias;
	}
	
	public void setPlazoDias(int plazoDias) {
		this.plazoDias = plazoDias;
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
				+ "\nTipo inversion: " + this.tipoInversion
				+ "\nGanancias: " + String.format("%.2f", montoGanado) 
				+ "\nPerdidas: " + String.format("%.2f", montoPerdido) 
				+ "\nPorcentaje promedio: " + String.format("%.2f", porcentajePromedio) + "%"
				+ "\nPlazo dias: " + this.plazoDias
				+ "\nSaldo inicial: " + String.format("%.2f", montoInicial) 
				+ "\nSaldo final: " + String.format("%.2f", montoTotal);
	}
}