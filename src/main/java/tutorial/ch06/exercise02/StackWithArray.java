package tutorial.ch06.exercise02;

import java.util.List;
import java.util.function.IntFunction;

/**
 * Reimplement the Stack<E> class, using an array to hold the elements. If
 * necessary, grow the array in the push method. Provide two solutions, one
 * with an E[] array and one with an Object[] array. Both solutions should
 * compile without warnings. Which do you prefer, and why?
 */
public class StackWithArray {

    class StackWithArrayOfObjects<E> {
        private static final int MIN_SIZE = 10;
        private int size = MIN_SIZE;
        private int index = 0;
        private Object[] store;

        public StackWithArrayOfObjects() {
            store = new Object[size];
        }

        public void push(final E newElement) {
            if (size - 1 >= index) {
                size *= 2;
                initNewArray();
            }
            index++;
            store[index] = newElement;
        }

        @SuppressWarnings("unchecked")
        public E pop() {
            E current = (E) store[index--];
            if (index < size / 2 && index >= MIN_SIZE) {
                size /= 2;
                initNewArray();
            }
            return current;
        }

        private void initNewArray() {
            Object[] newStore = new Object[size];
            for(int i = 0; i <= index; i++) {
                newStore[i] = store[i];
            }
            store = newStore;
        }

        public boolean isEmpty() {
            return index <= 0;
        }

    }

    class StackWithArrayOfTypeVariable<E> {
        private static final int MIN_SIZE = 10;
        private int size = MIN_SIZE;
        private int index = 0;
        private E[] store;
        private IntFunction<E[]> constructor;

        public StackWithArrayOfTypeVariable(final IntFunction<E[]> constructor) {
            this.constructor = constructor;
            store = this.constructor.apply(size);
        }

        public void push(final E newElement) {
            if (size - 1 >= index) {
                size *= 2;
                initNewArray();
            }
            index++;
            store[index] = newElement;
        }

        public E pop() {
            E current = store[index--];
            if (index < size / 2 && index >= MIN_SIZE) {
                size /= 2;
                initNewArray();
            }
            return current;
        }

        private void initNewArray() {
            E[] newStore = this.constructor.apply(size);
            for(int i = 0; i <= index; i++) {
                newStore[i] = store[i];
            }
            store = newStore;
        }

        public boolean isEmpty() {
            return index <= 0;
        }

    }

    public static void main(String[] args) {
        System.out.println("Stack with array of objects:");
        StackWithArray.StackWithArrayOfObjects<Integer> stackWithArrayOfObjects = new StackWithArray().new StackWithArrayOfObjects<>();
        List.of(1, 2, 3, 4, 5).forEach(stackWithArrayOfObjects::push);
        while(!stackWithArrayOfObjects.isEmpty()) {
            System.out.printf("%s, ", stackWithArrayOfObjects.pop());
        }
        System.out.println();
        System.out.println("Stack with array of typeVariable:");
        StackWithArray.StackWithArrayOfTypeVariable<Integer> stackWithArrayOfTypeVariables = new StackWithArray()
            .new StackWithArrayOfTypeVariable<>(Integer[]::new);
        List.of(1, 2, 3, 4, 5).forEach(stackWithArrayOfTypeVariables::push);
        while(!stackWithArrayOfTypeVariables.isEmpty()) {
            System.out.printf("%s, ", stackWithArrayOfTypeVariables.pop());
        }
    }
}
