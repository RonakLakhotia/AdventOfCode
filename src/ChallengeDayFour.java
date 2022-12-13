import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ChallengeDayFour implements ChallengeDay {

    @Override
    public void execute() throws IOException {

        BufferedReader objReader = new BufferedReader(new FileReader("test.txt"));
        String line;
        int count = 0;
        while ((line = objReader.readLine()) != null) {
            String[] listOfPairs = line.split(",");
            String firstRange = listOfPairs[0];
            String secondRange = listOfPairs[1];
            Pair firstPair = convertRangeToPairObject(firstRange);
            Pair secondPair = convertRangeToPairObject(secondRange);
            if (isOnePairIntersectsTheOtherPair(firstPair, secondPair)) {
                count++;
            }
        }
        System.out.println("Total count = " + count);
    }

    private static Pair convertRangeToPairObject(String range) {
        String[] ids = range.split("-");
        return new Pair(Integer.parseInt(ids[0]), Integer.parseInt(ids[1]));
    }

    private static boolean isOnePairIntersectsTheOtherPair(Pair firstPair, Pair secondPair) {
        Pair startingPair = firstPair.start <= secondPair.start ? firstPair : secondPair;
        Pair followingPair = firstPair.start > secondPair.start ? firstPair : secondPair;

        int firstPointer = startingPair.start;
        while (firstPointer <= startingPair.end) {
            if (firstPointer == followingPair.start) {
                return true;
            }
            firstPointer++;
        }
        return false;
    }

    static class Pair {
        private final int start;
        private final int end;

        public Pair(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
