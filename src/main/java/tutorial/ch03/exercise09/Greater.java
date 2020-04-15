package tutorial.ch03.exercise09;

/**
 * 9
 * Implement a class Greeter that implements Runnable and whose run
 * method prints n copies of "Hello, " + target, where n and target
 * are set in the constructor. Construct two instances with different messages and
 * execute them concurrently in two threads
 */

/**
 * 10.
 * Implement methods
 * public static void runTogether(Runnable... tasks)
 * public static void runInOrder(Runnable... tasks)
 * The first method should run each task in a separate thread and then return. The
 * second method should run all methods in the current thread and return when
 * the last one has completed.
 */
public class Greater implements Runnable {

    private final String target;
    private final int n;

    private Greater(final String target, final int n) {
        this.target = target;
        this.n = n;
    }

    public static Greater of(final String target, final int n) {
        return new Greater(target, n);
    }

    @Override
    public void run() {
        int counter = n;
        while(counter > 0) {
            System.out.println("Hello, " + target);
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter--;
        }
    }

    public static void runTogether(final Runnable... tasks) {
        for (Runnable task : tasks) {
            Thread thread = new Thread(task);
            thread.start();
        }
    }

    public static void runInOrder(final Runnable... tasks) {
        for (Runnable task : tasks) {
            task.run();
        }
    }

    public static void main(String[] args) {
        Greater greaterOne = Greater.of("John", 7);
        Greater greaterTwo = Greater.of("Jack", 5);
        System.out.println("Run in order:");
        runInOrder(greaterOne, greaterTwo);
        System.out.println("Run together:");
        runTogether(greaterOne, greaterTwo);
    }
}
