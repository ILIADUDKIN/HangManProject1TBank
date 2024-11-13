package backend.academy;

import java.util.HashSet;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GameState {
    public static HashSet<String> usingRightLetter;
    public static HashSet<String> usingWrongLetter;
    public static int attemptCounter; //число попыток, использованное пользователем
    public static int riddleLetters; //количество угаданных букв
    public static int result; //результат, 0 - игра еще идет, 1 - победа, 2 - поражение
}
