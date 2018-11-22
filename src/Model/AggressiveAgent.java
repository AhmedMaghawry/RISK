package Model;

import java.awt.Point;
import java.util.List;

public class AggressiveAgent extends Agent {

	public AggressiveAgent() {
		super(AgentType.Aggressive);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean place() {
		Country temp = getCountriesOwned().get(0);
		for (Country c : getCountriesOwned())
			if (c.getNumberArmies() > temp.getNumberArmies())
				temp = c;
		if (temp != null) {
			temp.addArmies(this.getBounceValue());
			temp.addArmies(this.newCountryBounce);
			this.newCountryBounce = 0;
			return true;
		}
		return false;
	}

	@Override
	public boolean attack() {

		List<Country> Allcountries = getAllcountries();
		List<Point> AllEdges = getAllEdges();
		Point result = null;
		int tempDamage = 0;
		for (Point edge : AllEdges)
			if (Allcountries.get(edge.x).getOwner().equals(this) || Allcountries.get(edge.y).getOwner().equals(this)) {
				int remain;
				if (Allcountries.get(edge.x).getOwner().equals(this))
					remain = Allcountries.get(edge.x).getNumberArmies() - Allcountries.get(edge.y).getNumberArmies();
				else
					remain = Allcountries.get(edge.x).getNumberArmies() - Allcountries.get(edge.y).getNumberArmies();
				int damage = Math.min(Allcountries.get(edge.x).getNumberArmies(),
						Allcountries.get(edge.y).getNumberArmies());
				if (remain > 1 && damage > tempDamage) {
					tempDamage = damage;
					result = edge;
				}
			}
		if(result == null)
			return false ;
		return true;

	}

	@Override
	public boolean move() {
		// TODO Auto-generated method stub
		return true;
	}

}
