# Compiladores

## Trabalho
Desenvolvimento de um compilador
- [Analisador Léxico](trabalho_parte_01-analisador_lexico/)
- [Analisador Sinático](trabalho_parte_02-analisador_sintatico/)
- [Árvore Sintática](trabalho_parte_03-arvore_sintatica/)

### Requisitos
- Java 1.8
- Javacc

```bash
sudo apt install javacc
```

### Gerar Parser 
```
javacc parser/langX++.jj
```

### Compilar
```
javac parser/langX.java
```

### Testes - Analisador Léxico
```
 java parser.langX -short testes_e_logs/teste-lexico.x
 java parser.langX -short testes_e_logs/teste-com-erro-lexico.x
```

### Debug - Analisador Sintático
```bash
java parser.langX -debug_AS testes_e_logs/debugAS.x
```

### Árvore Sintática
```bash
java parser.langX -print_tree testes_e_logs/teste_expressoes_logicas.x
java parser.langX -print_tree testes_e_logs/teste_com_erro_classbody.x
```

#### Notas
- O arquivo `langX+++.jj` foi identado com 4 espaços,
- Encoding dos arquivos: US-ASCII
