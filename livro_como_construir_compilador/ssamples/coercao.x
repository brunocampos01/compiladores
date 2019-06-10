

      class A extends C
      {
         int start()
         {
             C var1;
             A var2;
             B var3;
             var1 = new A();
             var2 = var1;
             var3 = var1;
         }
      }

      class B extends C
      {
      }

      class C
      {

      }
