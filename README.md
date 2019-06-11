# Compiladores

## Trabalho
Desenvolvimento de um compilador
- [Analisador Léxico](https://github.com/brunocampos01/compiladores/tree/master/trabalho_parte_01-analisador_lexico)
- [Analisador Sinático](https://github.com/brunocampos01/compiladores/tree/master/trabalho_parte_02-analisador_sintatico)
- [Analisador Semântico]()

### Requirements
- Java 1.8
- Javacc

```bash
sudo apt install javacc
```

### Generate Parser 
```
javacc parser/langX++.jj
```

###  Generate Class File 
```
javac parser/langX.java
```

### Tests
```
 java parser.langX -short testes_e_logs/teste-lexico.x
 java parser.langX -short testes_e_logs/teste-com-erro-lexico.x
```

### Debug - Syntactic Analyzer
```bash
java parser.langX -debug_AS testes_e_logs/debugAS.x
```
