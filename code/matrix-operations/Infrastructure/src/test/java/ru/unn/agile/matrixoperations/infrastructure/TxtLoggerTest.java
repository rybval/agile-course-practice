package ru.unn.agile.matrixoperations.infrastructure;

import ru.unn.agile.matrixoperations.viewmodel.ILogger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

public class TxtLoggerTest {
    private ILogger logger;

    @Before
    public void setUp() {
        logger = new TxtLogger();
    }

    @After
    public void tearDown() {
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
}
