package ru.unn.agile.matrixoperations.infrastructure;

import ru.unn.agile.matrixoperations.viewmodel.ILogger;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TextLogger implements ILogger {
    private static final String DEFAULT_FILENAME = "./user_actions.log";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final File file;

    TextLogger(final String filename) {
        file = new File(filename);
    }

    TextLogger() {
      this(DEFAULT_FILENAME);
    }

    private String timestamp() {
        return
         new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(Calendar.getInstance().getTime());
    }

    @Override
    public void log(final String message) {
        if (!file.exists()) {
            try {
                Files.createFile(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (file.isFile()) {
            try {
                FileWriter writer = new FileWriter(file.getPath());
                writer.append(timestamp() + " > " + message + System.getProperty("line.separator"));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<String> getLog() {
        List<String> out = new ArrayList<>();
        if (file.isFile()) {
            try {
                out = Files.readAllLines(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out;
    }
}
