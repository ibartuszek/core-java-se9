package tutorial.ch01;

public class StringExample {

    public static void main(String[] args) {
        testConcatenate();
        testSubString();
        testStringComparison();
        testStringParsing();
    }

    private static void testConcatenate() {
        int age = 34;
        System.out.println("Next year you will be " + age + 1);
        String names = String.join(", ", "Peter", "Paul", "Mary");
        System.out.println(names);
    }

    private static void testSubString() {
        String greeting = "Hello, World!";
        String location = greeting.substring(7, 12);
        System.out.println(location);
        String names = String.join("\t    ", "Peter", "Paul", "Mary");
        System.out.println(names);
        String[] result = names.split("\\s+ ");
        System.out.println(result[0]);
        System.out.println(result[1]);
        System.out.println(result[2]);
    }

    private static void testStringComparison() {
        String world = "WORLD";
        if ("world".equalsIgnoreCase(world)) {
            System.out.println("HELLO" + world);
        }
        System.out.println("world".compareTo("word"));
    }

    private static void testStringParsing() {
        System.out.println(Integer.toString(3, 2));
        System.out.println(Integer.parseInt("101", 2));
    }

}
