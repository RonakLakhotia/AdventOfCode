import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ChallengeDaySix implements ChallengeDay {


    @Override
    public void execute() throws IOException {
        BufferedReader objReader = new BufferedReader(new FileReader("test.txt"));
        String input = objReader.readLine();
        StringBuilder sb = new StringBuilder();

        int i = 0;
        int index = i;

        while (index < input.length()) {
            if (sb.length() == 14) {
                System.out.println("Total = " + (index));
                break;
            }
            char character = input.charAt(index++);
            String nextCharacter = String.valueOf(character);
            if (sb.indexOf(nextCharacter) >= 0) {
                i++;
                index = i;
                sb = new StringBuilder();
            } else {
                sb.append(nextCharacter);
            }
        }
     }
}
