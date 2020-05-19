package tutorial.ch04;

import java.util.ArrayList;

public class InheritenceTest {

    public static void main(String[] args) {
        // Anonymous subclass example:
        ArrayList<String> names = new ArrayList<String>(100) {
            public void add(int index, String element) {
                super.add(index, element);
                System.out.printf("Adding %s at %d\n", element, index);
            }
        };
        names.add(0, "Peter");
    }

}
