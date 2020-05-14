package tutorial.ch10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WordFinderUtility {

    public List<Path> initFileList(final Path directory) {
        List<Path> fileList = new ArrayList<>();
        addFiles(directory, fileList);
        return fileList;
    }

    private void addFiles(final Path directory, final List<Path> fileList) {
        try {
            Files.list(directory)
                .forEach(path -> {
                    if (path.toFile().isDirectory()) {
                        addFiles(path, fileList);
                    } else {
                        fileList.add(path);
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
