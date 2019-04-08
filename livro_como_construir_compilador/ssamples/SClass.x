
class SClass extends S2
{
int k;

   constructor(int t)
   {
      super(t);
      k = t;
   }


   int start()
   {
      SClass x;
      x = new SClass(10);
   }
}

class S2
{
   constructor(int t)
   {
      print "" + t;
   }
}


