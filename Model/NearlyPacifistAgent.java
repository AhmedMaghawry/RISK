package Model;

import javafx.util.Pair;

public class NearlyPacifistAgent extends Agent {

    public NearlyPacifistAgent() {
        super(AgentType.Nearly_Pacifist);
    }

    @Override
    public Pair<Country, Country> attack() {
        Country to  = getCountriesOwned().get(0).getAdj().get(0);
        Country from  = getCountriesOwned().get(0);
        int mindiff = 999;
        for (Country c : getCountriesOwned()) {
            for (Country adj : c.getAdj()) {
                if (adj.isBelongAgent(this))
                    continue;
                else if ((c.getNumberArmies() - adj.getNumberArmies() > 1) && adj.getNumberArmies() < mindiff) {
                    to = adj;
                    from = c;
                    mindiff = adj.getNumberArmies();
                }
            }
        }
        if (attack(from, to))
            return new Pair<>(from, to);
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
