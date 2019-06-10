import langXrt.Runtime;

public class matriz extends base
{	
	public matriz(int n, int m)
	{
		int i;
		v = new int[n][m];
		linhas = n;
		cols = m;
	}
	
	matriz soma(matriz x)
	{
		matriz s;
		s = new matriz(linhas, cols);
		int i,j;
		for (i = 0; i < linhas; i = i + 1)
		{
			for (j = 0; j < cols; j = j + 1)
			{
				int k;
				k = s.set(i,j, this.get(i,j) + x.get(i,j));
			}
		}
		return s;
	}
	
	matriz soma2(matriz x)
	{
		matriz s;
		s = new matriz(linhas, cols);
		int i,j;
		for (i = 0; i < linhas; i = i + 1)
		{
			for (j = 0; j < cols; j = j + 1)
			{
				s.v[i][j] = v[i][j] + x.v[i][j];
			}
		}
		return s;
	}
	
	int set(int i, int j, int value)
	{
		v[i][j] = value;
		return 0;
	}
	
	
	int Print()
	{
		int i,j;
		for (i = 0; i < linhas; i = i + 1)
		{
			System.out.print("\n");
			for (j = 0; j < cols; j = j + 1)
			{
				System.out.print( this.get(i,j) + " ");
			}
		}
		System.out.print( "\n");
		return 0;
	}
	
	static int start()
	{
		int i, j, m, n;
		System.out.print( "Número de linhas: ");
		n = Runtime.readInt();
		System.out.print( "Número de colunas: ");
		 m = Runtime.readInt();
		base m1, m2, v1[];
		matriz m3, v3[];
		m1 = new matriz(n,m);
		m3 = (matriz) m1;

		for (i = 0; i < n; i = i + 1)
		{
			for (j = 0; j < m; j = j + 1)
			{
				System.out.print( "v[" + i + "," + j + "] = ");
				int k;
				k = Runtime.readInt();
				k = m1.set(i,j,k);
			}
		} 
		m2 = m1.soma(m3);
		int k = (int) Math.sqrt(9);
		i = m1.Print();
		i = m2.Print();
		m2 = m1.soma2(m3);
		i = m2.Print(); 
		return 0;
	}
	
	static public void main(String[] x)
	{
		Runtime.initialize();
	   	start();
	   	Runtime.finilizy();
	}
}


class base
{
	int v[][];
	int linhas, cols;

	int set(int i, int j, int k)
	{
		System.out.print( "Isso não deve ser impresso nunca");
		return 0;
	}

	int get(int i, int j)
	{
		return v[i][j];
	}

	matriz soma(matriz x)
	{
	    return null;
	 }
	
	matriz soma2(matriz x)
	{
	    return null;
	 }
	
	int Print()
	{
	    return 0;
	 }
}
