package Model;

public class CompletelyPassiveAgent extends Agent {

	public CompletelyPassiveAgent() {
		super(AgentType.Completely_Passive);
	}

	@Override
	public boolean attack() {
		return true;
	}


	@Override
	public boolean place() {
		Country temp = getCountriesOwned().get(0);
		for (Country c : getCountriesOwned())
			if (c.getNumberArmies() < temp.getNumberArmies())
				temp = c;
		return place(temp);
	}

}
