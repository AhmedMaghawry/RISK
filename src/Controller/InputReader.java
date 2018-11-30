package Controller;

import Model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InputReader {
   private ArrayList< String > lines = new ArrayList<>();
   private  List<Country> nearCountries;
   private File input;
   private int verticesNum=0;
   private int edgesNum=0;
   private int continentsNum=0;
   private List<Country> vertices = new ArrayList<>();
   private Country vertex;
   private String line;
   private String[] lineElements;
   private String[]  player1Line;
   private String[]  player2Line;
   private ArrayList<String> countriesArmyLines = new ArrayList<>();
   private  int i=0,j=0,k=0;
   private ArrayList<Integer> allbonues = new ArrayList<>();
   private static InputReader reader = null;
   private List<SCountry> allSCountries = new ArrayList<>();
   private List<Integer> myCountries = new ArrayList<>(), opponentCountris = new ArrayList<>();
   private List<SContinent> continents = new ArrayList<>();
   private  ArrayList<Integer> nearSCountries;

   public static  InputReader getIntance(){
        if (reader == null)
            reader = new InputReader();
        return reader;
    }
    public InputReader() {
        this.input = null;
    }

    public void addFile(File input) {
        this.input = input;
    }
    public void readInput(){
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {

           while ((line = br.readLine()) != null) {
               // process the line.
               line.trim();
               line =  line.replace("  ", " ");
               line =  line.replace(")", "");
               line =  line.replace("(", "");
//               if(line.contains("A")||line.contains("c"))
//                   continue;
               lines.add(line);
           }
            NState.globalState = new NState();
            verticesNum = Integer.parseInt(lines.get(0).split(" ")[1]);
            edgesNum = Integer.parseInt(lines.get(1).split(" ")[1]);
            continentsNum = Integer.parseInt(lines.get(edgesNum+verticesNum+4).split(" ")[1]);
            System.out.println( "player1 "+lines.get(edgesNum+2));
            player1Line= lines.get(edgesNum+2).split(" ");
            player2Line = lines.get(edgesNum+3).split(" ");
//            System.out.println("verticesNum"+verticesNum);
//            System.out.println("edgesNum"+edgesNum);
//            System.out.println("continentsNum"+continentsNum);

            readCountries();
            readEdges();
            readArmies();
            readContinents();

            //printSuccssors();





        }
     catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void readCountries(){
        // Read the countries for non AI agents
        for ( i= 0;i<verticesNum;i++){
            vertices.add(new Country(i+1));
            // System.out.println(lines.get(edgesNum+4+i));
            countriesArmyLines.add(lines.get(edgesNum+4+i));
        }
        // Read the countries for  AI agents
        for (int i = 0; i < verticesNum +1; i++) {
            SCountry cc = new SCountry(i);
            // cc.setNumberArmies( i * 2);
            allSCountries.add(cc);
        }

        // Read the players countries
        for (i = 1; i < player1Line.length; i++){
            int index = Integer.parseInt(player1Line[i])-1;
            //  System.out.println("index "+index);
            vertices.get(index).setOwner(Agent.player1);
            allSCountries.get(index+1).owner = 1;
             myCountries.add(index+1);
        }
        for (i = 1; i < player2Line.length; i++){
            int index = Integer.parseInt(player2Line[i])-1;
            //  System.out.println("index "+index);
            vertices.get(index).setOwner(Agent.player2);
            allSCountries.get(index+1).owner = 2;
            opponentCountris.add(index+1);

        }
        NState.globalState.myCountries = myCountries;
        NState.globalState.opponentCountris = opponentCountris;

    }
    public void readEdges(){

        // Read the edges for non AI agents
        for ( j= 0;j<edgesNum;j++){
            lineElements = lines.get(j+2).split(" ");
            //   System.out.println(lineElements[0]+" "+lineElements[1]);
            int start = Integer.parseInt(lineElements[0])-1;
            int end = Integer.parseInt(lineElements[1])-1;
            vertices.get(start).addAdj(vertices.get(end));
            vertices.get(end).addAdj(vertices.get(start));
        }

        // Read the edges for  AI agents

        for ( j= 0;j<edgesNum;j++){
            lineElements = lines.get(j+2).split(" ");
            //   System.out.println(lineElements[0]+" "+lineElements[1]);
            int start = Integer.parseInt(lineElements[0]);
            int end = Integer.parseInt(lineElements[1]);
            allSCountries.get(start).adj.add(end);
            allSCountries.get(end).adj.add(start);
        }
    }
    public void readArmies(){
        // Read countries armies for non AI agents

        for ( j= 0;j<countriesArmyLines.size();j++) {
            String [] armyLine =  countriesArmyLines.get(j).split(" ");
            vertices.get(Integer.parseInt(armyLine[1])-1).addArmies(Integer.parseInt(armyLine[2]));
            System.out.println("army "+vertices.get(j).getNumberArmies());
        }
        // Read countries armies for  AI agents

        for ( j= 0;j<countriesArmyLines.size();j++) {
            String [] armyLine =  countriesArmyLines.get(j).split(" ");
            allSCountries.get(Integer.parseInt(armyLine[1])).setNumberArmies(Integer.parseInt(armyLine[2]));
          //  System.out.println("army "+vertices.get(j).getNumberArmies());
        }
        NState.globalState.allCountries=allSCountries;
    }
    public void readContinents(){
        // Read the continents for non AI agents
        for ( k= 0;k<continentsNum;k++){
            lineElements = lines.get(edgesNum+verticesNum+5+k).split(" ");
            int bonus = Integer.parseInt(lineElements[0]);
            nearCountries = new ArrayList<>();
            for(int i=1;i<lineElements.length;i++){
                Country nearCountry = vertices.get(Integer.parseInt(lineElements[i])-1);
                nearCountries.add(nearCountry);
            }
            Map.getIntance().addContinent(new Continent(bonus,nearCountries));
        }
        List<Continent> allContinents  =  Map.getIntance().getContinents();
        for ( k= 0;k<continentsNum;k++){
            List<Country> countriesInContinent = allContinents.get(k).getCountries();
            for(i=0;i<countriesInContinent.size();i++){
                if(countriesInContinent.get(i).getOwner()==Agent.player1)
                    Agent.player1.addCountry(countriesInContinent.get(i));
                else
                    Agent.player2.addCountry(countriesInContinent.get(i));
            }
        }

        // Read the continents for  AI agents
        for ( k= 0;k<continentsNum;k++){
            lineElements = lines.get(edgesNum+verticesNum+5+k).split(" ");
            int bonus = Integer.parseInt(lineElements[0]);
           nearSCountries = new ArrayList<>();
            for(int i=1;i<lineElements.length;i++){
                Integer nearSCountry = Integer.parseInt(lineElements[i]);
                nearSCountries.add(nearSCountry);
            }
            SContinent sContinent = new SContinent();
            sContinent.countries = nearSCountries;
            sContinent.bounse = bonus;
           NState.globalState.continents.add(sContinent);
        }

    }
    public List<Country> getVertices() {
        return vertices;
    }
//    public void printSuccssors(){
//        for (NState ss : NState.globalState.getSuccssors()) {
//            System.out.println("-------------------------------\n");
//            for (SCountry sc : ss.allCountries)
//                System.out.println("country (" + sc.id + ")  owner : " + sc.owner + "number ar  :" + sc.numberArmies);
//
//            System.out.println(" mine : ");
//            for (int i : ss.myCountries)
//                System.out.print(i + " ,");
//            System.out.println("\n oppen : ");
//            for (int i : ss.opponentCountris)
//                System.out.print(i + " ,");
//            if (ss.attack != null)
//                System.out.println("\n+" + ss.attack.getKey() + "  attack   " + ss.attack.getValue());
//            if (ss.place != null)
//                System.out.println("\n" + ss.place.getKey() + " get  " + ss.place.getValue());
//        }
//    }
}
