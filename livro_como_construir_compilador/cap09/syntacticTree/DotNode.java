package syntacticTree;

import parser.*;


public class DotNode extends ExpreNode {
    public ExpreNode expr;
    public Token field;

    public DotNode(Token t, ExpreNode e, Token t2) {
        super(t);
        field = t2;
        expr = e;
    }
}
