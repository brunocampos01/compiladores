/***********************************************
Esse programa implementa uma ?rvore de busca bin?ria
*************************************************/

class bintree { /* n? da ?rvore bin?ria */


class data { // define um classe aninhada do tipo data (dia, mes ano)
int dia, mes, ano;

constructor()  // construtor 1, sem par?metros
{
   ano = 1900; // inicializa em 1/1/1900
   mes = 1;
   dia = 1;
}

constructor(int d, int m, int a) // construtor 2 - dia m?s e ano como
{                // par?metros 
   dia = d;
   mes = m;
   ano = a;
}

constructor(int d, int m, int a) // construtor 2 - dia m?s e ano como
{                // par?metros 
   dia = d;
   mes = m;
   ano = a;
}

int compara(data x) // compara duas datas 
{             // < 0 - menor > 0 maior 0 igual
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

data key;       // chave de compara??o
bintree left,right; // refer?ncia para os filhos

constructor(data x)
{
   key = x;
   left = null;
   right = null;
}

int insert(data k) // adiciona um elemento na ?rvore
{
int x;

   x = k.compara(key);
   if (x < 0)
   {
      if (left != null)
         return left.insert(k);
      left = new bintree(k);
      return 1;
   }
   if (x > 0)
   {
      if (right != null)
         return right.insert(k);
      right = new bintree(k);
      return 1;
   }
   return 0;
}

string insert(data k)
{
   ;
}

int treeprint(int x) // imprime a ?rvore
{
int i;

   if (left != null)
      i = left.treeprint(x+4);
   for (i = 0; i < x; i = i + 1)
      print " ";
   print key.dia+ "/" + key.mes + "/" + key.ano + "\n";
  // if (right != null)
    //  i = right.treeprinnt(x+4);
// ERRO: treeprinnt n?o existe
}


int start()
{
bintree t;
int i, d, m, a;
data w;

   read d; read m; read a;
   w = new data(n, m[0], a);
// ERRO: m n?o ? array
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

}
