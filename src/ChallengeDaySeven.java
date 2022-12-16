import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ChallengeDaySeven implements ChallengeDay {

    private static final String LIST_COMMAND = "$ ls";
    private static final String CD_INTO = "$ cd";
    private static final String MOVE_ONE_LEVEL_UP = "..";
    private static final String HOME_DIR = "/";
    private static final String DIR_DESC = "dir";
    private static final Integer SIZE_REQUIRED_FOR_UPDATE = 30000000;
    private static final Integer SIZE_OF_FILESYSTEM = 70000000;

    @Override
    public void execute() throws IOException {

        BufferedReader objReader = new BufferedReader(new FileReader("/Users/ronak/workspace/AdventOfCode/src/test.txt"));

        Directory homeDir = new Directory(HOME_DIR);
        Directory currentDir = homeDir;
        String text;
        List<Directory> directoryList = new ArrayList<>();
        directoryList.add(homeDir);

        while ((text = objReader.readLine()) != null) {
            if ("quit".equals(text)) {
                break;
            }

            if (text.equals(CD_INTO + " " + HOME_DIR)) {
                currentDir = homeDir;
                continue;
            }

            if (text.startsWith(CD_INTO)) {
                String dirName = text.split(" ")[2];
                if (dirName.equals(MOVE_ONE_LEVEL_UP)) {
                    currentDir = currentDir.getParentDirectory();
                }
                Directory childDir = currentDir.getChildDir(dirName);
                if (childDir != null) {
                    childDir.setParentDirectory(currentDir);
                    currentDir = childDir;
                }
                continue;
            }

            if (text.startsWith(DIR_DESC)) {
                String dirName = text.split(" ")[1];
                Directory childDir = new Directory(dirName);
                currentDir.insertSubDirectory(childDir);
                directoryList.add(childDir);
                continue;
            }

            if (Character.isDigit(text.charAt(0))) {
                String[] fileInfo = text.split(" ");
                int fileSize = Integer.parseInt(fileInfo[0]);
                String fileName = fileInfo[1];
                File file = new File(fileName, fileSize);
                currentDir.insertFile(file);
            }
        }

        long ans = 0;
        for (Directory dir : directoryList) {
            int size = dir.sizeOfDirectory();
            if (size <= 100000) {
                ans += size;
            }
        }

        int spaceRemaining = SIZE_OF_FILESYSTEM - homeDir.sizeOfDirectory();
        int spaceToAcquire = SIZE_REQUIRED_FOR_UPDATE - spaceRemaining;
        int min = Integer.MAX_VALUE;

        for (Directory dir : directoryList) {
            int size = dir.sizeOfDirectory();
            if (size >= spaceToAcquire) {
                if (min > size) {
                    min = size;
                }
            }
        }

        System.out.println(min);
    }

    static class Directory {

        private final List<File> listOfFiles;
        private final List<Directory> listOfSubDirectories;

        private Directory parentDirectory;

        private final String name;
        private Integer sizeOfDir;

        public Directory(String name) {
            listOfFiles = new ArrayList<>();
            listOfSubDirectories = new ArrayList<>();
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public List<File> getListOfFiles() {
            return listOfFiles;
        }

        public List<Directory> getListOfSubDirectories() {
            return listOfSubDirectories;
        }

        public void setParentDirectory(Directory parentDirectory) {
            this.parentDirectory = parentDirectory;
        }

        public Directory getParentDirectory() {
            return parentDirectory;
        }

        public Integer sizeOfDirectory() {
            if (sizeOfDir != null) {
                return sizeOfDir;
            }
            int totalSize = 0;

            for (File file : listOfFiles) {
                totalSize += file.getSize();
            }

            for (Directory subDir : listOfSubDirectories) {
                totalSize += subDir.sizeOfDirectory();
            }
            this.sizeOfDir = totalSize;
            return sizeOfDir;
        }

        public void insertSubDirectory(Directory subDir) {
            listOfSubDirectories.add(subDir);
        }

        public void insertFile(File file) {
            listOfFiles.add(file);
        }

        public Directory getChildDir(String dirName) {
            for (Directory directory : listOfSubDirectories) {
                if (directory.getName().equals(dirName)) {
                    return directory;
                }
            }
            return null;
        }
    }

    static class File {

        private final String name;
        private final int size;

        public File(String name, int size) {
            this.name = name;
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return size;
        }
    }
}
