package semanalysis;

import parser.*;

import symtable.*;

import syntacticTree.*;


public class TypeCheck extends VarCheck {
    int nesting; // controla o nivel de aninhamento em comandos repetitivos
    protected int Nlocals; // conta número de variáveis locais num método
    type Returntype; // tipo de retorno de um método
    protected final EntrySimple STRING_TYPE;
    protected final EntrySimple INT_TYPE;
    protected final EntrySimple NULL_TYPE;
    protected EntryMethod CurMethod; // método sendo analisado
    boolean cansuper; // indica se chamada super é permitida

    public TypeCheck() {
        super();
        nesting = 0;
        Nlocals = 0;
        STRING_TYPE = (EntrySimple) Maintable.classFindUp("string");
        INT_TYPE = (EntrySimple) Maintable.classFindUp("int");
        NULL_TYPE = new EntrySimple("$NULL$");
        Maintable.add(NULL_TYPE);
    }

    public void TypeCheckRoot(ListNode x) throws SemanticException {
        VarCheckRoot(x); // faz análise das variáveis e métodos
        TypeCheckClassDeclListNode(x); // faz análise do corpo dos métodos

        if (foundSemanticError != 0) { // se houve erro, lança exceção
            throw new SemanticException(foundSemanticError +
                " Semantic Errors found (phase 3)");
        }
    }

    // ------------- lista de classes --------------------------
    public void TypeCheckClassDeclListNode(ListNode x) {
        if (x == null) {
            return;
        }

        try {
            TypeCheckClassDeclNode((ClassDeclNode) x.node);
        } catch (SemanticException e) { // se um erro ocorreu na classe, da a msg mas faz a análise p/ próxima
            System.out.println(e.getMessage());
            foundSemanticError++;
        }

        TypeCheckClassDeclListNode(x.next);
    }

    // verifica se existe referência circular de superclasses
    private boolean circularSuperclass(EntryClass orig, EntryClass e) {
        if (e == null) {
            return false;
        }

        if (orig == e) {
            return true;
        }

        return circularSuperclass(orig, e.parent);
    }

    // ------------- declaracão de classe -------------------------
    public void TypeCheckClassDeclNode(ClassDeclNode x)
        throws SemanticException {
        Symtable temphold = Curtable; // salva tabela corrente
        EntryClass nc;

        if (x == null) {
            return;
        }

        nc = (EntryClass) Curtable.classFindUp(x.name.image);

        if (circularSuperclass(nc, nc.parent)) { // se existe declaracão circular, ERRO
            nc.parent = null;
            throw new SemanticException(x.position, "Circular inheritance");
        }

        Curtable = nc.nested; // tabela corrente = tabela da classe
        TypeCheckClassBodyNode(x.body);
        Curtable = temphold; // recupera tabela corrente
    }

    // ------------- corpo da classe -------------------------
    public void TypeCheckClassBodyNode(ClassBodyNode x) {
        if (x == null) {
            return;
        }

        TypeCheckClassDeclListNode(x.clist);
        TypeCheckVarDeclListNode(x.vlist);
        TypeCheckConstructDeclListNode(x.ctlist);
        TypeCheckMethodDeclListNode(x.mlist);
    }

    // ---------------- Lista de declaracões de variáveis ----------------
    public void TypeCheckVarDeclListNode(ListNode x) {
        if (x == null) {
            return;
        }

        try {
            TypeCheckVarDeclNode((VarDeclNode) x.node);
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
        }

        TypeCheckVarDeclListNode(x.next);
    }

    // -------------------- Declaracão de variável --------------------
    public void TypeCheckVarDeclNode(VarDeclNode x) throws SemanticException {
        ListNode p;
        EntryVar l;

        if (x == null) {
            return;
        }

        for (p = x.vars; p != null; p = p.next) {
            VarNode q = (VarNode) p.node;

            // tenta pegar 2a. ocorrência da variável na tabela
            l = Curtable.varFind(q.position.image, 2);

            // se conseguiu a variável foi definida 2 vezes, ERRO
            if (l != null) {
                throw new SemanticException(q.position,
                    "Variable " + q.position.image + " already declared");
            }
        }
    }

