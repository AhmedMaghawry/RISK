package Model;

import java.util.ArrayList;
import java.util.List;

public class Country {

    private int countryId;
    private Agent owner;
    private int numberArmies;
    private List<Country> adj;

    public Country(int countryId) {
        this.countryId = countryId;
        owner = null;
        numberArmies = 0;
        adj = new ArrayList<>();
    }

    public int getCountryId() {
        return countryId;
    }

    public Agent getOwner() {
        return owner;
    }

    public int getNumberArmies() {
        return numberArmies;
    }

    public void addArmies(int armies) {
        numberArmies += armies;
    }

    public void subArmies(int armies) {
        numberArmies -= armies;
    }

    public void setOwner(Agent owner) {
        this.owner = owner;
    }

    public List<Country> getAdj() {
        return adj;
    }

    public void addAdj(Country country) {
        adj.add(country);
    }

    public boolean isBelongAgent(Agent agent) {
        return owner.equals(agent);
    }
}
