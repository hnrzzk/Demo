package priv.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Constant {
    public static Logger logger = LoggerFactory.getLogger("testLog");

    private Constant(){}
    private static final Constant INSTANCE = new Constant();
    public static Constant getInstance() {
        return INSTANCE;
    }
}
