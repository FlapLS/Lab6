package model.dto;

import model.enums.Command;

import java.io.Serializable;
import java.util.List;

public class ClientRequestDto implements Serializable {
    private Command command;
    private List<String> arguments;
    private MarineAddDto marineAddDto;

    public Command getCommand() {
        return command;
    }

    public ClientRequestDto command(Command command) {
        this.command = command;
        return this;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public ClientRequestDto arguments(List<String> arguments) {
        this.arguments = arguments;
        return this;
    }

    public MarineAddDto getMarineAddDto() {
        return marineAddDto;
    }

    public ClientRequestDto marineAddDto(MarineAddDto marineAddDto) {
        this.marineAddDto = marineAddDto;
        return this;
    }
}
