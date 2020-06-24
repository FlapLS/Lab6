package config;

import java.nio.file.Paths;

/**
 *
 */
public class Constant {
    private Constant() {
        throw new IllegalStateException("Класс утилита");
    }

    public final static int MAX_RESEND_COUNT = 4;
    public final static int HISTORY_SIZE = 7;
    public final static int BUFFER_SIZE = 1000_000;
    public final static int DEFAULT_PORT = 9999;
    public final static String DEFAULT_PATH = "./resources/default.xml";
    public final static String SCRIPTS_DIR = "./resources/";

}
