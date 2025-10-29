package CapaLogica;

import java.util.ArrayList;

public class Banco {
	private String nombre;
	private ArrayList <CuentaBancaria> cuentasBancarias;
	
	public Banco(String nombre) {
		this.nombre = nombre;
		this.cuentasBancarias = new ArrayList<CuentaBancaria>();
	}
	
	public void agregarCuentasBancarias(CuentaBancaria cuentaBancaria) {
		cuentasBancarias.add(cuentaBancaria);
	}
	
	public ArrayList<CuentaBancaria> getCuentasBancarias() {
		return cuentasBancarias;
	}
	
	public void setCuentasBancarias(ArrayList<CuentaBancaria> cuentasBancarias) {
		this.cuentasBancarias = cuentasBancarias;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
