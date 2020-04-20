package tutorial.ch05.exercise09;

import java.util.concurrent.locks.ReentrantLock;

public class AutoClosingReentrantLock extends ReentrantLock implements AutoCloseable {

    @Override
    public void close() throws Exception {
        super.unlock();
    }

    public static void main(String[] args) {
        try (AutoClosingReentrantLock lock = new AutoClosingReentrantLock()) {
            lock.lock();
            // Some exceptional method
            System.out.println("Hello World!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
