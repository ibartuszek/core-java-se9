package tutorial.ch03.exercise04;

/**
 * 4.
 * Implement a static of method of the IntSequence class that yields a
 * sequence with the arguments. For example, IntSequence.of(3, 1, 4,
 * 1, 5, 9) yields a sequence with six values. Extra credit if you return an
 * instance of an anonymous inner class
 */

/**
 * 5.
 * Add a static method with the name constant of the IntSequence class
 * that yields an infinite constant sequence. For example,
 * IntSequence.constant(1) yields values 1 1 1..., ad infinitum.
 * Extra credit if you do this with a lambda expression.
 */
public class AnonymousIntSequence {

    interface IntSequence {

        boolean hasNext();

        int next();

        static IntSequence of(int... integers) {
            return new IntSequence() {

                int index = 0;

                @Override
                public boolean hasNext() {
                    return index < integers.length;
                }

                @Override
                public int next() {
                    return integers[index++];
                }
            };
        }

        static IntSequence constant(int integer) {
            return new IntSequence() {

                @Override
                public boolean hasNext() {
                    return true;
                }

                @Override
                public int next() {
                    return integer;
                }
            };
        }
    }

    public static void main(String[] args) {
        IntSequence intSequence = IntSequence.of(3, 1, 4, 1, 5, 9);
        IntSequence infiniteIntSequence = IntSequence.constant(1);
        while (intSequence.hasNext()) {
            System.out.printf("%4d", intSequence.next());
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.printf("%4d", infiniteIntSequence.next());
        }
    }

}
