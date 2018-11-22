package Model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class State {
	List<Country> myCountries, oppenentCountris;
	Pair<Country, Integer> place;
	Pair<Country, Country> attack;
	State parent;

	public State() {
		// TODO Auto-generated constructor stub
	}

	public State(List<Country> myCountries, List<Country> oppenentCountris, Pair<Country, Integer> place,
			Pair<Country, Country> attack, State parent) {
		this.myCountries = myCountries;
		this.oppenentCountris = oppenentCountris;
		this.place = place;
		this.attack = attack;
		this.parent = parent;

	}

	public List<State> getSuccssors() {
		List<State> successos = new ArrayList<>();
		List<Pair<Country, Country>> avalibleAttacks = new ArrayList<>();
		for (Country c : myCountries) {
			avalibleAttacks.addAll(c.getAvalibleAttacks());
		}
		for (Country c : myCountries) {
			int bounse =Math.min(3,myCountries.size()/3);
			if(!attack.equals(null))
				bounse+=2;
			c.addArmies(bounse);
			Pair<Country, Integer> tempPlace=new Pair(c,bounse);
			
			for(Pair<Country, Country> p :avalibleAttacks){
				int damage =p.getValue().getNumberArmies();
				p.getValue().addArmies(-damage+1);			// assume only put one :) 
				p.getValue().setOwner(p.getKey().getOwner());
				List<Country>TMy=new ArrayList<>(myCountries);
				List<Country>TOp=new ArrayList<>(oppenentCountris);
//				t
//				TOp.remove(p.getValue());
				
				TMy.add(p.getValue());
				
				//State temp = new State(n));
			}
				
			
		}
		return null;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new State(myCountries,  oppenentCountris, place, attack, parent);
	}

	public static void main(String[] args) {
		List<Country> countries = new ArrayList<>();
		Agent a =new AggressiveAgent();
		for (int i = 0; i < 10; i++) {
			countries.add(new Country(i));
			countries.get(i).setOwner(a);
		}
		List<Country> countries2 = new ArrayList<>(countries);
		countries2.get(0).addArmies(10);

		Pair<Integer, Integer> x = new Pair<>(10, 15);
	}
}
