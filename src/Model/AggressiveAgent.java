package Model;

import javafx.util.Pair;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class AggressiveAgent extends Agent {

	public AggressiveAgent() {
		super(AgentType.Aggressive);
	}

	@Override
	public boolean place() {
		Country temp = getCountriesOwned().get(0);
		for (Country c : getCountriesOwned())
			if (c.getNumberArmies() > temp.getNumberArmies())
				temp = c;
		if (temp != null) {
			temp.addArmies(this.getBounceValue());
			temp.addArmies(this.newCountryBounce);
			this.newCountryBounce = 0;
			return true;
		}
		return false;
	}

	@Override
	public boolean attack() {
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
		return attack(best_attack.getKey(),best_attack.getValue());
	}

}
