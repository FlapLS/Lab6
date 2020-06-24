package managers;

import model.dto.ClientRequestDto;
import model.dto.ServerResponseDto;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.SerializationUtil;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Optional;

import static config.Constant.BUFFER_SIZE;


public class ConnectionManager implements Closeable {
    private final static Logger log = LogManager.getLogger(ConnectionManager.class);
    private DatagramChannel channel;
    private ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
    private SocketAddress address;

    public ConnectionManager(int port) throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress(port));
    }

    public Optional<ClientRequestDto> getRequest() {
        Optional<byte[]> bytes;
        if ((bytes = getRequestBytes()).isPresent()) {
            return SerializationUtil.deserialize(bytes.get(), ClientRequestDto.class);
        }
        return Optional.empty();
    }

    public boolean sendResponse(ServerResponseDto responseDto) {
        return sendResponseBytes(SerializationUtil.serialise(responseDto));
    }

    private Optional<byte[]> getRequestBytes() {
        buf.clear();
        try {
            if ((address = channel.receive(buf)) == null || buf.array().length == 0)
                return Optional.empty();
        } catch (IOException ex) {
            log.error("Ошибка во время считывания сообщения", ex);
            return Optional.empty();
        }
        return Optional.of(buf.array());
    }

    private boolean sendResponseBytes(byte[] response) {
        buf.clear();
        buf.put(response);
        buf.flip();
        try {
            channel.send(buf, address);
            return true;
        } catch (IOException ex) {
            log.error("Ошибка во время отправки ответа", ex);
            return false;
        }
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
