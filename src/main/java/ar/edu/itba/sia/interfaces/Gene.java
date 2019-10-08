package ar.edu.itba.sia.interfaces;

public interface Gene  {

    enum GeneType {
        ITEM, HEIGHT
    }

    GeneType getType();
}
