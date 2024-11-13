package backend.academy;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    public void testSetExceptionZeroOne() {
        // Arrange
        GameProcess gameProcess = new GameProcess("word", "hint", 1);

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStreamCaptor);

        // Mock Scanner input
        InputStream is = new ByteArrayInputStream("2\na\n1".getBytes());
        System.setIn(is);
        Scanner in = new Scanner(System.in);

        // Act
        Scanner inNew = new Scanner(System.in);
        int result = ExceptionFromUserMistake.setException(output, 1, inNew);

        // Assert
        String expectedOutput = "Ошибка! Введите 1 или 0.\nОшибка! Введите целое число.\n";
        String actualOutput = outputStreamCaptor.toString();
        assertEquals(expectedOutput, actualOutput);
        assertEquals(1, result); // Проверяем, что метод вернул правильное число
    }

    @Test
    void testCategoryChosenValidInput() {
        // Arrange
        GameProcess gameProcess = new GameProcess("word", "hint", 1);
        ChooseWord chooseWord = new ChooseWord();
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStreamCaptor);

        // Mock Scanner input
        InputStream is = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(is);
        Scanner in = new Scanner(System.in);

        // Act
        ChooseWord result = ChooseChecker.categoryChosen(chooseWord, output);

        // Assert
        String expectedOutput = "Перед вами список категорий: \n" + "0 - PROGRAMMING; 1 - ANIMALS; 2 - FRUITS; 3 - COUNTRIES; 4 - FILMS; 5 - MUSIC; 6 - SPORTS; 7 - FOOD; Введите номер категории: \n"
        + "Поздравляем! Ваша категория - ANIMALS\n";
        String actualOutput = outputStreamCaptor.toString();
        assertEquals(expectedOutput, actualOutput);
        assertEquals(ChooseWord.Category.ANIMALS, result.selectedCategory()); // Проверяем, что категория была выбрана правильно
    }

    @Test
    void testCategoryChosenUnValidInput() {
        // Arrange
        GameProcess gameProcess = new GameProcess("word", "hint", 1);
        ChooseWord chooseWord = new ChooseWord();
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStreamCaptor);

        // Mock Scanner input
        InputStream is = new ByteArrayInputStream("8\na\n1\n".getBytes());
        System.setIn(is);
        Scanner in = new Scanner(System.in);

        // Act
        ChooseWord result = ChooseChecker.categoryChosen(chooseWord, output);

        // Assert
        String expectedOutput = "Перед вами список категорий: \n" + "0 - PROGRAMMING; 1 - ANIMALS; 2 - FRUITS; 3 - COUNTRIES; 4 - FILMS; 5 - MUSIC; 6 - SPORTS; 7 - FOOD; Введите номер категории: \n"
            + "Ошибка! Введите число от 0 до 7.\nОшибка! Введите целое число.\n" + "Поздравляем! Ваша категория - ANIMALS\n";
        String actualOutput = outputStreamCaptor.toString();
        assertEquals(expectedOutput, actualOutput);
        assertEquals(ChooseWord.Category.ANIMALS, result.selectedCategory()); // Проверяем, что категория была выбрана правильно
    }

    @Test
    void testhardLevelChosenValidInput() {
        // Arrange
        GameProcess gameProcess = new GameProcess("word", "hint", 1);
        ChooseWord chooseWord = new ChooseWord();
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStreamCaptor);

        // Mock Scanner input
        InputStream is = new ByteArrayInputStream("3\n".getBytes());
        System.setIn(is);
        Scanner in = new Scanner(System.in);

        // Act
        int result = ChooseChecker.hardLevelChosen(output);

        // Assert
        assertEquals(result,3);

    }

    @Test
    void testhardLevelChosenUnValidInput() {
        // Arrange
        GameProcess gameProcess = new GameProcess("word", "hint", 1);
        ChooseWord chooseWord = new ChooseWord();
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStreamCaptor);

        // Mock Scanner Input
        InputStream is = new ByteArrayInputStream("8\na\n1\n".getBytes());
        System.setIn(is);
        Scanner in = new Scanner(System.in);

        // Act
        int result = ChooseChecker.hardLevelChosen(output);

        // Assert
        String expectedOutput = "Введите уровень сложности, где \n" + "3 - легкий\n" + "2 - средний \n" + "1 - сложный \n"
            + "Ошибка! Введите число от 1 до 3.\n" + "Ошибка! Введите целое число.\n";;
        String actualOutput = outputStreamCaptor.toString();
        assertEquals(expectedOutput,actualOutput);
    }

}
