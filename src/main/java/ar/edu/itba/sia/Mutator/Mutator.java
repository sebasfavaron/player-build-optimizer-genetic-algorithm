package ar.edu.itba.sia.Mutator;

import ar.edu.itba.sia.Character;
import ar.edu.itba.sia.Parameters;
import ar.edu.itba.sia.interfaces.Gene;
import ar.edu.itba.sia.genes.Height;
import ar.edu.itba.sia.genes.Item;

import java.util.List;
import java.util.Random;

public abstract class Mutator {

    private boolean uniform;
    private double currProbability;

    private static double PROBAILITY_DECREMENT = 0.0001;

    public Mutator(boolean uniform){
        this.uniform = uniform;
        this.currProbability = Parameters.getInstance().mutationCriteria.maxProbability;

    }

    public abstract Character mutate(Character character);

    public Gene mutateGen(Gene gene ) {
        Parameters parameters = Parameters.getInstance();
        if (gene.getType() == Gene.GeneType.HEIGHT) {
            double minHeight = parameters.mutationCriteria.minHeight;
            double maxHeight = parameters.mutationCriteria.maxHeight;

            float newHeight = (float) (parameters.random.nextDouble() * (maxHeight - minHeight) + minHeight);

            return new Height(newHeight);
        }
        Item item = (Item) gene;
        List<Item> itemsList = parameters.getItems(item.getItemType());

        int selectedItem = parameters.random.nextInt(itemsList.size());

        return itemsList.get(selectedItem);
    }

    public void updateProbability(){
        Parameters parameters = Parameters.getInstance();

        double minProbability = parameters.mutationCriteria.minProbability;
//        double maxProbability = parameters.mutationCriteria.maxProbability;

        if(this.currProbability <= minProbability) {
            this.currProbability = minProbability;
        }

        this.currProbability -= PROBAILITY_DECREMENT;
    }

    public double getProbability(){
        if(uniform){
            Parameters parameters = Parameters.getInstance();
            return parameters.mutationCriteria.probability;
        }
        return currProbability;
    }





}
