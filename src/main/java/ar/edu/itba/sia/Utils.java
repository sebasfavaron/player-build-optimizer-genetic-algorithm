package ar.edu.itba.sia;

import java.util.ArrayList;
import java.util.List;

public class Utils {
   public static List<Double> calcAccumAptitudes(List<Character> populationList) {
       List<Double> accumAptitude = new ArrayList<>();
       double accum = 0;

       /* Calculate the accumulated aptitudes */
       for(Character c : populationList) {
           accum += c.getFitness();
           accumAptitude.add(accum);
       }
       for(int i = 0; i < accumAptitude.size(); i++) {
           accumAptitude.set(i, accumAptitude.get(i) / accum);
       }

       return accumAptitude;
   }


    public static List<Double> calcAccumAptitudesBoltzman(List<Character> populationList, double temperature) {
        List<Double> accumAptitude = new ArrayList<>();
        double accum = 0;

        /* Calculate the accumulated aptitudes */
        for(Character c : populationList) {
            double value = Math.exp(c.getFitness() / temperature);

            accumAptitude.add(value);
            accum += value;
        }

        double mean = accum / populationList.size();

        for(int i = 0; i < accumAptitude.size(); i++) {
            accumAptitude.set(i, accumAptitude.get(i) / mean);
        }

        return accumAptitude;
    }
}
