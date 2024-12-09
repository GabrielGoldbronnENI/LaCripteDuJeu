package fr.eni.lacriptedujeu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorfulLoggerExample {
    private static final Logger logger = LoggerFactory.getLogger(ColorfulLoggerExample.class);


    public static void main(String[] args) {
        logger.debug("This is a DEBUG message");
        logger.info("This is an INFO message");
        logger.warn("This is a WARN message");
        logger.error("This is an ERROR message");
        logger.info("Opération réussie !");
    }
}
