package Model;

import javafx.util.Pair;

public class CompletelyPassiveAgent extends Agent {

	public CompletelyPassiveAgent() {
		super(AgentType.Completely_Passive);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Pair<Country, Country> attack() {
		return null;
	}

	@Override
	public Pair<Country, Integer> place() {
		Country temp = getCountriesOwned().get(0);
		for (Country c : getCountriesOwned())
			if (c.getNumberArmies() < temp.getNumberArmies())
				temp = c;
		int val = temp.getOwner().getBounceValue() + temp.getOwner().newCountryBounce;
		if (place(temp))
			return new Pair<>(temp, val);
		return null;
	}

}
