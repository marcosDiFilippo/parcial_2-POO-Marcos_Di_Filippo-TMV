package CapaLogica;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Movimiento {
	
	private static int numMov = 0;
	private Tipo_Movimiento tipo_Movimiento;
	private LocalDate fecha;
	private LocalDateTime hora;
	private String detalles;
	private double monto;
	private int numeroMovimiento;
	private MedioOperacion medioOperacion;
	
	public Movimiento(String detalles, double monto, Tipo_Movimiento tipo_Movimiento) {
		this.fecha = LocalDate.now();
		this.hora = LocalDateTime.now();
		this.detalles = detalles;
		this.monto = monto;
		this.tipo_Movimiento = tipo_Movimiento;
		numMov++;
		this.numeroMovimiento = numMov;
	}
	
	public Movimiento(String detalles, double monto, Tipo_Movimiento tipo_Movimiento, MedioOperacion medioOperacion) {
		this.fecha = LocalDate.now();
		this.hora = LocalDateTime.now();
		this.detalles = detalles;
		this.monto = monto;
		this.tipo_Movimiento = tipo_Movimiento;
		numMov++;
		this.numeroMovimiento = numMov;
		this.medioOperacion = medioOperacion;
	}
	
	@Override
	public String toString() {
		return "------------------------------------------------\nMovimiento: " + tipo_Movimiento 
				+ "\nFecha: " + fecha 
				+ "\nHorario: " + hora.getHour() + ":" + hora.getMinute() + ":" + hora.getSecond()
				+ "\nDetalles: " + detalles 
				+ "\nMonto: " + monto 
				+ "\nNumero movimiento: " + numeroMovimiento
				+ (medioOperacion != null ? 
						"\n"
						+"\nMedio: " + medioOperacion.getNombreMedio()
						+ "\nComision: " + medioOperacion.getComision()
						+ "\nTotal: " + (monto - medioOperacion.getComision()) : "")
				+ "\n------------------------------------------------";
	}

	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public LocalDateTime getHora() {
		return hora;
	}
	public void setHora(LocalDateTime hora) {
		this.hora = hora;
	}
	public String getDetalles() {
		return detalles;
	}
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	public Tipo_Movimiento getTipo_Movimiento() {
		return tipo_Movimiento;
	}
	
	public void setTipo_Movimiento(Tipo_Movimiento tipo_Movimiento) {
		this.tipo_Movimiento = tipo_Movimiento;
	}
	
	public int getNumeroMovimiento() {
		return numeroMovimiento;
	}
	
	public void setNumMov(int numeroMovimiento) {
		this.numeroMovimiento = numeroMovimiento;
	}
	
	public MedioOperacion getMedioOperacion() {
		return medioOperacion;
	}
	
	public void setMedioOperacion(MedioOperacion medioOperacion) {
		this.medioOperacion = medioOperacion;
	}
	
	public static int getNumMov() {
		return numMov;
	}
	
	public void setNumeroMovimiento(int numeroMovimiento) {
		this.numeroMovimiento = numeroMovimiento;
	}
}