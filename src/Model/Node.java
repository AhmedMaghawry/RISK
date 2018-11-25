package Model;

public class Node {

    private int numOfTurn_g;
    private int heuristic_h;
    private Country place;
    private Country attack;
    private Node parent;
    private Agent placer;
    private Agent enemy;

    public Node(Country place, Country attack, Node parent) {
        this.place = place;
        this.attack = attack;
        this.parent = parent;
        if (parent != null)
            this.numOfTurn_g = parent.getNumOfTurn_g() + 1;
        else
            this.numOfTurn_g = 1;
        placer = place.getOwner();
        enemy = attack.getOwner();
        evaluate_h();
    }

    private void evaluate_h() {
        float num = getEnemiesAtContinent(enemy, attack.getContinentOwned());
        if (num == 0) {
            heuristic_h = Integer.MAX_VALUE;
            return;
        }
        float ratio = (float) (1.0 / num);
        float first_part = ratio * attack.getContinentOwned().getBounceAdd();
        int gainFromAttack = getGain(place.getNumberArmies(), attack.getNumberArmies(), placer.getBounceValue() + placer.newCountryBounce);
        heuristic_h = (int)Math.ceil(first_part) + gainFromAttack;
    }

    private int getGain(int agentArmies, int enemyArmies, int bounce) {
        if ((agentArmies + bounce) - enemyArmies <= 1)
            return -1 * enemyArmies;
        return bounce - enemyArmies;
    }

    public int evaluate_f() {
        return heuristic_h - numOfTurn_g;
    }

    public int getNumOfTurn_g() {
        return numOfTurn_g;
    }

    public boolean isGoal() {
        if (enemy.getNumberOfArmies() == 0)
            return true;
        return false;
    }

    private float getEnemiesAtContinent(Agent enemy, Continent continent) {
        float num = 0;
        for (Country c : continent.getCountries()) {
            if (c.isBelongAgent(enemy))
                num++;
        }
        return num;
    }

    public Node getParent() {
        return parent;
    }

    public Country getPlace() {
        return place;
    }

    public Country getAttack() {
        return attack;
    }

    public void setPlacer(Agent placer) {
        this.placer = placer;
    }

    public void setEnemy(Agent enemy) {
        this.enemy = enemy;
    }

    public Agent getPlacer() {
        return placer;
    }

    public Agent getEnemy() {
        return enemy;
    }
}
