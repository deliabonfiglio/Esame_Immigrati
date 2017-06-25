package it.polito.tdp.borders.model;

import java.util.*;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private UndirectedGraph<Country, DefaultEdge> graph;
	private List<Country> countries;
	private Map<Integer, Country> countryMap;
	private List<CountryAndNum> stanziali ;
	
	public Model(){
		this.countries= new ArrayList<Country>();
		this.countryMap = new HashMap<Integer,Country>();

	}
	
	public List<Country>getCountries(){
		if(this.countries.isEmpty()){
			BordersDAO dao = new BordersDAO();
			countries= dao.loadAllCountries();
		
			for(Country c: countries)
				countryMap.put(c.getcCode(),c);
		}
		return countries;
	}
	
	public List<CountryAndNum> createGraph(int anno){
		//cosi ogni volta che viene chiamato il metodo lo crea
		this.graph= new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		
		//aggiungi vertici
		Graphs.addAllVertices(graph, this.getCountries());
		
		//aggiungi archi con query che ritorna coppie di country il cui anno è <= di quello passato
		BordersDAO dao = new BordersDAO();
		List<IntegerPair> confini = dao.getCountryPair(anno);
		
		
		for(IntegerPair iptemp: confini){
			graph.addEdge(this.countryMap.get(iptemp.getN1()), this.countryMap.get(iptemp.getN2()));
		}
		
		List<CountryAndNum> lista = new ArrayList<CountryAndNum>();
		for(Country ctemp:this.getCountries()){
			int confinanti= Graphs.neighborListOf(graph, ctemp).size();
				if(confinanti!= 0)
					lista.add(new CountryAndNum(ctemp, confinanti));
		}
		
		Collections.sort(lista);
		
		return lista;
	}
	
	public static void main(String args[]){
		Model m = new Model();
		List<CountryAndNum> lista = m.createGraph(2000);
		System.out.println(m.graph);
		System.out.println("---"+m.graph.edgeSet().size());
		
		for(CountryAndNum c: lista){
			System.out.format("%s:%d\n", c.getCountry().toString(), c.getNum());
		}
		
		m.createGraph(1900);
		System.out.println(m.graph);
	}

	public int simula(Country partenza) {
		//predispone e attiva la simulazione nuovo con dei dati puliti
		Simulatore sim = new Simulatore(graph);
		sim.inserisci(partenza);
		sim.run();
		this.stanziali= sim.getPresenti();
		return sim.getPassi();
	}

	public List<CountryAndNum> getStanziali() {
		// non posso chiedere qui al simulatore, perchè userei un simulatore diverso
		return this.stanziali;
	}
	
}