    // -------------- Lista de construtores ---------------------
    public void TypeCheckConstructDeclListNode(ListNode x) {
        if (x == null) {
            return;
        }

        try {
            TypeCheckConstructDeclNode((ConstructDeclNode) x.node);
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
        }

        TypeCheckConstructDeclListNode(x.next);
    }

    // ------------------ Declaracão de construtor -----------------
    public void TypeCheckConstructDeclNode(ConstructDeclNode x)
        throws SemanticException {
        EntryMethod t;
        EntryRec r = null;
        EntryTable e;
        EntryClass thisclass;
        EntryVar thisvar;
        ListNode p;
        VarDeclNode q;
        VarNode u;
        int n;

        if (x == null) {
            return;
        }

        p = x.body.param;
        n = 0;

        // monta a lista com os tipos dos parâmetros
        while (p != null) {
            q = (VarDeclNode) p.node; // q = nó com a declaracão do parâmetro
            u = (VarNode) q.vars.node; // u = nó com o nome e dimensão
            n++;

            // acha a entrada do tipo na tabela
            e = Curtable.classFindUp(q.position.image);

            // constrói a lista com os tipos dos parâmetros
            r = new EntryRec(e, u.dim, n, r);
            p = p.next;
        }

        if (r != null) {
            r = r.inverte(); // inverte a lista
        }

        // acha a entrada do construtor na tabela
        t = Curtable.methodFind("constructor", r);
        CurMethod = t; // guarda método corrente

        // inicia um novo escopo na tabela corrente
        Curtable.beginScope();

        // pega a entrada da classe corrente na tabela
        thisclass = (EntryClass) Curtable.levelup;

        thisvar = new EntryVar("this", thisclass, 0, 0);
        Curtable.add(thisvar); // inclui variável local "this" com número 0
        Returntype = null; // tipo de retorno do método = nenhum
        nesting = 0; // nível de aninhamento de comandos for
        Nlocals = 1; // inicializa numero de variáveis locais
        TypeCheckMethodBodyNode(x.body);
        t.totallocals = Nlocals; // número de variávamos locais do método
        Curtable.endScope(); // retira variáveis locais da tabela
    }

    // -------------------------- Lista de métodos -----------------
    public void TypeCheckMethodDeclListNode(ListNode x) {
        if (x == null) {
            return;
        }

        try {
            TypeCheckMethodDeclNode((MethodDeclNode) x.node);
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
        }

        TypeCheckMethodDeclListNode(x.next);
    }

    // --------------------- Declaracão de método ---------------
    public void TypeCheckMethodDeclNode(MethodDeclNode x)
        throws SemanticException {
        EntryMethod t;
        EntryRec r = null;
        EntryTable e;
        EntryClass thisclass;
        EntryVar thisvar;
        ListNode p;
        VarDeclNode q;
        VarNode u;
        int n;

        if (x == null) {
            return;
        }

        p = x.body.param;
        n = 0;

        // monta a lista com os tipos dos parâmetros
        while (p != null) {
            q = (VarDeclNode) p.node; // q = nó com a declaracão do parâmetro
            u = (VarNode) q.vars.node; // u = nó com o nome e dimensão
            n++;

            // acha a entrada do tipo na tabela
            e = Curtable.classFindUp(q.position.image);

            // constrói a lista com os tipos dos parâmetros
            r = new EntryRec(e, u.dim, n, r);
            p = p.next;
        }

        if (r != null) {
            r = r.inverte();
        }

        // acha a entrada do método na tabela
        t = Curtable.methodFind(x.name.image, r);
        CurMethod = t; // guarda método corrente

        // Returntype = tipo de retorno do método
        Returntype = new type(t.type, t.dim);

        // inicia um novo escopo na tabela corrente
        Curtable.beginScope();

        // pega a entrada da classe corrente na tabela
        thisclass = (EntryClass) Curtable.levelup;

        thisvar = new EntryVar("this", thisclass, 0, 0);
        Curtable.add(thisvar); // inclui variável local "this" na tabela

        nesting = 0; // nível de aninhamento de comandos for
        Nlocals = 1; // inicializa número de variáveis locais
        TypeCheckMethodBodyNode(x.body);
        t.totallocals = Nlocals; // número de variáveis locais declaradas
        Curtable.endScope(); // retira variáveis locais da tabela corrente
    }

