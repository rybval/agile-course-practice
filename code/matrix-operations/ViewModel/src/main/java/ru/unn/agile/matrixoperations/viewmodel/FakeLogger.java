/*
 *  Used for backward compatibility of ViewModel realization
 *  and for unit testing.
 */

package ru.unn.agile.matrixoperations.viewmodel;

import java.util.ArrayList;
import java.util.List;

class FakeLogger implements ILogger {
    @Override
    public List<String> getLog() {
        return new ArrayList<>();
    }

    @Override
    public void log(final String message) {
        assert true;
    }
}
