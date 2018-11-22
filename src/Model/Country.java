package Model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Country {

    private int countryId;
    private Agent owner;
    private int numberArmies;
    private List<Country> adj;
    private Continent continentOwned;

    public Country(int countryId) {
        this.countryId = countryId;
        owner = null;
        numberArmies = 0;
        adj = new ArrayList<>();
        this.continentOwned = continentOwned;
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
        this.owner.addArmies(armies);
    }

    public void subArmies(int armies) {
        numberArmies -= armies;
        this.owner.subArmies(armies);
    }

    public void setNumberArmies(int numberArmies) {
        this.numberArmies = numberArmies;
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

    @Override
    public boolean equals(Object obj) {
        Country newC = (Country) obj;
        if (this.countryId == newC.countryId)
            return true;
        else
            return false;
    }

    public Continent getContinentOwned() {
        return continentOwned;
    }

    public void setContinentOwned(Continent continentOwned) {
        this.continentOwned = continentOwned;
    }

    public boolean isAdj(Country country) {
        return adj.contains(country);
    }

    public List<Pair<Country, Country>> getAvalibleAttacks() {
        List<Pair<Country, Country>> avalibleAttacks = new ArrayList<>();
        for (Country c : adj) {
            if (!c.owner.equals(this.owner) && this.numberArmies - c.numberArmies > 1)
                avalibleAttacks.add(new Pair<>(this, c));

        }
        return avalibleAttacks;
    }
}
