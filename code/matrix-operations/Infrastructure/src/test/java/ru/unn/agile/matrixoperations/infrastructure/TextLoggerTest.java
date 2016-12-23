package ru.unn.agile.matrixoperations.infrastructure;

import ru.unn.agile.matrixoperations.viewmodel.ILogger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.*;

public class TextLoggerTest {
    private ILogger logger;
    private static final String FILENAME = "./test.log";

    @Before
    public void setUp() {
        logger = new TextLogger(FILENAME);
    }

    @After
    public void tearDown() {
        if (new File(FILENAME).isFile()) {
            try {
                Files.delete(new File(FILENAME).toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger = null;
    }

    @Test
    public void canLogMessage() {
        logger.log("Some message");
    }

    @Test
    public void canGetLog() {
        logger.getLog();
    }

    @Test
    public void checkMessageSavingCorrect() {
        String testMessage = "test message";

        logger.log(testMessage);
        String storedMessage = logger.getLog().get(0);

        assertTrue(storedMessage.endsWith(testMessage));
    }

    @Test
    public void checkSeveralMessagesSavingCorrect() {
        String[] testMessages = new String[]{
                "test message", "another test message", "message one more time"};

        for (String message : testMessages) {
            logger.log(message);
        }
        List<String> storedMessages = logger.getLog();

        for (int i = 0; i < storedMessages.size(); i++) {
            testMessages[i].endsWith(storedMessages.get(i));
        }
    }

    @Test
    public void canCreateLoggerWithFilename() {
        String testFilename = "test filename.log";
        logger = new TextLogger(testFilename);

        assertNotNull(logger);
    }

    @Test
    public void doesLogContainTimestamp() {
        String testMessage = "test message";

        logger.log(testMessage);
        String loggedMsg = logger.getLog().get(0);

        assertTrue(loggedMsg.matches("2[0-2][0-9][0-9]-[0-1][0-9]-[0-3][0-9] "
                                     + "[0-2][0-9]:[0-5][0-9]:[0-5][0-9].*"));

    }
}
