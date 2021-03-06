package ru.unn.agile.vector3d.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.Iterator;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.vector3d.model.Vector3D;

public class ViewModel {
    private final IntegerProperty activeTabIndex = new SimpleIntegerProperty();
    private final StringProperty vectorText = new SimpleStringProperty();
    private final StringProperty dotProductOperandText = new SimpleStringProperty();
    private final StringProperty crossProductOperandText = new SimpleStringProperty();
    private final StringProperty normResultText = new SimpleStringProperty();
    private final StringProperty normalizationResultText = new SimpleStringProperty();
    private final StringProperty dotProductResultText = new SimpleStringProperty();
    private final StringProperty crossProductResultText = new SimpleStringProperty();
    private final StringProperty statusText = new SimpleStringProperty();
    private final BooleanProperty buttonDisabled = new SimpleBooleanProperty();
    private final ObservableList<String> logsList = FXCollections.observableArrayList();
    private AbstractLogger.LoggerListener loggerLister;

    private AbstractLogger logger;

    public static class LogMessages {
        public static final String INIT
                = "Initialization started...";
        public static final String INIT_END
                = "Initialization completed";
        public static final String STATUS_CHANGED
                = "Status changed to ''{0}''";
        public static final String CALCULATION_AVAILABLE
                = "calculation available: {0}";
        public static final String FIRST_VECTOR_CHANGED
                = "Value of first vector changed from ''{0}'' to ''{1}''";
        public static final String SECOND_VECTOR_DOT_CHANGED
                = "Value of second vector (dot product) changed from ''{0}'' to ''{1}''";
        public static final String SECOND_VECTOR_CROSS_CHANGED
                = "Value of second vector (cross product) changed from ''{0}'' to ''{1}''";
        public static final String TAB_INDEX_CHANGED
                = "Tab index changed from ''{0}'' to ''{1}''";
        public static final String CALCULATE_PRESSED
                = "'Calculate' button was pressed";
        public static final String CALCULATE_END
                = "Calculation completed";
        public static final String NORM_CALCULATE
                = "Vector norm calculation";
        public static final String NORMALIZE_CALCULATE
                = "Normalized vector calculation";
        public static final String DOT_CALCULATE
                = "Dot product vectors calculation";
        public static final String CROSS_CALCULATE
                = "Cross product vectors calculation";
    }

    private static class FakeLogger extends AbstractLogger {

        @Override
        public void putLog(final String message, final Object... args) {
            //not implemented
        }

        @Override
        public Iterator<String> iterator() {
            return null;
        }
    }

    public abstract class ValueChangeListener implements ChangeListener<Object> {
        public abstract void logChange(ObservableValue<? extends Object> ov,
                                  Object oldValue, Object newValue);

        @Override
        public void changed(final ObservableValue<? extends Object> ov,
                            final Object oldValue, final Object newValue) {
            logChange(ov, oldValue, newValue);

            String strStatus = getStatus().toString();
            ViewModel.this.logger.putLog(LogMessages.STATUS_CHANGED, strStatus);
            ViewModel.this.statusText.set(strStatus);

            boolean btnDisabledVal = !canCalculate();
            ViewModel.this.logger.putLog(LogMessages.CALCULATION_AVAILABLE, !btnDisabledVal);
            ViewModel.this.buttonDisabled.set(btnDisabledVal);
        }
    }
    public ViewModel() {
        this(new FakeLogger());
    }

