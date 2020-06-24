package manager;

import model.dto.ClientRequestDto;
import model.dto.MarineAddDto;
import model.dto.ServerResponseDto;
import model.enums.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import util.SerializationUtil;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static config.Constant.BUFFER_SIZE;
import static config.Constant.RECEIVE_TIMEOUT_SECONDS;

/**
 * Класс, устанавлюиващий соединение с сервером и отправку данных.
 *
 * @author Базанов Евгений
 */
public class ConnectionManager implements Closeable {
    private final static Logger log = LogManager.getLogger(ConnectionManager.class);

    private DatagramChannel channel;
    private SocketAddress socketAddress;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);

    public ConnectionManager(String hostname, int port) throws IOException {
        socketAddress = new InetSocketAddress(hostname, port);
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.connect(socketAddress);
        log.debug(String.format("Установлено соединение с %s по порту %d", hostname, port));
    }

    /**
     *
     *
     * @param command название команды
     * @param commandArgs аргументы команды
     * @param marineAddDto
     * @return
     */
    public Optional<ServerResponseDto> sendCommand(Command command, String[] commandArgs, MarineAddDto marineAddDto) {
        return sendSynchronously(SerializationUtil.serialise(new ClientRequestDto()
                .command(command)
                .marineAddDto(marineAddDto)
                .arguments(Arrays.asList(commandArgs))));
    }

    /**
     *
     * @param bytes массив байт
     * @return
     */
    private Optional<ServerResponseDto> sendSynchronously(byte[] bytes) {
        try {
            channel.send(ByteBuffer.wrap(bytes), socketAddress);
        } catch (IOException ex) {
            log.error("Ошибка отправки запроса", ex);
            return Optional.empty();
        }
        LocalTime start = LocalTime.now();
        while (Duration.between(start, LocalTime.now()).getSeconds() < RECEIVE_TIMEOUT_SECONDS) {
            try {
                byteBuffer.clear();
                if (channel.receive(byteBuffer) != null)
                    return SerializationUtil.deserialize(byteBuffer.array(), ServerResponseDto.class);
            } catch (IOException ex) {
                log.error("Ошибка приёма ответа", ex);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public void close() throws IOException {
        channel.close();
    }

    @Override
    public void finalize() throws IOException {
        channel.close();
    }
}
