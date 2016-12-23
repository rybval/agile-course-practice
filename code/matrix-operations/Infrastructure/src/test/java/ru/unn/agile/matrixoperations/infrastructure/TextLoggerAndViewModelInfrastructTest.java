package ru.unn.agile.matrixoperations.infrastructure;

import ru.unn.agile.matrixoperations.viewmodel.ViewModelTests;

public class TextLoggerAndViewModelInfrastructTest extends ViewModelTests {
    @Override
    protected void setUpLogger() {
        logger = new TextLogger();
        viewModel.setLogger(logger);
    }
}
