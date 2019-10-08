package ar.edu.itba.sia.selector;

import ar.edu.itba.sia.Character;
import ar.edu.itba.sia.Parameters;
import ar.edu.itba.sia.Utils;
import ar.edu.itba.sia.interfaces.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UniversalSelector implements Selector {
    @Override
    public List<Character> select(List<Character> population, int selectionAmount) {
        List<Character> selectedCharacters = new ArrayList<>();
        List<Character> populationList = new ArrayList<>(population);
        Parameters p = Parameters.getInstance();

        /* Calculate the accumulated aptitudes */
        List<Double> accumAptitude = Utils.calcAccumAptitudes(populationList);

        double random = p.random.nextDouble();

        /* Pick characters inside */
        for(int i = 0; i < selectionAmount; i++) {
            double randomJ = (random + i - 1) / selectionAmount;

            /* Search random value in the accumAptitude array */
            for(int j = 0; j < accumAptitude.size(); j++) {
                if(accumAptitude.get(j) >= randomJ) {
                    selectedCharacters.add(populationList.get(j));
                    break;
                }
            }
        }

        return selectedCharacters;
    }
}
