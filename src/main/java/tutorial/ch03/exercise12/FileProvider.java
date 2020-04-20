package tutorial.ch03.exercise12;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Using the list(FilenameFilter) method of the java.io.File
 * class, write a method that returns all files in a given directory with a given
 * extension. Use a lambda expression, not a FilenameFilter. Which
 * variable from the enclosing scope does it capture?
 */
public class FileProvider {

    private final String path;
    private final String extension;

    private FileProvider(final String path, final String extension) {
        this.path = path;
        this.extension = extension;
    }

    public static FileProvider of(final String path, final String extension) {
        return new FileProvider(path, extension);
    }

    private List<String> provide() {
        return getFiles().stream()
            .map(File::getName)
            .filter(fileName -> fileName.contains(extension))
            .sorted(String::compareTo)
            .collect(Collectors.toList());
    }

    private List<File> getFiles() {
        File root = new File(path);
        List<File> fileList = new ArrayList<>();
        List<File> directoryList = root.isDirectory() ? new ArrayList<>(List.of(root)) : Collections.emptyList();
        for (int index = 0; index < directoryList.size(); index++) {
            Optional.ofNullable(directoryList.get(index).listFiles())
                .ifPresent(files -> Arrays.stream(files)
                    .forEach(file -> sortFiles(file, directoryList, fileList)));
        }
        return fileList;
    }

    private void sortFiles(final File file, final List<File> directoryList, final List<File> fileList) {
        if (file.isDirectory()) {
            directoryList.add(file);
        } else {
            fileList.add(file);
        }
    }

    public static void main(String[] args) {
        String path = "src/main/java/";
        String extension = ".java";
        List<String> files = FileProvider.of(path, extension).provide();
        System.out.printf("All %s file of:%s \n", extension, path);
        files.forEach(System.out::println);
    }

}
