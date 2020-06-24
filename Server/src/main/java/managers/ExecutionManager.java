package managers;

import com.google.common.collect.ImmutableSet;
import entities.SpaceMarine;
import entities.SpaceMarineFactory;
import model.dto.ClientRequestDto;
import model.dto.MarineAddDto;
import model.dto.ServerResponseDto;
import model.enums.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.CommandsHistory;

import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ExecutionManager {
    private final static Logger log = LogManager.getLogger(ExecutionManager.class);
    private CollectionManager collectionManager;
    private FileManager fileManager;

    public ExecutionManager(CollectionManager collectionManager, FileManager fileManager) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }

    public ServerResponseDto execute(ClientRequestDto commandDto) {
        return execute(commandDto.getCommand(), commandDto.getArguments(), commandDto.getMarineAddDto(), ImmutableSet.of());
    }

    private ServerResponseDto execute(Command command, List<String> args, MarineAddDto marineAddDto,
                                      Set<Command> excludedCommands) {
        if (excludedCommands.contains(command)) {
            return new ServerResponseDto().errorMessage(String.format("Комманда %s исключена из выполнения",
                    command.getName()));
        }
        CommandsHistory.addCommand(command);
        switch (command) {
            case ADD:
                final Optional<SpaceMarine> marine = marineAddDto == null ? SpaceMarineFactory.getSpaceMarine(fileManager,
                        collectionManager.getFreeRandomId()) : SpaceMarineFactory.getSpaceMarine(marineAddDto,
                        collectionManager.getFreeRandomId());
                if (marine.isPresent()) {
                    collectionManager.getCollection().addMarine(marine.get());
                    return new ServerResponseDto().message("Новый элемент добавлен в коллекцию!");
                } else {
                    return new ServerResponseDto().errorMessage("Не удалось создать SpaceMarine!");
                }

            case ADD_IF_MAX:
                final Optional<SpaceMarine> newMarine = marineAddDto == null ? SpaceMarineFactory.getSpaceMarine(fileManager,
                        collectionManager.getFreeRandomId()) : SpaceMarineFactory.getSpaceMarine(marineAddDto,
                        collectionManager.getFreeRandomId());
                if (newMarine.isPresent()) {
                    boolean isMax = collectionManager.getCollection().getMarines().stream()
                            .noneMatch(marine2 -> marine2.compareTo(newMarine.get()) > 0);
                    if (!isMax) {
                        log.warn("Введенный элемент не превышает наибольшее значение в коллекции !");
                        return new ServerResponseDto().errorMessage("Введенный элемент не превышает наибольшее значение в коллекции");
                    }
                    collectionManager.getCollection().addMarine(newMarine.get());
                    log.info("Новый элемент добавлен в коллекцию !");
                    return new ServerResponseDto().message("Новый элемент добавлен в коллекцию");
                } else {
                    return new ServerResponseDto().errorMessage("Не удалось создать SpaceMarine!");
                }

            case CLEAR:
                collectionManager.clear();
                log.info("Коллекция очищена");
                return new ServerResponseDto().message("Коллекция очищена");

            case EXECUTE_SCRIPT:
                try {
                    fileManager.openScript(args.get(0));
                } catch (FileNotFoundException e) {
                    return new ServerResponseDto().errorMessage("Файл не найден");
                }

                Optional<String> command2 = fileManager.readScriptLine();
                StringBuilder stringBuilder = new StringBuilder();

                while (command2.isPresent()) {
                    String[] rawCommand = command2.get().replaceAll(" +", " ").split(" ");
                    String commandName = rawCommand[0];
                    String[] commandArgs = Arrays.copyOfRange(rawCommand, 1, rawCommand.length);
                    Command.getByName(commandName).ifPresent(ongoingCommand -> {
                        ServerResponseDto responseDto = execute(ongoingCommand, Arrays.asList(commandArgs), null,
                                ImmutableSet.of(Command.EXECUTE_SCRIPT));
                        stringBuilder.append(responseDto.getMessage() != null? responseDto.getMessage() :
                                responseDto.getErrorMessage());
                        stringBuilder.append("\n");
                    });
                    command2 = fileManager.readScriptLine();
                }
                return new ServerResponseDto().message(stringBuilder.toString());

            case FILTER_BY_HEIGHT:
                try {
                    long expectedHeight = Long.parseLong(args.get(0));
                    StringBuilder stringBuilder2 = new StringBuilder();
                    collectionManager.getCollection().getMarines().stream()
                            .filter(marine2 -> marine2.getHeight() == expectedHeight)
                            .forEach(m -> stringBuilder2.append(m.toString()).append("\n"));
                    return new ServerResponseDto().message(stringBuilder2.toString());
                } catch (NumberFormatException ex) {
                    return new ServerResponseDto().errorMessage("Команда принимает только один цельночисленный id");
                }

            case HELP:
                StringBuilder stringBuilder3 = new StringBuilder();
                Arrays.stream(Command.values()).forEach(c -> stringBuilder3.append(String.format("%s %s : %s%n", c.getName(),
                        c.getArguments(), c.getInfo())));
                return new ServerResponseDto().message(stringBuilder3.toString());

            case HISTORY:
                return new ServerResponseDto().message(CommandsHistory.getHistory());

            case INFO:
                String infoString = "Тип коллекции: " + collectionManager.getCollection().getClass() + "\n" +
                        "Время инициализации коллекции: " +
                        collectionManager.getCreationTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                        "\n" + "Количество элементов: " + collectionManager.getSize() + "\n";
                return new ServerResponseDto().message(infoString);

            case PRINT_DESCENDING:
                StringBuilder stringBuilder2 = new StringBuilder();
                collectionManager.getCollection().getMarines().stream()
                        .sorted(Comparator.reverseOrder())
                        .forEach(m -> stringBuilder2.append(m).append("\n"));
                return new ServerResponseDto().message(stringBuilder2.toString());

            case PRINT_UNIQUE_CHAPTER:
                StringBuilder stringBuilder4 = new StringBuilder();

                collectionManager.getCollection().getMarines().stream()
                        .map(SpaceMarine::getChapter)
                        .collect(Collectors.toSet())
                        .forEach(c -> stringBuilder4.append(c).append("\n"));
                return new ServerResponseDto().message(stringBuilder4.toString());

            case REMOVE_BY_ID:
                try {
                    boolean isDeleted = collectionManager.getCollection()
                            .removeIf(m -> m.getId() == Integer.parseInt(args.get(0)));
                    if (isDeleted) {
                        return new ServerResponseDto().message("Элемент успешно удален");
                    } else {
                        return new ServerResponseDto().message("Не найден элемент с переданным id");
                    }
                } catch (NumberFormatException e) {
                    return new ServerResponseDto().errorMessage("Команда принимает только один цельночисленный id");
                }

            case REMOVE_LOWER:
                final Optional<SpaceMarine> compared = marineAddDto == null ? SpaceMarineFactory.getSpaceMarine(fileManager,
                        collectionManager.getFreeRandomId()) : SpaceMarineFactory.getSpaceMarine(marineAddDto,
                        collectionManager.getFreeRandomId());
                if (compared.isPresent()) {
                    boolean isDeleted = collectionManager.getCollection().removeIf(m -> (m.compareTo(compared.get()) < 0));
                    if (isDeleted) {
                        return new ServerResponseDto().message("Элемент(ы) успешно удален");
                    } else {
                        return new ServerResponseDto().message("Не найдены элементы меньше введенного");
                    }
                } else {
                    return new ServerResponseDto().errorMessage("Не удалось создать SpaceMarine!");
                }

            case SAVE:
                collectionManager.save();
                return new ServerResponseDto().message("Коллекция успешно сохраннена");

            case SHOW:
                return new ServerResponseDto().message(collectionManager.getCollection().toString());

            case UPDATE:
                try {
                    boolean isDeleted2 = collectionManager.getCollection()
                            .removeIf(m -> m.getId() == Integer.parseInt(args.get(0)));
                    if (!isDeleted2) {
                        return new ServerResponseDto().message("Не найден элемент с переданным id");
                    }
                    final Optional<SpaceMarine> marine1 = marineAddDto == null ? SpaceMarineFactory.getSpaceMarine(fileManager,
                            collectionManager.getFreeRandomId()) : SpaceMarineFactory.getSpaceMarine(marineAddDto,
                            collectionManager.getFreeRandomId());
                    if (marine1.isPresent()) {
                        collectionManager.getCollection().addMarine(marine1.get());
                        return new ServerResponseDto().message("Элемент успешно обновлен");
                    } else {
                        return new ServerResponseDto().errorMessage("Не удалось создать SpaceMarine!");
                    }
                } catch (NumberFormatException e) {
                    return new ServerResponseDto().errorMessage("Команда принимает только один цельночисленный id");
                }

            default:
                log.error("Неподдерживаемая команда");
                return new ServerResponseDto().errorMessage("Неподдерживаемая команда");
        }
    }
}
