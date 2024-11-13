package backend.academy;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import static backend.academy.GameState.attemptCounter;
import static backend.academy.GameState.result;
import static backend.academy.GameState.riddleLetters;
import static backend.academy.GameState.usingRightLetter;
import static backend.academy.GameState.usingWrongLetter;

public class GameProcess {
    public static final String ANOTHER_REQUEST = "Вы уже вводили эту букву! Повторите ввод.";
    private final String riddleWordHint;
    private final String riddleWord; //загаданное слово
    private final String[] riddleWordArray; //массив из букв загаданного слова
    public final int attempts; //общее число попыток пользователя
    public ArrayList<Boolean> gameCondition; //список на каждую букву
    public final String accessString = "abcdefghijklmnopqrstuvwxyz"
        + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        + "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
        + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
        + "-+";
    //конструктор

    public GameProcess(String riddleWord, String hint, int hardLevel) {
        this.riddleWord = riddleWord;
        this.riddleWordHint = hint;
        this.riddleWordArray = riddleWord.split("");
        this.attempts = hardLevel * (riddleWord.length() / 2);
    }

    public int isResult() {
        return result;
    }

    //состояние игры

    public void drawWord(PrintStream output) {
        for (int i = 0; i < riddleWord.length(); i++) {
            if (!gameCondition.get(i)) {
                output.print("_ ");
            } else {
                output.print(riddleWord.charAt(i) + " "); //
            }
        }
    }

    public int countOfLetterInTheWord(String letter) {
        int letterCounter = 0;
        for (String s : riddleWordArray) {
            if (s.equalsIgnoreCase(letter)) {
                letterCounter++;
            }
        }
        return letterCounter;
    }

    public void hintRequest(PrintStream output) { // Добавляем PrintStream
        if (attemptCounter == attempts / 2) {
            output.println(
                "Внимание! Осталось не так уж и много попыток. Вы хотите использовать подсказку?\n" + "1 - Да\n"
                    + "0 - Нет\n");
            Scanner in = new Scanner(System.in);
            ExceptionFromUserMistake.setException(output, 1, in);
            output.println(riddleWordHint);
        }
    }

    public void outputToUser(PrintStream output) {
        drawWord(output);
        output.println();
    }

    public void startOutputToUser(PrintStream output) {
        output.println("Игра началась!"
            + "На начальный момент у вас есть "
            + attempts
            + " попыток."
            + "Количество использованных попыток: "
            + attemptCounter);
        hintRequest(output);
        outputToUser(output);
    }

    public void gameStart(PrintStream output) { // Добавляем PrintStream
        gameCondition = new ArrayList<>(); // инициализация состояния игры
        for (int i = 0; i < riddleWord.length(); i++) {
            gameCondition.add(false);
        }
        usingRightLetter = new HashSet<String>(Collections.emptySet());
        usingWrongLetter = new HashSet<String>(Collections.emptySet());
        startOutputToUser(output);
    }

    public void rightLetter(PrintStream output, String letter) {
        if (!usingRightLetter.contains(letter) && !usingWrongLetter.contains(letter)) {
            output.println("Поздравляем! Вы угадали букву. Мы ставим букву на своё место!");
            for (int i = 0; i < riddleWord.length(); i++) {
                if (riddleWordArray[i].equalsIgnoreCase(letter)) {
                    gameCondition.set(i, true);
                    usingRightLetter.add(letter);
                    usingRightLetter.add(letter.toUpperCase());
                    riddleLetters++;
                }
            }
            outputToUser(output);
            if (riddleLetters == riddleWord.length()) {
                output.println("Поздравляем! Вы победили!"); //
                result = 1;
            }
        } else {
            output.println(ANOTHER_REQUEST);
        }
    }

    public void wrongLetter(PrintStream output, String letter) {
        if (!usingRightLetter.contains(letter) && !usingWrongLetter.contains(letter)) {
            usingWrongLetter.add(letter);
            usingWrongLetter.add(letter.toUpperCase());
            output.println("Извините, но вы не угадали!\n" + "Использованы (неверны) буквы: " + usingWrongLetter);
            attemptCounter++;
            hintRequest(output);
            if (attemptCounter == attempts) {
                output.println("Вы проиграли!");
                result = 2;
                output.println("Загаданное слово - " + riddleWord);
            }
            HangManAnimation hangman = new HangManAnimation(riddleWord.length(), attempts, attemptCounter);
            hangman.drawHangman(output);
        } else {
            output.println(ANOTHER_REQUEST);
        }
    }

    public String incorrectLetterInput(PrintStream output) {
        Scanner in = new Scanner(System.in);
        String letter = in.nextLine();
        boolean validIn = false;
        while (!validIn) {
            if (letter.length() != 1) {
                output.println("Буква - строка из одного слова! Ввод неккоректен");
                letter = in.nextLine();
            } else if (!accessString.contains(letter)) {
                output.println("Введите букву!");
                letter = in.nextLine();
            } else {
                validIn = true;
            }
        }
        return letter;
    }

    public void gameStep(PrintStream output) {
        output.println("Осталось попыток: " + (attempts - attemptCounter));
        output.println("Введите букву: ");
        String letter = incorrectLetterInput(output);
        if (countOfLetterInTheWord(letter) > 0) {
            rightLetter(output, letter);
        } else {
            wrongLetter(output, letter);
        }
    }
}
