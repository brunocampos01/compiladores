package syntacticTree;

import parser.*;


public class NewObjectNode extends ExpreNode {
    public Token name;
    public ListNode args;

    public NewObjectNode(Token t, Token t2, ListNode l) {
        super(t);
        name = t2;
        args = l;
    }
}
