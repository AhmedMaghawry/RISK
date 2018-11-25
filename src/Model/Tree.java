package Model;

import javafx.util.Pair;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Tree {

    private Map map;
    private Agent agent;
    private Country bestPlace;
    private Country bestAttack;

    public Tree (Map map, Agent agent) {
        this.map = map;
        this.agent = agent;
        start();
    }

    private void start() {
        Comparator<Node> nodeComparator = new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return  o2.evaluate_f() - o1.evaluate_f();
            }
        };

        PriorityQueue<Node> heap = new PriorityQueue<Node>(nodeComparator);

        for (Continent continent : map.getContinents()) {
            for (Country country : continent.getCountries()) {
                if (!country.isBelongAgent(agent))
                    continue;
                for (Country adj : country.getAllAvalibleAttacks()) {
                    boolean done = false;
                    for (Continent x : map.getContinents()) {
                        for (Country y : x.getCountries()) {
                            if (y.getCountryId() == adj.getCountryId()) {
                                heap.add(new Node(country,y, null));
                                done = true;
                            }
                            if (done)
                                break;
                        }
                        if (done)
                            break;
                    }
                }
            }
        }

        while (!heap.isEmpty()) {
            Node x = heap.remove();

            //x.setPlacer(x.getPlace().getOwner());
            //x.setEnemy(x.getAttack().getOwner());
            x.getPlace().getOwner().place(x.getPlace());
            x.getPlace().getOwner().attack(x.getPlace(), x.getAttack());
            //agent.place(x.getPlace());
            //agent.attack(x.getPlace(), x.getAttack());

            if (x.isGoal()) {
                while (x.getParent() != null)
                    x = x.getParent();
                bestPlace = x.getPlace();
                bestAttack = x.getAttack();
                break;
            }

            for (Continent continent : map.getContinents()) {
                for (Country country : continent.getCountries()) {
                    if (!country.isBelongAgent(x.getPlace().getOwner()))
                        continue;
                    for (Country adj : country.getAllAvalibleAttacks()) {
                        boolean done = false;
                        for (Continent z : map.getContinents()) {
                            for (Country y : z.getCountries()) {
                                if (y.getCountryId() == adj.getCountryId()) {
                                    if (!y.isBelongAgent(x.getPlace().getOwner())) {
                                        country.setOwner(x.getPlacer());
                                        y.setOwner(x.getEnemy());
                                        Node node = new Node(country,y, x);
                                        //node.setEnemy(x.getEnemy());
                                        //node.setPlacer(x.getPlacer());
                                        heap.add(node);
                                    }
                                    done = true;
                                }
                                if (done)
                                    break;
                            }
                            if (done)
                                break;
                        }
                    }
                }
            }

        }
    }

    public Pair<Country, Country> getBestAttack() {
        return new Pair<>(bestPlace, bestAttack);
    }

    public Country getBestPlace() {
        return bestPlace;
    }
}
