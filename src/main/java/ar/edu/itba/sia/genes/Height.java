package ar.edu.itba.sia.genes;

import ar.edu.itba.sia.interfaces.Gene;

public class Height implements Gene {

    private final float height;
    @Override
    public GeneType getType() {
        return GeneType.HEIGHT;
    }

    public Height(float height) {
        this.height = height;
    }

    public float get() {
        return height;
    }

    @Override
    public String toString() {
        return
                ""+ height;
    }
}
