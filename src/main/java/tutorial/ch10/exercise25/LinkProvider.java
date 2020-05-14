package tutorial.ch10.exercise25;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 25
 * Write a program that asks the user for a URL, reads the web page at that
 * URL, and displays all the links. Use a CompletableFuture for each step.
 * Don’t call get.
 */
public class LinkProvider {

    private final String regex = "(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public List<URL> provide(final String target) {
        CompletableFuture<Stream<URL>> future = getFuture(target);
        List<URL> resultList = createList(future);
        waitForFuture(future);
        return resultList;
    }

    private CompletableFuture<Stream<URL>> getFuture(final String target) {
        return CompletableFuture.supplyAsync(() -> getContent(target))
             .thenApply(this::getUrlMatches)
            .thenApply(stringStream -> stringStream.map(this::convert));
    }

    private String getContent(final String target) {
        StringBuilder builder = new StringBuilder();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(target))
                .GET()
                .build();
            CompletableFuture<HttpResponse<String>> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            future.whenComplete((response, throwable) -> {
                if (throwable == null) {
                    builder.append(response.body());
                } else {
                    throw new RuntimeException(throwable);
                }
            });
            waitForFuture(future);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    private void waitForFuture(final Future future) {
        while (!future.isDone()) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Stream<String> getUrlMatches(final String content) {
        Matcher matcher = Pattern.compile(regex).matcher(content);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list.stream();
    }

    private URL convert(final String target) {
        URL url;
        try {
            url = new URL(target);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return url;
    }

    private List<URL> createList(final CompletableFuture<Stream<URL>> future) {
        List<URL> resultList = new CopyOnWriteArrayList<>();
        future.whenComplete((result, throwable) -> {
            if (throwable == null) {
                result.forEach(resultList::add);
            } else {
                throw new RuntimeException(throwable);
            }
        });
        return resultList;
    }

    public static void main(String[] args) {
        System.out.println(new LinkProvider().provide("https://index.hu"));
    }

}
