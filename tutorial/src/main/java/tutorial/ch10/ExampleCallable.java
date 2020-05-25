package tutorial.ch10;

import java.util.concurrent.Callable;

class ExampleCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        String threadName = Thread.currentThread().getName();
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            System.err.println(threadName + " is interrupted.");
            throw e;
        }
        System.out.println(threadName + " is returning.");
        return threadName;
    }

}
