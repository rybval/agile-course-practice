package ru.unn.agile.matrixoperations.viewmodel;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.matrixoperations.model.Matrix;

import java.util.ArrayList;

public class MatrixViewModel {
    private Matrix sourceMatrix;
    private final ObservableList<MatrixRow> rows = FXCollections.observableArrayList();

    public class MatrixRow {
        private final int rowIndex;
        private final ArrayList<FloatProperty> rowProperties = new ArrayList<>();

        public MatrixRow(final int index) {
            rowIndex = index;
            final Matrix sourceMatrix = MatrixViewModel.this.sourceMatrix;
            for (int i = 0; i < sourceMatrix.getColumns(); i++) {
                final int colIndex = i;
                FloatProperty fp = new SimpleFloatProperty(sourceMatrix.getElement(rowIndex, i));
                fp.addListener((observable, oldValue, newValue) ->
                        sourceMatrix.setElement(rowIndex, colIndex, newValue.floatValue()));
                rowProperties.add(fp);
            }
        }

        public FloatProperty getCellValue(final int colIndex) {
            return rowProperties.get(colIndex);
        }

        public int getColumnsCount() {
            return rowProperties.size();
        }
    }

    public MatrixViewModel(final Matrix srcMatrix) {
        setMatrixData(srcMatrix);
    }

    public void setMatrix(final Matrix srcMatrix) {
        setMatrixData(srcMatrix);
    }

    public Matrix getMatrix() {
        return sourceMatrix;
    }

    public ObservableList<MatrixRow> getRows() {
        return rows;
    }

    private void setMatrixData(final Matrix m) {
        rows.clear();
        sourceMatrix = m;
        for (int row = 0; row < sourceMatrix.getRows(); row++) {
            rows.add(new MatrixRow(row));
        }
    }
}
