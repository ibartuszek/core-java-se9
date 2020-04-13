package tutorial.ch02.exercise16;

import java.util.LinkedList;
import java.util.List;

/**
 * 16:
 * Implement a class Queue, an unbounded queue of strings. Provide methods
 * add, adding at the tail, and remove, removing at the head of the queue. Store
 * elements as a linked list of nodes. Make Node a nested class. Should it be
 * static or not
 * 17:
 * Provide an iterator—an object that yields the elements of the queue in turn—
 * for the queue of the preceding class. Make Iterator a nested class with
 * methods next and hasNext. Provide a method iterator() of the
 * Queue class that yields a Queue.Iterator. Should Iterator be static
 * or not?
 */
public class Queue {

    public static class Node {

        private String name;

        public Node(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Node{name='" + name + "'}";
        }
    }

    private class Iterator {

        private int index = 0;

        public boolean hasNext(){
            return index < nodeList.size();
        }

        public Node next() {
            return nodeList.get(index++);
        }

    }

    private List<Node> nodeList = new LinkedList<>();

    public void add(final Node node) {
        nodeList.add(node);
    }

    public Node remove() {
        return nodeList.remove(0);
    }

    public Iterator iterator() {
        return new Iterator();
    }

    @Override
    public String toString() {
        return "Queue{nodeList=" + nodeList + "}";
    }

    public static void main(String[] args) {
        Queue queue = new Queue();
        queue.add(new Queue.Node("First node"));
        queue.add(new Queue.Node("Second node"));
        queue.add(new Queue.Node("Third node"));
        System.out.println(queue.remove());
        System.out.println(queue);
        Iterator queueIterator = queue.iterator();
        while(queueIterator.hasNext()) {
            System.out.println(queueIterator.next());
        }
    }

}
