package Model;

import java.util.List;

public class Continent {

    private int bounceAdd;
    private List<Country> countries;

    public Continent(int bounceAdd, List<Country> countries) {
        this.bounceAdd = bounceAdd;
        this.countries = countries;
    }

    public int getBounceAdd() {
        return bounceAdd;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public boolean isSingleOwner(List<Country> countries_agent) {
        for (Country c : countries) {
            boolean flag = false;
            for (Country a : countries_agent) {
                if (c.getCountryId() == a.getCountryId()) {
                    flag = true;
                    break;
                }
            }
            if (!flag)
                return false;
        }
        return true;
    }

    public void addCountry(Country country) {
        countries.add(country);
    }
}
