package ru.unn.agile.matrixoperations.infrastructure;

import ru.unn.agile.matrixoperations.viewmodel.ILogger;
import ru.unn.agile.matrixoperations.viewmodel.ViewModel;
import ru.unn.agile.matrixoperations.viewmodel.ViewModelTests;

public class TextLoggerAndViewModelInfrastructTest extends ViewModelTests {
    @Override
    protected void setUpLogger() {
        ILogger logger = new TextLogger();
        setLogger(logger);

        ViewModel vm = new ViewModel();
        vm.setLogger(logger);
        setViewModel(vm);
    }
}
