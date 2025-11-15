package CapaLogica;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Banco {
	private String nombre;
	private ArrayList <CuentaBancaria> cuentasBancarias;
	private ArrayList <CuentaInversion> cuentasInversion;
	
	public Banco(String nombre) {
		this.nombre = nombre;
		this.cuentasBancarias = new ArrayList<CuentaBancaria>();
		this.cuentasInversion = new ArrayList<CuentaInversion>();
	}
	
	public void agregarCuentasBancarias(CuentaBancaria cuentaBancaria) {
		cuentasBancarias.add(cuentaBancaria);
	}
	
	public void agregarCuentasInversion(CuentaInversion cuentaInversion) {
		cuentasInversion.add(cuentaInversion);
	}
	
	public List<CuentaBancaria> filtrarCuentas(String alias) {
		List <CuentaBancaria> cuentasFiltradas = cuentasBancarias.stream()
				.filter(cuenta -> !cuenta.getAlias().equals(alias))
				.collect(Collectors.toList());
		
		return cuentasFiltradas;
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
	
	public ArrayList<CuentaInversion> getCuentasInversion() {
		return cuentasInversion;
	}
	
	public void setCuentasInversion(ArrayList<CuentaInversion> cuentasInversion) {
		this.cuentasInversion = cuentasInversion;
	}
}
