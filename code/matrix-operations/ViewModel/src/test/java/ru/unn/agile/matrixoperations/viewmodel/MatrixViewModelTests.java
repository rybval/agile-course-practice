package ru.unn.agile.matrixoperations.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.matrixoperations.model.Matrix;

import static org.junit.Assert.assertEquals;

public class MatrixViewModelTests {
    private final float delta = 0.00001f;
    private MatrixViewModel matrixModel;

    private Matrix srcMatrix;
    private float[] srcData = {1, 2,
                               3, 4,
                               5, 6};

    private Matrix updateMatrix;
    private float[] updateData = {9, 8, 7,
                                  6, 6, 4,
                                  3, 2, 1,
                                  9, 8, 7};

    @Before
    public void setUp() {
        srcMatrix = new Matrix(3, 2, srcData);
        updateMatrix = new Matrix(4, 3, updateData);

        matrixModel = new MatrixViewModel(srcMatrix);
    }

    @After
    public void tearDown() {
        srcMatrix = null;
        updateMatrix = null;
        matrixModel = null;
    }

    @Test
    public void testInitialsRows() {
        assertEquals(srcMatrix.getRows(), matrixModel.getRows().size());
    }

    @Test
    public void testInitialsColumns() {
        assertEquals(srcMatrix.getColumns(), matrixModel.getRows().get(0).getColumnsCount());
    }

    @Test
    public void testInitialMatrix() {
        assertEquals(srcMatrix, matrixModel.getMatrix());
    }

    @Test
    public void testUpdateMatrix() {
        matrixModel.setMatrix(updateMatrix);
        assertEquals(updateMatrix, matrixModel.getMatrix());
    }

    @Test
    public void testRowsCountForUpdatedMatrix() {
        matrixModel.setMatrix(updateMatrix);
        assertEquals(updateMatrix.getRows(), matrixModel.getRows().size());
    }

    @Test
    public void testColumnsCountForUpdatedMatrix() {
        matrixModel.setMatrix(updateMatrix);
        assertEquals(updateMatrix.getColumns(), matrixModel.getRows().get(0).getColumnsCount());
    }

    @Test
    public void testGetCellValue() {
        for (int r = 0; r < srcMatrix.getRows(); r++) {
            for (int c = 0; c < srcMatrix.getColumns(); c++) {
                assertEquals(srcMatrix.getElement(r, c),
                        matrixModel.getRows().get(r).elementProperty(c).get(), delta);
            }
        }
    }

    @Test
    public void testSetCellValue() {
        float testValue = 334.5f;
        for (int r = 0; r < srcMatrix.getRows(); r++) {
            for (int c = 0; c < srcMatrix.getColumns(); c++) {
                matrixModel.getRows().get(r).elementProperty(c).set(testValue);
                assertEquals(testValue, matrixModel.getMatrix().getElement(r, c), delta);
            }
        }
    }

    @Test
    public void canAccessElementProperty() {
        float setValue = 3.14f;

        matrixModel.elementProperty(1, 1).set(setValue);
        float getValue = matrixModel.elementProperty(1, 1).get();

        assertEquals(setValue, getValue, Float.MIN_VALUE);
    }
}
