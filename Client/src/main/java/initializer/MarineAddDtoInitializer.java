package initializer;


import entities.AstartesCategory;
import manager.IOManager;
import model.dto.MarineAddDto;

/**
 * Класс для инициализации полей SpaceMarine.
 *
 * @author Базанов Евгений.
 */
public class MarineAddDtoInitializer {
    private static IOManager ioManager;

    /**
     * Метод, реализующий инициализацию полей класса SpaceMarine.
     *
     * @return инициализированные поля класса SpaceMarine.
     */
    public static MarineAddDto initialize(IOManager ioManager) {
        MarineAddDtoInitializer.ioManager = ioManager;
        return new MarineAddDto().marineName(initName())
                .coordinatesX(initCoordinateX())
                .coordinatesY(initCoordinateY())
                .marineHealth(initHealth())
                .marineAchievements(initAchievements())
                .marineHeight(initHeight())
                .marineCategory(initCategory())
                .chapterName(initName())
                .chapterMarinesCount(initChapterMarinesCount())
                .chapterWorld(initChapterWorld());
    }

    /**
     * Метод, реализующий инициализацию поля x класса Coordinates.
     */
    private static int initCoordinateX() {
        String coordinate = ioManager.requestParameter("coordinates.x");
        while (true) {
            try {
                return Integer.parseInt(coordinate);
            } catch (NumberFormatException e) {
                ioManager.getInteractive().println("coordinates.x должен быть цельночисленным");
                coordinate = ioManager.requestParameter("coordinates.x");
            }
        }
    }

    /**
     * Метод, реализующий инициализацию поля y класса Coordinates.
     */
    private static long initCoordinateY() {
        String coordinate = ioManager.requestParameter("coordinates.y");
        while (true) {
            try {
                long y = Long.parseLong(coordinate);
                if (y > 72) {
                    ioManager.getInteractive().println("Максимальное значение coordinates.y - 72");
                    coordinate = ioManager.requestParameter("coordinates.y");
                } else {
                    return y;
                }
            } catch (NumberFormatException e) {
                ioManager.getInteractive().println("coordinates.y долежен быть цельночисленным");
                coordinate = ioManager.requestParameter("coordinates.y");
            }
        }
    }

    /**
     * Метод, реализующий инициализацию поля name класса Chapter
     */
    private static String initChapterName() {
        String name = ioManager.requestParameter("name для chapter");
        while (name == null || name.trim().isEmpty()) {
            ioManager.getInteractive().println("name не может быть null или пустой строкой");
            name = ioManager.requestParameter("name для chapter");
        }
        return name;
    }

    /**
     * Метод,реализующий инициализацию поля marinesCount класса Chapter.
     */
    private static long initChapterMarinesCount() {
        String stringCount = ioManager.requestParameter("marinesCount для chapter");
        long count;
        while (true) {
            try {
                count = Long.parseLong(stringCount);
                if (count <= 0 || count >= 1000) {
                    ioManager.getInteractive().println("Значение marinesCount должно быть больше 0, Максимальное значение поля: 1000");
                    stringCount = ioManager.requestParameter("marinesCount для chapter");
                } else {
                    return count;
                }
            } catch (NumberFormatException e) {
                ioManager.getInteractive().println("marinesCount долежен быть цельночисленным");
                stringCount = ioManager.requestParameter("marinesCount для chapter");
            }
        }
    }

    /**
     * Метод,реализующий инициализацию поля world класса Chapter.
     */
    private static String initChapterWorld() {
        String world = ioManager.requestParameter("world для chapter");
        while (world == null || world.trim().isEmpty()) {
            ioManager.getInteractive().println("world не может быть null или пустой строкой");
            world = ioManager.requestParameter("world для chapter");
        }
        return world;
    }

    /**
     * Метод, реализующий инициализацию поля name класса SpaceMarine.
     *
     * @return поле name.
     */
    private static String initName() {
        String name = ioManager.requestParameter("name");
        while (name == null || name.trim().isEmpty()) {
            ioManager.getInteractive().println("name не может быть null или пустой строкой");
            name = ioManager.requestParameter("name");
        }
        return name;
    }

    /**
     * Метод, реализующий инициализацию поля health класса SpaceMarine.
     *
     * @return поле health.
     */
    private static float initHealth() {
        String healthString = ioManager.requestParameter("health");
        while (true) {
            try {
                float health = Float.parseFloat(healthString);
                if (health < 0) {
                    ioManager.getInteractive().println("health должен быть больше нуля");
                    healthString = ioManager.requestParameter("health");
                } else {
                    return health;
                }
            } catch (NumberFormatException e) {
                ioManager.getInteractive().println("health должен быть типом с плавающей точкой");
                healthString = ioManager.requestParameter("health");
            }
        }
    }

    /**
     * Метод, реализующий инициализацию поля achievements класса SpaceMarine.
     *
     * @return поле achievement.
     */
    private static String initAchievements() {
        String achievements = ioManager.requestParameter("achievements");
        while (achievements == null || achievements.trim().isEmpty()) {
            ioManager.getInteractive().println("achievements не может быть null или пустой строкой");
            achievements = ioManager.requestParameter("achievements");
        }
        return achievements;
    }

    /**
     * Метод, реализующий инициализацию поля height класса SpaceMarine.
     *
     * @return поле height.
     */
    private static long initHeight() {
        String height = ioManager.requestParameter("height");
        while (true) {
            try {
                return Long.parseLong(height);
            } catch (NumberFormatException e) {
                ioManager.getInteractive().println("height долежен быть цельночисленным");
                height = ioManager.requestParameter("height");
            }
        }
    }

    /**
     * Метод, реализующий инициализацию поля category класса SpaceMarine.
     *
     * @return поле category.
     */
    private static AstartesCategory initCategory() {
        String categoryString = ioManager.requestParameter("category (возможные значения: DREADNOUGHT, " +
                "AGGRESSOR, ASSAULT, TERMINATOR, LIBRARIAN)");
        while (true) {
            try {
                return AstartesCategory.valueOf(categoryString);
            } catch (IllegalArgumentException e) {
                ioManager.getInteractive().println("значение введено в неправильном формате");
                categoryString = ioManager.requestParameter("category (возможные значения: DREADNOUGHT," +
                        " AGGRESSOR, ASSAULT, TERMINATOR, LIBRARIAN)");
            }
        }
    }
}
