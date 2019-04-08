
class matriz extends base
{	
	constructor(int n, int m)
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
	}
	
	
	int Print()
	{
		int i,j;
		for (i = 0; i < linhas; i = i + 1)
		{
			print "\n";
			for (j = 0; j < cols; j = j + 1)
			{
				print this.get(i,j) + " ";
			}
		}
		print "\n";
	}
	
	int start()
	{
		int i, j, m, n;
		print "Número de linhas: ";
		read n;
		print "Número de colunas: ";
		read m;
		base m1, m2;
		matriz m3;
		m1 = new matriz(n,m);
		m3 = m1;
		for (i = 0; i < n; i = i + 1)
		{
			for (j = 0; j < m; j = j + 1)
			{
				print "v[" + i + "," + j + "] = ";
				int k;
				read k;
				k = m1.set(i,j,k);
			}
		}
		m2 = m1.soma(m3);
		i = m1.Print();
		i = m2.Print();
		m2 = m1.soma2(m3);
		i = m2.Print(); 
	}
}


class base
{
	int v[][];
	int linhas, cols;

	int set(int i, int j, int k)
	{
		print "Isso não deve ser impresso nunca";
	}

	int get(int i, int j)
	{
		return v[i][j];
	}

	matriz soma(matriz x)
	    ;
	
	matriz soma2(matriz x)
	    ;
	
	int Print()
	    ;
}
