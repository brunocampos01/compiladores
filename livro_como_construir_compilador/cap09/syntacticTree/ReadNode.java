package syntacticTree;

import parser.*;


public class ReadNode extends StatementNode {
    public ExpreNode expr;

    public ReadNode(Token t, ExpreNode e) {
        super(t);
        expr = e;
    }
}
