package ar.edu.itba.sia.Mutator;

import ar.edu.itba.sia.Character;
import ar.edu.itba.sia.Parameters;
import ar.edu.itba.sia.interfaces.Gene;

public class MultiGeneMutator extends Mutator {

    public MultiGeneMutator(boolean uniform) {
        super(uniform);
    }

    @Override
    public Character mutate(Character character) {
        Parameters p = Parameters.getInstance();
        Gene[] oldGenes = character.getGenes();
        Gene[] newGenes = new Gene[oldGenes.length];
        for(int i = 0; i< oldGenes.length; i++){
            if (p.random.nextDouble() < getProbability()){
                newGenes[i] = mutateGen(oldGenes[i]);
            }else{
                newGenes[i] = oldGenes[i];
            }
        }
        return new Character(newGenes);
    }
}
