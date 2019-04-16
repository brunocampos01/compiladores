/***********************************************
Esse programa implementa uma árvore de busca binária
*************************************************/

class bintree{
  class data{ // define um classe aninhada do tipo data (dia, mes ano)
    int dia, mes, ano;

    constructor(){
      ano = 1900; // inicializa em 1/1/1900
      mes = 1;
      dia = 1;
    }

    constructor(int d, int m, int a){ 
      dia = d;
      mes = m;
      ano = a;
    }

    int compara(data x){
      if ( ano < x.ano) return -1;
      if ( ano > x.ano) return 1;
      if ( mes < x.mes) return -1;
      if ( mes > x.mes) return 1;
      if ( dia < x.dia) return -1;
      if ( dia > x.dia) return 1;
      return 0;
    }
  } // final classe data


  // vari?veis da classe bintree
  data key;           // chave de compara??o
  bintree left,right; // refer?ncia para os filhos

  constructor(data x){
    key = x;
    left = null;
    right = null;
  }

  int insert(data k){
    int x;

    x = k.compara(key);
    if (x < 0){
        if (left != null)
          return left.insert(k);
        left = new bintree(k);
        return 1;
    }
    if (x > 0){
        if (right != null)
          return right.insert(k);
        right = new bintree(k);
        return 1;
    }
    return 0;
  }

  int treeprint(int x){
    int i;

    if (left != null)
        i = left.treeprint(x+4);
    for (i = 0; i < x; i = i + 1)
        print " ";
    print key.dia+ "/" + key.mes + "/" + key.ano + "\n";
    if (right != null)
        i = right.treeprint(x+4);
  }

  int start(){
    bintree t;
    int i, d, m, a;
    data w;

    read d; read m; read a;
    w = new data(d, m, a);
    t = new bintree(w);
    for (i = 0; i < 10; i = i + 1)
    {
        read d; read m; read a;
        w = new data(d, m, a);
        if (t.insert(w) == 0)
          print "Elemento ja existe\n";
    }
    i = t.treeprint(0);
    return 0;
  }

  // Extensões adicionadas
  // BYTE, AND, PUBLIC, FINAL, PRIVATE
  public final byte idade(byte i){
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
    if(!a){
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
