package symtable;


// classe que abriga uma declaracão de variável na tabela de símbolos
public class EntryVar extends EntryTable {
    public EntryTable type; // apontador para o tipo da variável
    public int dim; // número de dimensões da variável
    public int localcount; // numeração seqüencial para as vars. locais

    // cria uma entrada para var. de classe
    public EntryVar(String n, EntryTable p, int d) {
        name = n; // nome da variável
        type = p; // apontador para a classe
        dim = d; // número de dimensões
        localcount = -1; // número seqüencial é sempre -1 (var. não local)
    }

    // cria uma entrada para var.local
    public EntryVar(String n, EntryTable p, int d, int k) {
        name = n; // nome da variável
        type = p; // apontador para a classe
        dim = d; // número de dimensões
        localcount = k; // inclui tbem o número seqüencial 
    }

    public String dscJava() {
        String s = strDim(dim);
        s += type.dscJava();

        return s;
    }
}
