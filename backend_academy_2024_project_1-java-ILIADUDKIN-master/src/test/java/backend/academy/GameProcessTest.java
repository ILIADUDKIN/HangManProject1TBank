package backend.academy;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class GameProcessTest {

    public void defaultArrange(GameProcess gameProcess){
        gameProcess.gameCondition = new ArrayList<>();
        gameProcess.gameCondition.add(true);
        gameProcess.gameCondition.add(false);
        gameProcess.gameCondition.add(true);
        gameProcess.gameCondition.add(false);
    }

    public void defaultInitialization(GameProcess gameProcess){
        GameState.riddleLetters = 2;
        GameState.usingRightLetter = new HashSet<>(){};
        GameState.usingRightLetter.add("w");
        GameState.usingRightLetter.add("r");
        GameState.usingWrongLetter = new HashSet<>(){};
        GameState.usingWrongLetter.add("m");
        GameState.usingWrongLetter.add("n");
    }

    public void defaultAssert(HashSet<String> testSet, GameProcess gameProcess,
        int expectedRiddleLetters, String expectedOutput, boolean expectedRiddleSuccess, ByteArrayOutputStream outputStreamCaptor){
        assertEquals(testSet, GameState.usingRightLetter);
        assertEquals(expectedRiddleLetters, GameState.riddleLetters);
        assertEquals(expectedRiddleSuccess, gameProcess.gameCondition.get(1));
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void testCountOfLetterInTheWord() {

        // Arrange
        GameProcess gameProcess = new GameProcess("tesTWord", "testHint", 1);

        // Act
        int letterCounter = gameProcess.countOfLetterInTheWord("t");

        // Assert
        assertEquals(2, letterCounter); // В слове "testWord" две буквы "t" разного регистра
    }

    @Test
    void testDrawWord() {

        // Arrange
        GameProcess gameProcess = new GameProcess("word", "hint", 1);
        defaultArrange(gameProcess);
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStreamCaptor);

        // Act
        gameProcess.drawWord(output);

        // Assert
        String expectedOutput = "w _ r _ ";
        assertEquals(expectedOutput, outputStreamCaptor.toString()); // проверяем верность вывода
    }

    @Test
    void testRightLetterNotSameLetter() {
        // Arrange
        GameProcess gameProcess = new GameProcess("word", "hint", 1);
        defaultArrange(gameProcess);
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStreamCaptor);
        defaultInitialization(gameProcess);

        // Act
        gameProcess.rightLetter(output,"o");

        // Assert
        String expectedOutput = "Поздравляем! Вы угадали букву. Мы ставим букву на своё место!\n" + "w o r _ \n";
        HashSet<String> testSet = new HashSet<>(){};
        testSet.add("w");
        testSet.add("r");
        testSet.add("o");
        testSet.add("O");
        int expectedRiddleLetters = 3;
        boolean expectedRiddleSuccess = true;
        defaultAssert(testSet,gameProcess,expectedRiddleLetters,expectedOutput,
            expectedRiddleSuccess,outputStreamCaptor);
    }

    @Test
    void testRightLetterSameLetter() {
        // Arrange
        GameProcess gameProcess = new GameProcess("word", "hint", 1);
        defaultArrange(gameProcess);
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStreamCaptor);
        defaultInitialization(gameProcess);

        // Act
        gameProcess.rightLetter(output,"w");

        // Assert
        String expectedOutput = "Вы уже вводили эту букву! Повторите ввод.\n";
        HashSet<String> testSet = new HashSet<>(){};
        testSet.add("w");
        testSet.add("r");
        int expectedRiddleLetters = 2;
        boolean expectedRiddleSuccess = false;
        defaultAssert(testSet,gameProcess,expectedRiddleLetters,expectedOutput,
            expectedRiddleSuccess,outputStreamCaptor);
    }

    @Test
    void testWrongLetter() {
        // Arrange
        GameProcess gameProcess = new GameProcess("word", "hint", 1);
        defaultArrange(gameProcess);
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStreamCaptor);
        defaultInitialization(gameProcess);
        GameState.attemptCounter = 1;

        // Act
        gameProcess.wrongLetter(output,"e");


        // Assert
        HashSet<String> testSet = new HashSet<>(){};
        testSet.add("m");
        testSet.add("n");
        testSet.add("e");
        testSet.add("E");
        assertEquals(GameState.attemptCounter, 2);
        assertEquals(testSet, GameState.usingWrongLetter);
    }

    @Test
    void testWin() {
        // Arrange
        GameProcess gameProcess = new GameProcess("wor", "hint", 1);
        defaultArrange(gameProcess);
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStreamCaptor);
        defaultInitialization(gameProcess);

        // Act
        gameProcess.rightLetter(output,"o");

        // Assert
        String expectedOutput = "Поздравляем! Вы угадали букву. Мы ставим букву на своё место!\n" + "w o r \n" + "Поздравляем! Вы победили!\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void testLose() {
        // Arrange
        GameProcess gameProcess = new GameProcess("word", "hint", 1);
        defaultArrange(gameProcess);
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStreamCaptor);
        defaultInitialization(gameProcess);
        GameState.attemptCounter = 1;

        // Act
        gameProcess.wrongLetter(output,"e");


        // Assert
        String expectedOutput = "Извините, но вы не угадали!\n" + "Использованы (неверны) буквы: [e, E, m, n]\n"
            + "Вы проиграли!\n" + "Загаданное слово - word\n" + "   --------  \n" + "   |      |  \n"
            + "   |      O  \n" + "   |     /|\\ \n" + "   |     / \\ \n\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());

    }

    @Test
    void testIncorrectLetterInput() {
        // Arrange
        GameProcess gameProcess = new GameProcess("word", "hint", 1);
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStreamCaptor);

        // Mock Scanner input
        InputStream is = new ByteArrayInputStream("123\na\n".getBytes());
        System.setIn(is);

        Scanner in = new Scanner(System.in);

        // Act
        String result = gameProcess.incorrectLetterInput(output);

        // Assert
        String expectedOutput = "Буква - строка из одного слова! Ввод неккоректен\n";
        String actualOutput = outputStreamCaptor.toString();
        assertEquals(expectedOutput, actualOutput);
        assertEquals("a", result); // Проверяем, что метод вернул правильную букву
    }



}
