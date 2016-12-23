package ru.unn.agile.matrixoperations.view;

import ru.unn.agile.matrixoperations.infrastructure.TextLogger;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import ru.unn.agile.matrixoperations.model.Matrix;
import ru.unn.agile.matrixoperations.viewmodel.ILogger;
import ru.unn.agile.matrixoperations.viewmodel.MatrixViewModel;
import ru.unn.agile.matrixoperations.viewmodel.ViewModel;

public class MatrixCalculator {
    @FXML
    private ViewModel viewModel;
    @FXML
    private ComboBox<Matrix.Operation> cbOperation;
    @FXML
    private Button btnCalculate;
    @FXML
    private TextField leftMatrixRowsCount;
    @FXML
    private TextField leftMatrixColumnsCount;
    @FXML
    private TableView<MatrixViewModel.MatrixRow> leftTable;
    @FXML
    private TextField rightMatrixRowsCount;
    @FXML
    private TextField rightMatrixColumnsCount;
    @FXML
    private TableView<MatrixViewModel.MatrixRow> rightTable;
    @FXML
    private Label resultMatrixRowsCount;
    @FXML
    private Label resultMatrixColumnsCount;
    @FXML
    private TableView<MatrixViewModel.MatrixRow> resultTable;
    @FXML
    private Label statusLabel;

    private final ILogger logger = new TextLogger();

    @FXML
    void initialize() {
        viewModel.setLogger(logger);

        bindControls();
        bindLeftMatrixControls();
        bindRightMatrixControls();
        bindResultMatrixControls();
        bindStatus();

        bindMatrixView(leftTable, viewModel.leftMatrix());
        bindMatrixView(rightTable, viewModel.rightMatrix());
        bindMatrixView(resultTable, viewModel.resultMatrix(), false);
    }

    private void bindControls() {
        cbOperation.valueProperty().bindBidirectional(viewModel.operationProperty());

        btnCalculate.setOnAction(event -> {
            viewModel.calculate();
            updateBindMatrixView(resultTable, viewModel.resultMatrix());
        });
    }

    private void bindLeftMatrixControls() {
        leftMatrixRowsCount
                .textProperty()
                .bindBidirectional(viewModel.leftMatrixRowsProperty(),
                        new NumberStringConverter());

        leftMatrixColumnsCount
                .textProperty()
                .bindBidirectional(viewModel.leftMatrixColumnsProperty(),
                        new NumberStringConverter());

        ChangeListener<Number> leftMatrixDimensionListener =
                (observable, oldValue, newValue) -> {
                    viewModel.reloadLeftMatrix();
                    updateBindMatrixView(leftTable, viewModel.leftMatrix());
                };
        viewModel.leftMatrixRowsProperty().addListener(leftMatrixDimensionListener);
        viewModel.leftMatrixColumnsProperty().addListener(leftMatrixDimensionListener);
    }

    private void bindMatrixView(final TableView<MatrixViewModel.MatrixRow> tv,
                                final MatrixViewModel mvm) {
        bindMatrixView(tv, mvm, true);
    }

    private void bindMatrixView(final TableView<MatrixViewModel.MatrixRow> tv,
                                final MatrixViewModel mvm,
                                final boolean isEditable) {
        tv.setEditable(isEditable);
        updateBindMatrixView(tv, mvm);
    }

    private void updateBindMatrixView(final TableView<MatrixViewModel.MatrixRow> tv,
                                      final MatrixViewModel mvm) {
        tv.getColumns().clear();
        tv.setItems(mvm.getRows());

        int columns = mvm.getMatrix().getColumns();
        for (int col = 0; col < columns; col++) {
            final int columnIndex = col;
            TableColumn<MatrixViewModel.MatrixRow, Number> column =
                    new TableColumn<>("#" + (col + 1));
            column.setCellValueFactory(param -> param.getValue().elementProperty(columnIndex));
            column.setCellFactory(TextFieldTableCell
                    .<MatrixViewModel.MatrixRow, Number>forTableColumn(
                            new NumberStringConverter()));
            tv.getColumns().add(column);
        }
    }

    private void bindRightMatrixControls() {
        rightMatrixRowsCount
                .textProperty()
                .bindBidirectional(viewModel.rightMatrixRowsProperty(),
                        new NumberStringConverter());
        rightMatrixColumnsCount
                .textProperty()
                .bindBidirectional(viewModel.rightMatrixColumnsProperty(),
                        new NumberStringConverter());

        ChangeListener<Number> rightMatrixDimensionListener =
                (observable, oldValue, newValue) -> {
                    viewModel.reloadRightMatrix();
                    updateBindMatrixView(rightTable, viewModel.rightMatrix());
                };
        viewModel.rightMatrixRowsProperty().addListener(rightMatrixDimensionListener);
        viewModel.rightMatrixColumnsProperty().addListener(rightMatrixDimensionListener);
    }

    private void bindResultMatrixControls() {
        resultMatrixRowsCount
                .textProperty()
                .bindBidirectional(viewModel.resultMatrixRowsProperty(),
                        new NumberStringConverter());
        resultMatrixColumnsCount
                .textProperty()
                .bindBidirectional(viewModel.resultMatrixColumnsProperty(),
                        new NumberStringConverter());
    }

    private void bindStatus() {
        statusLabel
                .textProperty()
                .bindBidirectional(viewModel.statusProperty(),
                        new StringConverter<ViewModel.Status>() {
                            @Override
                            public String toString(final ViewModel.Status object) {
                                return object.toString();
                            }

                            @Override
                            public ViewModel.Status fromString(final String string) {
                                for (ViewModel.Status value : ViewModel.Status.values()) {
                                    if (value.toString().compareToIgnoreCase(string) == 0) {
                                        return value;
                                    }
                                }
                                return null;
                            }
                        });
    }
}