    //-------------------------- Corpo de método ----------------------
    public void TypeCheckMethodBodyNode(MethodBodyNode x) {
        if (x == null) {
            return;
        }

        TypeCheckLocalVarDeclListNode(x.param); // trata parâmetro como var. local

        cansuper = false;

        if (Curtable.levelup.parent != null) { // existe uma superclasse para a classe corrente ?
                                               // acha primeiro comando do método

            StatementNode p = x.stat;

            while (p instanceof BlockNode)
                p = (StatementNode) ((BlockNode) p).stats.node;

            cansuper = p instanceof SuperNode; // verifica se é chamada super
        }

        try {
            TypeCheckStatementNode(x.stat);
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
        }
    }

    //------------------------ lista de variáveis locais ----------------------
    public void TypeCheckLocalVarDeclListNode(ListNode x) {
        if (x == null) {
            return;
        }

        try {
            TypeCheckLocalVarDeclNode((VarDeclNode) x.node);
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
        }

        TypeCheckLocalVarDeclListNode(x.next);
    }

    //---------------------- Declaracão de variáveis locais ----------------------
    public void TypeCheckLocalVarDeclNode(VarDeclNode x)
        throws SemanticException {
        ListNode p;
        VarNode q;
        EntryVar l;
        EntryVar u;
        EntryTable c;

        if (x == null) {
            return;
        }

        // procura tipo da declaracão na tabela de símbolos
        c = Curtable.classFindUp(x.position.image);

        // se não achou, ERRO
        if (c == null) {
            throw new SemanticException(x.position,
                "Class " + x.position.image + " not found.");
        }

        for (p = x.vars; p != null; p = p.next) {
            q = (VarNode) p.node;
            l = Curtable.varFind(q.position.image);

            // se variável já existe é preciso saber que tipo de variável é
            if (l != null) {
                // primeiro verifica se é local, definida no escopo corrente
                if (l.scope == Curtable.scptr) { // se for, ERRO
                    throw new SemanticException(q.position,
                        "Variable " + p.position.image + " already declared");
                }

                // c.c. verifica se é uma variável de classe
                if (l.localcount < 0) { // se for dá uma advertência
                    System.out.println("Line " + q.position.beginLine +
                        " Column " + q.position.beginColumn +
                        " Warning: Variable " + q.position.image +
                        " hides a class variable");
                } else { // senão, é uma variável local em outro escopo
                    System.out.println("Line " + q.position.beginLine +
                        " Column " + q.position.beginColumn +
                        " Warning: Variable " + q.position.image +
                        " hides a parameter or a local variable");
                }
            }

            // insere a variável local na tabela corrente
            Curtable.add(new EntryVar(q.position.image, c, q.dim, Nlocals++));
        }
    }

    // --------------------------- Comando composto ----------------------
    public void TypeCheckBlockNode(BlockNode x) {
        Curtable.beginScope(); // início de um escopo
        TypeCheckStatementListNode(x.stats);
        Curtable.endScope(); // final do escopo, libera vars locais
    }

    // --------------------- Lista de comandos --------------------
    public void TypeCheckStatementListNode(ListNode x) {
        if (x == null) {
            return;
        }

        try {
            TypeCheckStatementNode((StatementNode) x.node);
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
        }

        TypeCheckStatementListNode(x.next);
    }

    // --------------------------- Comando print ---------------------
    public void TypeCheckPrintNode(PrintNode x) throws SemanticException {
        type t;

        if (x == null) {
            return;
        }

        // t = tipo e dimensão do resultado da expressão
        t = TypeCheckExpreNode(x.expr);

        // tipo tem que ser string e dimensão tem que ser 0
        if ((t.ty != STRING_TYPE) || (t.dim != 0)) {
            throw new SemanticException(x.position, "string expression required");
        }
    }

    // ---------------------- Comando read --------------------------
    public void TypeCheckReadNode(ReadNode x) throws SemanticException {
        type t;

        if (x == null) {
            return;
        }

        // verifica se o nó filho tem um tipo válido
        if (!(x.expr instanceof DotNode || x.expr instanceof IndexNode ||
                x.expr instanceof VarNode)) {
            throw new SemanticException(x.position,
                "Invalid expression in read statement");
        }

        // verifica se e uma atribuição para "this"
        if (x.expr instanceof VarNode) {
            EntryVar v = Curtable.varFind(x.expr.position.image);

            if ((v != null) && (v.localcount == 0)) // é a variável local 0?
             {
                throw new SemanticException(x.position,
                    "Reading into variable " + " \"this\" is not legal");
            }
        }

        // verifica se o tipo é string ou int
        t = TypeCheckExpreNode(x.expr);

        if ((t.ty != STRING_TYPE) && (t.ty != INT_TYPE)) {
            throw new SemanticException(x.position,
                "Invalid type. Must be int or string");
        }

        // verifica se não é array
        if (t.dim != 0) {
            throw new SemanticException(x.position, "Cannot read array");
        }
    }

