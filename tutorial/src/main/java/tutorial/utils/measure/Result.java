package tutorial.utils.measure;

import java.text.MessageFormat;

public final class Result<R> {

    private final R result;
    private final long elapsedTime;

    public Result(final R result, final long elapsedTime) {
        this.result = result;
        this.elapsedTime = elapsedTime;
    }

    public R getResult() {
        return result;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    @Override
    public String toString() {
        return MessageFormat.format( "Get the result takes: {0} nanoseconds and the result is: {1}", elapsedTime, result);
    }
}
