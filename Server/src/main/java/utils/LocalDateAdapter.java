package utils;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Класс,предназначенный для парсинга поля creationDate в файл формата xml и обратно.
 *
 * @author Базанов Евгений.
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String marshal(LocalDate dateTime) {
        return dateTime.format(dateFormat);
    }

    @Override
    public LocalDate unmarshal(String text) throws JAXBException {
        try {
            return LocalDate.parse(text, dateFormat);
        } catch (DateTimeParseException e) {
            throw new PropertyException("Error in parse date");
        }
    }
}