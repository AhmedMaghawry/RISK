package Model;

//import javafx.util.Pair;

import javafx.util.Pair;

public class HumanAgent extends Agent {

    public HumanAgent() {
        super(AgentType.Human);
    }

    public boolean attack_for_human(Country from, Country to) {
        return attack(from, to);
    }

    public boolean place_for_human(Country to) {
        return place(to);
    }

    @Override
    public Pair<Country, Country> attack() {
        return null;
    }

    @Override
    public Pair<Country, Integer> place() {
        return null;
    }
}
