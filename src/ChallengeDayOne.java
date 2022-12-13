import util.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

public class ChallengeDayOne implements ChallengeDay {

    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int currentElfCalorieCount = 0;
        PriorityQueue<Integer> queueOfCalorieCapacityOfElves = new PriorityQueue<Integer>(Comparator.reverseOrder());

        while (true) {
            String text = reader.readLine();
            if ("quit".equals(text)) {
                break;
            }
            if (Text.isTextEmptyOrNull(text)) {
                queueOfCalorieCapacityOfElves.add(currentElfCalorieCount);
                currentElfCalorieCount = 0;
                continue;
            }
            int calories = Integer.parseInt(text);
            currentElfCalorieCount += calories;
        }
        System.out.println((queueOfCalorieCapacityOfElves.poll() + queueOfCalorieCapacityOfElves.poll() + queueOfCalorieCapacityOfElves.poll()));
    }
}
