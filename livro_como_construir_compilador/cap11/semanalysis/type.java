package semanalysis;

import symtable.*;


public class type {
    public EntryTable ty; // entrada na tabela do tipo
    public int dim; // dimens�o

    public type(EntryTable t, int d) {
        ty = t;
        dim = d;
    }

    public String dscJava() {
        return EntryTable.strDim(dim) + ty.dscJava();
    }
}
