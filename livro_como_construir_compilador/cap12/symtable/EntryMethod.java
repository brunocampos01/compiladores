package symtable;


// corresponde a uma declaracão de método na tabela de símbolos
public class EntryMethod extends EntryTable {
    public EntryTable type; // tipo de retorno do método
    public int dim; // número de dimensões do retorno
    public EntryRec param; // tipo dos parâmetros
    public int totallocals; // número de variáveis locais
    public int totalstack; // tamanho da pilha necessária
    public boolean fake; // true se é um falso construtor
    public boolean hassuper; // true se método possui chamada super

    // cria elemento para inserir na tabela 
    public EntryMethod(String n, EntryTable p, int d, EntryRec r) {
        name = n;
        type = p;
        dim = d;
        param = r;
        totallocals = 0;
        totalstack = 0;
        fake = false;
        hassuper = false;
    }

    public EntryMethod(String n, EntryTable p, boolean b) {
        name = n;
        type = p;
        dim = 0;
        param = null;
        totallocals = 0;
        totalstack = 0;
        fake = b;
        hassuper = false;
    }

    public String dscJava() {
        String s = strDim(dim);
        s += type.dscJava();

        return s;
    }
}
