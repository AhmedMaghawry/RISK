package Model;

import javafx.util.Pair;

import java.util.List;

public class HumanAgent extends Agent {

    public HumanAgent() {
        super(AgentType.Human);
    }

    //Only for Human Agent
    public boolean attack(List<Pair<Pair<Country, Country>,Integer>> input) {
        //TODO:Implement
        return true;
    }

    //Only for Human Agent
    public boolean move(List<Pair<Pair<Country, Country>,Integer>> input) {
        //TODO:Implement
        return true;
    }

    //Only for Human Agent
    public boolean place(List<Pair<Country, Integer>> input) {
        for (Pair<Country, Integer> x : input) {
            if (x.getKey().isBelongAgent(this)) {
                x.getKey().addArmies(x.getValue());
                addArmies(x.getValue());
            } else
                return false;
        }
        return true;
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
