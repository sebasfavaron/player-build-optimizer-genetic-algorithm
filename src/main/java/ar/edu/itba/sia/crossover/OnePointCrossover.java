package ar.edu.itba.sia.crossover;

import ar.edu.itba.sia.Character;
import ar.edu.itba.sia.Parameters;
import ar.edu.itba.sia.genes.Item;
import ar.edu.itba.sia.interfaces.Crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OnePointCrossover implements Crossover {
    @Override
    public List<Character> cross(Character p1, Character p2) {
        List<Character> crossedChildren = new ArrayList<>();
        Parameters p = Parameters.getInstance();

        /* Get the amount of items */
        int itemAmount = p1.getItems().length;
        int genesAmount = itemAmount + 1;

        /* Get the random chromosome crossing point */
        int crossingPoint = p.random.nextInt(genesAmount + 1);

        /* Build child items genes */
        Item child1Items[] = new Item[itemAmount];
        Item child2Items[] = new Item[itemAmount];


        for(int i = 0; i < itemAmount; i++) {
            if(i < crossingPoint) {
                child1Items[i] = p1.getItems()[i];
                child2Items[i] = p2.getItems()[i];
            } else {
                child1Items[i] = p2.getItems()[i];
                child2Items[i] = p1.getItems()[i];
            }
        }

        /* Build child height */
        float child1Height = p2.getHeight();
        float child2Height = p1.getHeight();

        if(genesAmount - 1 < crossingPoint) {
            child1Height = p1.getHeight();
            child2Height = p2.getHeight();
        }

        /* Build children */
        crossedChildren.add(new Character(child1Items, child1Height));
        crossedChildren.add(new Character(child2Items, child2Height));

        return crossedChildren;
    }
}
