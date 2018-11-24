package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GreedyAgent extends Agent{

	public GreedyAgent() {
		super(AgentType.Greedy);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean attack() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean move() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean place() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<NState> generate_game(NState s) {
		List<NState> game =new ArrayList<>();
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
		return game;
	}
	

}
