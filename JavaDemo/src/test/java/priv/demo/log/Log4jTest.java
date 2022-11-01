package priv.demo.log;

import org.junit.Test;
import priv.log.Log4j2Demo;

public class Log4jTest {
    @Test
    public void test() {
        Log4j2Demo demo = new Log4j2Demo();
        demo.printLog();
        System.out.println("finish");
    }
}
