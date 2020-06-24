package config;
//TODO
public class Constant {
    private Constant() {
        throw new IllegalStateException("Класс утилита");
    }

    public final static int RECEIVE_TIMEOUT_SECONDS = 3;
    public final static int BUFFER_SIZE = 1000_000;
    public final static String SERVER_HOST = "localhost";
    public final static int SERVER_PORT = 9999;
}
