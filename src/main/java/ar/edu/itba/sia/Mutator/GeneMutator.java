package ar.edu.itba.sia.Mutator;

import ar.edu.itba.sia.Character;
import ar.edu.itba.sia.Parameters;
import ar.edu.itba.sia.interfaces.Gene;

import java.util.Random;

public class GeneMutator extends Mutator{

    public GeneMutator(boolean uniform) {
        super(uniform);
    }


    @Override
    public Character mutate(Character character) {
        Parameters p = Parameters.getInstance();
        Gene[] oldGenes = character.getGenes();
        Gene[] newGenes = new Gene[oldGenes.length];

        int genToMutate = p.random.nextInt(oldGenes.length);

        for(int i = 0; i< oldGenes.length; i++){
            if (i == genToMutate && p.random.nextDouble() < getProbability()){
                newGenes[i] = mutateGen(oldGenes[i]);
            }
            else{
                newGenes[i] = oldGenes[i];
            }
        }
        return new Character(newGenes);
    }
}
