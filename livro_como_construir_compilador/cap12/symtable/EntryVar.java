package symtable;


// classe que abriga uma declarac�o de vari�vel na tabela de s�mbolos
public class EntryVar extends EntryTable {
    public EntryTable type; // apontador para o tipo da vari�vel
    public int dim; // n�mero de dimens�es da vari�vel
    public int localcount; // numera��o seq�encial para as vars. locais

    // cria uma entrada para var. de classe
    public EntryVar(String n, EntryTable p, int d) {
        name = n; // nome da vari�vel
        type = p; // apontador para a classe
        dim = d; // n�mero de dimens�es
        localcount = -1; // n�mero seq�encial � sempre -1 (var. n�o local)
    }

    // cria uma entrada para var.local
    public EntryVar(String n, EntryTable p, int d, int k) {
        name = n; // nome da vari�vel
        type = p; // apontador para a classe
        dim = d; // n�mero de dimens�es
        localcount = k; // inclui tbem o n�mero seq�encial 
    }

    public String dscJava() {
        String s = strDim(dim);
        s += type.dscJava();

        return s;
    }
}
