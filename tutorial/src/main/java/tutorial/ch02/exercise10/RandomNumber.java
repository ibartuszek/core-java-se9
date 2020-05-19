package tutorial.ch02.exercise10;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * In the RandomNumbers class, provide two static methods
 * randomElement that get a random element from an array or array list of
 * integers. (Return zero if the array or array list is empty.) Why couldn't you
 * make these methods into instance methods of int[] or
 * ArrayList<Integer>?
 */
public class RandomNumber {

    public static int randomElement(final int[] array) {
        int randomNumber = 0;
        if (array != null && array.length > 0) {
            randomNumber = array[new Random().nextInt(array.length)];
        }
        return randomNumber;
    }

    public static int randomElement(final List<Integer> list) {
        int randomNumber = 0;
        if (list != null && list.size() > 0) {
            randomNumber = list.get(new Random().nextInt(list.size()));
        }
        return randomNumber;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5};
        int[] emptyArray = {};
        List<Integer> list = Arrays.stream(array).boxed().collect(Collectors.toList());
        List<Integer> emptyList = Collections.emptyList();
        System.out.println(randomElement(array));
        System.out.println(randomElement(emptyArray));
        System.out.println(randomElement(list));
        System.out.println(randomElement(emptyList));
    }

}
