package Model;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

public class SCountry {

    public int owner;
    public List<Integer> adj;

    public void setNumberArmies(int numberArmies) {
        this.numberArmies = numberArmies;
    }

    public int numberArmies;
    public int id;

    public SCountry(int id) {
        this.id = id;
        adj = new ArrayList<>();
    }

    public List<Pair<Integer, Integer>> getAvalibleAttacks(List<SCountry> allCountries) {
        List<Pair<Integer, Integer>> avalibleAttacks = new ArrayList<>();
        for (int country : adj) {
            SCountry c = allCountries.get(country);
            if (c.owner != owner && numberArmies - c.numberArmies > 1)
                avalibleAttacks.add(new Pair<>(id, c.id));
        }
        return avalibleAttacks;
    }

    public List<Pair<Integer, Integer>> getAllAttacks(List<SCountry> allCountries) {
        List<Pair<Integer, Integer>> avalibleAttacks = new ArrayList<>();
        for (int country : adj) {
            SCountry c = allCountries.get(country);
            if (c.owner != owner)
                avalibleAttacks.add(new Pair<>(id, c.id));
        }
        return avalibleAttacks;
    }

    protected SCountry clone() {
        SCountry country = new SCountry(id);
        country.owner = owner;
        country.adj = new ArrayList<>();
        country.numberArmies=numberArmies;
        for(Integer i :adj)
            country.adj.add(i.intValue());
        return country;
    }
}