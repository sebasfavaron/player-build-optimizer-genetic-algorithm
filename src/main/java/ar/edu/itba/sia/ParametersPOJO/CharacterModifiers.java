package ar.edu.itba.sia.ParametersPOJO;

public class CharacterModifiers {
    public final float attackModifier;
    public final float defenseModifier;
    public final float forceModifier;
    public final float agilityModifier;
    public final float expertiseModifier;
    public final float resistanceModifier;
    public final float lifeModifier;

    public CharacterModifiers(float attackModifier, float defenseModifier, float forceModifier, float agilityModifier, float expertiseModifier, float resistanceModifier, float lifeModifier) {
        this.attackModifier = attackModifier;
        this.defenseModifier = defenseModifier;
        this.forceModifier = forceModifier;
        this.agilityModifier = agilityModifier;
        this.expertiseModifier = expertiseModifier;
        this.resistanceModifier = resistanceModifier;
        this.lifeModifier = lifeModifier;
    }

    public float getAttack() {
        return attackModifier;
    }

    public float getDefense() {
        return defenseModifier;
    }

    public float getForce() {
        return forceModifier;
    }

    public float getAgility() {
        return agilityModifier;
    }

    public float getExpertise() {
        return expertiseModifier;
    }

    public float getResistance() {
        return resistanceModifier;
    }

    public float getLifeModifier() {
        return lifeModifier;
    }

    @Override
    public String toString() {
        return "CharacterModifiers{" +
                "attackModifier=" + attackModifier +
                ", defenseModifier=" + defenseModifier +
                ", forceModifier=" + forceModifier +
                ", agilityModifier=" + agilityModifier +
                ", expertiseModifier=" + expertiseModifier +
                ", resistanceModifier=" + resistanceModifier +
                ", lifeModifier=" + lifeModifier +
                '}';
    }
}
