package Model;

import jdk.nashorn.internal.objects.AccessorPropertyDescriptor;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AStarAgent extends Agent {

    private NState currentState;
    private List<Country> countries;
    private int t = 0;
    private boolean first = true;

    public AStarAgent(List<Country> countries) {
        super(AgentType.A_Search);
        this.countries = countries;
    }

    @Override
    public boolean attack() {
        boolean res = attack(getCountry(currentState.attack.getKey()), getCountry(currentState.attack.getValue()));
        return res;
    }

    private Country getCountry(Integer key) {
        for (Country country : countries) {
            if (country.getCountryId() == key)
                return country;
        }
        return null;
    }

    @Override
    public boolean place() {
        currentState = simulateGame(currentState);
        return place(getCountry(currentState.place.getKey()));
    }

    private NState simulateGame(NState stateIn) {
        NState state = stateIn;
        Comparator<NState> nodeComparator = new Comparator<NState>() {
            @Override
            public int compare(NState o1, NState o2) {
                return  evaluate_f(o2) - evaluate_f(o1);
            }
        };

        PriorityQueue<NState> heap = new PriorityQueue<NState>(nodeComparator);
        for (NState ns : state.getSuccssors()){
            ns.parent = null;
            heap.add(ns);
        }

        while (!state.opponentCountris.isEmpty()) {
            state=heap.remove();
            if (first)
                t++;
            if (state.oppenentPlace == null)
                break;
            for (NState ns :state.getSuccssors()){
                ns.turn = state.turn + 1;
                heap.add(ns);
            }
        }

        while (state.parent != null)
            state = state.parent;
        first = false;
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
            gainFromAttack = getGain(getSCountry(state.place.getKey(), state.allCountries).numberArmies, getSCountry(state.attack.getValue(), state.allCountries).numberArmies, state.place.getValue());
        else
            gainFromAttack = getGain(getSCountry(prev.place.getKey(), prev.allCountries).numberArmies, getSCountry(prev.attack.getValue(), prev.allCountries).numberArmies, state.place.getValue());
        heuristic_h = (int)Math.ceil(first_part) + gainFromAttack + saedBounce;
        return heuristic_h;
    }

    private int getEnemiesAtContinent(SContinent continent, List<SCountry>countries) {
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

    public void setCurrentState(NState currentState) {
        this.currentState = currentState;
    }

    public int getT() {
        return t;
    }
}
