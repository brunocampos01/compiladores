# Relatório Analisador Léxico X+++

## Equipe
14104255 - Bruno Aurélio Rôzza de Moura Campos<br/>
14101370 - Fabiano Pereira de Oliveira<br/>
14101383 - Laís Ferrigo Perazzolo<br/>
14101398 - Thary Correia<br/>

## Papeís no Desenvolvimento

Houve 3 encontros com **todos** os membros participando do desenvolvimento da primeira parte do trabalho.

#### Comandos utilizados
```bash
sudo apt install javacc
```

- Generate parser 
```bash
javacc langX+++.jj
```

- Generate .class 
```bash
javac parser/langX.java
```

- Testes
```bash
java parser.langX testes_e_logs/bintree-erro-sintatico.x
java parser.langX testes_e_logs/teste_expressoes_logicas.x 
```

- Debug Analisador Sintático
```bash
java parser.langX -debug_AS testes_e_logs/debugAS.x
```


#### Notas

- Todo o trabalho foi versionado usando a ferramenta git
- Encoding dos arquivos: US-ASCII
