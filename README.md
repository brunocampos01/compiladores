# Compiladores
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/6f4c4d114f684374a142090ce8bc799f)](https://www.codacy.com/gh/brunocampos01/introducao-a-programacao-orientada-a-objetos/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=brunocampos01/introducao-a-programacao-orientada-a-objetos&amp;utm_campaign=Badge_Grade)
![License](https://img.shields.io/badge/Code%20License-MIT-green.svg)
![java](https://img.shields.io/badge/UFSC-Compiladores-green.svg)

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

---

<p  align="left">
<br/>
<a href="mailto:brunocampos01@gmail.com" target="_blank"><img src="https://github.com/brunocampos01/devops/blob/master/images/email.png" alt="Gmail" width="30">
</a>
<a href="https://stackoverflow.com/users/8329698/bruno-campos" target="_blank"><img src="https://github.com/brunocampos01/devops/blob/master/images/stackoverflow.png" alt="GitHub" width="30">
</a>
<a href="https://www.linkedin.com/in/brunocampos01" target="_blank"><img src="https://github.com/brunocampos01/devops/blob/master/images/linkedin.png" alt="LinkedIn" width="30"></a>
<a href="https://github.com/brunocampos01" target="_blank"><img src="https://github.com/brunocampos01/devops/blob/master/images/github.png" alt="GitHub" width="30"></a>
<a href="https://brunocampos01.netlify.app/" target="_blank"><img src="https://github.com/brunocampos01/devops/blob/master/images/blog.png" alt="Website" width="30">
</a>
<a href="https://medium.com/@brunocampos01" target="_blank"><img src="https://github.com/brunocampos01/devops/blob/master/images/medium.png" alt="GitHub" width="30">
</a>
<a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-sa/4.0/88x31.png",  align="right" /></a><br/>
</p>
