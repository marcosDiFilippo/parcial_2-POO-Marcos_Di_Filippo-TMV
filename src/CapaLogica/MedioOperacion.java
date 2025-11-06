package CapaLogica;

public class MedioOperacion {
	private NombreMedio nombreMedio;
	private double comision;
	
	public MedioOperacion(NombreMedio nombreMedio, double comision) {
		this.nombreMedio = nombreMedio;
		this.comision = comision;
	}
	
	public MedioOperacion(NombreMedio nombreMedio) {
		this.nombreMedio = nombreMedio;
	}
	
	public NombreMedio getNombreMedio() {
		return nombreMedio;
	}

	public void setNombreMedio(NombreMedio nombreMedio) {
		this.nombreMedio = nombreMedio;
	}

	public double getComision() {
		return comision;
	}

	public void setComision(double comision) {
		this.comision = comision;
	}
}