package ar.edu.itba.sia;

import ar.edu.itba.sia.Mutator.GeneMutator;
import ar.edu.itba.sia.Mutator.MultiGeneMutator;
import ar.edu.itba.sia.Mutator.Mutator;
import ar.edu.itba.sia.crossover.AnularCrossover;
import ar.edu.itba.sia.crossover.OnePointCrossover;
import ar.edu.itba.sia.crossover.TwoPointCrossover;
import ar.edu.itba.sia.crossover.UniformCrossover;
import ar.edu.itba.sia.enums.EndCriteriaType;
import ar.edu.itba.sia.enums.SelectorType;
import ar.edu.itba.sia.genes.Height;
import ar.edu.itba.sia.genes.Item;
import ar.edu.itba.sia.interfaces.Crossover;
import ar.edu.itba.sia.interfaces.Gene;
import ar.edu.itba.sia.interfaces.Selector;
import ar.edu.itba.sia.replacer.FirstReplacer;
import ar.edu.itba.sia.replacer.Replacer;
import ar.edu.itba.sia.replacer.SecondReplacer;
import ar.edu.itba.sia.replacer.ThirdReplacer;
import ar.edu.itba.sia.selector.*;
import org.jfree.data.xy.XYSeries;

import java.util.*;


public class GeneticEngine {
    private List<Character> population;
    private List<Character> previousPopulation;
    private Replacer replacer;
    private int gensWithoutProgress;

    public GeneticEngine() {
        int populationSize = Parameters.getInstance().populationSize;

        this.population = new ArrayList<>();

        /* Initialize the population */
        populationInitializer(populationSize);

        /* Initialize replacer */
        this.replacer = initReplacer();

        // Counts the (continuous) times the best character (of all time) has gone undefeated
        this.gensWithoutProgress = 0;

    }

    public void run() throws Exception {
        int generation = 1;
        Character best = calculateLocalBest();
        final XYSeries bestFitness = new XYSeries("bestFitness");
        final XYSeries avgFitness = new XYSeries("avgFitness");
        ArrayList<XYSeries> seriesList = new ArrayList<>();
        seriesList.add(bestFitness);
        seriesList.add(avgFitness);
        XYLineChart chart = new XYLineChart("SIA", "Fitness over time", "Generation", "Fitness", seriesList);


        chart.getPlot().getRangeAxis().setAutoRange(true);
        while(endCriteria(generation, best)) {
            printInfo(best, generation, avgFitness);
            bestFitness.add(generation, best.getFitness()); // Update graph
            this.population = replacer.replace(population);
            best = calculateLocalBest();
            generation++;
        }

        printInfo(best, generation, avgFitness);
        printUniqueCharacters(best, generation);
    }

    // Prints number of characters with a unique fitness
    private void printUniqueCharacters(Character best, int generation) {
        Map<Character, Integer> unique = new HashMap<>();
        for(Character c : population) {
            boolean isOriginal = true;
            for(Character original : unique.keySet()) {
                if(c.getFitness() == original.getFitness()) {
                    unique.put(original, unique.get(original) + 1);
                    isOriginal = false;
                    break;
                }
            }
            if(isOriginal) {
                unique.put(c, 1);
            }
        }

        System.out.println("# final unique characters: " + unique.size());
        List<Integer> groupNumbers = new ArrayList<>(unique.values());
        groupNumbers.sort(Integer::compareTo);
        Collections.reverse(groupNumbers);
        System.out.print("Unique groups: {");
        groupNumbers.forEach((integer) -> System.out.print(integer + ", "));
        System.out.println("}");
    }

    private void printInfo(Character best, int generation, XYSeries averageSeries){
        Parameters params = Parameters.getInstance();

        System.out.println("Local best:  " + best);
        if(params.endCriteria.getType().equals(EndCriteriaType.CONTENT)){
            System.out.println("Global best: " + params.endCriteria.getBest());
        }
        double sum = 0;
        int likeBest = 0;
        for (Character c : population) {
            sum += c.getFitness();
            /*if(c.getFitness() == best.getFitness()) {
                likeBest++;
            }*/
        }
        //System.out.println("Pop size: " + population.size());
        double average = sum / population.size();
        System.out.println("Avg fitness: " + average);
        averageSeries.add(generation, average);
        //System.out.println("# like best: " + likeBest);
        System.out.println("# generations: " + generation + "\n");
    }

