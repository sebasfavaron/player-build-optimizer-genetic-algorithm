package ar.edu.itba.sia.ParametersPOJO;

public class MutationCriteria {
    public final float minHeight;
    public final float maxHeight;
    public final double probability;
    public final double minProbability;
    public final double maxProbability;
    public final boolean uniform;

    public MutationCriteria(float minHeight, float maxHeight, double probability, double minProbability, double maxProbability, boolean uniform) {
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.probability = probability;
        this.minProbability = minProbability;
        this.maxProbability = maxProbability;
        this.uniform = uniform;
    }

    public float getMinHeight() {
        return minHeight;
    }

    public float getMaxHeight() {
        return maxHeight;
    }

    public double getProbability() {
        return probability;
    }

    public double getMinProbability() {
        return minProbability;
    }

    public double getMaxProbability() {
        return maxProbability;
    }

    public boolean isUniform() {
        return uniform;
    }

    @Override
    public String
    toString() {
        return "MutationCriteria{" +
                "minHeight=" + minHeight +
                ", maxHeight=" + maxHeight +
                ", probability=" + probability +
                ", minProbability=" + minProbability +
                ", maxProbability=" + maxProbability +
                ", uniform=" + uniform +
                '}';
    }
}
