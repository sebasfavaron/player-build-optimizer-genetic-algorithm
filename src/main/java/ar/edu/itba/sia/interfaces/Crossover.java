package ar.edu.itba.sia.interfaces;

import ar.edu.itba.sia.Character;

import java.util.List;

public interface Crossover {
    List<Character> cross(Character p1, Character p2);
}
