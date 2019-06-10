/*
– Possibilidade de inicializar uma variável fora de qualquer método;
– Operadores lógicos AND, OR, XOR e NOT;
– Expressões lógicas com estes operadores;
– Uso de expressoes lógicas nos contextos adequados;
– Novos tipos de variáveis e literais: BYTE, SHORT, LONG e FLOAT, além dos já existentes;
– Qualificadores de identificadores: FINAL, PUBLIC, PRIVATE e PROTECTED, como usado em Java.
*/

class TesteExpressoesLogicas {

    float semFinal ;
    float a, b ;
    float variavelInicializada = 1.0 ;


    final byte outMethodByte ;
    final short outMethodShort ;
    final long outMethodLong ;
    final float outMethodFloat ;

    // OR, PUBLIC
    public imprimeIteracao(byte i) {
        if((i > 10) || (i < 50)) {
            print i ;
        }
        return i ;
      }

    // AND, PUBLIC
    public imprimeIteracao() {
        if((a > 10) && (b < 50)) {
            print i ;
        }
        return i ;
      }

    // NOT, PROTECTED
    protected compara(long a) {
        if( !(a) ){
          return 1 ;
        }
        return 0 ;
      }

    // XOR, PRIVATE
    private testaXor() {
        a = a ^ b ;
        b = b ^ a ;

        print "a = "+a ;
        print "b = "+b ;
      }
}
