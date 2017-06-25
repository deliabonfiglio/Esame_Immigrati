package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento>{
	//c'è un solo tipo di evento che è l'ingresso
	
	private int num; //quante persone entrate
	private Country country;
	private int time; // a quale istante di tempo
	public Evento(int num, Country country, int time) {
		super();
		this.num = num;
		this.country = country;
		this.time = time;
	}
	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}
	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}
	@Override
	public int compareTo(Evento altro) {
		return this.time-altro.time;
	}
	
	

}
