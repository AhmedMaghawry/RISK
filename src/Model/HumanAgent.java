package Model;

//import javafx.util.Pair;

import java.util.List;

public class HumanAgent extends Agent {

    public HumanAgent() {
        super(AgentType.Human);
    }

    //Only for Human Agent
    public boolean attack(Country from, Country to) {
        if (from == null || to == null || from.getOwner() == null)
            return false;

        if (!from.getOwner().equals(this) || (to.getOwner() != null && to.getOwner().equals(this)) || !from.isAdj(to))
            return false;

        if (to.getOwner() == null) {
            from.setNumberArmies(1);
            to.setNumberArmies(from.getNumberArmies() - to.getNumberArmies() - 1);
            addCountry(to);
            newCountryBounce = 2;
            return true;
        }

        if (from.getNumberArmies() - to.getNumberArmies() > 1) {
            subArmies(to.getNumberArmies());
            from.setNumberArmies(1);
            to.setNumberArmies(from.getNumberArmies() - to.getNumberArmies() - 1);
            addCountry(to);
            to.getOwner().subCountry(to);
            newCountryBounce = 2;
        } else {
            subArmies(from.getNumberArmies());
            from.setNumberArmies(0);
            subCountry(from);
        }
        return true;
    }

    //Only for Human Agent
    public boolean place(Country to) {
        if (to == null)
            return false;

        if (to.isBelongAgent(this)) {
            to.addArmies(getBounceValue() + newCountryBounce);
            newCountryBounce = 0;
        } else
            return false;
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
