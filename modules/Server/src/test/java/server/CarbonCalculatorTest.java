package server;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CarbonCalculatorTest {

    @Test
    public void electricityTestDefault() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(8220, (int)calc.electricity(100,0));
    }

    @Test
    public void naturalGas() {

    }

    @Test
    public void fuelOil() {
    }

    @Test
    public void propane() {
    }

    @Test
    public void vehicle() {
    }

    @Test
    public void publicTransport() {
    }

    @Test
    public void airTravel() {
    }

    @Test
    public void food() {
    }

    @Test
    public void servicesAndGoods() {
    }

    @Test
    public void poundsToKilograms() {
    }

    @Test
    public void main() {
    }
}