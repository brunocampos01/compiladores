# Relatório Árvore Sintático X+++

## Equipe
14104255 - Bruno Aurélio Rôzza de Moura Campos<br/>
14101370 - Fabiano Pereira de Oliveira<br/>
14101383 - Laís Ferrigo Perazzolo<br/>
14101398 - Thary Correia<br/>

## Papeis no Desenvolvimento
Houve 1 encontro com **todos** os membros participando do desenvolvimento da terceira parte do trabalho.


## Alterações que foram realizadas sobre o projeto sugerido nos capítulos 6 e 7 de Delamaro (2004)

- construção da árvore sintática
- impressão da árvore sintática

#### syntacticTree/FloatConstNode.java
- Arquivo criado
```java
package syntacticTree;

import parser.*;


public class FloatConstNode extends ExpreNode {
    public FloatConstNode(Token t) {
        super(t);
    }
}
```

#### syntacticTree/ByteConstNode.java
- Arquivo criado
```java
package syntacticTree;

import parser.*;


public class ByteConstNode extends ExpreNode {
    public ByteConstNode(Token t) {
        super(t);
    }
}
```

#### syntacticTree/LongConstNode.java
- Arquivo criado
```java
package syntacticTree;

import parser.*;


public class LongConstNode extends ExpreNode {
    public LongConstNode(Token t) {
        super(t);
    }
}
```

#### syntacticTree/ShortConstNode.java
- Arquivo criado
```java
package syntacticTree;

import parser.*;


public class ShortConstNode extends ExpreNode {
    public ShortConstNode(Token t) {
        super(t);
    }
}
```


#### syntacticTree/PrintTree.java
- Alterado o arquivo
```java
    // PARTE 03
    // ------------------------ Constante BYTE ----------------------------
    public void numberByteConstNode(ByteConstNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
    }

    public void printByteConstNode(ByteConstNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": ByteConstNode ===> " +
                x.position.image);
    }

    // PARTE 03
    // ------------------------ Constante SHORT ----------------------------
    public void numberShortConstNode(ShortConstNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
    }

    public void printShortConstNode(ShortConstNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": ShortConstNode ===> " +
                x.position.image);
    }

    // PARTE 03
    // ------------------------ Constante LONG ----------------------------
    public void numberLongConstNode(LongConstNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
    }

    public void printLongConstNode(LongConstNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": LongConstNode ===> " +
                x.position.image);
    }

    // PARTE 03
    // ------------------------ Constante FLOAT ----------------------------
    public void numberFloatConstNode(FloatConstNode x) {
        if (x == null) {
            return;
        }

        x.number = kk++;
    }

    public void printFloatConstNode(FloatConstNode x) {
        if (x == null) {
            return;
        }

        System.out.println();
        System.out.print(x.number + ": FloatConstNode ===> " +
                x.position.image);
    }
```

- Na parte `expressão em geral`
```java
    public void printExpreNode(ExpreNode x) {
    ...
        // PARTE 03
        } else if (x instanceof ByteConstNode) {
            printByteConstNode((ByteConstNode) x);
        } else if (x instanceof ShortConstNode) {
            printShortConstNode((ShortConstNode) x);
        } else if (x instanceof LongConstNode) {
            printLongConstNode((LongConstNode) x);
        } else if (x instanceof FloatConstNode) {
            printFloatConstNode((FloatConstNode) x);
```

```java
    public void numberExpreNode(ExpreNode x) {
    ...
        // PARTE 03
        } else if (x instanceof ByteConstNode) {
            numberByteConstNode((ByteConstNode) x);
        } else if (x instanceof ShortConstNode) {
            numberShortConstNode((ShortConstNode) x);
        } else if (x instanceof LongConstNode) {
            numberLongConstNode((LongConstNode) x);
        } else if (x instanceof FloatConstNode) {
            numberFloatConstNode((FloatConstNode) x);
```


#### langX+++.jj
- Alterado o arquivo
```java
```

### Comandos utilizados
```bash
sudo apt install javacc
```

- Geração do parser 
```bash
javacc parser/langX+++.jj
```

- Geração dos arquivos `.class` 
```bash
javac parser/langX.java
```

- Testes
```bash
java parser.langX testes_e_logs/teste_com_erro_classbody.x
java parser.langX testes_e_logs/teste_expressoes_logicas.x
```

- Debug Analisador Sintático
```bash
java parser.langX -debug_AS testes_e_logs/debugAS.x
```

- Árvore Sintática
```bash
java parser.langX -print_tree testes_e_logs/teste_expressoes_logicas.x
java parser.langX -print_tree testes_e_logs/teste_com_erro_classbody.x
```

#### Notas
- O arquivo `langX+++.jj` foi identado com 4 espaços,
- Todo o trabalho foi versionado usando a ferramenta git,
- Encoding dos arquivos: US-ASCII
