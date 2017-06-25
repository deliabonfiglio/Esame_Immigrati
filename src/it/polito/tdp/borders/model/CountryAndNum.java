package it.polito.tdp.borders.model;

public class CountryAndNum implements Comparable<CountryAndNum>{
	private Country country;
	private int num;
	public CountryAndNum(Country country, int num) {
		super();
		this.country = country;
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
	
	//confrota 2 elementi second il creiterio del numero di stati decrescente
	@Override
	public int compareTo(CountryAndNum altro) {
		
		return -(this.num-altro.num);
	}
	
	
}
