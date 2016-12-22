package ru.unn.agile.matrixoperations.viewmodel;

import java.util.ArrayList;
import java.util.List;

class TestingLogger implements ILogger {
    private List<String> messages = new ArrayList<>();

    @Override
    public void log(final String message) {
        messages.add(message);
    }

    @Override
    public List<String> getLog() {
        return messages;
    }
}
