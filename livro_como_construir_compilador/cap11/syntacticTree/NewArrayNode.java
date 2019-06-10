package syntacticTree;

import parser.*;


public class NewArrayNode extends ExpreNode {
    public Token name;
    public ListNode dims;

    public NewArrayNode(Token t, Token t2, ListNode d) {
        super(t);
        name = t2;
        dims = d;
    }
}
