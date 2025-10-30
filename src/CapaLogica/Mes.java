package CapaLogica;

public enum Mes {
	ENERO(1, 31)
	, FEBRERO(2, 28)
	, MARZO(3, 31)
	, ABRIL(4, 30)
	, MAYO(5, 31)
	, JUNIO(6, 30)
	, JULIO(7, 31)
	, AGOSTO(8, 31)
	, SEPTIEMBRE(9, 30)
	, OCTUBRE(10, 31)
	, NOVIEMBRE(11, 30)
	, DICIEMBRE(12, 31);

	private int numero;
	
	private String [] dias;
	
	private Mes(int numero, int cantidadDias) {
		this.numero = numero;
		this.dias = new String[cantidadDias];
	}
	
	public String[] getDias() {
		return dias;
	}
	
	
	public int getNumero() {
		return numero;
	}
}