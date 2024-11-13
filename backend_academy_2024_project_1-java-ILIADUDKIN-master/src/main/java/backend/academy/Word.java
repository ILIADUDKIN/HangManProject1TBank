package backend.academy;

import lombok.Getter;
import lombok.Setter;
import static backend.academy.UserInteraction.COUNT_OF_LEVELS_OF_DIFFICULTY;

@Getter class Word {
    private final String word;
    @Setter private int hardLevel;
    private final String hint;

    Word(String word, int hardLevel, String hint) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Слово не может быть пустым или null.");
        }
        if (hardLevel <= 0 || hardLevel > COUNT_OF_LEVELS_OF_DIFFICULTY) {
            throw new IllegalArgumentException("Уровень сложности должен быть больше 0 и меньше 3.");
        }
        if (hint == null || hint.isEmpty()) {
            throw new IllegalArgumentException("Подсказка не может быть пустой или null.");
        }
        this.word = word;
        this.hardLevel = hardLevel;
        this.hint = hint;
    }

}