    // --------------------- Comando return -------------------------
    public void TypeCheckReturnNode(ReturnNode x) throws SemanticException {
        type t;

        if (x == null) {
            return;
        }

        // t = tipo e dimensão do resultado da expressão
        t = TypeCheckExpreNode(x.expr);

        // verifica se é igual ao tipo do método corrente
        if (t == null) { // t == null não tem expressão no return

            if (Returntype == null) {
                return;
            } else { // se Returntype != null e um método, então ERRO
                throw new SemanticException(x.position,
                    "Return expression required");
            }
        } else {
            if (Returntype == null) { // retorno num construtor, ERRO
                throw new SemanticException(x.position,
                    "Constructor cannot return a value");
            }
        }

        // compara tipo e dimensão
        if ((t.ty != Returntype.ty) || (t.dim != Returntype.dim)) {
            throw new SemanticException(x.position, "Invalid return type");
        }
    }

    // ------------------------ Comando super --------------------------
    public void TypeCheckSuperNode(SuperNode x) throws SemanticException {
        type t;

        if (x == null) {
            return;
        }

        if (Returntype != null) {
            throw new SemanticException(x.position,
                "super is only allowed in constructors");
        }

        if (!cansuper) {
            throw new SemanticException(x.position,
                "super must be first statement in the constructor");
        }

        cansuper = false; // não permite outro super

        // p aponta para a entrada da superclasse da classe corrente
        EntryClass p = Curtable.levelup.parent;

        if (p == null) {
            throw new SemanticException(x.position,
                "No superclass for this class");
        }

        // t.ty possui um EntryRec com os tipos dos parâmetros
        t = TypeCheckExpreListNode(x.args);

        // procura o construtor na tabela da superclasse
        EntryMethod m = p.nested.methodFindInclass("constructor",
                (EntryRec) t.ty);

        // se não achou, ERRO
        if (m == null) {
            throw new SemanticException(x.position,
                "Constructor " + p.name + "(" +
                ((t.ty == null) ? "" : ((EntryRec) t.ty).toStr()) +
                ") not found");
        }

        CurMethod.hassuper = true; // indica que existe chamada a super no método
    }

    // ------------------------- Comando de atribuição -------------------
    public void TypeCheckAtribNode(AtribNode x) throws SemanticException {
        type t1;
        type t2;
        EntryVar v;

        if (x == null) {
            return;
        }

        // verifica se o nó filho tem um tipo válido
        if (!(x.expr1 instanceof DotNode || x.expr1 instanceof IndexNode ||
                x.expr1 instanceof VarNode)) {
            throw new SemanticException(x.position,
                "Invalid left side of assignment");
        }

        // verifica se é uma atribuição para "this"
        if (x.expr1 instanceof VarNode) {
            v = Curtable.varFind(x.expr1.position.image);

            if ((v != null) && (v.localcount == 0)) // é a variável local 0?
             {
                throw new SemanticException(x.position,
                    "Assigning to variable " + " \"this\" is not legal");
            }
        }

        t1 = TypeCheckExpreNode(x.expr1);
        t2 = TypeCheckExpreNode(x.expr2);

        // verifica tipos das expressões
        // verifica dimensões
        if (t1.dim != t2.dim) {
            throw new SemanticException(x.position,
                "Invalid dimensions in assignment");
        }

        // verifica se lado esquerdo é uma classe e direito é null, OK
        if (t1.ty instanceof EntryClass && (t2.ty == NULL_TYPE)) {
            return;
        }

        // verifica se t2 e subclasse de t1
        if (!(isSubClass(t2.ty, t1.ty) || isSubClass(t1.ty, t2.ty))) {
            throw new SemanticException(x.position,
                "Incompatible types for assignment ");
        }
    }

