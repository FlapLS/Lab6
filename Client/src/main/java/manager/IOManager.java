package manager;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Класс для управления потоками ввода-вывода
 *
 * @author Базанов Евгений
 */
public class IOManager {
    private final PrintStream interactive;
    private final PrintStream result;
    private final Scanner input;

    public IOManager(PrintStream interactive, PrintStream result, Scanner input) {
        this.interactive = interactive;
        this.result = result;
        this.input = input;
    }

    /**
     * Метод, получает строку из потока ввода
     */
    public String nextLine() {
        return input.nextLine();
    }

    /**
     * Метод, проверяет готовность потока ввода
     */
    public boolean inputReady() {
        return input.hasNext();
    }

    /**
     * Метод в интерактивном режиме запрашивает параметр
     *
     * @param paramName имя параметр
     * @return введеную пользователем строку
     */
    public String requestParameter(String paramName) {
        interactive.printf("Введите %s: ", paramName);
        return input.nextLine();
    }

    public PrintStream getInteractive() {
        return interactive;
    }

    public PrintStream getResult() {
        return result;
    }
}