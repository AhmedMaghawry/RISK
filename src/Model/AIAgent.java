package Model;

import java.util.ArrayList;
import java.util.List;

public abstract class AIAgent extends Agent {

	protected List<NState> game;
	protected List<Country> countries;
	protected NState currentState;

	public AIAgent(AgentType agentType) {
		super(agentType);
		game = new ArrayList<>();

	}

	protected abstract NState simulateGame(NState currentState);

	@Override
	public boolean attack() {
		boolean res = attack(getCountry(currentState.attack.getKey()), getCountry(currentState.attack.getValue()));
		return res;
	}

	@Override
	public boolean move() {
		return false;
	}

	@Override
	public boolean place() {
		if (game.isEmpty()) {
			currentState = simulateGame(currentState);
			if (!game.isEmpty())
				game.remove(0); // should not happened as the game should have ended
		} else {
			currentState = game.remove(0);
		}

		return place(getCountry(currentState.place.getKey()));
	}

	private Country getCountry(Integer key) {
		for (Country country : countries) {
			if (country.getCountryId() == key)
				return country;
		}
		return null;
	}

	public void setCurrentState(NState currentState) {
		this.currentState = currentState;
	}

}
