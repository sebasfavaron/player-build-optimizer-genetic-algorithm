package ar.edu.itba.sia.crossover;

import ar.edu.itba.sia.Character;
import ar.edu.itba.sia.Parameters;
import ar.edu.itba.sia.genes.Item;
import ar.edu.itba.sia.interfaces.Crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnularCrossover implements Crossover {


    @Override
    public List<Character> cross(Character p1, Character p2) {
        List<Character> crossedChildren = new ArrayList<>();
        Parameters p = Parameters.getInstance();

        /* Get the amount of items */
        int itemAmount = p1.getItems().length;
        int genesAmount = itemAmount + 1;


        int r = p.random.nextInt(genesAmount);
        int l = p.random.nextInt(genesAmount/2);


        /* Build child items genes */
        Item[] child1Items = new Item[itemAmount];
        Item[] child2Items = new Item[itemAmount];


        for(int i = 0; i < itemAmount; i++) {
            if(i >= r && i <= r+l) {
                child1Items[i] = p2.getItems()[i];//swap
                child2Items[i] = p1.getItems()[i];
            } else {
                child1Items[i] = p1.getItems()[i];//no swap
                child2Items[i] = p2.getItems()[i];
            }
        }



        if(r+l >= genesAmount){
            for(int i=0; i <= (r+l) % genesAmount; i++){
                child1Items[i] = p2.getItems()[i];
                child2Items[i] = p1.getItems()[i];
            }
        }

        /* Build child height */
        float child1Height = p1.getHeight();
        float child2Height = p2.getHeight();

        if(r+l >= itemAmount) {
            child1Height = p2.getHeight();
            child2Height = p1.getHeight();
        }

        /* Build children */
        crossedChildren.add(new Character(child1Items, child1Height));
        crossedChildren.add(new Character(child2Items, child2Height));

        return crossedChildren;




    }
}
