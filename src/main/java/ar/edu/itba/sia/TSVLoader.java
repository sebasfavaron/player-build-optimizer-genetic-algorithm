package ar.edu.itba.sia;

import ar.edu.itba.sia.genes.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TSVLoader {

    public static Map<Item.Type, List<Item>> loadItems(String pathToFolder){

        Map<Item.Type, List<Item>> map = new HashMap<>();

        map.put(Item.Type.WEAPON, loadFromFile(pathToFolder+"armas.tsv",Item.Type.WEAPON));
        map.put(Item.Type.BOOTS, loadFromFile(pathToFolder+"botas.tsv",Item.Type.BOOTS));
        map.put(Item.Type.HELMET, loadFromFile(pathToFolder+"cascos.tsv",Item.Type.HELMET));
        map.put(Item.Type.GLOVES, loadFromFile(pathToFolder+"guantes.tsv",Item.Type.GLOVES));
        map.put(Item.Type.ARMOUR, loadFromFile(pathToFolder+"pecheras.tsv",Item.Type.ARMOUR));

        return  map;

    }


    private static List<Item> loadFromFile(String pathToFile, Item.Type type){
        List<Item> inputList = new ArrayList<>();

        Function<String, Item> mapToItem = (line) -> {
            String[] p = line.split("\t");// a CSV has comma separated lines
            Item item =
                    new Item(
                            Integer.parseInt(p[0]),
                            Float.parseFloat(p[1]),
                            Float.parseFloat(p[2]),
                            Float.parseFloat(p[3]),
                            Float.parseFloat(p[4]),
                            Float.parseFloat(p[5]),
                            type
                    );

            //more initialization goes here
            return item;
        };


        try{
            File inputF = new File(pathToFile);
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
            // skip the header of the csv
            inputList = br
                    .lines()
                    .skip(1)
                    .map(mapToItem)
                    .collect(Collectors.toList());
            br.close();
        } catch (IOException e) {
            System.err.println("Could not open data files");
        }
        return inputList ;

    }


}
