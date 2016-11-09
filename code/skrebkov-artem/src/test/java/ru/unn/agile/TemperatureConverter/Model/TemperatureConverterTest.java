package ru.unn.agile.TemperatureConverter.Model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.*;
import static ru.unn.agile.TemperatureConverter.Model.TemperatureScale.*;

@RunWith(Parameterized.class)
public class TemperatureConverterTest {
    private static final double DELTA = 0.000001;

    @Parameterized.Parameters(name = "{index}: from {0} {2}  to {1} {3}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0.0, 273.15, CELSIUS, KELVIN}, {100.0, 373.15, CELSIUS, KELVIN},
                {0.0, 32.0, CELSIUS, FAHRENHEIT}, {100.0, 212.0, CELSIUS, FAHRENHEIT},
                {0.0, 0.0, CELSIUS, NEWTON}, {100.0, 33.0, CELSIUS, NEWTON},
                {0.0, -273.15, KELVIN, CELSIUS}, {100.0, -173.15, KELVIN, CELSIUS},
                {0.0, -90.1395, KELVIN, NEWTON}, {100.0, -57.1395, KELVIN, NEWTON},
                {0.0, -459.67, KELVIN, FAHRENHEIT}, {100.0, -279.67, KELVIN, FAHRENHEIT},
                {0.0, 273.15, NEWTON, KELVIN}, {33.0, 373.15, NEWTON, KELVIN},
                {0.0, 32.0, NEWTON, FAHRENHEIT}, {11.0, 92.0, NEWTON, FAHRENHEIT},
                {0.0, 0.0, NEWTON, CELSIUS}, {33.0, 100.0, NEWTON, CELSIUS},
                {8.33, 260.0, FAHRENHEIT, KELVIN},
                {5.0, -15.0, FAHRENHEIT, CELSIUS},
                {2.0, -5.5, FAHRENHEIT, NEWTON},
        });
    }

    private final double sourceDegree;
    private final double destinationDegree;
    private final TemperatureScale sourceScale;
    private final TemperatureScale destinationScale;

    public TemperatureConverterTest(final double sourceDegree,
                                    final double destinationDegree,
                                    final TemperatureScale sourceScale,
                                    final TemperatureScale destinationScale) {
        this.sourceDegree = sourceDegree;
        this.destinationDegree = destinationDegree;
        this.sourceScale = sourceScale;
        this.destinationScale = destinationScale;

    }

    @Test
    public void testTemperatureConverter() {
        TemperatureConverter converter = new TemperatureConverter();
        assertEquals(destinationDegree,
                converter.compute(sourceDegree, sourceScale, destinationScale),
                DELTA);
    }
}
