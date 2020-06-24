package entities;

import managers.FileManager;
import model.dto.MarineAddDto;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class SpaceMarineFactory {
    private SpaceMarineFactory() {
        throw new IllegalStateException("Класс утилита");
    }

    /**
     * Получить SpaceMarine от клиента
     *
     * @param marineAddDto Dto с данными
     * @param id           идентификатор SpaceMarine
     * @return Optional<SpaceMarine>
     */
    public static Optional<SpaceMarine> getSpaceMarine(MarineAddDto marineAddDto, int id) {
        SpaceMarine marine = new SpaceMarine();
        marine.setId(id);
        try {
            setProperty(marine::setName, marineAddDto::getMarineName);
            setProperty(marine::setHealth, marineAddDto::getMarineHealth);
            setProperty(marine::setAchievements, marineAddDto::getMarineAchievements);
            setProperty(marine::setHeight, marineAddDto::getMarineHeight);
            setProperty(marine::setCategory, marineAddDto::getMarineCategory);

            Coordinates coordinates = new Coordinates();
            setProperty(coordinates::setX, marineAddDto::getCoordinatesX);
            setProperty(coordinates::setY, marineAddDto::getCoordinatesY);
            marine.setCoordinates(coordinates);

            Chapter chapter = new Chapter();
            setProperty(chapter::setName, marineAddDto::getChapterName);
            setProperty(chapter::setMarinesCount, marineAddDto::getChapterMarinesCount);
            setProperty(chapter::setWorld, marineAddDto::getChapterWorld);
            marine.setChapter(chapter);
            marine.setCreationDate(LocalDate.now());
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.of(marine);
    }

    /**
     * Получить SpaceMarine для скрипта
     *
     * @param fileManager менеджер файлов
     * @param id          идентификатор SpaceMarine
     * @return Optional<SpaceMarine>
     */
    public static Optional<SpaceMarine> getSpaceMarine(FileManager fileManager, int id) {
        SpaceMarine marine = new SpaceMarine();
        marine.setId(id);
        Function<Constraint, Optional<String>> propertyFunction = getPropertyFunction(fileManager);
        try {
            marine.setName(propertyFunction.apply(Constraint.MARINE_NAME).orElseThrow(Exception::new));

            Coordinates coordinates = new Coordinates();
            coordinates.setX(Integer.parseInt(propertyFunction.apply(Constraint.COORDINATES_X).orElseThrow(Exception::new)));
            coordinates.setY(Long.parseLong(propertyFunction.apply(Constraint.COORDINATES_Y).orElseThrow(Exception::new)));
            marine.setCoordinates(coordinates);

            marine.setHealth(Float.parseFloat(propertyFunction.apply(Constraint.MARINE_HEALTH).orElseThrow(Exception::new)));
            marine.setAchievements(propertyFunction.apply(Constraint.MARINE_ACHIEVEMENTS).orElseThrow(Exception::new));
            marine.setHeight(Long.parseLong(propertyFunction.apply(Constraint.MARINE_HEIGHT).orElseThrow(Exception::new)));
            marine.setCategory(AstartesCategory.valueOf(propertyFunction.apply(Constraint.MARINE_CATEGORY).orElseThrow(Exception::new)));

            Chapter chapter = new Chapter();
            chapter.setName(propertyFunction.apply(Constraint.CHAPTER_NAME).orElseThrow(Exception::new));
            chapter.setMarinesCount(Long.parseLong(propertyFunction.apply(Constraint.CHAPTER_MARINES_COUNT).orElseThrow(Exception::new)));
            chapter.setWorld(propertyFunction.apply(Constraint.CHAPTER_WORLD).orElseThrow(Exception::new));
            marine.setChapter(chapter);
            marine.setCreationDate(LocalDate.now());
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(marine);
    }

    private static <T> void setProperty(Consumer<T> setter, Supplier<T> getter) throws Exception {
        T value = getter.get();
        if (value == null)
            throw new Exception();

        setter.accept(value);
    }

    private static Function<Constraint, Optional<String>> getPropertyFunction(FileManager fileManager) {
        return constraint -> getProperty(fileManager, constraint);
    }

    private static Optional<String> getProperty(FileManager fileManager, Constraint constraint) {
        Optional<String> property = fileManager.readScriptLine();
        if (property.isPresent() && !constraint.validate(property.get())) {
            return property;
        }
        return Optional.empty();
    }

    private enum Constraint {
        MARINE_NAME(name -> name == null || name.trim().isEmpty()),
        MARINE_HEALTH(health -> {
            try {
                return Float.parseFloat(health) < 0;
            } catch (NumberFormatException e) {
                return true;
            }
        }),
        MARINE_ACHIEVEMENTS(achievements -> achievements == null || achievements.trim().isEmpty()),
        MARINE_HEIGHT(height -> {
            try {
                return Long.parseLong(height) <= 0;
            } catch (NumberFormatException e) {
                return true;
            }
        }),
        MARINE_CATEGORY(category -> {
            try {
                AstartesCategory.valueOf(category);
                return false;
            } catch (IllegalArgumentException e) {
                return true;
            }
        }),
        COORDINATES_X(x -> {
            try {
                Integer.parseInt(x);
                return false;
            } catch (NumberFormatException e) {
                return true;
            }
        }),
        COORDINATES_Y(y -> {
            try {
                return Integer.parseInt(y) > Coordinates.upper;
            } catch (NumberFormatException e) {
                return true;
            }
        }),
        CHAPTER_NAME(name -> name == null || name.trim().isEmpty()),
        CHAPTER_MARINES_COUNT(marinesCount -> {
            try {
                long count = Long.parseLong(marinesCount);
                return count <= Chapter.lower || count >= Chapter.upper;
            } catch (NumberFormatException e) {
                return true;
            }
        }),
        CHAPTER_WORLD(world -> world == null || world.trim().isEmpty());

        private final Function<String, Boolean> validator;

        Constraint(Function<String, Boolean> validator) {
            this.validator = validator;
        }

        /**
         * Проверяет является ли значение невалидным
         */
        public boolean validate(String input) {
            return validator.apply(input);
        }
    }

}
