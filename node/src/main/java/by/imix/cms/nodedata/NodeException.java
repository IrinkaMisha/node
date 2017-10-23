package by.imix.cms.nodedata;

/**
 * Created by miha on 13.05.2015.
 */
public class NodeException extends RuntimeException {
    public NodeException() {
    }

    public NodeException(String s) {
        super(s);
    }

    public NodeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NodeException(Throwable throwable) {
        super(throwable);
    }

    public NodeException(String s, Throwable throwable, boolean b, boolean b2) {
        super(s, throwable, b, b2);
    }
}
