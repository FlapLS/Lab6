package entities;

import utils.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

/**
 * Класс SpaceMarine, объектами которого заполняется коллекция.
 *
 * @author Базанов Евгений.
 */
@XmlRootElement(name = "SpaceMarine")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "name", "creationDate", "coordinates", "health", "achievements", "height", "category", "chapter"})
public class SpaceMarine implements Comparable<SpaceMarine> {
    @XmlElement
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой
    @XmlElement
    private Coordinates coordinates; //Поле не может быть null
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @XmlElement
    private float health; //Значение поля должно быть больше 0
    @XmlElement
    private String achievements; //Поле не может быть null
    @XmlElement
    private Long height; //Поле не может быть null
    @XmlElement
    private AstartesCategory category; //Поле не может быть null
    @XmlElement
    private Chapter chapter; //Поле не может быть null

    public SpaceMarine() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    public void setCategory(AstartesCategory category) {
        this.category = category;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    /**
     * Метод, проверяющей валидность значений полей объекта.
     *
     * @return true при валидности значений всех полей, в противном false.
     */
    public boolean isValuesValid() {
        if (id == null) {
            return false;
        }
        if (name == null) {
            return false;
        }
        if (coordinates == null || !coordinates.isValuesValid()) {
            return false;
        }
        if (creationDate == null) {
            return false;
        }
        if (health < 0) {
            return false;
        }
        if (achievements == null) {
            return false;
        }
        if (height == null) {
            return false;
        }
        if (category == null) {
            return false;
        }
        if (chapter == null || !chapter.isValuesValid()) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(SpaceMarine o) {
        return Float.compare(this.health, o.health);
    }

    @Override
    public String toString() {
        return "SpaceMarine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", health=" + health +
                ", achievements='" + achievements + '\'' +
                ", height=" + height +
                ", category=" + category +
                ", chapter=" + chapter +
                '}';
    }
}