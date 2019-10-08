package ar.edu.itba.sia.selector;

import ar.edu.itba.sia.Character;
import ar.edu.itba.sia.interfaces.Selector;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EliteSelector implements Selector {
    @Override
    public List<Character> select(List<Character> population, int selectionAmount) {
        return population.stream()
                .sorted(Comparator.comparingDouble(Character::getFitness).reversed())
                .limit(selectionAmount)
                .collect(Collectors.toList());
    }
}
