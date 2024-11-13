package backend.academy;

import java.io.PrintStream;
import lombok.experimental.UtilityClass;
import static backend.academy.UserInteraction.startOfInteractionWithUser;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        PrintStream output = System.out;
        ChooseWord newWord = startOfInteractionWithUser(output);

        String[] wordAndHint = newWord.getRandomWord(newWord.selectedCategory(), UserInteraction.levelOfDifficulty());
        GameProcess game = new GameProcess(wordAndHint[0], wordAndHint[1], UserInteraction.levelOfDifficulty());
        game.gameStart(output);
        while (game.isResult() == 0) {
            game.gameStep(output);
        }
    }
}
