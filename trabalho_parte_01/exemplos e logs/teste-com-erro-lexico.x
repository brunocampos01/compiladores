/***********************************************
Esse programa implementa uma série de testes léxicos da linguagem X+++
*************************************************/

class teste {

// Extensões adicionadas
  // BYTE, AND, PUBLIC, FINAL, PRIVATE
  public #final b?te idade(byte i){
    private byte idade;

    if(idade > 0 && idade <= 127){
      idade = i;
      return idade;
    }
  }

  // SHORT, OR, PROTECTED
  protected imprimeIteracao(short i){
    for(i = 0; i < 100; i++) {
      if(i > 10 || i < 50){
        print i;
      }
    }
  }

  // LONG, NOT
  protected int compara(long a){
    if(!?a){
      return 1;
    }
    return 0;
  }

  // XOR, FLOAT
  protected testaXor(){
    float a = 1;
    float b = 2;

    a = a ^ b;
    b = b ^ a;

    print "a= "+a;
    print "b= "+b;
  }

}