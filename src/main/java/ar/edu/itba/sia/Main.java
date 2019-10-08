package ar.edu.itba.sia;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        ParameterLoader loader;
        Map tsvLoader = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            loader = new ParameterLoader("./configFiles/parameters.json");

            System.out.println(loader.getCharacterModifiers());
            System.out.println(loader.getMutationCriteria());
            System.out.println(loader.getEndCriteria());
            System.out.println(loader.getDataPath());

            if(tsvLoader == null){
                tsvLoader = TSVLoader.loadItems(loader.getDataPath());
            }

            int seed;
            if(loader.isDeterministic()){
                seed = 53845;
            } else {
                seed = new Random().nextInt(Integer.MAX_VALUE);
            }

            new Parameters.Builder()
                    .withCharacterModifiers(loader.getCharacterModifiers())
                    .withMutationCriteria(loader.getMutationCriteria())
                    .withMethods(loader.getMethods())
                    .withItems(tsvLoader)
                    .withEndCriteria(loader.getEndCriteria())
                    .withPopulationSize(loader.getPopulationSize())
                    .withReplacementK(loader.getReplacementK())
                    .withA(loader.getA())
                    .withB(loader.getB())
                    .withBoltzmannTemperature(loader.getBoltzmannTemperature())
                    .withTournamentProbability(loader.isTournamentProbability())
                    .withTournamentPlayerSize(loader.getTournamentPlayerSize())
                    .withRandom(new Random(seed))
                    .build();

            GeneticEngine engine = new GeneticEngine();
            engine.run();

            System.out.println("\nPress ENTER below to run again (Tip: you can change configurations " +
                    "(except the input data path) before re-running (make sure changes are saved))");
            System.out.println("This was done to avoid the data load time between runs");
            reader.readLine();
        }
    }
}