    private boolean endCriteria(int generation, Character best) throws Exception {
        Parameters params = Parameters.getInstance();

        switch (params.endCriteria.getType()){
            case MAX_GENERATIONS: return generation < params.endCriteria.getMaxGenerations();
            case STRUCTURE:
                if(previousPopulation == null) {
                    previousPopulation = population;
                    return true;
                }
               double endPercentage =  params.endCriteria.getStructurePercentage();
                int repeated = 0;
                for( Character c1 : population){
                    for( Character c2 : previousPopulation){
                        if(c1.getFitness() == c2.getFitness()){
                            if(c1.hasSameGenes(c2)){
                                previousPopulation.remove(c2);
                                repeated++;
                                break;
                            }
                        }
                    }
                }
                previousPopulation = population;

                return (double)repeated/population.size() < endPercentage;
            case CONTENT:
                System.out.println(gensWithoutProgress);
                if(params.endCriteria.getBest() == null || best.getFitness() > params.endCriteria.getBest().getFitness()) {
                    params.endCriteria.updateBest(best);
                    gensWithoutProgress = 0;
                } else {
                    gensWithoutProgress++;
                }
                return gensWithoutProgress < params.endCriteria.getContentK();
            case LOCAL_OPTIMUM:
               return best.getFitness() < params.endCriteria.getExpectedFitness();

            default: throw new Exception("No valid end criteria");
        }
    }

    private Character calculateLocalBest(){
        return population
                .stream()
                .max(Comparator.comparingDouble(Character::getFitness))
                .get();
    }

    private void populationInitializer(int populationSize) {
        for(int i = 0; i < populationSize; i++) {
            this.population.add(new Character(randomGenes()));
        }
    }

    private Gene[] randomGenes() {
        Gene[] genes = new Gene[6];
        int genePos = 0;

        /* Random item genes */
        for(Item.Type type : Item.Type.values()) {
            genes[genePos++] = randomItem(type);
        }

        /* Random height gene */
        genes[genePos] = randomHeight();

        return genes;
    }

    private Height randomHeight() {
        Parameters p = Parameters.getInstance();
        float h =
                (float) p.random.nextDouble() * (p.mutationCriteria.maxHeight - p.mutationCriteria.minHeight) + p.mutationCriteria.minHeight;
        return new Height(h);
    }

    private Item randomItem(Item.Type type) {
        Parameters param = Parameters.getInstance();

        /* Get all items available of that type and pick a random one */
        List<Item> itemList = param.itemsMap.get(type);

        return itemList.get(param.random.nextInt(itemList.size()));
    }


    private Replacer initReplacer() {
        Parameters params = Parameters.getInstance();

        switch(params.methods.replacementType) {
            case FIRST_REPLACER:
                return new FirstReplacer(initSelector(params.methods.selectionType1),
                        initSelector(params.methods.selectionType2), initSelector(params.methods.selectionType3),
                        initSelector(params.methods.selectionType4), initCrossover(), initMutator());
            case SECOND_REPLACER:
                return new SecondReplacer(initSelector(params.methods.selectionType1),
                        initSelector(params.methods.selectionType2), initSelector(params.methods.selectionType3),
                        initSelector(params.methods.selectionType4), initCrossover(), initMutator());
            case THIRD_REPLACER:
                return new ThirdReplacer(initSelector(params.methods.selectionType1),
                        initSelector(params.methods.selectionType2), initSelector(params.methods.selectionType3),
                        initSelector(params.methods.selectionType4), initCrossover(), initMutator());
        }

        return null;
    }

    private Selector initSelector(SelectorType selType) {
        Parameters params = Parameters.getInstance();

        switch(selType) {
            case ELITE: return new EliteSelector();
            case ROULETTE: return new RouletteSelector();
            case UNIVERSAL: return new UniversalSelector();
            case BOLTZMANN: return new BoltzmanSelector(params.boltzmannTemperature);
            case TOURNAMENT: return new TournamentSelector(params.tournamentProbability, params.tournamentPlayerSize);
            case RANKING: return new RankingSelector();
        }

        return null;
    }

    private Crossover initCrossover() {
        Parameters params = Parameters.getInstance();

        switch(params.methods.crossingType) {
            case ONE_POINT:
                return new OnePointCrossover();
            case TWO_POINT:
                return new TwoPointCrossover();
            case ANULAR:
                return new AnularCrossover();
            case UNIFORM:
                return new UniformCrossover();
        }
        return null;
    }

    private Mutator initMutator() {
        Parameters params = Parameters.getInstance();

        switch(params.methods.mutationType) {
            case GENE:
                return new GeneMutator(params.mutationCriteria.isUniform());
            case MULTI_GENE:
                return new MultiGeneMutator(params.mutationCriteria.isUniform());
        }
        return null;
    }
}
