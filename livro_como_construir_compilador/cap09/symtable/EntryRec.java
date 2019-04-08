package symtable;


// lista de EntryClass, usada para representar os tipos de uma lista
// de par�metros
public class EntryRec extends EntryTable {
    public EntryTable type; // tipo de um objeto
    public int dim; // dimens�o
    public EntryRec next; // apontador para o resto da lista
    public int cont; // n�mero de elementos a partir daquele elemento

    // cria elemento
    public EntryRec(EntryTable p, int d, int c) {
        type = p;
        cont = c;
        dim = d;
        next = null;
    }

    // cria elemento e p�e no in�cio da lista
    public EntryRec(EntryTable p, int d, int c, EntryRec t) {
        type = p;
        cont = c;
        dim = d;
        next = t;
    }

    // inverte a lista de EntryRec
    public EntryRec inverte(EntryRec ant) {
        EntryRec r = this;

        if (next != null) {
            r = next.inverte(this);
        }

        cont = ant.cont + 1;
        next = ant;

        return r;
    }

    public EntryRec inverte() {
        EntryRec r = this;

        cont = 1;

        if (next != null) {
            r = next.inverte(this);
        }

        next = null;

        return r;
    }

    // devolve a representa��o da EntryRec na forma de string
    public String toStr() {
        String s;

        s = type.name;

        for (int i = 0; i < dim; i++)
            s += "[]";

        if (next != null) {
            s += (", " + next.toStr());
        }

        return s;
    }

    // devolve descritor da EntryRec
    public String dscJava() {
        String s;

        s = strDim(dim);
        s += type.dscJava();

        if (next != null) {
            s += next.dscJava();
        }

        return s;
    }

    // verifica a igualdade de dois objetos do tipo EntryRec
    public boolean equals(EntryRec x) {
        EntryRec p;
        EntryRec q;

        if ((x == null) || (cont != x.cont)) {
            return false;
        }

        p = this;
        q = x;

        do {
            if ((p.type != q.type) || (p.dim != q.dim)) {
                return false;
            }

            p = p.next;
            q = q.next;
        } while ((p != null) && (q != null));

        return p == q; // null
    }
}
