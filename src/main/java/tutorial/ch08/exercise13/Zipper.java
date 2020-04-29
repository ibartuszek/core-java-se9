package tutorial.ch08.exercise13;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 13
 * Write a method public static <T> Stream<T> zip(Stream<T>
 * first, Stream<T> second) that alternates elements from the streams
 * first and second (or null if the stream whose turn it is runs out of
 * elements).
 */
public class Zipper<T> {

    public Stream<T> zipWithWhile(final Stream<T> first, final Stream<T> second) {
        Iterator<T> firstIterator = first.iterator();
        Iterator<T> secondIterator = second.iterator();
        List<T> result = new ArrayList<>();
        while (firstIterator.hasNext() || secondIterator.hasNext()) {
            result.add(getNextElement(firstIterator));
            result.add(getNextElement(secondIterator));
        }
        return result.stream();
    }

    public Stream<T> zipWithIterate(final Stream<T> first, final Stream<T> second) {
        Iterator<T> firstIterator = first.iterator();
        Iterator<T> secondIterator = second.iterator();
        return Stream.iterate(
            new ElementWrapper<T>(firstIterator.next()), this::hasNextElement, (element) -> !element.isFirstStreamOrigin()
                    ? new ElementWrapper<>(getNextElement(firstIterator), element, true)
                    : new ElementWrapper<>(getNextElement(secondIterator), element, false)
            ).map(ElementWrapper::getElement);
    }

    private T getNextElement(final Iterator<T> iterator) {
        T result;
        if (iterator.hasNext()) {
            result = iterator.next();
        } else {
            result = null;
        }
        return result;
    }

    private boolean hasNextElement(final ElementWrapper<T> elementWrapper) {
        return elementWrapper.getElement() != null || elementWrapper.getPreviousElement().getElement() != null;
    }

    static class ElementWrapper<T> {

        private boolean firstStreamOrigin;
        private T element;
        private ElementWrapper<T> previousElement;

        public ElementWrapper(final T element) {
            this.firstStreamOrigin = true;
            this.element = element;
            this.previousElement = null;
        }

        public ElementWrapper(final T element, final ElementWrapper<T> previousElement, final boolean firstStreamOrigin) {
            this.firstStreamOrigin = firstStreamOrigin;
            this.element = element;
            this.previousElement = previousElement;
        }

        public boolean isFirstStreamOrigin() {
            return firstStreamOrigin;
        }

        public T getElement() {
            return element;
        }

        public ElementWrapper<T> getPreviousElement() {
            return previousElement;
        }

        @Override
        public String toString() {
            return element != null ? element.toString() : null;
        }

    }

    public static void main(String[] args) {
        Stream<Integer> first = Stream.of(1, 3, 5, 7, 9);
        Stream<Integer> second = Stream.of(10, 8, 6, 4, 2, 0, -2);
        System.out.println(new Zipper<Integer>().zipWithIterate(first, second).collect(Collectors.toList()));
    }

}