    protected boolean isSubClass(EntryTable t1, EntryTable t2) {
        // verifica se são o mesmo tipo (vale para tipos simples)
        if (t1 == t2) {
            return true;
        }

        // verifica se são classes
        if (!(t1 instanceof EntryClass && t2 instanceof EntryClass)) {
            return false;
        }

        // procura t2 nas superclasses de t1
        for (EntryClass p = ((EntryClass) t1).parent; p != null;
                p = p.parent)
            if (p == t2) {
                return true;
            }

        return false;
    }

    // ---------------------------------- comando if --------------------
    public void TypeCheckIfNode(IfNode x) {
        type t;

        if (x == null) {
            return;
        }

        try {
            t = TypeCheckExpreNode(x.expr);

            if ((t.ty != INT_TYPE) || (t.dim != 0)) {
                throw new SemanticException(x.expr.position,
                    "Integer expression expected");
            }
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
        }

        try {
            TypeCheckStatementNode(x.stat1);
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
        }

        try {
            TypeCheckStatementNode(x.stat2);
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
        }
    }

    // ------------------------- comando for -----------------------
    public void TypeCheckForNode(ForNode x) {
        type t;

        if (x == null) {
            return;
        }

        // analisa inicialização
        try {
            TypeCheckStatementNode(x.init);
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
        }

        // analisa expressão de controle
        try {
            t = TypeCheckExpreNode(x.expr);

            if ((t.ty != INT_TYPE) || (t.dim != 0)) {
                throw new SemanticException(x.expr.position,
                    "Integer expression expected");
            }
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
        }

        // analisa expressão de incremento
        try {
            TypeCheckStatementNode(x.incr);
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
        }

        // analisa comando a ser repetido
        try {
            nesting++; // incrementa o aninhamento
            TypeCheckStatementNode(x.stat);
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
        }

        nesting--; // decrementa o aninhamento
    }

    // --------------------------- Comando break --------------------
    public void TypeCheckBreakNode(BreakNode x) throws SemanticException {
        if (x == null) {
            return;
        }

        // verifica se está dentro de um for. Se não, ERRO
        if (nesting <= 0) {
            throw new SemanticException(x.position,
                "break not in a for statement");
        }
    }

    // --------------------------- Comando vazio -------------------
    public void TypeCheckNopNode(NopNode x) {
        // nada a ser feito
    }

    // ------------------ Expressões ----------------------------------------
    // -------------------------- Alocação de objeto ------------------------
    public type TypeCheckNewObjectNode(NewObjectNode x)
        throws SemanticException {
        type t;
        EntryMethod p;
        EntryTable c;

        if (x == null) {
            return null;
        }

        // procura a classe da qual se deseja criar um objeto
        c = Curtable.classFindUp(x.name.image);

        // se não achou, ERRO
        if (c == null) {
            throw new SemanticException(x.position,
                "Class " + x.name.image + " not found");
        }

        // t.ty recebe a lista de tipos dos argumentos
        t = TypeCheckExpreListNode(x.args);

        // procura um construtor com essa assinatura
        Symtable s = ((EntryClass) c).nested;
        p = s.methodFindInclass("constructor", (EntryRec) t.ty);

        // se não achou, ERRO
        if (p == null) {
            throw new SemanticException(x.position,
                "Constructor " + x.name.image + "(" +
                ((t.ty == null) ? "" : ((EntryRec) t.ty).toStr()) +
                ") not found");
        }

        // retorna c como tipo, dimensão = 0, local = -1 (não local)
        t = new type(c, 0);

        return t;
    }

    // -------------------------- Alocação de array ------------------------
    public type TypeCheckNewArrayNode(NewArrayNode x) throws SemanticException {
        type t;
        EntryTable c;
        ListNode p;
        ExpreNode q;
        int k;

        if (x == null) {
            return null;
        }

        // procura o tipo da qual se deseja criar um array
        c = Curtable.classFindUp(x.name.image);

        // se não achou, ERRO
        if (c == null) {
            throw new SemanticException(x.position,
                "Type " + x.name.image + " not found");
        }

        // para cada expressão das dimensões, verifica se tipo e int
        for (k = 0, p = x.dims; p != null; p = p.next) {
            t = TypeCheckExpreNode((ExpreNode) p.node);

            if ((t.ty != INT_TYPE) || (t.dim != 0)) {
                throw new SemanticException(p.position,
                    "Invalid expression for an array dimension");
            }

            k++;
        }

        return new type(c, k);
    }

