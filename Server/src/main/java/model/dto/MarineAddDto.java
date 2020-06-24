package model.dto;

import entities.AstartesCategory;

import java.io.Serializable;

/**
 *Класс задающюий все параметры обьекта SpaceMarine
 *
 * @author Базанов Евгений
 */
public class MarineAddDto implements Serializable {
    private String chapterName;
    private long chapterMarinesCount;
    private String chapterWorld;

    private int coordinatesX;
    private long coordinatesY;

    private String MarineName;
    private float MarineHealth;
    private String MarineAchievements;
    private long MarineHeight;
    private AstartesCategory MarineCategory;

    public String getChapterName() {
        return chapterName;
    }

    public long getChapterMarinesCount() {
        return chapterMarinesCount;
    }

    public String getChapterWorld() {
        return chapterWorld;
    }

    public int getCoordinatesX() {
        return coordinatesX;
    }

    public long getCoordinatesY() {
        return coordinatesY;
    }

    public String getMarineName() {
        return MarineName;
    }

    public float getMarineHealth() {
        return MarineHealth;
    }

    public String getMarineAchievements() {
        return MarineAchievements;
    }

    public long getMarineHeight() {
        return MarineHeight;
    }

    public MarineAddDto chapterName(String chapterName) {
        this.chapterName = chapterName;
        return this;
    }

    public MarineAddDto chapterMarinesCount(long chapterMarinesCount) {
        this.chapterMarinesCount = chapterMarinesCount;
        return this;
    }

    public MarineAddDto chapterWorld(String chapterWorld) {
        this.chapterWorld = chapterWorld;
        return this;
    }

    public MarineAddDto coordinatesX(int coordinatesX) {
        this.coordinatesX = coordinatesX;
        return this;
    }

    public MarineAddDto coordinatesY(long coordinatesY) {
        this.coordinatesY = coordinatesY;
        return this;
    }

    public MarineAddDto marineName(String marineName) {
        MarineName = marineName;
        return this;
    }

    public MarineAddDto marineHealth(float marineHealth) {
        MarineHealth = marineHealth;
        return this;
    }

    public MarineAddDto marineAchievements(String marineAchievements) {
        MarineAchievements = marineAchievements;
        return this;
    }

    public MarineAddDto marineHeight(long marineHeight) {
        MarineHeight = marineHeight;
        return this;
    }

    public AstartesCategory getMarineCategory() {
        return MarineCategory;
    }

    public MarineAddDto marineCategory(AstartesCategory marineCategory) {
        MarineCategory = marineCategory;
        return this;
    }
}
