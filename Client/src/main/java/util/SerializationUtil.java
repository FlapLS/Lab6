package util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Optional;
/**
 * Класс, проводящий сериализацию и десериализацию объектов.
 *
 * @author Базанов Евгений
 */
public class SerializationUtil {
    private final static Logger log = LogManager.getLogger(SerializationUtil.class);

    private SerializationUtil() {
        throw new IllegalStateException("Класс утилита");
    }
    /**
     * Сериализует объект
     * @param object объект
     * @param <T> тип объекта
     * @return массив байтов
     */
    public static <T> byte[] serialise(T object) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException ex) {
            log.error("Ошибка сериализации", ex);
            throw new RuntimeException(ex);
        }
    }
    /**
     * Десериализует объект
     * @param bytes массив байтов
     * @param clazz
     * @param <T> тип
     * @return
     */
    public static <T> Optional<T> deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             ObjectInput objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return Optional.ofNullable(clazz.cast(objectInputStream.readObject()));
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            log.error("Ошибка десериализации", ex);
            return Optional.empty();
        }
    }
}
