package Model;

import javafx.util.Pair;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class AggressiveAgent extends Agent {

	public AggressiveAgent() {
		super(AgentType.Aggressive);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Pair<Country, Integer> place() {
		Country temp = getCountriesOwned().get(0);
		for (Country c : getCountriesOwned())
			if (c.getNumberArmies() > temp.getNumberArmies())
				temp = c;
		if (temp != null) {
			temp.addArmies(this.getBounceValue());
			temp.addArmies(this.newCountryBounce);
			int temp_num = this.newCountryBounce;
			this.newCountryBounce = 0;
			return new Pair<>(temp, temp_num + this.getBounceValue());
		}
		return null;
	}

	@Override
	public Pair<Country, Country> attack() {
		List<Pair<Country, Country>> avalibleAttacks = new ArrayList<>();
		for(Country c :getCountriesOwned()){
			avalibleAttacks.addAll(c.getAvalibleAttacks());
		}
		int max_damage=0;
		Pair<Country, Country> best_attack=null;
		for(Pair<Country, Country> p :avalibleAttacks)
			if(p.getValue().getNumberArmies()>max_damage){
				best_attack=p;
				max_damage=p.getValue().getNumberArmies();
			}
		if (attack(best_attack.getKey(),best_attack.getValue()))
			return new Pair<>(best_attack.getKey(), best_attack.getValue());
		return null;
	}

}
