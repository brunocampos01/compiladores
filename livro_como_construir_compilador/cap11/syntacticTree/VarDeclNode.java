package syntacticTree;

import parser.*;


public class VarDeclNode extends StatementNode {
    public ListNode vars;

    public VarDeclNode(Token t, ListNode p) {
        super(t);
        vars = p;
    }
}
