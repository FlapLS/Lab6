import managers.CollectionManager;
import managers.ConnectionManager;
import managers.ExecutionManager;
import managers.FileManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.SocketException;
import java.util.stream.Stream;

import static config.Constant.DEFAULT_PORT;
import static config.Constant.MAX_RESEND_COUNT;

/**
 * Главный класс программы, метод main котрого реализует работу консольного приложения для управления коллекцией объектов
 * в интерактивном режиме.
 *
 * @author Базанов Евгений.
 */
public class Main {
    private final static Logger log = LogManager.getLogger(Main.class);


    public static void main(String[] args) throws IOException {
        String filePath = null;
        int port = DEFAULT_PORT;

        switch (args.length) {
            case 0:
                break;
            case 1:
                filePath = args[0];
                break;
            case 2:
                filePath = args[0];
                port = Integer.parseInt(args[1]);
                break;
            default:
                throw new IllegalArgumentException("Максимальное кол-во аргументов - 2");
        }

        FileManager fileManager = new FileManager(filePath);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        try (ConnectionManager connectionManager = new ConnectionManager(port)) {
            ExecutionManager executionManager = new ExecutionManager(collectionManager, fileManager);
            log.info(String.format("Сервер запущен на порту %s", port));
            while (true) {
                connectionManager.getRequest().ifPresent(requestDto -> {
                    Stream.iterate(1, i -> i + 1)
                            .limit(MAX_RESEND_COUNT)
                            .peek(i -> log.debug(String.format("%d попытка отправить ответ", i)))
                            .map(i -> connectionManager.sendResponse(executionManager.execute(requestDto)))
                            .filter(b -> b)
                            .findAny()
                            .orElseGet(() -> {
                                log.debug("Соединение закрыто");
                                return null;
                            });
                });
            }
        } catch (SocketException ex) {
            throw new RuntimeException(String.format("Не удалось стартовать сервер на порту %d", port));
        }

    }
}
