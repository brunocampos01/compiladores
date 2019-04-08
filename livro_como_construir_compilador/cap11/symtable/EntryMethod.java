package symtable;


// corresponde a uma declarac�o de m�todo na tabela de s�mbolos
public class EntryMethod extends EntryTable {
    public EntryTable type; // tipo de retorno do m�todo
    public int dim; // n�mero de dimens�es do retorno
    public EntryRec param; // tipo dos par�metros
    public int totallocals; // n�mero de vari�veis locais
    public int totalstack; // tamanho da pilha necess�ria
    public boolean fake; // true se � um falso construtor
    public boolean hassuper; // true se m�todo possui chamada super

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
