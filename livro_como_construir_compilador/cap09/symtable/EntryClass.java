package symtable;


// classe corresponde a uma declaracão de classe na tab. de símbolos
public class EntryClass extends EntryTable {
    public Symtable nested; // tabela para declaracão de elementos aninhados
    public EntryClass parent; // entrada correspondente à superclasse

    public EntryClass(String n, Symtable t) {
        name = n; // nome da classe declarada
        nested = new Symtable(this); // tabela onde inserir vars, métodos ou classes
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
            p = ""; // não é uma classe aninhada
        } else {
            p = up.completeName() + "$"; // classe aninhada
        }

        return p + name; // retorna nome nível superior $ nome da classe
    }

    public String dscJava() // devolve descritor de tipo
     {
        return "L" + completeName() + ";";
    }
}
