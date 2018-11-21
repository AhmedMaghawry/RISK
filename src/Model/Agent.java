package Model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Agent {

    private int bounceValue;
    private AgentType type;
    private String agentId;
    private AgentState state;
    private int numberOfArmies;
    private List<Country> countriesOwned;

    public Agent(AgentType agentType) {
        this.type = agentType;
        state = AgentState.Alive;
        bounceValue = 2;
        agentId = generateId();
        numberOfArmies = 0;
        countriesOwned = new ArrayList<>();
    }

    public abstract boolean attack();
    public abstract boolean move();
    public abstract boolean place();

    public void addBounce(int bounceValue) {
        this.bounceValue += bounceValue;
    }

    public void decBounce(int bounceValue) {
        this.bounceValue -= bounceValue;
    }

    public boolean isAlive() {
        return state == AgentState.Alive;
    }

    public void updateState() {
        if (numberOfArmies == 0)
            this.state = AgentState.Dead;
        else
            this.state = AgentState.Alive;
    }

    public void addArmies(int armies) {
        numberOfArmies += armies;
    }

    public List<Country> getCountriesOwned() {
        return countriesOwned;
    }

    public void addCountry(Country country) {
        countriesOwned.add(country);
    }

    @Override
    public boolean equals(Object obj) {
        Agent newAgent = (Agent) obj;
        return this.agentId.equals(newAgent.agentId);
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}
