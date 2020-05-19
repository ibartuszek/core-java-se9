package tutorial.ch10.exercise02;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Random;

import tutorial.utils.measure.Measurer;

/**
 * 02
 * How large does an array have to be for Arrays.parallelSort to be
 * faster than Arrays.sort on your computer?
 */
public class ArraySorter {

    public static void main(String[] args) {
        int size = 200000;
        int numberOfPrintedElements = 10;
        Measurer measurer = new Measurer();
        int[] firstArray = initArray(size);
        int[] secondArray = Arrays.copyOf(firstArray, firstArray.length);
        printFirstElements(numberOfPrintedElements, firstArray);
        System.out.println(MessageFormat.format( "Sort without parallel execution an array with {0} elements takes: {1} nanoseconds.", size,
            measurer.measureVoid(Arrays::sort, firstArray)));
        System.out.println(MessageFormat.format( "Sort with parallel execution an array with {0} elements takes: {1} nanoseconds.", size,
            measurer.measureVoid(Arrays::parallelSort, secondArray)));
        printFirstElements(numberOfPrintedElements, firstArray);
    }

    private static int[] initArray(final int size) {
        int[] result = new int[size];
        Random random = new Random();
        for (int index = 0; index < size; index++) {
            result[index] = random.nextInt();
        }
        return result;
    }

    private static void printFirstElements(final int numberOfPrintedElements, final int[] firstArray) {
        for (int index = 0; index < numberOfPrintedElements; index++) {
            System.out.print(firstArray[index] + ", ");
        }
        System.out.println("...");
    }

}
