package it.polito.tdp.borders.model;

import java.util.*;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {
	//parametri di simulazione
	
	private int INIZIALI = 1000;
	private double PERC_STANZIALI = 0.5;
	
	//modello del mondo
	//per ogni nazione devo sapere quanti stanziali ci sono
	private Map<Country, Integer> stanziali;
	private UndirectedGraph<Country, DefaultEdge> graph;
	private int passi;
	
	//coda degli eventi
	private PriorityQueue<Evento> coda;
	
	public Simulatore(UndirectedGraph<Country, DefaultEdge> graph){
		this.graph= graph;
		this.stanziali= new HashMap<Country, Integer>();
		
		for(Country cvertex: graph.vertexSet())
			stanziali.put(cvertex, 0);
		
		this.coda = new PriorityQueue<Evento>();
	}
	
	public void inserisci(Country c){
		Evento e = new Evento(INIZIALI, c, 1);
		coda.add(e);
	}

	public void run(){
		this.passi=0;
		
		while(!coda.isEmpty()){
			Evento e = coda.poll();
			this.passi= e.getTime();//voglio il t massimo e poichè la coda è ordinata in base al tempo alla fine del while ho il tmaz
			//gestisco gli stanzali approssimando per difetto= cast a int
			int stanz = (int) (e.getNum()*PERC_STANZIALI);
			
			//gestisco i nomandi
			int confinanti = Graphs.neighborListOf(graph, e.getCountry()).size();
			int nomadi = (e.getNum()-stanz)/confinanti;
			
			//devo aggiungere il resto della divisione xk sto troncando
			stanz = e.getNum()- nomadi * confinanti;
			
			//aggiornare il modell del mondo, quindi contabilizzare gli stanziali incrementando il valore
			stanziali.put(e.getCountry(),stanziali.get(e.getCountry())+stanz);
			
			
			if(nomadi>0){
			
			//schedulare gli eventi futuri e inserire le destinazioni dei nomadi
				for(Country c : Graphs.neighborListOf(graph, e.getCountry())){
					Evento ev = new Evento(nomadi, c, e.getTime()+1);
					coda.add(ev);
				}
			}
		}
	}
	
	public int getPassi(){
		return this.passi;
	}
	
	public List<CountryAndNum> getPresenti(){
		List<CountryAndNum> presenti = new ArrayList<CountryAndNum> ();
		
		for(Country c : stanziali.keySet()){
			if(stanziali.get(c)>0){
				presenti.add(new CountryAndNum(c, stanziali.get(c)));
			}
		}
		Collections.sort(presenti);
		return presenti;
	}
}

