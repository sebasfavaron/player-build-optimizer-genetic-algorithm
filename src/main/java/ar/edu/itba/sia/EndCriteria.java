package ar.edu.itba.sia;

import ar.edu.itba.sia.enums.EndCriteriaType;
import com.google.gson.JsonParseException;

public class EndCriteria {
    private EndCriteriaType type;
    private int maxGenerations;
    private int contentK;
    private double structurePercentage;
    private Character best;
    private double expectedFitness;

    public EndCriteria(String type, int maxGenerations, int contentK, double structurePercentage, double expectedFitness) {
        this.maxGenerations = maxGenerations;
        this.contentK = contentK;
        this.structurePercentage = structurePercentage;
        this.expectedFitness  = expectedFitness;
        switch (type){
            case "MAX_GENERATIONS": this.type = EndCriteriaType.MAX_GENERATIONS; break;
            case "STRUCTURE": this.type = EndCriteriaType.STRUCTURE; break;
            case "CONTENT": this.type = EndCriteriaType.CONTENT; break;
            case "LOCAL_OPTIMUM": this.type = EndCriteriaType.LOCAL_OPTIMUM; break;
            default: throw new JsonParseException("Invalid end criteria type\n");
        }
    }

    public double getExpectedFitness() {
        return expectedFitness;
    }

    public EndCriteriaType getType() {
        return type;
    }

    public int getContentK() {
        return contentK;
    }

    public int getMaxGenerations() {
        return maxGenerations;
    }

    public double getStructurePercentage() {
        return structurePercentage;
    }

    @Override
    public String toString() {
        return "EndCriteria{" +
                "type=" + type +
                ", maxGenerations=" + maxGenerations +
                ", contentK=" + contentK +
                ", structurePercentage=" + structurePercentage +
                "}";
    }

    public Character getBest() {
        return best;
    }

    public void updateBest(Character newBest) {
        this.best = newBest;
    }
}
