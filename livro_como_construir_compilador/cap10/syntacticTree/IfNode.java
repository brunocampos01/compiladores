package syntacticTree;

import parser.*;


public class IfNode extends StatementNode {
    public StatementNode stat1;
    public StatementNode stat2;
    public ExpreNode expr;

    public IfNode(Token t, ExpreNode e, StatementNode s1, StatementNode s2) {
        super(t);
        expr = e;
        stat1 = s1;
        stat2 = s2;
    }
}
