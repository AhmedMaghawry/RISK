package Model;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private List<Continent> continents;
    private static Map graph = null;

    public static  Map getIntance(){
        if (graph == null)
            graph = new Map();
        return graph;
    }

    private Map() {
        this.continents = new ArrayList<>();
    }

    public void addContinent(Continent continent) {
        continents.add(continent);
    }
    public List<Continent> getContinents() {
        return continents;
    }
}