    // --------------------------- Lista de expressões ---------------
    public type TypeCheckExpreListNode(ListNode x) {
        type t;
        type t1;
        EntryRec r;
        int n;

        if (x == null) {
            return new type(null, 0);
        }

        try {
            // pega tipo do primeiro nó da lista
            t = TypeCheckExpreNode((ExpreNode) x.node);
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
            t = new type(NULL_TYPE, 0);
        }

        // pega tipo do restante da lista. t1.ty contém um EntryRec
        t1 = TypeCheckExpreListNode(x.next);

        // n = tamanho da lista em t1
        n = (t1.ty == null) ? 0 : ((EntryRec) t1.ty).cont;

        // cria novo EntryRec com t.ty como 1.o elemento
        r = new EntryRec(t.ty, t.dim, n + 1, (EntryRec) t1.ty);

        // cria type com r como variável ty
        t = new type(r, 0);

        return t;
    }

    // --------------------- Expressão relacional -------------------
    public type TypeCheckRelationalNode(RelationalNode x)
        throws SemanticException {
        type t1;
        type t2;
        int op; // operação

        if (x == null) {
            return null;
        }

        op = x.position.kind;
        t1 = TypeCheckExpreNode(x.expr1);
        t2 = TypeCheckExpreNode(x.expr2);

        // se ambos são int, retorna OK
        if ((t1.ty == INT_TYPE) && (t2.ty == INT_TYPE)) {
            return new type(INT_TYPE, 0);
        }

        // se a dimensão é diferente, ERRO
        if (t1.dim != t2.dim) {
            throw new SemanticException(x.position,
                "Can not compare objects with different dimensions");
        }

        // se dimensão > 0 só pode comparar igualdade
        if ((op != langXConstants.EQ) && (op != langXConstants.NEQ) &&
                (t1.dim > 0)) {
            throw new SemanticException(x.position,
                "Can not use " + x.position.image + " for arrays");
        }

        // se dois são objetos do mesmo tipo pode comparar igualdade
        // isso inclui 2 strings
        if ((isSubClass(t2.ty, t1.ty) || isSubClass(t1.ty, t2.ty)) &&
                ((op == langXConstants.NEQ) || (op == langXConstants.EQ))) {
            return new type(INT_TYPE, 0);
        }

        // se um é objeto e outro null, pode comparar igualdade
        if (((t1.ty instanceof EntryClass && (t2.ty == NULL_TYPE)) ||
                (t2.ty instanceof EntryClass && (t1.ty == NULL_TYPE))) &&
                ((op == langXConstants.NEQ) || (op == langXConstants.EQ))) {
            return new type(INT_TYPE, 0);
        }

        throw new SemanticException(x.position,
            "Invalid types for " + x.position.image);
    }

    // ------------------------ Soma ou subtração  -------------------
    public type TypeCheckAddNode(AddNode x) throws SemanticException {
        type t1;
        type t2;
        int op; // operação
        int i;
        int j;

        if (x == null) {
            return null;
        }

        op = x.position.kind;
        t1 = TypeCheckExpreNode(x.expr1);
        t2 = TypeCheckExpreNode(x.expr2);

        // se dimensão > 0, ERRO
        if ((t1.dim > 0) || (t2.dim > 0)) {
            throw new SemanticException(x.position,
                "Can not use " + x.position.image + " for arrays");
        }

        i = j = 0;

        if (t1.ty == INT_TYPE) {
            i++;
        } else if (t1.ty == STRING_TYPE) {
            j++;
        }

        if (t2.ty == INT_TYPE) {
            i++;
        } else if (t2.ty == STRING_TYPE) {
            j++;
        }

        // 2 operadores inteiros, OK
        if (i == 2) {
            return new type(INT_TYPE, 0);
        }

        // um inteiro e um string só pode somar
        if ((op == langXConstants.PLUS) && ((i + j) == 2)) {
            return new type(STRING_TYPE, 0);
        }

        throw new SemanticException(x.position,
            "Invalid types for " + x.position.image);
    }

