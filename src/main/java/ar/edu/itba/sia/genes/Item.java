package ar.edu.itba.sia.genes;

import ar.edu.itba.sia.interfaces.Gene;

import java.util.Objects;

public class Item implements Gene {
    private final int id;
    private final double force;
    private final double agility;
    private final double expertise;
    private final double resistance;
    private final double life;
    private final Type type;

    @Override
    public GeneType getType() {
        return GeneType.ITEM;
    }

    public Item.Type getItemType(){
        return type;
    }

    public enum Type {
        WEAPON, BOOTS, HELMET, GLOVES, ARMOUR
    }

    public Item(int id, double force, double agility, double expertise, double resistance, double life, Type type){
        this.id = id;
        this.force = force;
        this.agility = agility;
        this.expertise = expertise;
        this.resistance = resistance;
        this.life = life;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public double getForce() {
        return force;
    }

    public double getAgility() {
        return agility;
    }

    public double getExpertise() {
        return expertise;
    }

    public double getResistance() {
        return resistance;
    }

    public double getLife() {
        return life;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Item)) return false;
        if (!super.equals(object)) return false;
        Item item = (Item) object;
        return type == item.type && id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                '}';
    }
}