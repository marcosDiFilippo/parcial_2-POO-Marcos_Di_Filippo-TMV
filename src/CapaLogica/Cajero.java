package CapaLogica;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Cajero {
	private String ubicacion;
	private double saldo;
	private ArrayList <Movimiento> movimientosCajero;
	private static ArrayList <Cajero> cajeros = new ArrayList<Cajero>();
	private MedioOperacion medioOperacion;
	
	public Cajero(String ubicacion, MedioOperacion medioOperacion) {
		this.ubicacion = ubicacion;
		this.saldo = 0;
		this.movimientosCajero = new ArrayList<Movimiento>();
		this.medioOperacion = medioOperacion;
	}

	public static void agregarCajero(Cajero cajero) {
		cajeros.add(cajero);
	}
	
	public void agregarMovimientos(Movimiento movimiento) {
		this.movimientosCajero.add(movimiento);
	}
	
	public static String[] incluirCajeros() {
		String [] cajeros = new String[getCajeros().size()];
		
		for (int i = 0; i < cajeros.length; i++) {
			cajeros[i] = getCajeros().get(i).getUbicacion();
		}
		
		return cajeros;
	}
	
	public boolean validarMontoMayorSaldo(double monto) {
		if (monto > this.saldo) {
			JOptionPane.showMessageDialog(null, "No hay fondos suficientes en el cajero, por favor ingrese un monto menor");
			return true;
		}
		return false;
	}
	
	public boolean validarTotalMayorSaldo(double monto, double comision) {
		double total = monto + comision;
		if (total > saldo) {
			JOptionPane.showMessageDialog(null, "No se ha podido realizar el retiro"
					+ "\nEl monto ingresado junto con la comision da un total de: " + total 
					+ "\nEl saldo del cajero es de: " + saldo);
			return true;
		}
		return false;
	}
	
	public void sumarSaldo(double monto) {
		this.saldo += monto;
	}
	
	public void restarSaldo(double monto) {
		this.saldo -= monto;
	}
	
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double monto) {
		this.saldo = monto;
	}
	
	public ArrayList<Movimiento> getMovimientosCajero() {
		return movimientosCajero;
	}
	
	public void setMovimientosCajero(ArrayList<Movimiento> movimientosCajero) {
		this.movimientosCajero = movimientosCajero;
	}
	
	public static ArrayList<Cajero> getCajeros() {
		return cajeros;
	}
	
	public static void setCajeros(ArrayList<Cajero> cajeros) {
		Cajero.cajeros = cajeros;
	}
	
	public MedioOperacion getMedioOperacion() {
		return medioOperacion;
	}
	
	public void setMedioOperacion(MedioOperacion medioOperacion) {
		this.medioOperacion = medioOperacion;
	}

	@Override
	public String toString() {
		return "Ubicacion: " + ubicacion + " | Saldo: " + saldo + "\n";
	}
}
