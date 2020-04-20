package tutorial.ch05.exercise15;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * 15. Implement and test a log record formatter that produces an HTML file.
 */
public class HtmlLogger extends Formatter {

    private static final int LIMIT = 1000;
    private static final int COUNT = 10;

    private final Logger logger;

    private HtmlLogger(final String name, final Handler handler) {
        this.logger = Logger.getLogger(name);
        logger.addHandler(handler);
    }

    public static HtmlLogger getLogger(final String path, final String name) throws IOException {
        Handler handler = new FileHandler(path, LIMIT, COUNT, true);
        HtmlLogger htmlLogger = new HtmlLogger(name, handler);
        handler.setFormatter(htmlLogger);
        return htmlLogger;
    }

    public void log(final Level level, final String msg) {
        logger.log(level, msg);
    }

    @Override
    public String format(final LogRecord logRecord) {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(logRecord.getMillis()/1000, 0, ZoneOffset.UTC);
        return MessageFormat.format("<div class={0}>{1}:{2}</div>", logRecord.getLevel(), localDateTime, logRecord.getMessage());
    }

    public static void main(String[] args) throws IOException {
        HtmlLogger htmlLogger = HtmlLogger.getLogger("src/main/java/tutorial/ch05/exercise15/log", "htmlLogger");
        htmlLogger.log(Level.INFO, "test");
    }

}
