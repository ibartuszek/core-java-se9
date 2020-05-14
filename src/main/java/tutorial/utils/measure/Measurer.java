package tutorial.utils.measure;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Measurer {

    public <T> long measureVoid(final Consumer<T> function, T arg) {
        long start = System.currentTimeMillis();
        function.accept(arg);
        long end = System.currentTimeMillis();
        return end - start;
    }

    public <R> Result<R> measure(final Supplier<R> function) {
        long start = System.currentTimeMillis();
        R result = function.get();
        long end = System.currentTimeMillis();
        return new Result(result,end - start);
    }

    public <T, R> Result<R> measure(final Function<T, R> function, T arg) {
        long start = System.currentTimeMillis();
        R result = function.apply(arg);
        long end = System.currentTimeMillis();
        return new Result(result,end - start);
    }

    public <T, U, R> Result<R> measure(final BiFunction<T, U, R> function, T firstArg, U secondArg) {
        long start = System.currentTimeMillis();
        R result = function.apply(firstArg, secondArg);
        long end = System.currentTimeMillis();
        return new Result(result,end - start);
    }
}


