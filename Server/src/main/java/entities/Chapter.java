package entities;


import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

/**
 * Класс для описание главы SpaceMarine.
 *
 * @author Базанов Евгений.
 */
public class Chapter {
    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой
    @XmlElement
    private long marinesCount; //Значение поля должно быть больше 0, Максимальное значение поля: 1000
    @XmlElement
    private String world; //Поле может быть null

    final static long lower = 0; //минимальное значение поля marinesCount
    final static long upper = 1000; //максимальное значение поля marinesCoun

    public void setName(String name) {
        this.name = name;
    }

    public void setMarinesCount(long marinesCount) {
        this.marinesCount = marinesCount;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    /**
     * Метод, проверяющий правильность вводимых полей.
     *
     * @return true в случаи правильного ввода всех полей, false в случаи не соблюдений ввода полей.
     */
    public boolean isValuesValid() {
        if (name == null || name.isEmpty()) {
            return false;
        }
        if (marinesCount <= lower || marinesCount > upper) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chapter chapter = (Chapter) o;
        return marinesCount == chapter.marinesCount &&
                name.equals(chapter.name) &&
                world.equals(chapter.world);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, marinesCount, world);
    }


    @Override
    public String toString() {
        return String.format("Chapter{name='%s', marinesCount=%d, world='%s'}", name, marinesCount, world);
    }
}