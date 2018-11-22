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
    public InputReader(File input) {
        this.input = input;
    }


    public void readInput(){
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {

           while ((line = br.readLine()) != null) {
               // process the line.
               line.trim();
               line.replace("  ", " ");
               line.replace(")", "");
               line.replace("(", "");
               lines.add(line);
           }

            verticesNum = Integer.parseInt(lines.get(0).split(" ")[1]);
            edgesNum = Integer.parseInt(lines.get(1).split(" ")[1]);
            continentsNum = Integer.parseInt(lines.get(edgesNum+2).split(" ")[1]);

            for (int i= 0;i<verticesNum;i++)
                vertices.add(new Country(i+1));
            for (int j= 0;j<edgesNum;j++){
                lineElements = lines.get(j+2).split(" ");
                int start = Integer.parseInt(lineElements[0]);
                int end = Integer.parseInt(lineElements[1]);
                vertices.get(start).addAdj(vertices.get(end));
                vertices.get(end).addAdj(vertices.get(start));
            }
            for (int k= 0;k<continentsNum;k++){
                lineElements = lines.get(edgesNum+3+k).split(" ");
                int bonus = Integer.parseInt(lineElements[0]);
                nearCountries = new ArrayList<>();
                for(int i=1;i<lineElements.length;i++){
                    nearCountries.add(vertices.get(Integer.parseInt(lineElements[i])));
                }
                Map.getIntance().addContinent(new Continent(bonus,nearCountries));
            }

        }
     catch (IOException e) {
            e.printStackTrace();
        }


    }
}
