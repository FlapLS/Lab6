package utils;

import model.enums.Command;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

import static config.Constant.HISTORY_SIZE;

/**
 * Вспомогательный класс для реализации команды history.
 *
 * @author Базанов Евгений.
 */
public class CommandsHistory {
    private final static Queue<Command> history = new LinkedList<>();

    private CommandsHistory() {
        throw new IllegalStateException("Класс утилита");
    }

    /**
     * Метод, реализуюший выполнение команды history.
     *
     * @param command название команды.
     */
    public static void addCommand(Command command) {
        if (history.size() >= HISTORY_SIZE) {
            history.poll();
        }
        history.add(command);
    }


    public static String getHistory() {
        return history.stream().map(Command::getName).collect(Collectors.joining("\n"));
    }
}