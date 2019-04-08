package syntacticTree;

import parser.*;


public class ConstructDeclNode extends GeneralNode {
    public MethodBodyNode body;

    public ConstructDeclNode(Token t, MethodBodyNode m) {
        super(t);
        body = m;
    }
}
