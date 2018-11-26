package Model;

import java.util.ArrayList;
import java.util.List;

public class GreedyAgent extends AIAgent{

    public GreedyAgent(List<Country> countries) {
        super(AgentType.Greedy);
        this.countries = countries;
    }
    
    @Override
    protected NState simulateGame(NState s)  {
        game =new ArrayList<>();
        while(! s.opponentCountris.isEmpty()){
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
        return game.get(0);
    }

}