package ar.edu.itba.sia.replacer;

import ar.edu.itba.sia.Character;
import ar.edu.itba.sia.Mutator.Mutator;
import ar.edu.itba.sia.Parameters;
import ar.edu.itba.sia.interfaces.Crossover;
import ar.edu.itba.sia.interfaces.Selector;

import java.util.*;

public abstract class Replacer {
    protected final Selector selector1;
    protected final Selector selector2;
    protected final Selector selector3;
    protected final Selector selector4;
    protected final Crossover crossover;
    protected final Mutator mutator;

    public Replacer(Selector selector1, Selector selector2, Selector selector3, Selector selector4, Crossover crossover,
                    Mutator mutator) {
        this.selector1 = selector1;
        this.selector2 = selector2;
        this.selector3 = selector3;
        this.selector4 = selector4;
        this.crossover = crossover;
        this.mutator = mutator;
    }

    public abstract List<Character> replace(List<Character> previousGeneration);

    protected void updateMutationProbability(){
        Parameters parameters = Parameters.getInstance();
        if(!parameters.mutationCriteria.isUniform()){
            mutator.updateProbability(); // Update probability once a generation
        }
    }

    protected List<Character> crossCharacters(List<Character> characters) {
        List<Character> crossedCharacter = new ArrayList<>();
        Parameters p = Parameters.getInstance();

        while(crossedCharacter.size() < characters.size()) {
            /* Pick to random characters */
            Character c1 = characters.get(p.random.nextInt(characters.size()));
            Character c2 = characters.get(p.random.nextInt(characters.size()));

            /* Cross them */
            crossedCharacter.addAll(crossover.cross(c1, c2));
        }

        return crossedCharacter;
    }
}
