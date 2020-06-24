package manager;

import initializer.MarineAddDtoInitializer;
import model.dto.MarineAddDto;
import model.enums.Command;

import java.util.Arrays;

/**
 * Класс, предназначеный для вызова команд, работающих с колекцией.
 *
 * @author Базанов Евгений.
 */
public class CommandManager {
    private final IOManager ioManager;
    private final ConnectionManager connectionManager;

    public CommandManager(IOManager io, ConnectionManager connectionManager) {
        this.ioManager = io;
        this.connectionManager = connectionManager;
    }

    /**
     * Метод,реализуюший выполнение команд.
     */
    public void start() {
        while (true) {
            doIterationWithoutCommands();
        }
    }

    /**
     * Метод,реализуюший выполнение команды execute script.
     */
    public void executeScript() {
        while (ioManager.inputReady()) {
            doIterationWithoutCommands("execute_script");
        }
    }

    /**
     * Метод, выполняющий одну итерацию с прочтением команды, исключив список комманд переданный в аргументе.
     *
     * @param excludedCommands команды которые не могут быть выполненны в рамках итерации
     */
    private void doIterationWithoutCommands(String... excludedCommands) {
        String enteredLine = ioManager.nextLine().trim();
        if (enteredLine.equals("")) return;
        String[] rawCommand = enteredLine.replaceAll(" +", " ").split(" ");
        String commandName = rawCommand[0];
        String[] commandArgs = Arrays.copyOfRange(rawCommand, 1, rawCommand.length);
        Command ongoingCommand = Command.getByName(commandName).orElse(null);
        if (ongoingCommand != null) {
            if (commandArgs.length != ongoingCommand.getArgumentsCount()) {
                ioManager.getResult().println("Переданно неверное количество аргументов, ожидается: " + ongoingCommand.getArgumentsCount());
                return;
            }
            if (Arrays.asList(excludedCommands).contains(commandName)) {
                ioManager.getResult().println("Невозможно исполнить команду " + commandName);
                return;
            }
            sendCommand(ongoingCommand, commandArgs);
        } else {
            ioManager.getResult().println("Неизвестная команда, чтобы посмотреть список команд введите help");
        }
    }
    //tODO
    private void sendCommand(Command ongoingCommand, String[] commandArgs) {
        MarineAddDto marineAddDto = Command.ADD_COMMANDS.contains(ongoingCommand)? MarineAddDtoInitializer.initialize(ioManager)
                : null;
        connectionManager.sendCommand(ongoingCommand, commandArgs, marineAddDto).ifPresent(serverResponseDto -> {
            if (serverResponseDto.getErrorMessage() != null) {
                ioManager.getResult().println(serverResponseDto.getErrorMessage());
            } else {
                ioManager.getResult().println(serverResponseDto.getMessage());
            }
        });
    }
}