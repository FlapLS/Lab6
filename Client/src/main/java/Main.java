import manager.CommandManager;
import manager.ConnectionManager;
import manager.IOManager;

import java.io.IOException;
import java.util.Scanner;

import static config.Constant.SERVER_HOST;
import static config.Constant.SERVER_PORT;

/**
 * Главный класс программы, метод main котрого реализует работу консольного приложения для управления коллекцией объектов
 * в интерактивном режиме.
 *
 * @author Базанов Евгений.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        final IOManager ioManager = new IOManager(System.out, System.out, new Scanner(System.in));
        ConnectionManager connectionManager = new ConnectionManager(SERVER_HOST, SERVER_PORT);
        final CommandManager commandManager = new CommandManager(ioManager, connectionManager);

        commandManager.start();
    }
}
