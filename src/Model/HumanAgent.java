package Model;

//import javafx.util.Pair;

import java.util.List;

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
    public boolean attack() {
        return false;
    }

    @Override
    public boolean move() {
        return false;
    }

    @Override
    public boolean place() {
        return false;
    }
}
