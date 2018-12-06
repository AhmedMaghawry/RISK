package Model;

import jdk.nashorn.internal.objects.AccessorPropertyDescriptor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AStarAgent extends AIAgent {

	protected NState currentState;
	protected int depth;

	public AStarAgent(List<Country> countries, int depth) {
		super(AgentType.A_Search);
		this.countries = countries;
		this.depth = depth;
	}

	@Override
	protected NState simulateGame(NState stateIn) {
		int dd = stateIn.turn + depth + 1;
		System.out.println("-----------------------------here-------------------------------------");
		NState state = stateIn;
		Comparator<NState> nodeComparator = new Comparator<NState>() {
			@Override
			public int compare(NState o1, NState o2) {
				return evaluate_f(o2) - evaluate_f(o1);
			}
		};

		PriorityQueue<NState> heap = new PriorityQueue<NState>(nodeComparator);
		for (NState ns : state.getSuccssors()) {
			ns.parent = null;
			heap.add(ns);
		}

		while (!state.opponentCountris.isEmpty()) {
			state = heap.remove();
			if (state.oppenentPlace == null)
				break;
			// for real time a search ;
			if (state.turn > dd)
				break;
			for (NState ns : state.getSuccssors()) {
				ns.turn = state.turn + 1;
				if(ns.turn<=dd)
					heap.add(ns);
			}
		}
		game = new ArrayList();
		while (state.parent != null) {
			game.add(0, state);
			state = state.parent;

		}
		return state;
	}

	private int evaluate_h(NState state) {
		int heuristic_h = 0;
		int saedBounce = state.getAvailableAttacks().size() + state.getMyBounce();
		if (state.attack == null)
			return saedBounce;
		SContinent continentHappenAttack = getSContinent(state.continents, state.attack.getValue());
		int num = getEnemiesAtContinent(continentHappenAttack, state.allCountries) + 1;
		float ratio = (float) (1.0 / num);
		float first_part = ratio * continentHappenAttack.bounse;
		NState prev = state.parent;
		int gainFromAttack;
		if (prev == null)
			gainFromAttack = getGain(getSCountry(state.place.getKey(), state.allCountries).numberArmies,
					getSCountry(state.attack.getValue(), state.allCountries).numberArmies, state.place.getValue());
		else
			gainFromAttack = getGain(getSCountry(prev.place.getKey(), prev.allCountries).numberArmies,
					getSCountry(prev.attack.getValue(), prev.allCountries).numberArmies, state.place.getValue());
		heuristic_h = (int) Math.ceil(first_part) + gainFromAttack + saedBounce;
		return heuristic_h;
	}

	private int getEnemiesAtContinent(SContinent continent, List<SCountry> countries) {
		int num = 0;
		for (int c : continent.countries) {
			SCountry country = getSCountry(c, countries);
			if (country.owner != 1)
				num++;
		}
		return num;
	}

	private SCountry getSCountry(int c, List<SCountry> countries) {
		for (SCountry country : countries) {
			if (country.id == c)
				return country;
		}
		return null;
	}

	private SContinent getSContinent(List<SContinent> continents, Integer country) {
		for (SContinent continent : continents) {
			if (continent.countries.contains(country))
				return continent;
		}
		return null;
	}

	private int getGain(int agentArmies, int enemyArmies, int bounce) {
		if ((agentArmies + bounce) - enemyArmies <= 1)
			return -1 * enemyArmies;
		return bounce - enemyArmies;
	}

	public int evaluate_f(NState state) {
		return evaluate_h(state) - state.turn * state.allCountries.size();
	}

}
