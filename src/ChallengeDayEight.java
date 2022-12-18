import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ChallengeDayEight implements ChallengeDay {

    @Override
    public void execute() throws IOException {

        BufferedReader objReader = new BufferedReader(new FileReader("/Users/ronaklakhotia/workspace/AdventOfCode/src/test.txt"));
        String text;
        ArrayList<String> rows = new ArrayList<>();

        while ((text = objReader.readLine()) != null) {
            rows.add(text);
        }

        int[][] trees = new int[rows.size()][rows.size()];
        prepareTwoDArray(rows, trees);

        System.out.println(getNumOfTreesVisible(trees));
    }

    private static int getNumOfTreesVisible(int[][] trees) {
        int score;
        int maxScore = -1;

        for (int i = 1; i < trees[0].length - 1; i++) {
            for (int j = 1; j < trees[0].length - 1; j++) {
                int num;
                score = 1;

                num = firstTreeTallerOrEqualOnLeftOrTop(trees[i][j], Arrays.copyOfRange(trees[i], 0, j));
                score = score * (j - num);

                num = isTreeVisibleInGivenRange(trees[i][j], Arrays.copyOfRange(trees[i], j+1, trees[0].length));
                score = score * num;


                num = firstTreeTallerOrEqualOnLeftOrTop(trees[i][j], getColumnElements(trees, 0, i-1, j));
                score = score * (i - num);


                num = isTreeVisibleInGivenRange(trees[i][j], getColumnElements(trees, i+1, trees[0].length - 1, j));
                score = score * num;

                maxScore = Math.max(score, maxScore);

            }
        }
        return maxScore;
    }

    private static int isTreeVisibleInGivenRange(int treeHeight, int[] treesInRange) {
        int index = 1;
        for (int heightOfTree : treesInRange) {
            if (treeHeight <= heightOfTree) {
                return index;
            }
            index++;
        }
        return index-1;
    }

    private static int firstTreeTallerOrEqualOnLeftOrTop(int treeHeight, int[] treesInRange) {
        int index = 0;
        for (int i = 0; i < treesInRange.length; i++) {
            if (treeHeight <= treesInRange[i]) {
                index = i;
            }
        }
        return index;
    }

    private static int[] getColumnElements(int[][] trees, int startRow, int endRow, int columnIndex) {
        int[] columnElements = new int[endRow - startRow + 1];
        for (int i = startRow; i <= endRow; i++) {
            columnElements[i-startRow] = trees[i][columnIndex];
        }
        return columnElements;
    }

    private static void prepareTwoDArray(ArrayList<String> rows, int[][] trees) {
        int index = 0;
        for (String row : rows) {
            trees[index++] = Arrays.stream(row.split("")).mapToInt(Integer::parseInt).toArray();
        }
    }
}