    public ViewModel(final AbstractLogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger can't be null");
        }
        this.logger = logger;
        init();
    }

    public void setLogger(final AbstractLogger logger) {
        this.logger = logger;
        this.logger.addListener(loggerLister);
    }

    public IntegerProperty activeTabIndexProperty() {
        return activeTabIndex;
    }

    public StringProperty vectorTextProperty() {
        return vectorText;
    }

    public StringProperty dotProductOperandTextProperty() {
        return dotProductOperandText;
    }

    public StringProperty crossProductOperandTextProperty() {
        return crossProductOperandText;
    }

    public StringProperty normResultTextProperty() {
        return normResultText;
    }

    public StringProperty normalizationResultTextProperty() {
        return normalizationResultText;
    }

    public StringProperty dotProductResultTextProperty() {
        return dotProductResultText;
    }

    public StringProperty crossProductResultTextProperty() {
        return crossProductResultText;
    }

    public StringProperty statusTextProperty() {
        return statusText;
    }

    public BooleanProperty buttonDisabledProperty() {
        return buttonDisabled;
    }

    public OperationTab getActiveTab() {
        return OperationTab.fromIndex(activeTabIndex.get());
    }

    public void setActiveTab(final OperationTab operation) {
        activeTabIndex.set(operation.ordinal());
    }

    public boolean isButtonDisabled() {
        return buttonDisabled.get();
    }

    public String getVectorText() {
        return vectorText.get();
    }

    public void setVectorText(final String text) {
        vectorText.set(text);
    }

    public String getDotProductOperandText() {
        return dotProductOperandText.get();
    }

    public void setDotProductOperandText(final String text) {
        dotProductOperandText.set(text);
    }

    public String getCrossProductOperandText() {
        return crossProductOperandText.get();
    }

    public void setCrossProductOperandText(final String text) {
        crossProductOperandText.set(text);
    }

    public String getNormResultText() {
        return normResultText.get();
    }

    public String getNormalizationResultText() {
        return normalizationResultText.get();
    }

    public String getDotProductResultText() {
        return dotProductResultText.get();
    }

    public String getCrossProductResultText() {
        return crossProductResultText.get();
    }

    public String getStatusText() {
        return statusText.get();
    }

    public AbstractLogger getLogger() {
        return logger;
    }

    public ObservableList<String> logsItems() {
        return logsList;
    }

    boolean validate(final String text) {
        String coordPattern = "[\\+-]?[0-9]+(\\.[0-9]+)?";
        String vectorPattern = " *" + coordPattern + ", *"
                                    + coordPattern + ", *"
                                    + coordPattern + " *";
        String vectorInBracketsPattern = " *\\(" + vectorPattern + "\\) *";
        return Pattern.matches("^" + vectorPattern + "$", text)
            || Pattern.matches("^" + vectorInBracketsPattern + "$", text);
    }

    Vector3D vectorFromString(final String text) {
        if (!validate(text)) {
            throw new IllegalArgumentException();
        }
        String[] coordTexts = text.replaceAll("[ \\+\\(\\)]+", "").split(",");
        double[] coords = new double[coordTexts.length];
        for (int i = 0; i < coordTexts.length; i++) {
            coords[i] = Double.parseDouble(coordTexts[i]);
        }
        return new Vector3D(coords);
    }

    boolean canCalculate() {
        return validate(vectorText.get())
            && (getActiveTab() != OperationTab.DOTPRODUCT
                || validate(dotProductOperandText.get()))
            && (getActiveTab() != OperationTab.CROSSPRODUCT
                || validate(crossProductOperandText.get()));
    }

    public Status getStatus() {
        Status status;
        if (vectorText.isEmpty().get()
            || (dotProductOperandText.isEmpty().get()
                && (getActiveTab() == OperationTab.DOTPRODUCT))
            || (crossProductOperandText.isEmpty().get()
                && (getActiveTab() == OperationTab.CROSSPRODUCT))) {
            status = Status.WAITING;
        } else if (canCalculate()) {
            status = Status.READY;
        } else {
            status = Status.BAD;
        }
        return status;
    }

    public void calculate() {
        logger.putLog(LogMessages.CALCULATE_PRESSED);

        Vector3D vector = vectorFromString(vectorText.get());
        switch (getActiveTab()) {
            case NORM:
                logger.putLog(LogMessages.NORM_CALCULATE);
                normResultText.set(new Double(vector.getNorm()).toString());
                break;
            case NORMALIZATION:
                logger.putLog(LogMessages.NORMALIZE_CALCULATE);
                normalizationResultText.set(vector.normalize().toString());
                break;
            case DOTPRODUCT:
                logger.putLog(LogMessages.DOT_CALCULATE);
                Vector3D dotProductOperand = vectorFromString(dotProductOperandText.get());
                Double dotProductResult = vector.dot(dotProductOperand);
                dotProductResultText.set(dotProductResult.toString());
                break;
            case CROSSPRODUCT:
                logger.putLog(LogMessages.CROSS_CALCULATE);
                Vector3D crossProductOperand = vectorFromString(crossProductOperandText.get());
                Vector3D crossProductResult = vector.cross(crossProductOperand);
                crossProductResultText.set(crossProductResult.toString());
                break;
            default:
                break;
        }
        logger.putLog(LogMessages.CALCULATE_END);
    }

    public enum OperationTab {
        NORM,
        NORMALIZATION,
        DOTPRODUCT,
        CROSSPRODUCT;

        public static OperationTab fromIndex(final int index) {
            if (index == OperationTab.NORM.ordinal()) {
                return OperationTab.NORM;
            } else if (index == OperationTab.NORMALIZATION.ordinal()) {
                return OperationTab.NORMALIZATION;
            } else if (index == OperationTab.DOTPRODUCT.ordinal()) {
                return OperationTab.DOTPRODUCT;
            } else if (index == OperationTab.CROSSPRODUCT.ordinal()) {
                return OperationTab.CROSSPRODUCT;
            } else {
                return null;
            }
        }
    };

    private void init() {
        loggerLister = new AbstractLogger.LoggerListener() {
            @Override
            public void onLogAdded(final String message) {
                ViewModel.this.logsItems().add(message);
            }
        };
        this.logger.addListener(loggerLister);

        this.logger.putLog(LogMessages.INIT);

        vectorText.addListener(new ValueChangeListener() {
            @Override
            public void logChange(final ObservableValue<? extends Object> ov,
                                  final Object oldValue, final Object newValue) {
                ViewModel.this.logger.putLog(LogMessages.FIRST_VECTOR_CHANGED,
                        oldValue, newValue);
            }
        });

        dotProductOperandText.addListener(new ValueChangeListener() {
            @Override
            public void logChange(final ObservableValue<? extends Object> ov,
                                  final Object oldValue, final Object newValue) {
                ViewModel.this.logger.putLog(LogMessages.SECOND_VECTOR_DOT_CHANGED,
                        oldValue, newValue);
            }
        });

        crossProductOperandText.addListener(new ValueChangeListener() {
            @Override
            public void logChange(final ObservableValue<? extends Object> ov,
                                  final Object oldValue, final Object newValue) {
                ViewModel.this.logger.putLog(LogMessages.SECOND_VECTOR_CROSS_CHANGED,
                        oldValue, newValue);
            }
        });

        activeTabIndex.addListener(new ValueChangeListener() {
            @Override
            public void logChange(final ObservableValue<? extends Object> ov,
                                  final Object oldValue, final Object newValue) {
                ViewModel.this.logger.putLog(LogMessages.TAB_INDEX_CHANGED, oldValue, newValue);
            }
        });

        activeTabIndex.set(OperationTab.NORM.ordinal());
        vectorText.set("");
        dotProductOperandText.set("");
        crossProductOperandText.set("");

        normResultText.set("");
        normalizationResultText.set("");
        dotProductResultText.set("");
        crossProductResultText.set("");
        this.logger.putLog(LogMessages.INIT_END);
    }
}

enum Status {
    WAITING("Пожалуйста, введите операнды."),
    BAD("Введены некорректные данные."),
    READY("К расчёту готов.");

    private final String message;

    Status(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
