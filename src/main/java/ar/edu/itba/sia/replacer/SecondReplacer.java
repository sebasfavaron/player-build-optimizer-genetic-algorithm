package ar.edu.itba.sia.replacer;

import ar.edu.itba.sia.Character;
import ar.edu.itba.sia.Mutator.Mutator;
import ar.edu.itba.sia.Parameters;
import ar.edu.itba.sia.interfaces.Crossover;
import ar.edu.itba.sia.interfaces.Selector;

import java.util.*;
import java.util.stream.Collectors;

public class SecondReplacer extends Replacer {

    public SecondReplacer(Selector selector1, Selector selector2, Selector selector3, Selector selector4,
                          Crossover crossover, Mutator mutator) {
        super(selector1, selector2, selector3, selector4, crossover, mutator);
    }

    @Override
    public List<Character> replace(List<Character> previousGeneration) {
//        List<Character> remainingPreviousGeneration = new ArrayList<>(previousGeneration);
        Parameters params = Parameters.getInstance();

        int select1Size = (int)(params.replacementK * params.A);
        /* Select characters to combine */
        List<Character> selection = this.selector1.select(previousGeneration, select1Size);
 //       remainingPreviousGeneration.removeAll(selection);
        selection.addAll(this.selector2.select(previousGeneration, params.replacementK-select1Size));

        /* Do the crossing to the selected characters */
        List<Character> crossed = crossCharacters(selection);

        /* Mutate the crossed characters */
        updateMutationProbability();
        List<Character> mutated = crossed.stream()
                .map(mutator::mutate)
                .collect(Collectors.toList());

        /* Add the new characters to the new generation */
        List<Character> newGeneration = new ArrayList<>(mutated);

//        /* Select (N - K) character from previous generation*/
//        remainingPreviousGeneration = new HashSet<>(previousGeneration);
//        int select3Size = (int)((previousGeneration.size() - params.replacementK) * params.B);
//        List<Character> selection2 = this.selector3.select(remainingPreviousGeneration, select3Size);
//        remainingPreviousGeneration.removeAll(selection2);
//        selection2.addAll(this.selector4.select(remainingPreviousGeneration,
//                (previousGeneration.size() - params.replacementK) - select3Size));
//        remainingPreviousGeneration = new ArrayList<>(previousGeneration);

       int select3Size = (int)((previousGeneration.size() - params.replacementK) * params.B);
        List<Character> selection2 = this.selector3.select(previousGeneration, select3Size);
//        remainingPreviousGeneration.removeAll(propBPrevGen);
        selection2.addAll(this.selector4.select(previousGeneration,
                (previousGeneration.size() - params.replacementK) - select3Size));


        /* Add (N - K) selected characters from the original generation and add them to the new generation */
        newGeneration.addAll(selection2);

        return newGeneration;
    }

//    private List<Character> getProportionGeneration(List<Character> generation){
//        List<Character> gen = new LinkedList<>(generation);
//        List<Character> selection = new ArrayList<>();
//        Parameters params = Parameters.getInstance();
//        int prop = (int) (generation.size() * params.B);
//
//        for(int i = 0; i < prop; i++){
//              selection.add(gen.get(i));
//        }
//
//        return selection;
//
//    }

}
