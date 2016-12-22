package ru.unn.agile.matrixoperations.viewmodel;

import java.util.List;

public interface ILogger {
    void log(String message);
    List<String> getLog();
}
