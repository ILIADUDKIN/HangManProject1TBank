package backend.academy;

import java.io.PrintStream;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import static backend.academy.ChooseChecker.categoryChosen;
import static backend.academy.ChooseChecker.hardLevelChosen;
import static backend.academy.ChooseChecker.isCategoryChosen;
import static backend.academy.ChooseChecker.isHardLevelChosen;

@UtilityClass
public class UserInteraction {
    final static int COUNT_OF_LEVELS_OF_DIFFICULTY = 3;
    @Getter private static int isCategoryChoose;
    @Getter private static int isLevelOfDifficultyChoose;
    @Getter private static int levelOfDifficulty;

    public void selectCategory(PrintStream output, ChooseWord newWord) {
        isCategoryChoose = isCategoryChosen(output);
        if (isCategoryChoose == 1) {
            categoryChosen(newWord, output); // если пользователь решил выбрать категорию
        } else {
            newWord.setRandomCategory(); // если пользователь решил не выбирать категорию, то устанавливается случайная
        }
    }

    public void selectDifficulty(PrintStream output) {
        isLevelOfDifficultyChoose = isHardLevelChosen(output);
        if (isLevelOfDifficultyChoose == 1) {
            levelOfDifficulty = hardLevelChosen(output);
        } else {
            levelOfDifficulty = ThreadLocalRandom.current().nextInt(1, COUNT_OF_LEVELS_OF_DIFFICULTY + 1);
        }
    }


    // начальный этап взаимдействия с пользователем
    public static ChooseWord startOfInteractionWithUser(PrintStream output) {
        ChooseWord newWord = new ChooseWord();
        // Установка категории и уровня слоности
        selectCategory(output, newWord);
        selectDifficulty(output);
        output.println("Ваша категория - " + newWord.selectedCategory()
            + "\nВаш уровень сложности - " + UserInteraction.levelOfDifficulty());
        return newWord;
    }




}
