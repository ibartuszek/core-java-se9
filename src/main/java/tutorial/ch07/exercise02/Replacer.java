package tutorial.ch07.exercise02;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

/**
 * 02
 * In an array list of strings, make each string uppercase. Do this with (a) an
 * iterator, (b) a loop over the index values, and (c) the replaceAll method.
 */
public class Replacer {

    public void replaceWithIterator(final ArrayList<String> list) {
        ListIterator<String> iterator = list.listIterator();
        while(iterator.hasNext()) {
            String current = iterator.next();
            iterator.set(current.toUpperCase());
        }
    }

    public void replaceWithIndexes(final ArrayList<String> list) {
        for(int index = 0; index < list.size(); index++) {
            list.set(index, list.get(index).toUpperCase());
        }
    }

    public void replaceWithReplaceAll(final ArrayList<String> list) {
        list.replaceAll(String::toUpperCase);
    }

    public static void main(String[] args) {
        Replacer replacer = new Replacer();
        testReplace(replacer::replaceWithIterator, "with iterator");
        testReplace(replacer::replaceWithIndexes, "with indexed for loop");
        testReplace(replacer::replaceWithReplaceAll, "with replaceAll");
    }

    private static void testReplace(final Consumer<ArrayList<String>> replace, final String message) {
        ArrayList<String> list = new ArrayList<>(List.of("First", "Second", "Third", "Forth", "Fifth"));
        replace.accept(list);
        System.out.println(MessageFormat.format("new list {0}: {1}", message, list));
    }

}
