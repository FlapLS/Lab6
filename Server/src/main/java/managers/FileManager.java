package managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;

import static config.Constant.DEFAULT_PATH;
import static config.Constant.SCRIPTS_DIR;

/**
 * Класс для инициализации и хранения пути к файлу коллекции.
 *
 * @author Базанов Евгений.
 */
public class FileManager {
    private static final File DEFAULT_FILE = new File(DEFAULT_PATH);

    private String path;
    private File file;
    private Scanner scriptScanner;

    /**
     * Конструктор проверяет путь к файлу коллекции, и проверяет файл коллекции.
     *
     * @param pathStr путь до файла
     */
    public FileManager(String pathStr) {
        if (pathStr == null) {
            System.err.println("Не передан путь до файла, используется стандартный файл");
            this.path = DEFAULT_PATH;
            this.file = DEFAULT_FILE;
            return;
        }

        final Path path = Paths.get(pathStr);
        if (!Files.exists(path) || !Files.isReadable(path) || !Files.isWritable(path)) {
            System.err.println("Ошибка в обработке файла, используется стандартный файл");
            this.path = DEFAULT_PATH;
            this.file = DEFAULT_FILE;
            return;
        }

        this.path = pathStr;
        this.file = new File(pathStr);
    }

    public String getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    /**
     * Открыть скрипт для выполнения
     * @param name имя скрипта
     * @throws FileNotFoundException если файл не найден
     */
    public void openScript(String name) throws FileNotFoundException {
        if (scriptScanner != null) {
            scriptScanner.close();
        }
            scriptScanner = new Scanner(new File(SCRIPTS_DIR + name));
    }

    /**
     * Прочитать строчку из скрипта
     * @return строчка скрипта
     */
    public Optional<String> readScriptLine() {
        if (scriptScanner.hasNextLine())
            return Optional.ofNullable(scriptScanner.nextLine());
        else
            return Optional.empty();
    }
}