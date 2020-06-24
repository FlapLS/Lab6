package model.enums;

import com.google.common.collect.ImmutableSet;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

/**
 *
 */
public enum Command implements Serializable {
    ADD("add", "добавить новый элемент в коллекцию", "{element}", 0),

    ADD_IF_MAX("add_if_max", "добавить новый элемент в коллекцию, если его" +
            " значение превышает значение наибольшего элемента этой коллекции", "{element}",  0),

    CLEAR("clear", "очистить коллекцию", "",0),

    EXECUTE_SCRIPT("execute_script","считать и исполнить скрипт из указанного файла." +
            " В скрипте содержатся команды в таком же виде, в котором их вводит пользователь", "file_name",
            1),

    FILTER_BY_HEIGHT("filter_by_height","вывести элементы, значение поля height которых равно" +
            " заданному", "height", 1),

    HELP("help", "вывести справку по доступным командам", "", 0),

    HISTORY("history"," вывести последние 7 команд (без их аргументов)","",
            0),

    INFO("info","вывести в стандартный поток вывода информацию о коллекции (тип, дата " +
            "инициализации, количество элементов и т.д.)","",0),

    PRINT_DESCENDING("print_descending","вывести элементы коллекции в порядке убывания",
            "",0),

    PRINT_UNIQUE_CHAPTER("print_unique_chapter","вывести уникальные значения поля chapter " +
            "всех элементов в коллекции","",0),

    REMOVE_BY_ID("remove_by_id","удалить элемент из коллекции по его id",
            "id",1),

    REMOVE_LOWER("remove_lower","удалить из коллекции все элементы, меньшие, чем заданный",
        "{element}",0),

    SAVE("save","сохранить коллекцию в файл","",0),

    SHOW("show","вывести в стандартный поток вывода все элементы коллекции в строковом" +
            " представлении","",0),

    UPDATE("update","обновить значение элемента коллекции, id которого равен заданному",
            "id {element}", 1);

    public static final Set<Command> ADD_COMMANDS = ImmutableSet.of(ADD, ADD_IF_MAX, REMOVE_LOWER, UPDATE);

    private String name;
    private String info;
    private String arguments;
    private int argumentsCount;

    Command(String commandName, String info, String arguments, int argumentsCount) {
        this.name = commandName;
        this.info = info;
        this.arguments = arguments;
        this.argumentsCount = argumentsCount;
    }

    public static Optional<Command> getByName(String name) {
        return Arrays.stream(Command.values())
                .filter(command -> command.getName().equals(name))
                .findAny();
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public int getArgumentsCount() {
        return argumentsCount;
    }

    public String getArguments() {
        return arguments;
    }
}
