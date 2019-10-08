package ar.edu.itba.sia.selector;

import ar.edu.itba.sia.Character;
import ar.edu.itba.sia.Parameters;
import ar.edu.itba.sia.Utils;
import ar.edu.itba.sia.interfaces.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BoltzmanSelector implements Selector {
    private double temperature;

    private static double TEMPERATURE_DECREMENT = 1;

    public BoltzmanSelector(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public List<Character> select(List<Character> population, int selectionAmount) {
        List<Character> selectedCharacters = new ArrayList<>();
        List<Character> populationList = new ArrayList<>(population);
        Parameters p = Parameters.getInstance();

        /* Calculate the accumulated aptitudes */
        List<Double> accumAptitude = Utils.calcAccumAptitudesBoltzman(populationList, temperature);

        /* Pick characters inside (using roulette)*/
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

        decrementTemperature();

        return selectedCharacters;
    }

    private void decrementTemperature() {
        this.temperature -= TEMPERATURE_DECREMENT;
        if(this.temperature <= 0) {
            this.temperature = 1;
        }
    }
}
