package ar.edu.itba.sia.selector;

import ar.edu.itba.sia.Character;
import ar.edu.itba.sia.Parameters;
import ar.edu.itba.sia.Utils;
import ar.edu.itba.sia.interfaces.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RouletteSelector implements Selector {
    @Override
    public List<Character> select(List<Character> population, int selectionAmount) {
        Parameters p = Parameters.getInstance();
        List<Character> selectedCharacters = new ArrayList<>();
        List<Character> populationList = new ArrayList<>(population);

        /* Calculate the accumulated aptitudes */
        List<Double> accumAptitude = Utils.calcAccumAptitudes(populationList);

        /* Pick characters inside */
        for(int i = 0; i < selectionAmount; i++) {
            double random = p.random.nextDouble();

            /* Search random value in the accumAptitude array */
            for(int j = 0; j < accumAptitude.size(); j++) {
                if(accumAptitude.get(j) >= random) {
                    selectedCharacters.add(populationList.get(j));
                    break;
                }
            }
        }

        return selectedCharacters;
    }
}
