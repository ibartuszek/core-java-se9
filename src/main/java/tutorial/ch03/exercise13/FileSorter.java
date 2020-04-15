package tutorial.ch03.exercise13;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Given an array of File objects, sort it so that directories come before files,
 * and within each group, elements are sorted by path name. Use a lambda
 * expression to specify the Comparator.
 */
public class FileSorter {

    public File[] sort(final File[] files) {
        return Arrays.stream(files)
            .sorted(Comparator
                .comparing(File::isFile)
                .thenComparing(File::getName))
            .toArray(File[]::new);
    }

    public static void main(String[] args) {
        String path = "C:/Projects/core-java-se9/src/main/java/tutorial/ch01";
        List<File> fileList = Arrays.asList(Objects.requireNonNull(new File(path).listFiles()));
        Collections.shuffle(fileList);
        File[] sortedList = new FileSorter()
            .sort(fileList.toArray(File[]::new));
        System.out.println("Before sorting:");
        System.out.println("######################################################################################");
        fileList.forEach(System.out::println);
        System.out.println();
        System.out.println("After sorting:");
        System.out.println("######################################################################################");
        Arrays.stream(sortedList).forEach(System.out::println);
    }

}
