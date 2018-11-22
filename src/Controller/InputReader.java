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

            verticesNum = Integer.parseInt(lines.get(0).split(" ")[1]);
            edgesNum = Integer.parseInt(lines.get(1).split(" ")[1]);
            continentsNum = Integer.parseInt(lines.get(edgesNum+verticesNum+4).split(" ")[1]);
            System.out.println( "player1 "+lines.get(edgesNum+2));
            player1Line= lines.get(edgesNum+2).split(" ");
            player2Line = lines.get(edgesNum+3).split(" ");
//            System.out.println("verticesNum"+verticesNum);
//            System.out.println("edgesNum"+edgesNum);
//            System.out.println("continentsNum"+continentsNum);

            // Read the countries
            for ( i= 0;i<verticesNum;i++){
                vertices.add(new Country(i+1));
               // System.out.println(lines.get(edgesNum+4+i));
                countriesArmyLines.add(lines.get(edgesNum+4+i));
            }
            // Read the players countries
            for (i = 1; i < player1Line.length; i++){
                int index = Integer.parseInt(player1Line[i])-1;
                //  System.out.println("index "+index);
                vertices.get(index).setOwner(Agent.player1);
            }
            for (i = 1; i < player2Line.length; i++){
                int index = Integer.parseInt(player2Line[i])-1;
                //  System.out.println("index "+index);
                vertices.get(index).setOwner(Agent.player2);
            }
                // Read the edges
            for ( j= 0;j<edgesNum;j++){
                lineElements = lines.get(j+2).split(" ");
             //   System.out.println(lineElements[0]+" "+lineElements[1]);
                int start = Integer.parseInt(lineElements[0])-1;
                int end = Integer.parseInt(lineElements[1])-1;
                vertices.get(start).addAdj(vertices.get(end));
                vertices.get(end).addAdj(vertices.get(start));
            }

            // Read countries armies

            for ( j= 0;j<countriesArmyLines.size();j++) {
                String [] armyLine =  countriesArmyLines.get(j).split(" ");
                vertices.get(Integer.parseInt(armyLine[1])-1).addArmies(Integer.parseInt(armyLine[2]));
                System.out.println("army "+vertices.get(j).getNumberArmies());
            }


            // Read the continents
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
        }
     catch (IOException e) {
            e.printStackTrace();
        }


    }

    public List<Country> getVertices() {
        return vertices;
    }

}
