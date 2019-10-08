package ar.edu.itba.sia;

import ar.edu.itba.sia.ParametersPOJO.CharacterModifiers;
import ar.edu.itba.sia.ParametersPOJO.Methods;
import ar.edu.itba.sia.ParametersPOJO.MutationCriteria;
import ar.edu.itba.sia.enums.*;
import ar.edu.itba.sia.genes.Item;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Parameters {
//    public final float attackModifier;
//    public final float defenseModifier;
//    public final float forceModifier;
//    public final float agilityModifier;
//    public final float expertiseModifier;
//    public final float resistanceModifier;
//    public final float lifeModifier;
//
    public final CharacterModifiers characterModifiers;

//    public final float minHeight;
//    public final float maxHeight;
//    public final double probability;
//    public final double minProbability;
//    public final double maxProbability;
//    public final boolean uniformMutator;

    public final MutationCriteria mutationCriteria;

//    public final SelectorType selectorType;
//    public final CrossoverType crossoverType;
//    public final MutatorType mutatorType;
//    public final ReplacerType replacerType;

    public final Methods methods;

    public final int populationSize;

    public final int replacementK;

    public final double A;

    public final double B;

    public final int boltzmannTemperature;

    public final boolean tournamentProbability;

    public final int tournamentPlayerSize;

    public final EndCriteria endCriteria;

    public final Random random;

    public final Map<Item.Type, List<Item>> itemsMap;

    private static Parameters instance = null;

    public static class Builder {
        private CharacterModifiers characterModifiers;
        private MutationCriteria mutationCriteria;
        private Methods methods;
        private EndCriteria endCriteria;
        private int populationSize;
        private Map<Item.Type, List<Item>> itemsMap;
        private int replacementK;
        private double A;
        private double B;
        private int boltzmannTemperature;
        private boolean tournamentProbability;
        private int tournamentPlayerSize;
        private Random random;

        public Parameters build() {
            Parameters parameters = new Parameters(characterModifiers, mutationCriteria, methods,
                    itemsMap, endCriteria, populationSize, replacementK, A, B, boltzmannTemperature,
                    tournamentProbability, tournamentPlayerSize, random);
            Parameters.instance = parameters;
            return parameters;
        }

        public Builder withCharacterModifiers(CharacterModifiers characterModifiers){
            this.characterModifiers = characterModifiers;
            return this;
        }

        public Builder withMutationCriteria(MutationCriteria mutationCriteria) {
            this.mutationCriteria = mutationCriteria;
            return this;
        }

        public Builder withItems(Map<Item.Type, List<Item>> itemsMap){
            this.itemsMap = itemsMap;
            return this;
        }

        public Builder withMethods(Methods methods){
            this.methods = methods;
            return this;
        }

        public Builder withEndCriteria(EndCriteria endCriteria){
            this.endCriteria = endCriteria;
            return this;
        }

        public Builder withPopulationSize(int populationSize){
            this.populationSize = populationSize;
            return this;
        }

        public Builder withReplacementK(int replacementK) {
            this.replacementK = replacementK;
            return this;
        }

        public Builder withA(double A){
            this.A = A;
            return this;
        }

        public Builder withB(double B){
            this.B = B;
            return this;
        }

        public Builder withBoltzmannTemperature(int boltzmannTemperature){
            this.boltzmannTemperature = boltzmannTemperature;
            return this;
        }

        public Builder withTournamentProbability(boolean tournamentProbability){
            this.tournamentProbability = tournamentProbability;
            return this;
        }

        public Builder withTournamentPlayerSize(int tournamentPlayerSize){
            this.tournamentPlayerSize = tournamentPlayerSize;
            return this;
        }

        public Builder withRandom(Random random){
            this.random = random;
            return this;
        }
    }

    public Parameters(CharacterModifiers characterModifiers, MutationCriteria mutationCriteria, Methods methods,
                      Map<Item.Type, List<Item>> itemsMap, EndCriteria endCriteria, int populationSize,
                      int replacementK, double A, double B, int boltzmannTemperature, boolean tournamentProbability,
                      int tournamentPlayerSize, Random random) {
        this.characterModifiers = characterModifiers;
        this.mutationCriteria = mutationCriteria;
        this.methods = methods;
        this.itemsMap = itemsMap;
        this.endCriteria = endCriteria;
        this.populationSize = populationSize;
        this.replacementK = replacementK;
        this.A = A;
        this.B = B;
        this.boltzmannTemperature = boltzmannTemperature;
        this.tournamentProbability = tournamentProbability;
        this.tournamentPlayerSize = tournamentPlayerSize;
        this.random = random;
    }

    public static Parameters getInstance() {
        if(instance == null) {
            throw new IllegalStateException();
        }

        return instance;
    }

    public List<Item> getItems(Item.Type type){
        return itemsMap.get(type);
    }
}
