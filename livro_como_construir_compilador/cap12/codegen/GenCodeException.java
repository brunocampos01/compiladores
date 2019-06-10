package codegen;

import parser.*;


public class GenCodeException extends Exception {
    GenCodeException(Token t, String msg) {
        super("Line " + t.beginLine + " column " + t.beginColumn + ": " + msg);
    }

    GenCodeException(String msg) {
        super(msg);
    }
}
