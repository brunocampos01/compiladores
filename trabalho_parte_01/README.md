# Lexer parser

### Pre-requirements
- Javacc<br/>
```
sudo apt install javacc
```

### Generate parser 
```
javacc langX++.jj
```

###  Gerar .class 
```
javac parser/langX.java
```


### Testes
```
 java parser.langX -short exemplos/bintree-erro-lexico.x
  java parser.langX -short exemplos/bintree.x

```
