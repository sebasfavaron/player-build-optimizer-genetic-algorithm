package ar.edu.itba.sia.selector;

import ar.edu.itba.sia.Character;
import ar.edu.itba.sia.Parameters;
import ar.edu.itba.sia.interfaces.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TournamentSelector implements Selector {

    private final boolean probabilistic;
    private final int tournamentPlayers;

    public TournamentSelector(boolean probabilistic, int tournamentPlayers) {
        this.probabilistic = probabilistic;
        this.tournamentPlayers = tournamentPlayers;
    }

    @Override
    public List<Character> select(List<Character> population, int selectionAmount) {
        List<Character> selectedCharacters = new ArrayList<>();
        List<Character> populationList = new ArrayList<>(population);

        for(int i = 0; i < selectionAmount; i++) {
            /* Pick random players */
            List<Character> randomPlayer = getRandomPlayers(populationList);

            selectedCharacters.add(getSelectedPlayer(randomPlayer));
        }

        return selectedCharacters;
    }

    private List<Character> getRandomPlayers(List<Character> population) {
        List<Character> players = new ArrayList<>();
        Parameters p = Parameters.getInstance();

        for(int i = 0; i < tournamentPlayers; i++) {
            players.add(population.get(p.random.nextInt(population.size())));
        }

        return players;
    }

    private Character getSelectedPlayer(List<Character> randomPlayers) {
        Character character = deterministicPick(randomPlayers);
        Parameters p = Parameters.getInstance();

        if(!this.probabilistic) {
            return character;
        }

        if(p.random.nextDouble() < 0.75) {
            return character;
        }

        return worstPick(randomPlayers);
    }

    private Character deterministicPick(List<Character> randomPlayers) {
        Character bestCharacter = null;

        for(Character c : randomPlayers) {
            if(bestCharacter == null || bestCharacter.getFitness() < c.getFitness()) {
                bestCharacter = c;
            }
        }

        return bestCharacter;
    }

    private Character worstPick(List<Character> randomPlayers) {
        Character worstCharacter = null;

        for(Character c : randomPlayers) {
            if(worstCharacter == null || worstCharacter.getFitness() > c.getFitness()) {
                worstCharacter = c;
            }
        }

        return worstCharacter;

    }
}
