package utils;

import entities.CollectionSpaceMarines;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Scanner;

/**
 * Класс, предназначенный для парсинга объектов типа {@link entities.SpaceMarine} в файл формата xml и обратно.
 *
 * @author Базанов Евгений.
 */
public class Parser {
    /**
     * Метод для сохранения данных в файл формата xml.
     *
     * @param marine объект типа SpaceMarine.
     * @param path   путь к файлу.
     */
    public static void saveXmlStringToFile(CollectionSpaceMarines marine, String path) {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path))) {
            writer.write(convertObjectToXml(marine));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для парсинга данных из объекта в xml.
     *
     * @param marine объект типа SpaceMarine.
     * @return строку в xml формате.
     */
    private static String convertObjectToXml(CollectionSpaceMarines marine) {
        try {
            StringWriter stringWriter = new StringWriter();

            Marshaller marshaller = JAXBContext.newInstance(CollectionSpaceMarines.class).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(marine, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод для парсинга из xml файла в объект.
     *
     * @param file файл в формате xml с сериализованным представлением объекта CollectionSpaceMarines.
     * @return объект типа CollectionSpaceMarines.
     */
    public static CollectionSpaceMarines parseXmlFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNext()) {
                builder.append(scanner.nextLine());
            }
            JAXBContext context = JAXBContext.newInstance(CollectionSpaceMarines.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (CollectionSpaceMarines) unmarshaller.unmarshal(new StringReader(builder.toString()));
        } catch (JAXBException e) {
            System.err.println("Ошибка при парсинге XML файла, загруженна пустая коллекция");
            return new CollectionSpaceMarines();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Указан невенрый путь к файлу");
        }
    }
}