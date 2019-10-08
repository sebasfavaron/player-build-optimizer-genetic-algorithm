package ar.edu.itba.sia.replacer;

import ar.edu.itba.sia.Character;
import ar.edu.itba.sia.Mutator.Mutator;
import ar.edu.itba.sia.Parameters;
import ar.edu.itba.sia.interfaces.Crossover;
import ar.edu.itba.sia.interfaces.Selector;

import java.util.*;
import java.util.stream.Collectors;

public class ThirdReplacer extends Replacer {


    public ThirdReplacer(Selector selector1, Selector selector2, Selector selector3,
                         Selector selector4, Crossover crossover, Mutator mutator) {
        super(selector1, selector2, selector3, selector4, crossover, mutator);
    }

    @Override
    public List<Character> replace(List<Character> previousGeneration) {
        List<Character> newGeneration = new ArrayList<>();
        List<Character> remainingPreviousGeneration = new ArrayList<>(previousGeneration);
        Parameters params = Parameters.getInstance();

        /* Select characters to combine */
        int select1Size = (int)(params.replacementK * params.A);
        /* Select characters to combine */
        List<Character> selection = this.selector1.select(previousGeneration, select1Size);
//        remainingPreviousGeneration.removeAll(selection);
        selection.addAll(this.selector2.select(previousGeneration, params.replacementK-select1Size));

        /* Do the crossing to the selected characters */
        List<Character> crossed = crossCharacters(selection);

        /* Mutate the crossed characters */
        updateMutationProbability();
        List<Character> mutated = crossed.stream()
                .map(mutator::mutate)
                .collect(Collectors.toList());


        newGeneration = new ArrayList<>(mutated);
        /* Select (N - K) character from previous generation*/
//        remainingPreviousGeneration = new ArrayList<>(previousGeneration);
//        int select3Size = (int)((previousGeneration.size() - params.replacementK) * params.B);
//        List<Character> selection2 = this.selector3.select(previousGeneration, select3Size);
//        remainingPreviousGeneration.removeAll(selection2);
//        selection2.addAll(this.selector4.select(previousGeneration,
//                (previousGeneration.size() - params.replacementK) - select3Size));
//
//        /* Add (N - K) selected characters from the original generation and add them to the new generation */
//        newGeneration.addAll(selection2);

        /* Pick K characters from the N + K and add them to the new generation */
        newGeneration.addAll(pickSelectedAndUnselectedCharacters(previousGeneration, mutated));

        return newGeneration;
    }

    private List<Character> pickSelectedAndUnselectedCharacters(List<Character> allCharacters,
                                                                List<Character> children) {
        List<Character> remainingCharacters = new ArrayList<>(allCharacters);
        remainingCharacters.addAll(children);
        List<Character> pickedCharacter = new ArrayList<>();
        Parameters params = Parameters.getInstance();

        int select3Size = (int)(params.replacementK * params.B);
        pickedCharacter.addAll(this.selector3.select(remainingCharacters, select3Size));
        //remainingCharacters.removeAll(pickedCharacter);
        pickedCharacter.addAll(this.selector4.select(remainingCharacters,
                params.replacementK - select3Size));

        return pickedCharacter;
    }
}
