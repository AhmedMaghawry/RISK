package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GreedyAgent extends Agent{

    private List<NState> game;
    private List<Country> countries;
    private NState currentState;
    private int t = 0;

    public GreedyAgent(List<Country> countries) {
        super(AgentType.Greedy);
        this.countries = countries;
    }

    @Override
    public boolean attack() {
        NState g = game.get(getTurns());
        return attack(getCountry(g.attack.getKey()), getCountry(g.attack.getValue()));
    }

    @Override
    public boolean place() {
        if (game == null)
            game = generate_game(NState.globalState);
        NState g = game.get(getTurns());
        return place(getCountry(g.place.getKey()));
    }

    public List<NState> generate_game(NState s) {
        List<NState> game =new ArrayList<>();
        while(! s.opponentCountris.isEmpty()){
            t++;
            List<NState> childs = s.getSuccssors();
            NState best=childs.get(0);
            for (NState ns :childs){
                if(ns.damage>best.damage)
                    best=ns;
                else if(ns.damage == best.damage)
                    if(ns.getMyBounce()>best.getMyBounce())
                        best=ns;
            }
            game.add(best);
            s=best;
        }
        return game;
    }

    private Country getCountry(Integer key) {
        for (Country country : countries) {
            if (country.getCountryId() == key)
                return country;
        }
        return null;
    }

    public void setCurrentState(NState currentState) {
        this.currentState = currentState;
    }

    public int getT() {
        return t;
    }
}