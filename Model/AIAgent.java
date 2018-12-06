package Model;

import javafx.util.Pair;

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
    public Pair<Country, Country> attack() {
        if (currentState.attack == null)
            return null;
        Country atak = getCountry(currentState.attack.getKey());
        Country ataked = getCountry(currentState.attack.getValue());
        boolean res = attack(atak, ataked);
        if (res)
            return new Pair<>(atak, ataked);
        return null;
    }

    @Override
    public Pair<Country, Integer> place() {
        if (game.isEmpty()) {
            currentState = simulateGame(currentState);
        } else {
            currentState = game.remove(0);
        }
        Country placed = getCountry(currentState.place.getKey());
        int val = placed.getOwner().getBounceValue() + placed.getOwner().newCountryBounce;
        if (place(placed))
            return new Pair<>(placed, val);
        return null;
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