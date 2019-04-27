# Lexer parser

### Pre-requirements
- Java 1.8
- Javacc<br/>
```
sudo apt install javacc
```

### Generate parser 
```
javacc langX++.jj
```

###  Generate .class 
```
javac parser/langX.java
```

### Tests
```
 java parser.langX -short testes_e_logs/teste-lexico.x
 java parser.langX -short testes_e_logs/teste-com-erro-lexico.x

```
