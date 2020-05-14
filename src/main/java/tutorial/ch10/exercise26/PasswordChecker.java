package tutorial.ch10.exercise26;

import java.net.Authenticator;
import java.net.InetAddress;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 26
 * Write a method public static <T> CompletableFuture<T> repeat(Supplier<T> action, Predicate<T> until)
 * that asynchronously repeats the action until it produces a value that is accepted by the until function,
 * which should also run asynchronously. Test with a function that reads a java.net.PasswordAuthentication
 * from the console, and a function that simulates a validity check by sleeping for a second and then
 * checking that the password is "secret". Hint: Use recursion.
 */
public class PasswordChecker {

    private static final CustomAuthenticator AUTHENTICATOR = new CustomAuthenticator();

    public static <T> CompletableFuture<T> repeat(final Supplier<T> action, final Predicate<T> until) {
        return CompletableFuture.supplyAsync(action)
            .thenApply(authentication -> checkAuthentication(until, authentication))
            .exceptionally((throwable) -> handleInvalidAuthentication(action, until, throwable));
    }

    private static <T> T checkAuthentication(final Predicate<T> until, final T authentication) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (until.test(authentication)) {
            return authentication;
        } else {
            throw new IllegalArgumentException("Wrong username or password");
        }
    }

    private static <T> T handleInvalidAuthentication(final Supplier<T> action, final Predicate<T> until, final Throwable throwable) {
        System.out.println(throwable.getMessage());
        T result = null;
        try {
            result = repeat(action, until).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static PasswordAuthentication provideAuthentication() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please type your username:");
        String userName = in.nextLine();
        System.out.println("Please type your password:");
        String password = in.nextLine();
        return new PasswordAuthentication(userName, password.toCharArray());
    }

    private static boolean checkAuthentication(final PasswordAuthentication passwordAuthentication) {
        PasswordAuthentication other = AUTHENTICATOR.getPasswordAuthentication();
        return other.getUserName().equals(passwordAuthentication.getUserName())
            && Arrays.equals(other.getPassword(), passwordAuthentication.getPassword());
    }

    public static void main(String[] args) {
        CompletableFuture<PasswordAuthentication> repeat = PasswordChecker.repeat(PasswordChecker::provideAuthentication, PasswordChecker::checkAuthentication);
        while (!repeat.isDone()) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Welcome!");
    }

}

class CustomAuthenticator extends Authenticator {

    @Override
    public PasswordAuthentication requestPasswordAuthenticationInstance(final String host, final InetAddress addr, final int port,
        final String protocol, final String prompt, final String scheme, final URL url, final RequestorType reqType) {
        return super.requestPasswordAuthenticationInstance(host, addr, port, protocol, prompt, scheme, url, reqType);
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("user", "secret".toCharArray());
    }
}