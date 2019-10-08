package ar.edu.itba.sia.ParametersPOJO;

import ar.edu.itba.sia.enums.CrossoverType;
import ar.edu.itba.sia.enums.MutatorType;
import ar.edu.itba.sia.enums.ReplacerType;
import ar.edu.itba.sia.enums.SelectorType;

public class Methods {
    public final CrossoverType crossingType;
    public final SelectorType selectionType1;
    public final SelectorType selectionType2;
    public final SelectorType selectionType3;
    public final SelectorType selectionType4;
    public final ReplacerType replacementType;
    public final MutatorType mutationType;

    public Methods(CrossoverType crossingType, SelectorType selectionType1, SelectorType selectionType2,
                   SelectorType selectionType3, SelectorType selectionType4, ReplacerType replacementType,
                   MutatorType mutationType) {
        this.crossingType = crossingType;
        this.selectionType1 = selectionType1;
        this.selectionType2 = selectionType2;
        this.selectionType3 = selectionType3;
        this.selectionType4 = selectionType4;
        this.replacementType = replacementType;
        this.mutationType = mutationType;
    }




}
