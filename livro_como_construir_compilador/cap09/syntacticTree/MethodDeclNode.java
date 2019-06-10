package syntacticTree;

import parser.*;


public class MethodDeclNode extends GeneralNode {
    public int dim;
    public Token name;
    public MethodBodyNode body;

    public MethodDeclNode(Token t, int k, Token t2, MethodBodyNode b) {
        super(t);
        dim = k;
        name = t2;
        body = b;
    }
}
