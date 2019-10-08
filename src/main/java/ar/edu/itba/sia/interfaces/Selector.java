package ar.edu.itba.sia.interfaces;

import ar.edu.itba.sia.Character;

import java.util.List;
import java.util.Set;

public interface Selector {
    List<Character> select(List<Character> population, int selectionAmount);
}
