import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChallengeDayThree implements ChallengeDay {

    @Override
    public void execute() throws IOException {

        int totalScore = 0;
        BufferedReader objReader;
        objReader = new BufferedReader(new FileReader("test.txt"));
        String rucksackFirstElf;
        String rucksackSecondElf;
        String rucksackThirdElf;
        StringBuilder sb;

        while ((rucksackFirstElf = objReader.readLine()) != null) {
            rucksackSecondElf = objReader.readLine();
            rucksackThirdElf = objReader.readLine();
            System.out.println(rucksackFirstElf);

            sb = new StringBuilder();
            sb.append(rucksackFirstElf)
              .append("-")
              .append(rucksackSecondElf)
              .append("-")
              .append(rucksackThirdElf);

            char commonCharacter = getCommonCharacter(sb);
            System.out.println(commonCharacter);
            totalScore += getCharacterWeight(commonCharacter);
        }
        System.out.println("Total score = " + totalScore);
    }

    private int getCharacterWeight(char commonCharacter) {
        if (Character.isLowerCase(commonCharacter)) {
            return commonCharacter - 96;
        }
        return commonCharacter - 38;
    }

    private static char getCommonCharacter(StringBuilder sb) {
        Map<Character, Integer> countOfEachCharacter = new HashMap<>();
        Map<Character, Integer> previousWordNumberOfCharacter = new HashMap<>();

        int wordNumber = 1;

        for (int i = 0; i < sb.length(); ++i) {
            char character = sb.charAt(i);
            if (character == '-') {
                wordNumber++;
            }
            if (! countOfEachCharacter.containsKey(character)) {
                countOfEachCharacter.put(character, 1);
                previousWordNumberOfCharacter.put(character, wordNumber);
            }
            if (countOfEachCharacter.containsKey(character) && wordNumber != previousWordNumberOfCharacter.get(character)) {
                int count = countOfEachCharacter.get(character);
                countOfEachCharacter.put(character, ++count);
                previousWordNumberOfCharacter.put(character, wordNumber);
            }
            if (countOfEachCharacter.get(character) == 3) {
                return character;
            }
        }
        return '-';
    }
}
