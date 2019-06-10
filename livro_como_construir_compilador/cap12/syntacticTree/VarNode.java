package syntacticTree;

import parser.*;


public class VarNode extends ExpreNode {
    public int dim;

    public VarNode(Token t) {
        super(t);
        dim = 0;
    }

    public VarNode(Token t, int k) {
        super(t);
        dim = k;
    }
}
