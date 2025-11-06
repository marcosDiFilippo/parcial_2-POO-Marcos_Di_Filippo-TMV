package CapaLogica;

public class MovimientoPorCajero extends Movimiento {

	private Cajero cajero;
	
	public MovimientoPorCajero(String detalles, double monto, Tipo_Movimiento tipo_Movimiento, Cajero cajero) {
		super(detalles, monto, tipo_Movimiento);
		this.cajero = cajero;
	}
	
	public Cajero getCajero() {
		return cajero;
	}
	
	public void setCajero(Cajero cajero) {
		this.cajero = cajero;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\nCajero: " + cajero.getUbicacion() + "\n------------------------------------------------";
	}
}
