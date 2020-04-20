package tutorial.ch05.exercise14;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * 14. Implement and test a log record filter that filters out log records containing
 * bad words such as sex, drugs, and C++.
 */
public class LoggerFilter {

    private final Logger logger;
    private final List<String> bannedWordList;

    private LoggerFilter(final Logger logger, final List<String> bannedWordList) {
        this.logger = logger;
        this.bannedWordList = transformBannedWords(bannedWordList);
    }

    private List<String> transformBannedWords(final List<String> bannedWordList) {
        return bannedWordList.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
    }

    public static LoggerFilter getLogger(final Logger logger, final List<String> bannedWordList) {
        return new LoggerFilter(logger, bannedWordList);
    }

    public void log(final Level level, final String msg) {
        if (notContain(msg)) {
            logger.log(level, msg);
        }
    }

    private boolean notContain(final String msg) {
        return bannedWordList.stream()
            .filter(msg.toUpperCase()::contains)
            .findAny()
            .isEmpty();
    }

    public static void main(String[] args) throws InterruptedException {
        List<String> bannedWordList = List.of("sex", "drugs", "c++");
        Logger logger = Logger.getLogger("FilteredLogger");
        LoggerFilter loggerFilter = LoggerFilter.getLogger(logger, bannedWordList);
        List<String> messageList = getMessageList();
        System.out.println("Logger:");
        messageList.forEach(msg -> logger.log(Level.INFO, msg));
        Thread.sleep(1000L);
        System.out.println("Filtered Logger:");
        messageList.forEach(msg -> loggerFilter.log(Level.INFO, msg));
    }

    private static List<String> getMessageList() {
        return List.of("First message",
            "Banned message: sex",
            "Banned message: drugs",
            "Banned message: c++",
            "Second message");
    }

}