    // ---------------------- Multiplicação ou divisão --------------------
    public type TypeCheckMultNode(MultNode x) throws SemanticException {
        type t1;
        type t2;
        int op; // operação
        int i;
        int j;

        if (x == null) {
            return null;
        }

        op = x.position.kind;
        t1 = TypeCheckExpreNode(x.expr1);
        t2 = TypeCheckExpreNode(x.expr2);

        // se dimensão > 0, ERRO
        if ((t1.dim > 0) || (t2.dim > 0)) {
            throw new SemanticException(x.position,
                "Can not use " + x.position.image + " for arrays");
        }

        // só dois int's são aceitos
        if ((t1.ty != INT_TYPE) || (t2.ty != INT_TYPE)) {
            throw new SemanticException(x.position,
                "Invalid types for " + x.position.image);
        }

        return new type(INT_TYPE, 0);
    }

    // ------------------------- Expressão unaria ------------------------
    public type TypeCheckUnaryNode(UnaryNode x) throws SemanticException {
        type t;

        if (x == null) {
            return null;
        }

        t = TypeCheckExpreNode(x.expr);

        // se dimensão > 0, ERRO
        if (t.dim > 0) {
            throw new SemanticException(x.position,
                "Can not use unary " + x.position.image + " for arrays");
        }

        // só int é aceito
        if (t.ty != INT_TYPE) {
            throw new SemanticException(x.position,
                "Incompatible type for unary " + x.position.image);
        }

        return new type(INT_TYPE, 0);
    }

    // -------------------------- Constante inteira ----------------------
    public type TypeCheckIntConstNode(IntConstNode x) throws SemanticException {
        int k;

        if (x == null) {
            return null;
        }

        // tenta transformar imagem em número inteiro
        try {
            k = Integer.parseInt(x.position.image);
        } catch (NumberFormatException e) { // se deu erro, formato e inválido (possivelmente fora dos limites)
            throw new SemanticException(x.position, "Invalid int constant");
        }

        return new type(INT_TYPE, 0);
    }

    // ------------------------ Constante string ----------------------------
    public type TypeCheckStringConstNode(StringConstNode x) {
        if (x == null) {
            return null;
        }

        return new type(STRING_TYPE, 0);
    }

    // ------------------------------ Constante null --------------------------
    public type TypeCheckNullConstNode(NullConstNode x) {
        if (x == null) {
            return null;
        }

        return new type(NULL_TYPE, 0);
    }

    // -------------------------------- Nome de variável ------------------
    public type TypeCheckVarNode(VarNode x) throws SemanticException {
        EntryVar p;

        if (x == null) {
            return null;
        }

        // procura variável na tabela
        p = Curtable.varFind(x.position.image);

        // se não achou, ERRO
        if (p == null) {
            throw new SemanticException(x.position,
                "Variable " + x.position.image + " not found");
        }

        return new type(p.type, p.dim);
    }

    // ---------------------------- Chamada de método ------------------------
    public type TypeCheckCallNode(CallNode x) throws SemanticException {
        EntryClass c;
        EntryMethod m;
        type t1;
        type t2;

        if (x == null) {
            return null;
        }

        // calcula tipo do primeiro filho
        t1 = TypeCheckExpreNode(x.expr);

        // se for array, ERRO
        if (t1.dim > 0) {
            throw new SemanticException(x.position, "Arrays do not have methods");
        }

        // se não for uma classe, ERRO
        if (!(t1.ty instanceof EntryClass)) {
            throw new SemanticException(x.position,
                "Type " + t1.ty.name + " does not have methods");
        }

        // pega tipos dos argumentos
        t2 = TypeCheckExpreListNode(x.args);

        // procura o método desejado na classe t1.ty
        c = (EntryClass) t1.ty;
        m = c.nested.methodFind(x.meth.image, (EntryRec) t2.ty);

        // se não achou, ERRO
        if (m == null) {
            throw new SemanticException(x.position,
                "Method " + x.meth.image + "(" +
                ((t2.ty == null) ? "" : ((EntryRec) t2.ty).toStr()) +
                ") not found in class " + c.name);
        }

        return new type(m.type, m.dim);
    }

