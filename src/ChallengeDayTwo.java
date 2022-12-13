import util.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChallengeDayTwo implements ChallengeDay {

    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Map<String, GameMoves> mapInputToEnum = getMap();
        int totalScore = 0;

        while (true) {
            String text = reader.readLine();
            if ("quit".equals(text)) {
                break;
            }
            if (Text.isTextEmptyOrNull(text)) {
                continue;
            }
            String opponentMove = String.valueOf(text.charAt(0));
            String outcomeOfRound = String.valueOf(text.charAt(2));
            GameMoves opponentMoveEnum = mapInputToEnum.get(opponentMove);
            totalScore += getTotalScoreForRound(opponentMoveEnum, outcomeOfRound);
        }
        System.out.println("Total Score = " + totalScore);
    }

    private static Map<String, GameMoves> getMap() {
        return new HashMap<>() {{
            put("A", GameMoves.Rock);
            put("B", GameMoves.Paper);
            put("C", GameMoves.Scissor);
        }};
    }

    private static int getTotalScoreForRound(GameMoves opponentMove, String outcomeOfRound) {
        return getScoreForRoundResult(outcomeOfRound)
                + getScoreForMove(Objects.requireNonNull(getEnum(movePlayerShouldPlay(outcomeOfRound, opponentMove))));
    }

    private static int getScoreForMove(GameMoves playerMove) {
        return playerMove.getScoreForMove();
    }

    private static int getScoreForRoundResult(String outcomeOfRound) {
        return switch (outcomeOfRound) {
            case "X" -> 0;
            case "Y" -> 3;
            case "Z" -> 6;
            default -> -1;
        };
    }

    private static String movePlayerShouldPlay(String outcomeOfRound, GameMoves opponentMove) {
        return switch (outcomeOfRound) {
            case "X" -> opponentMove.getMoveDefeated();
            case "Y" -> opponentMove.toString();
            case "Z" -> opponentMove.getMoveLostTo();
            default -> outcomeOfRound;
        };
    }

    private static GameMoves getEnum(String move) {
        for (GameMoves gameMove : GameMoves.values()) {
            if (gameMove.toString().equals(move)) {
                return gameMove;
            }
        }
        return null;
    }

    private enum GameMoves {

        Rock("Scissor", "Paper", 1),
        Paper("Rock", "Scissor", 2),
        Scissor("Paper", "Rock", 3);

        private final String moveDefeated;
        private final String moveLostTo;
        private final int scoreForMove;

        GameMoves(String moveDefeated, String moveLostTo, int scoreForMove) {
            this.moveDefeated = moveDefeated;
            this.moveLostTo = moveLostTo;
            this.scoreForMove = scoreForMove;
        }

        public String getMoveDefeated() {
            return moveDefeated;
        }

        public String getMoveLostTo() {
            return moveLostTo;
        }

        public int getScoreForMove() {
            return scoreForMove;
        }
    }
}
