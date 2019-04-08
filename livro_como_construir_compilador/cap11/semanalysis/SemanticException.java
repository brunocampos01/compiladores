package semanalysis;

import parser.*;


public class SemanticException extends Exception {
    SemanticException(Token t, String msg) {
        super("Line " + t.beginLine + " column " + t.beginColumn + ": " + msg);
    }

    SemanticException(String msg) {
        super(msg);
    }
}