    // --------------------------- Indexação de variável ---------------
    public type TypeCheckIndexNode(IndexNode x) throws SemanticException {
        EntryClass c;
        type t1;
        type t2;

        if (x == null) {
            return null;
        }

        // calcula tipo do primeiro filho
        t1 = TypeCheckExpreNode(x.expr1);

        // se não for array, ERRO
        if (t1.dim <= 0) {
            throw new SemanticException(x.position,
                "Can not index non array variables");
        }

        // pega tipo do índice
        t2 = TypeCheckExpreNode(x.expr2);

        // se não for int, ERRO
        if ((t2.ty != INT_TYPE) || (t2.dim > 0)) {
            throw new SemanticException(x.position,
                "Invalid type. Index must be int");
        }

        return new type(t1.ty, t1.dim - 1);
    }

    // -------------------------- Acesso a campo de variável ---------------
    public type TypeCheckDotNode(DotNode x) throws SemanticException {
        EntryClass c;
        EntryVar v;
        type t;

        if (x == null) {
            return null;
        }

        // calcula tipo do primeiro filho
        t = TypeCheckExpreNode(x.expr);

        // se for array, ERRO
        if (t.dim > 0) {
            throw new SemanticException(x.position, "Arrays do not have fields");
        }

        // se não for uma classe, ERRO
        if (!(t.ty instanceof EntryClass)) {
            throw new SemanticException(x.position,
                "Type " + t.ty.name + " does not have fields");
        }

        // procura a variável desejada na classe t.ty
        c = (EntryClass) t.ty;
        v = c.nested.varFind(x.field.image);

        // se não achou, ERRO
        if (v == null) {
            throw new SemanticException(x.position,
                "Variable " + x.field.image + " not found in class " + c.name);
        }

        return new type(v.type, v.dim);
    }

    // --------------------------- Expressão em geral --------------------------
    public type TypeCheckExpreNode(ExpreNode x) throws SemanticException {
        if (x instanceof NewObjectNode) {
            return TypeCheckNewObjectNode((NewObjectNode) x);
        } else if (x instanceof NewArrayNode) {
            return TypeCheckNewArrayNode((NewArrayNode) x);
        } else if (x instanceof RelationalNode) {
            return TypeCheckRelationalNode((RelationalNode) x);
        } else if (x instanceof AddNode) {
            return TypeCheckAddNode((AddNode) x);
        } else if (x instanceof MultNode) {
            return TypeCheckMultNode((MultNode) x);
        } else if (x instanceof UnaryNode) {
            return TypeCheckUnaryNode((UnaryNode) x);
        } else if (x instanceof CallNode) {
            return TypeCheckCallNode((CallNode) x);
        } else if (x instanceof IntConstNode) {
            return TypeCheckIntConstNode((IntConstNode) x);
        } else if (x instanceof StringConstNode) {
            return TypeCheckStringConstNode((StringConstNode) x);
        } else if (x instanceof NullConstNode) {
            return TypeCheckNullConstNode((NullConstNode) x);
        } else if (x instanceof IndexNode) {
            return TypeCheckIndexNode((IndexNode) x);
        } else if (x instanceof DotNode) {
            return TypeCheckDotNode((DotNode) x);
        } else if (x instanceof VarNode) {
            return TypeCheckVarNode((VarNode) x);
        } else {
            return null;
        }
    }

    // --------------------------- Comando em geral -------------------
    public void TypeCheckStatementNode(StatementNode x)
        throws SemanticException {
        if (x instanceof BlockNode) {
            TypeCheckBlockNode((BlockNode) x);
        } else if (x instanceof VarDeclNode) {
            TypeCheckLocalVarDeclNode((VarDeclNode) x);
        } else if (x instanceof AtribNode) {
            TypeCheckAtribNode((AtribNode) x);
        } else if (x instanceof IfNode) {
            TypeCheckIfNode((IfNode) x);
        } else if (x instanceof ForNode) {
            TypeCheckForNode((ForNode) x);
        } else if (x instanceof PrintNode) {
            TypeCheckPrintNode((PrintNode) x);
        } else if (x instanceof NopNode) {
            TypeCheckNopNode((NopNode) x);
        } else if (x instanceof ReadNode) {
            TypeCheckReadNode((ReadNode) x);
        } else if (x instanceof ReturnNode) {
            TypeCheckReturnNode((ReturnNode) x);
        } else if (x instanceof SuperNode) {
            TypeCheckSuperNode((SuperNode) x);
        } else if (x instanceof BreakNode) {
            TypeCheckBreakNode((BreakNode) x);
        }
    }
}
