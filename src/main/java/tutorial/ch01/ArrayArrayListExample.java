package tutorial.ch01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayArrayListExample {

    public static void main(String[] args) {
        int[] integers = {1, 2, 3};
        int[] copy = Arrays.copyOf(integers, integers.length);
        copy[0] = 2;
        System.out.println(copy[0] + "!=" + integers[0]);
        ArrayList<Integer> integerList = new ArrayList<>(List.of(4, 5, 6));
        ArrayList<Integer> copyIntegerList = new ArrayList<>(integerList);
        copyIntegerList.set(0, 5);
        System.out.println(copyIntegerList.get(0) + "!=" + integerList.get(0));
        ArrayList<String> nameList = new ArrayList<>(List.of("John", "Jane"));
        String[] names = nameList.toArray(new String[0]);
        System.out.println("Hello " + names[0]);
        Collections.sort(nameList);
        Arrays.sort(names);

        // Multithreading:
        Arrays.parallelSort(names);
        System.out.println(Arrays.toString(names));

        // Useful functions on lists
        Collections.reverse(nameList);
        Collections.shuffle(nameList);
        System.out.println(nameList);
    }

}
