package tutorial.ch03.exercise08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Implement the method void luckySort(ArrayList<String>
 * strings, Comparator<String> comp) that keeps calling
 * Collections.shuffle on the array list until the elements are in
 * increasing order, as determined by the comparator.
 */
public class LuckySorter {

    private LuckySorter() {
    }

    public static LuckySorter of() {
        return new LuckySorter();
    }

    public void luckySort(ArrayList<String> strings, Comparator<String> comparator) {
        ArrayList<String> orderedList = new ArrayList<>(strings);
        orderedList.sort(comparator);
        int iteration = 0;
        while(!orderedList.equals(strings)) {
            Collections.shuffle(strings);
            System.out.printf("%4d", iteration++);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>(List.of("4", "2", "3", "1"));
        System.out.println(strings);
        LuckySorter luckySorter = LuckySorter.of();
        luckySorter.luckySort(strings, String::compareTo);
        System.out.println(strings);
        luckySorter.luckySort(strings, Comparator.reverseOrder());
        System.out.println(strings);
    }

}
