package tutorial.ch04.exercise11;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 11.
 * Write the “Hello, World” program, using reflection to look up the out field
 * of java.lang.System and using invoke to call the println method.
 * 12.
 * Measure the performance difference between a regular method call and a
 * method call via reflection.
 */
public class HelloWorld {

    public void printMessageOnConsole(final String message) {
        try {
            Field out = System.class.getDeclaredField("out");
            Method method = out.getType().getMethod("println", java.lang.String.class);
            method.setAccessible(true);
            method.invoke(System.out, message);
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorld();
        long simple = testSimple();
        long invoke = testInvoke(helloWorld);
        System.out.printf("Simple method invocation takes: %d nanoseconds\n", simple);
        System.out.printf("Method invocation with reflection takes: %d nanoseconds", invoke);
    }

    private static long testInvoke(final HelloWorld helloWorld) {
        long start = System.nanoTime();
        for (int index = 0; index < 100; index++) {
            helloWorld.printMessageOnConsole("Hello World!");
        }
        long end = System.nanoTime();
        return end - start;
    }

    private static long testSimple() {
        long start = System.nanoTime();
        for (int index = 0; index < 100; index++) {
            System.out.println("Hello World");
        }
        long end = System.nanoTime();
        return end - start;
    }

}
