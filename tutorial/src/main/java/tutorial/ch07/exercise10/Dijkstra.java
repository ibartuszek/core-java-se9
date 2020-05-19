package tutorial.ch07.exercise10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * 10
 * Implement Dijkstra's algorithm to find the shortest paths in a network of
 * cities, some of which are connected by roads. (For a description, check out
 * your favorite book on algorithms or the Wikipedia article.) Use a helper class
 * Neighbor that stores the name of a neighboring city and the distance.
 * Represent the graph as a map from cities to sets of neighbors. Use a
 * PriorityQueue<Neighbor> in the algorithm
 */
public class Dijkstra {

    private static final String SEPARATOR = ",";

    private final Set<Path> paths = new TreeSet<>();
    private final Map<String, Set<Path>> cities = new HashMap<>();

    public Dijkstra(final String path) {
        List<String> cityNames = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            Arrays.stream(scanner.nextLine().split(SEPARATOR))
                .forEach(word -> cityNames.add(word.trim()));
            while (scanner.hasNextLine()) {
                addNewPath(scanner.nextLine().split(SEPARATOR), cityNames);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addNewPath(final String[] elements, final List<String> cityNames) {
        String firstCity = elements[0].trim();
        Set<Path> pathsOfCity = new TreeSet<>();
        if (cityNames.contains(firstCity)) {
            for (int index = 1; index < cityNames.size(); index++) {
                String secondCity = cityNames.get(index - 1);
                double distance = getDistance(elements[index].trim());
                if (distance > 0.0D) {
                    pathsOfCity.add(new Path(firstCity, secondCity, distance));
                }
            }
            paths.addAll(pathsOfCity);
            cities.put(firstCity, pathsOfCity);
        }
    }

    private double getDistance(final String distanceAsString) {
        double distance = 0.0D;
        try {
            distance = Double.parseDouble(distanceAsString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return distance;
    }

    public Map<String, Neighbour> search(final String cityName) {
        if (!cities.containsKey(cityName)) {
            throw new IllegalArgumentException("This city is not on the map!");
        }
        Neighbour previous = new Neighbour(cityName);
        Map<String, Neighbour> result = new HashMap<>(Map.of(cityName, previous));
        PriorityQueue<Neighbour> queue = new PriorityQueue<>();
        addNeighboursToQueue(previous, queue, result);
        while (!queue.isEmpty()) {
            addNeighboursToQueue(queue.poll(), queue, result);
        }
        return result;
    }

    private void addNeighboursToQueue(final Neighbour previous, final PriorityQueue<Neighbour> queue, final Map<String, Neighbour> result) {
        for (Path path : cities.get(previous.getName())) {
            if (path.contains(previous.getName())) {
                String otherCity = path.getTheOtherCity(previous.getName());
                Neighbour current = new Neighbour(otherCity, previous.getDistance() + path.getDistance(), previous);
                if (!result.containsKey(otherCity) || result.get(otherCity).getDistance() > current.getDistance()) {
                    queue.add(current);
                    result.put(otherCity, current);
                }
            }
        }
    }

    public static void main(String[] args) {
        Dijkstra dijkstra = new Dijkstra("src/main/java/tutorial/ch07/exercise10/cities");
        Map<String, Neighbour> shortestPaths = dijkstra.search("Budapest");
        shortestPaths.values().forEach(System.out::println);
    }

    class Path implements Comparable<Path>{

        private final TreeSet<String> cities;
        private final double distance;

        public Path(final String firstCity, final String secondCity, final double distance) {
            this.cities = new TreeSet<>(String::compareTo);
            cities.add(firstCity);
            cities.add(secondCity);
            this.distance = distance;
        }

        public double getDistance() {
            return distance;
        }

        public boolean contains(final String city) {
            return cities.contains(city);
        }

        public String getTheOtherCity(final String city) {
            String otherCity = null;
            if (contains(city)) {
                for (String c : cities) {
                    if (!c.equals(city)) {
                        otherCity = c;
                    }
                }
            }
            return otherCity;
        }

        @Override
        public int compareTo(final Path path) {
            int result = cities.first().compareTo(path.cities.first());
            if (result == 0) {
                result = cities.last().compareTo(path.cities.last());
            }
            return result;
        }

        @Override
        public String toString() {
            return "Path{"
                + "cities=" + cities
                + ", distance=" + distance
                + '}';
        }
    }

    class Neighbour implements Comparable<Neighbour> {

        private final String name;
        private double distance;
        private Neighbour previous;

        public Neighbour(final String name) {
            this.name = name;
        }

        public Neighbour(final String name, final double distance, final Neighbour previous) {
            this(name);
            this.distance = distance;
            this.previous = previous;
        }

        public String getName() {
            return name;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(final double distance) {
            this.distance = distance;
        }

        public Neighbour getPrevious() {
            return previous;
        }

        public void setPrevious(final Neighbour previous) {
            this.previous = previous;
        }

        @Override
        public int compareTo(final Neighbour neighbour) {
            return Double.compare(distance, neighbour.distance);
        }

        @Override
        public String toString() {
            return "Neighbour{"
                + "name='" + name + '\''
                + ", distance=" + distance
                + ", previous=" + previous +
                '}';
        }
    }

}
