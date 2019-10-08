package ar.edu.itba.sia;

import ar.edu.itba.sia.ParametersPOJO.CharacterModifiers;
import ar.edu.itba.sia.ParametersPOJO.Methods;
import ar.edu.itba.sia.ParametersPOJO.MutationCriteria;
import ar.edu.itba.sia.enums.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.FileReader;

import java.nio.file.Paths;


public class ParameterLoader {
    private String file;
    private String dataPath;
    private CharacterModifiers characterModifiers;
    private MutationCriteria mutationCriteria;
    private Methods methods;
    private EndCriteria endCriteria;
    private int populationSize;
    private int replacementK;
    private double A;
    private double B;
    private int boltzmannTemperature;
    private boolean tournamentProbability;
    private int tournamentPlayerSize;
    private boolean deterministic;

    public ParameterLoader(String file) {
        this.file = file;
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(Paths.get(file).toFile()));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to open file" + file);
            System.exit(-1);
            return;
        }

        Gson gson = new Gson();
        JsonObject js = gson.fromJson(bufferedReader, JsonObject.class);

        this.characterModifiers = getCharacterModifiers(js.get("characterModifiers").getAsString());
        this.mutationCriteria = getMutationCriteria(js.getAsJsonObject("mutationCriteria"));
        this.methods = getMethods(js.getAsJsonObject("methods"));
        this.dataPath = js.get("dataPath").getAsString();
        this.endCriteria = getEndCriteriaParameters(js.getAsJsonObject("endCriteria"));
        this.populationSize = js.get("populationSize").getAsInt();
        this.replacementK = js.get("replacementK").getAsInt();
        this.A = js.get("A").getAsDouble();
        this.B = js.get("B").getAsDouble();
        this.boltzmannTemperature = js.get("boltzmannTemperature").getAsInt();
        this.tournamentProbability = js.get("tournamentProbability").getAsBoolean();
        this.tournamentPlayerSize = js.get("tournamentPlayerSize").getAsInt();
        this.deterministic = js.get("deterministic").getAsBoolean();
    }

    private CharacterModifiers getCharacterModifiers(String characterType) {
        switch (characterType){
            case "WARRIOR1": return new CharacterModifiers(0.6f, 0.4f, 1.1f, 0.9f, 0.8f, 1.0f, 0.9f);
            case "WARRIOR2": return new CharacterModifiers(0.6f, 0.4f, 1.2f, 1.0f, 0.8f, 0.8f, 0.8f);
            case "WARRIOR3": return new CharacterModifiers(0.6f, 0.4f, 0.8f, 0.9f, 0.9f, 1.2f, 1.1f);
            case "WARRIOR4": return new CharacterModifiers(0.6f, 0.4f, 1.0f, 1.1f, 1.0f, 1.0f, 1.0f);
            case "ARCHER1": return new CharacterModifiers(0.9f, 0.1f, 0.8f, 1.1f, 1.1f, 0.9f, 0.7f);
            case "ARCHER2": return new CharacterModifiers(0.9f, 0.1f, 0.9f, 1.1f, 1.0f, 0.9f, 0.8f);
            case "ARCHER3": return new CharacterModifiers(0.9f, 0.1f, 0.8f, 0.8f, 0.8f, 1.1f, 1.2f);
            case "ASSASSIN1": return new CharacterModifiers(0.7f, 0.3f, 0.8f, 1.2f, 1.1f, 1.0f, 0.8f);
            case "ASSASSIN2": return new CharacterModifiers(0.7f, 0.3f, 0.9f, 1.0f, 1.1f, 1.0f, 0.9f);
            case "ASSASSIN3": return new CharacterModifiers(0.7f, 0.3f, 0.9f, 0.9f, 1.0f, 1.1f, 1.0f);
            case "DEFENDER1": return new CharacterModifiers(0.1f, 0.9f, 1.0f, 0.9f, 0.7f, 1.2f, 1.1f);
            case "DEFENDER2": return new CharacterModifiers(0.1f, 0.9f, 1.1f, 0.8f, 0.8f, 1.1f, 1.1f);
            case "DEFENDER3": return new CharacterModifiers(0.1f, 0.9f, 0.9f, 0.9f, 0.9f, 1.0f, 1.3f);
            case "DEFENDER4": return new CharacterModifiers(0.1f, 0.9f, 0.8f, 0.9f, 1.2f, 1.2f, 0.8f);
            default: throw new JsonParseException("Invalid character type");
        }
    }

    private MutationCriteria getMutationCriteria(JsonObject js) {
        return new MutationCriteria(
                js.get("minHeight").getAsFloat(),
                js.get("maxHeight").getAsFloat(),
                js.get("probability").getAsFloat(),
                js.get("minProbability").getAsFloat(),
                js.get("maxProbability").getAsFloat(),
                js.get("uniform").getAsBoolean()
        );
    }

    private Methods getMethods(JsonObject js) {
        CrossoverType cType;
        SelectorType sType1;
        SelectorType sType2;
        SelectorType sType3;
        SelectorType sType4;
        ReplacerType rType;
        MutatorType mType;

        switch (js.get("crossing").getAsString()) {
            case "ONE_POINT": cType = CrossoverType.ONE_POINT; break;
            case "TWO_POINT": cType = CrossoverType.TWO_POINT; break;
            case "ANULAR": cType = CrossoverType.ANULAR; break;
            case "UNIFORM": cType = CrossoverType.UNIFORM; break;
            default: throw new JsonParseException("Invalid crossing type\n");
        }

        sType1 = selectType(js.get("selection1").getAsString());
        sType2 = selectType(js.get("selection2").getAsString());
        sType3 = selectType(js.get("selection3").getAsString());
        sType4 = selectType(js.get("selection4").getAsString());

        switch (js.get("replacement").getAsString()) {
            case "FIRST_REPLACER": rType = ReplacerType.FIRST_REPLACER; break;
            case "SECOND_REPLACER": rType = ReplacerType.SECOND_REPLACER; break;
            case "THIRD_REPLACER": rType = ReplacerType.THIRD_REPLACER; break;
            default: throw new JsonParseException("Invalid replacement type\n");
        }

        switch (js.get("mutation").getAsString()) {
            case "GENE": mType = MutatorType.GENE; break;
            case "MULTI_GENE": mType = MutatorType.MULTI_GENE; break;
            default: throw new JsonParseException("Invalid mutation type\n");
        }

        return new Methods(cType, sType1, sType2, sType3, sType4, rType, mType);
    }

    private EndCriteria getEndCriteriaParameters(JsonObject js) {
        return new EndCriteria(
                js.get("type").getAsString(),
                js.get("maxGenerations").getAsInt(),
                js.get("contentK").getAsInt(),
                js.get("structurePercentage").getAsDouble(),
                js.get("expectedFitness").getAsDouble()
        );
    }

    private SelectorType selectType(String selType){
        switch (selType) {
            case "ELITE": return SelectorType.ELITE;
            case "ROULETTE": return SelectorType.ROULETTE;
            case "UNIVERSAL": return SelectorType.UNIVERSAL;
            case "BOLTZMANN": return SelectorType.BOLTZMANN;
            case "TOURNAMENT": return SelectorType.TOURNAMENT;
            case "RANKING": return SelectorType.RANKING;
            default: throw new JsonParseException("Invalid selection type\n");
        }
    }

    public String getFile() {
        return file;
    }

    public String getDataPath() {
        return dataPath;
    }

    public CharacterModifiers getCharacterModifiers() {
        return characterModifiers;
    }

    public MutationCriteria getMutationCriteria() {
        return mutationCriteria;
    }

    public Methods getMethods() {
        return methods;
    }

    public EndCriteria getEndCriteria() {
        return endCriteria;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getReplacementK() {
        return replacementK;
    }

    public double getA() {
        return A;
    }

    public double getB() {
        return B;
    }

    public int getBoltzmannTemperature() {
        return boltzmannTemperature;
    }

    public int getTournamentPlayerSize() {
        return tournamentPlayerSize;
    }

    public boolean isTournamentProbability() {
        return tournamentProbability;
    }

    public boolean isDeterministic() {
        return deterministic;
    }
}
