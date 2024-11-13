package backend.academy;

import java.io.PrintStream;
import java.util.ArrayList;

public class HangManAnimation {

    private final int currentStage; // Текущий этап виселицы
    private final ArrayList<Integer> hangmanStagesIndexes;
    private final int stagesLen = 16;

    public HangManAnimation(int wordLength, int attempts, int attemptCounter) {
        // Число попыток
        this.currentStage = attemptCounter;
        hangmanStagesIndexes = calculateStageIndexes(attempts);
    }

    public void drawHangman(PrintStream output) {
        String stick = "   |         \n";
        String rope = "   --------  \n";
        String ropeToNeck = "   |      |  \n";
        String headOnRope = "   |      O  \n";
        String handOnRope = "   |     /|\\ \n";
        String[] hangmanStages = {
            "   -  \n"
                + stick
                + stick
                + stick
                + stick,
            "   --  \n"
                + stick
                + stick
                + stick
                + stick,
            "   ---  \n"
                + stick
                + stick
                + stick
                + stick,
            "   ----  \n"
                + stick
                + stick
                + stick
                + stick,
            "   -----  \n"
                + stick
                + stick
                + stick
                + stick,
            "   ------  \n"
                + stick
                + stick
                + stick
                + stick,
            "   -------  \n"
                + stick
                + stick
                + stick
                + stick,
            rope
                + stick
                + stick
                + stick
                + stick,
            rope
                + stick
                + stick
                + stick
                + stick,
            rope
                + ropeToNeck
                + stick
                + stick
                + stick,
            rope
                + ropeToNeck
                + headOnRope
                + stick
                + stick,
            rope
                +  ropeToNeck
                + headOnRope
                +  "   |     /   \n"
                + stick,
            rope
                + ropeToNeck
                + headOnRope
                + "   |     /|  \n"
                + stick,
            rope
                + ropeToNeck
                + headOnRope
                + handOnRope
                + stick,
            rope
                + ropeToNeck
                + "   |      O   \n"
                + handOnRope
                + "   |     /    \n",
            rope
                + ropeToNeck
                + headOnRope
                +  handOnRope
                + "   |     / \\ \n"

        };
        output.println(hangmanStages[hangmanStagesIndexes.get(currentStage - 1)]);
    }

    private ArrayList<Integer> calculateStageIndexes(int attempts) {
        ArrayList<Integer> indexes = new ArrayList<>();
        if (attempts == 1) {
            indexes.add(stagesLen - 1);
        } else {
            for (int i = 0; i < attempts; i++) {
                indexes.add((int) Math.round((double) i * (stagesLen - 1) / (attempts - 1)));
            }
        }
        return indexes;
    }
}

