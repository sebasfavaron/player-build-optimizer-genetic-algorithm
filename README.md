# Optimizing player builds with Genetic Algorithms
Project for "Sistemas de inteligencia artificial" @ITBA, written in Java

## Objective
Implement a genetic algorithm engine in Java, capable of using different replacing, selection and mutation methods.
The objective is optimizing a players build (items and height) so that it performs better (given by a pre-defined fitness function).

## Requisites

* Java

## Running the GA

We decided not to include the `fulldata` files in this repository. So, to run just put the `fulldata` folder in the `configFolder` folder.
>Note: the `configFolder` is in the root folder, but to run the already compiled binary, you need to copy the `fulldata` folder also inside the `binaries` folder.

To run it again you can just press `ENTER` on the console (all parameters can be modified between runs, exept the data path). This loop was done to avoid data load times between runs.

### Build code

An already builded `jar` is provided in the `binaries` folder. But if you want to build it yourself, you can just run in the root folder:
```
$ mvn clean package
```

The generated `jar` with the configuration files will be created inside the `target` folder.

### Running the jar

Move to the folder containing the `jar`, check that the `fulldata` folder, and the `parameters.json` is at the same level of the `jar`. Then run:
```
$ java -jar <jarName>
```

## Arguments

Configuration is done in the `parameters.json` file.



* crossing: ONE_POINT, TWO_POINT, ANULAR or UNIFORM.
* selection: ELITE, ROULLETE, BOLTZMANN, TOURNAMENT or RANKING.
    * The selection for the crossover is done by A * selection1 + (1 - B) * selection2.
    * The selection for the replacement is done by B * selection13 + (1 - B) * selection4.
* replacement: FIRST_REPLACER, SECOND_REPLACER or THIRD_REPLACER.
* mutation: GENE or MULTI_GENE
* characterModifier: (CHARACTER CLASS) + (character number)
* mutationCriteria:
    * minHeight and maxHeight: interval of heights a character can have.
    * uniform: if the mutation is uniform or not (boolean).
    * probability: probability for uniform mutation.
    * minProbability and maxProbability: interval of probabilities for the non uniform mutation.
* endCriteria:
    * type: MAX_GENERATIONS, STRUCTURE, CONTENT or LOCAL_OPTIMUM.
    * maxGenerations: max amount of generations to run for MAX_GENERATIONS end criteria.
    * contentK: max amount of generations without the best fitness changing for CONTENT end criteria.
    * structurePercentage: max percentage of the population that doesn't change for STRUCTURE end criteria.
    * expectedFitness: max fitness for LOCAL_OPTIMUM.
* dataPath: path to the file containing the items' data.
* replacementK: number of children created for repacement methods 2 and 3.
* populationSize: amount of individuals per generation.

* A and B: percentages for selection.

* boltzmannTemperature: staring temperature for the BOLTZMANN selection.
* turnamentProbability: if the TOURNAMENT selection is deterministic (false) or probabilistic (true).
* tournamentPlayerSize: amount of characters compared simultaneously for the TOURNAMENT selection.
* deterministic: if true, uses prefixed seed for random operations.
