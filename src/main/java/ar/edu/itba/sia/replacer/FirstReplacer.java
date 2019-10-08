package ar.edu.itba.sia.replacer;

import ar.edu.itba.sia.Character;
import ar.edu.itba.sia.Mutator.Mutator;
import ar.edu.itba.sia.Parameters;
import ar.edu.itba.sia.interfaces.Crossover;
import ar.edu.itba.sia.interfaces.Selector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FirstReplacer extends Replacer {


    public FirstReplacer(Selector selector1, Selector selector2, Selector selector3, Selector selector4,
                         Crossover crossover, Mutator mutator) {
        super(selector1, selector2, selector3, selector4, crossover, mutator);
    }

    @Override
    public List<Character> replace(List<Character> previousGeneration) {
        List<Character> newGeneration = new ArrayList<>();
        Parameters params = Parameters.getInstance();

        List<Character> remainingPreviousGeneration = new ArrayList<>(previousGeneration);
        while(newGeneration.size() < previousGeneration.size() && previousGeneration.size() > 1) {
            List<Character> selection;
            if(newGeneration.size() > previousGeneration.size()*params.A){
                selection = this.selector1.select(remainingPreviousGeneration, 2);
            } else {
                selection = this.selector2.select(remainingPreviousGeneration, 2);
            }

            List<Character> crossed = this.crossover.cross(selection.get(0), selection.get(1));

            updateMutationProbability();
            List<Character> mutated = crossed
                    .stream()
                    .map(mutator::mutate)
                    .collect(Collectors.toList());

            newGeneration.addAll(mutated);

            /* Remove selected items from the remaining generation set */
            remainingPreviousGeneration.removeAll(selection);
        }
        return newGeneration;
    }
}
