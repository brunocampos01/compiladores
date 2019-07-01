/*
– Possibilidade de inicializar uma variável fora de qualquer método;
– Operadores lógicos AND, OR, XOR e NOT;
– Expressões lógicas com estes operadores;
– Uso de expressoes lógicas nos contextos adequados;
– Novos tipos de variáveis e literais: BYTE, SHORT, LONG e FLOAT, além dos já existentes;
– Qualificadores de identificadores: FINAL, PUBLIC, PRIVATE e PROTECTED, como usado em Java.
*/

class TesteExpressoesLogicas {

    byte outMethodByte ;
    short outMethodShort ;
    long outMethodLong ;
    float outMethodFloat ;

    float withoutFinal ;
    final float withFinal ;
    int a, b ;
    float startedVariable = 1.0 ;
    string literal = "dez garantido" ;

    public byte variablePublic ;
    protected short variableProtected ;
    private long variablePrivate ;
    final private float variablePrivateAndFinal ;


    constructor(int t) {
        super(t);
        k = t;
    }

    public int start() {
        SClass x;
        x = new SClass(10);
        return 10;
    }

    // OR
    public int imprimeIteracao (string i) {
        if((i > 10) || (i < 50)) {
            print i ;
        }
        return i ;
    }

    // AND
    public int imprimeIteracao(int i) {
        if((a > 10) && (b < 50)) {
            print i ;
        }
        return i ;
    }

    // NOT
    protected int compara(long a) {
        if( !(a) ){
          return 1 ;
        }
        return 0 ;
    }

    // XOR
    private int testaXor() {
        a = a ^ b ;
        b = b ^ a ;

        print "a = "+a ;
        print "b = "+b ;

        return a ;
    }
}
