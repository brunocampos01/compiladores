package syntacticTree;

import parser.*;


abstract public class GeneralNode {
    public Token position;

    public GeneralNode(Token x) {
        position = x;
    }
}
