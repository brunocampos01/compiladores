package syntacticTree;

import parser.*;


public class CallNode extends ExpreNode {
    public ExpreNode expr;
    public Token meth;
    public ListNode args;

    public CallNode(Token t, ExpreNode e, Token m, ListNode l) {
        super(t);
        expr = e;
        meth = m;
        args = l;
    }
}
