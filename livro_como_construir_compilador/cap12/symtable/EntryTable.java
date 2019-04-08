package symtable;


// classe geral para as possíveis entradas na tabela de símbolos
abstract public class EntryTable {
    public String name; // nome do símbolo (var., método ou classe)
    public EntryTable next; // apontador para próximo dentro da tabela 
    public int scope; // número do aninhamento corrente
    public Symtable mytable; // entrada aponta para a tabela da qual ela é parte

    abstract public String dscJava();

    static public String strDim(int n) {
        String p = "";

        for (int i = 0; i < n; i++)
            p += "[";

        return p;
    }
}
