package dao;

public class Data implements Comparable<Data>{

	private int dia;
	private int mes;
	private int ano;
	
	public Data(int dia, int mes, int ano) {
		this.dia = dia;
		this.mes = mes;
		this.ano = ano;
	}
	
	public int getDia() {
		return dia;
	}
	
	public int getMes() {
		return mes;
	}
	
	public int getAno() {
		return ano;
	}
	
	@Override
	public String toString() {
		return dia + "/" + mes + "/" + ano;
	}

	@Override
	public int compareTo(Data outraData) {
		if(ano != outraData.getAno())
			return ano - outraData.getAno(); 
		
		if(mes != outraData.getMes())
			return mes - outraData.getMes(); 
		
		if(dia != outraData.getDia())
			return dia - outraData.getDia(); 
		
		return 0;
	}
	
}
