package ar.edu.itba.sia;

import ar.edu.itba.sia.interfaces.Gene;
import ar.edu.itba.sia.genes.Height;
import ar.edu.itba.sia.genes.Item;

import java.util.Arrays;
import java.util.Objects;

import static java.lang.Math.pow;
import static java.lang.Math.tanh;


public class Character {
    private final long id;
    private final Item[] items;
    private final Height height;
    private final float fitness;

    private static long ID = 0;

    public Character(Item[] items, float height){
        this.id = ID++;
        this.items = items;
        this.height = new Height(height);
        this.fitness = calcFitness();

        if(ID == Long.MAX_VALUE) {
            ID = 0;
        }
    }

    public Character(Gene[] genes){
        this.id = ID++;
        this.items = new Item[genes.length-1];
        for(int i=0 ; i< items.length; i++){
            items[i] = (Item) genes[i];
        }
        this.height = (Height) genes[genes.length-1];
        this.fitness = calcFitness();

        if(ID == Long.MAX_VALUE) {
            ID = 0;
        }
    }

    private float calcFitness() {
        Parameters param = Parameters.getInstance();

        return param.characterModifiers.attackModifier * calcAttack() + param.characterModifiers.defenseModifier * calcDefense();
    }

    private float calcAttack() {
        return (getAgility() + getExpertise()) * getForce() * calcATM();
    }

    private float calcDefense() {
        return (getResistance() + getExpertise()) * getLife() * calcDEM();
    }

    private float calcATM() {
        return (float) (0.5 - pow((3 * height.get() - 5), 4) + pow((3 * height.get() - 5 ), 2) + height.get() / 2);
    }

    private float calcDEM(){
        return (float) (2 + pow((3 * height.get() - 5), 4) - pow((3 * height.get() - 5), 2) - height.get() / 2);
    }

    private float getForce() {
        double sum = Arrays.stream(items)
                .mapToDouble(Item::getForce)
                .sum();

        return (float) (100 * tanh(0.01 * sum));
    }

    private float getAgility() {
        double sum = Arrays.stream(items)
                .mapToDouble(Item::getAgility)
                .sum();

        return (float) (tanh(0.01 * sum));
    }

    private float getExpertise(){
        double sum = Arrays.stream(items)
                .mapToDouble(Item::getExpertise)
                .sum();

        return (float) (0.6 * tanh(0.01 * sum));
    }

    private float getResistance(){
        double sum = Arrays.stream(items)
                .mapToDouble(Item::getResistance)
                .sum();

        return (float) (tanh(0.01 * sum));
    }

    private float getLife(){
        double sum = Arrays.stream(items)
                .mapToDouble(Item::getLife)
                .sum();

        return (float) (100 * tanh(0.01 * sum));
    }

    public Item[] getItems() {
        return items;
    }

    public float getFitness() {
        return fitness;
    }

    public float getHeight() {
        return height.get();
    }

    public Gene[] getGenes() {
        Gene[] genes = new Gene[items.length+1];

        System.arraycopy(items, 0, genes, 0, items.length);

        genes[items.length] = height;
        return genes;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Character{" +
                "items=" + Arrays.toString(items) +
                ", height=" + height +
                ", fitness=" + fitness +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return id == character.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean hasSameGenes(Character c1){

        boolean sameItems = true;

        return c1.height == this.height
                && Arrays.equals(items, c1.items);
    }


}