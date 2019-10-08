package ar.edu.itba.sia.selector;

import ar.edu.itba.sia.Character;
import ar.edu.itba.sia.Parameters;
import ar.edu.itba.sia.interfaces.Selector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class RankingSelector implements Selector {
    @Override
    public List<Character> select(List<Character> population, int selectionAmount) {
        List<Character> selectedCharacters = new ArrayList<>();
        List<Character> populationList = new ArrayList<>(population);
        Parameters p = Parameters.getInstance();

        /* Calculate the accumulated aptitudes */
        List<Double> accumAptitude = generateAccumAptitude(populationList);

        /* Pick characters inside */
        for(int i = 0; i < selectionAmount; i++) {
            double random = p.random.nextDouble() * accumAptitude.get(accumAptitude.size() - 1);

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

    private List<Double> generateAccumAptitude(List<Character> populationList) {
        List<Double> list = new ArrayList<>();

        populationList.sort(Comparator.comparingDouble(Character::getFitness));

        double acum = 0;

        for(Character c : populationList) {
            acum += c.getFitness();
            list.add((double) c.getFitness());
        }

        for(int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i) / acum);
        }

        return list;
    }
}
