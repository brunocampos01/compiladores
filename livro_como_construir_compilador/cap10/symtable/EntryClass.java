package symtable;


// classe corresponde a uma declarac�o de classe na tab. de s�mbolos
public class EntryClass extends EntryTable {
    public Symtable nested; // tabela para declarac�o de elementos aninhados
    public EntryClass parent; // entrada correspondente � superclasse

    public EntryClass(String n, Symtable t) {
        name = n; // nome da classe declarada
        nested = new Symtable(this); // tabela onde inserir vars, m�todos ou classes
        parent = null; // sua superclasse
    }

    public String completeName() // devolve nome completo da classe
     {
        String p;
        Symtable t;
        EntryClass up;

        t = mytable;
        up = (EntryClass) t.levelup;

        if (up == null) {
            p = ""; // n�o � uma classe aninhada
        } else {
            p = up.completeName() + "$"; // classe aninhada
        }

        return p + name; // retorna nome n�vel superior $ nome da classe
    }

    public String dscJava() // devolve descritor de tipo
     {
        return "L" + completeName() + ";";
    }
}
