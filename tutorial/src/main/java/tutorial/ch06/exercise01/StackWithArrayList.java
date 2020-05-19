package tutorial.ch06.exercise01;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement a class Stack<E> that manages an array list of elements of type
 * E. Provide methods push, pop, and isEmpty.
 */
public class StackWithArrayList<E> {

    private final List<E> list;

    public StackWithArrayList() {
        this.list = new ArrayList<>();
    }

    public void push(final E newElement) {
        list.add(newElement);
    }

    public E pop() {
        return list.remove(list.size() - 1);
    }

    public boolean isEmpty() {
        return list.size() <= 0;
    }

    public static void main(String[] args) {
        StackWithArrayList<Integer> stack = new StackWithArrayList<>();
        List.of(1, 2, 3, 4, 5).forEach(stack::push);
        while(!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}
