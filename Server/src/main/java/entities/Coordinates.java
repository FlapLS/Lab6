package entities;


import javax.xml.bind.annotation.XmlElement;

/**
 * Класс с координатами SpaceMarine.
 *
 * @author Базанов Евгений.
 */
public class Coordinates {
    @XmlElement
    private Integer x; //Поле не может быть null
    @XmlElement
    private Long y; //Максимальное значение поля: 72, Поле не может быть null

    final static Long upper = 72L; //аксимальное значение поля y 72

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Long y) {
        this.y = y;
    }

    /**
     * Метод, проверяющий правильность вводимых полей
     *
     * @return true в случаи правильного ввода всех полей, false в случаи не соблюдений ввода полей
     */
    public boolean isValuesValid() {
        if (x == null) {
            return false;
        }
        if (y == null || y > upper) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Coordinates{x=%d, y=%d}", x, y);
    }
}