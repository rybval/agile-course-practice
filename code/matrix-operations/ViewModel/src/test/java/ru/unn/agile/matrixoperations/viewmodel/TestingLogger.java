package ru.unn.agile.matrixoperations.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class TestingLogger implements ILogger {
    private ArrayList<String> log;

    @Override
    public void log(final String message) {
        log.add(message);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
