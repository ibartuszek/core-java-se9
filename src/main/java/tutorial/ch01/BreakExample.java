package tutorial.ch01;

public class BreakExample {

    public static void main(String[] args) {
        int a = 0;
        outer:
        while(true) {
            System.out.println(a++);
            if (a > 10) break outer;
        }
        System.out.println("End of the while with \'outer\'");

        exit: {
            System.out.println("Hello " + a++);
            if (a > 11) break exit;
            System.out.println("Never reached");
        }

    }

}
