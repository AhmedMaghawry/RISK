import Model.*;

import java.util.ArrayList;
import java.util.List;

public class MainTest {

    public static void main(String[] args) {
        Country eg = new Country(1);
        Country alg = new Country(2);
        eg.addAdj(alg);
        alg.addAdj(eg);
        Country fr = new Country(3);
        Country eng = new Country(4);
        fr.addAdj(eng);
        eng.addAdj(fr);

        alg.addAdj(fr);
        fr.addAdj(alg);

        List afrList = new ArrayList<>();
        afrList.add(eg);
        afrList.add(alg);

        List eroList = new ArrayList<>();
        eroList.add(fr);
        eroList.add(eng);

        Continent afr = new Continent(3, afrList);
        Continent ero = new Continent(5, eroList);

        Map map = Map.getIntance();
        map.addContinent(afr);
        map.addContinent(ero);

        print("Map Creation Finished");

        HumanAgent player = new HumanAgent();
        player.addCountry(eg);
        player.addCountry(alg);
        eg.addArmies(5);
        alg.addArmies(4);
        print("The Attack Happen : " + player.attack_for_human(eg, alg));
        print("The Attack Happen : " + player.attack_for_human(eg, fr));
        print("The Attack Happen : " + player.attack_for_human(alg, fr));
        print("The Attack Happen : " + player.attack_for_human(alg, fr));
        print("The Attack Happen : " + player.attack_for_human(alg, eng));
        print("The Attack Happen : " + player.attack_for_human(fr, eng));

        print("The Attack Finished");
    }

    public static void print(String s) {
        System.out.println(s);
    }
}
