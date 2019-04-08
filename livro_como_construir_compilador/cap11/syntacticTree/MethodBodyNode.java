package syntacticTree;

import parser.*;


public class MethodBodyNode extends GeneralNode {
    public ListNode param;
    public StatementNode stat;

    public MethodBodyNode(Token t, ListNode l, StatementNode s) {
        super(t);
        param = l;
        stat = s;
    }
}
