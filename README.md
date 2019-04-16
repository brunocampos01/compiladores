# Compilador


## Analisador léxico (lexer) 

- É a primeira etapa de um compilador.  
- Ele é responsável por verificar todos os argumentos de entrada e depois classificar cada primitiva (simbolo) em token (símbolos ) léxicos.  
- Em seguida, esses símbolos seram manipulados por um parser (leitor de saída)  
Imagem


### Funcionamento do analisar léxico:  

1. isolar cada um de seus argumentos na forma de uma lista ligada   
2. avaliar os argumentos na forma normal (BNF, veremos depois)   
3. verificar se os argumentos são do tipo correto  
4. criar um ‘node’ para cada tipo apropriado   
5. preencher o campo do node com o resultado   
6. retornar o node 
Imagem

## Analisador sintático (Parser)  
- Analisa tokens e constroi uma arvore sintática  
- A análise sintática determina se uma cadeia de tokens (simbolos), já analisados pelo analisador léxico, pode ser gerado por uma gramatica.  
Imagem

Existem geradores de analisadores sintáticos :
- Yacc  
- Lex ou Flex  
- Bison
