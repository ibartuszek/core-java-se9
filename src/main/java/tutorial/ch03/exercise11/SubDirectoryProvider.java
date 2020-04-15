package tutorial.ch03.exercise11;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 11.
 * Using the listFiles(FileFilter) and isDirectory methods of
 * the java.io.File class, write a method that returns all subdirectories of a
 * given directory. Use a lambda expression instead of a FileFilter object.
 * Repeat with a method expression and an anonymous inner class.
 */

/**
 * 12.
 * Using the list(FilenameFilter) method of the java.io.File
 * class, write a method that returns all files in a given directory with a given
 * extension. Use a lambda expression, not a FilenameFilter. Which
 * variable from the enclosing scope does it capture?
 */
public class SubDirectoryProvider {

    private final String path;
    private final FilteredFileProvider filteredFileProvider;

    private SubDirectoryProvider(final String path, final FilteredFileProvider filteredFileProvider) {
        this.path = path;
        this.filteredFileProvider = filteredFileProvider;
    }

    public static SubDirectoryProvider of(final String path, final FilteredFileProvider filteredFileProvider) {
        return new SubDirectoryProvider(path, filteredFileProvider);
    }

    public List<String> provide() {
        return initStore().stream()
            .map(File::getPath)
            .sorted(String::compareTo)
            .collect(Collectors.toList());
    }

    private List<File> initStore() {
        File root = new File(path);
        List<File> store = root.isDirectory() ? new ArrayList<>(List.of(root)) : Collections.emptyList();
        for (int index = 0; index < store.size(); index++) {
            File[] files = filteredFileProvider.getFiles(store, index);
            if (files != null) {
                store.addAll(Arrays.asList(files));
            }
        }
        return store;
    }

    public static void main(String[] args) {
        String path = "C:/Projects/core-java-se9/src/main/java/";
        List<String> subdirectories = SubDirectoryProvider.of(path, new FilteredFileProvider.WithAnonymousClass()).provide();;
        List<String> subdirectories2 = SubDirectoryProvider.of(path, new FilteredFileProvider.WithLambdaExpression()).provide();;
        List<String> subdirectories3 = SubDirectoryProvider.of(path, new FilteredFileProvider.WithMethodReference()).provide();;
        System.out.println("All subdirectories of: " + path);
        subdirectories.forEach(System.out::println);
    }

}
