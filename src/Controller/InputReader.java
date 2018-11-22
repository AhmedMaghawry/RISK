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
   public   List<Country> player1Countries= new ArrayList<>();
   public   List<Country> player2Countries = new ArrayList<>();
   private  int i=0,j=0,k=0;
    public InputReader(File input) {
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
            for ( j= 0;j<countriesArmyLines.size();j++) {
                String [] armyLine =  countriesArmyLines.get(j).split(" ");
                vertices.get(Integer.parseInt(armyLine[1])-1).addArmies(Integer.parseInt(armyLine[2]));
                System.out.println("army "+vertices.get(j).getNumberArmies());
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
            // Read the players countries
                for (i = 1; i < player1Line.length; i++){
                    int index = Integer.parseInt(player1Line[i])-1;
                  //  System.out.println("index "+index);
                    player1Countries.add(vertices.get(index));
                }
               for (i = 1; i < player2Line.length; i++){
                int index = Integer.parseInt(player2Line[i])-1;
                //  System.out.println("index "+index);
                 player2Countries.add(vertices.get(index));
              }
            // Read countries armies


            // Read the continents
            for ( k= 0;k<continentsNum;k++){
                lineElements = lines.get(edgesNum+verticesNum+5+k).split(" ");
             //   System.out.println(lineElements[0]+" "+lineElements[1]+" "+lineElements[2]);

                int bonus = Integer.parseInt(lineElements[0]);
                nearCountries = new ArrayList<>();
                for(int i=1;i<lineElements.length;i++){
                    Country nearCountry = vertices.get(Integer.parseInt(lineElements[i])-1);
                    nearCountries.add(nearCountry);
                }
                Map.getIntance().addContinent(new Continent(bonus,nearCountries));
            }

        }
     catch (IOException e) {
            e.printStackTrace();
        }


    }
}
