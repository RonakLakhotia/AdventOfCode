import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ChallengeDayFive implements ChallengeDay {

//    private static final int SIZE_ELEMENT = 3;
    @Override
    public void execute() throws IOException {
        BufferedReader objReader = new BufferedReader(new FileReader("test.txt"));
        List<Container> listOfContainers = getListOfEmptyContainers();
        List<String> inputOfContainersInOneLine = getCrateOrientation(objReader);

        for (int i = inputOfContainersInOneLine.size() - 2; i >= 0; --i) {
            String crateOrientation = inputOfContainersInOneLine.get(i);
            String[] crates = crateOrientation.split(" ");
            int containerNum = 0;
            int emptyPositions = 0;
            for (String crate: crates) {
                if (! crate.trim().isEmpty()) {
                    containerNum++;
                    listOfContainers.get(containerNum).putCrateInContainer(crate.charAt(1) + "");
                } else {
                    emptyPositions++;
                    if (emptyPositions % 4 == 0) {
                        containerNum++;
                    }
                }
            }
        }
//        System.out.println(listOfContainers.get(2).removeCrateFromContainer());

        String instructionText;
        while ((instructionText = objReader.readLine()) != null) {
            Instruction instruction = new Instruction(instructionText);
            Container sourceContainer = listOfContainers.get(instruction.getSourceContainer());
            Container destinationContainer = listOfContainers.get(instruction.getDestinationContainer());
            sourceContainer.transferCratesFromCurrentContainerToNew(instruction.getNumOfStacksToMove(), destinationContainer);
        }

        for (int i = 1; i <= 9; ++i) {
            if (! listOfContainers.get(i).isContainerEmpty()) {
                System.out.print(listOfContainers.get(i).removeCrateFromContainer());
            }
        }
        System.out.println();
    }

    private static List<String> getCrateOrientation(BufferedReader objReader) throws IOException {
        String currentLine;
        List<String> inputOfContainersInOneLine = new ArrayList<>();

        while ((currentLine = objReader.readLine()) != null) {
            if (currentLine.isEmpty()) {
                break;
            }
            inputOfContainersInOneLine.add(currentLine);
        }
        return inputOfContainersInOneLine;
    }

    private static  List<Container> getListOfEmptyContainers() {

        List<Container> containers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            containers.add(new Container());
        }
        return containers;
    }

    static class Container {
        private final Stack<String> storageContainer;

        public Container() {
            storageContainer = new Stack<>();
        }

        public void putCrateInContainer(String crate) {
            storageContainer.push(crate);
        }

        private String removeCrateFromContainer() {
            return storageContainer.pop();
        }

        public void transferCratesFromCurrentContainerToNew(int numOfCratesToTransfer, Container newContainer) {
            Stack<String> temporaryStack = new Stack<>();
            for (int i = 0; i < numOfCratesToTransfer; ++i) {
                temporaryStack.push(removeCrateFromContainer());
            }
            for (int i = 0; i < numOfCratesToTransfer; ++i) {
                newContainer.putCrateInContainer(temporaryStack.pop());
            }
        }

        public boolean isContainerEmpty() {
            return storageContainer.empty();
        }
    }

    static class Instruction {
        private final int numOfStacksToMove;
        private final int sourceContainer;
        private final int destinationContainer;

        public Instruction(String text) {
            String[] details = text.split(" ");
            this.numOfStacksToMove = Integer.parseInt(details[1]);
            this.sourceContainer = Integer.parseInt(details[3]);
            this.destinationContainer = Integer.parseInt(details[5]);
        }

        public int getNumOfStacksToMove() {
            return numOfStacksToMove;
        }

        public int getSourceContainer() {
            return sourceContainer;
        }

        public int getDestinationContainer() {
            return destinationContainer;
        }
    }
}
