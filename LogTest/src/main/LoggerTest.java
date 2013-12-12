package main;


import org.apache.log4j.Logger;

/**
 * Hello world!
 */
public class LoggerTest {
    public static final Logger LOG=Logger.getLogger(LoggerTest.class);
    public static void main(String[] args) {
        LOG.info("Hello World!");

    }
}
