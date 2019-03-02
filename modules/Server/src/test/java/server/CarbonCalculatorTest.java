package server;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CarbonCalculatorTest {

    @Test
    public void electricityTestDefault() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(8220, (int)calc.electricity(100,0));
        Assert.assertEquals(3728, (int)calckg.electricity(100, 0));
    }

    @Test
    public void naturalGasTestDefault() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(10235, (int)calc.naturalGas(100,0));
        Assert.assertEquals(4642, (int)calckg.naturalGas(100, 0));
    }

    @Test
    public void fuelOilTestDefault() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(9320, (int)calc.fuelOil(100,0));
        Assert.assertEquals(4227, (int)calckg.fuelOil(100, 0));
    }

    @Test
    public void propaneTestDefault() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(5291, (int)calc.propane(100,0));
        Assert.assertEquals(2400, (int)calckg.propane(100, 0));
    }

    @Test
    public void vehicle() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(5321, (int)calc.vehicle(100,20));
        Assert.assertEquals(2413, (int)calckg.vehicle(100, 20));
    }

    @Test
    public void publicTransport() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(1785, (int)calc.publicTransport(5000));
        Assert.assertEquals(809, (int)calckg.publicTransport(5000));
    }

    @Test
    public void airTravel() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(5592, (int)calc.airTravel(5000));
        Assert.assertEquals(2536, (int)calckg.airTravel(5000));
    }

    @Test(expected = IllegalArgumentException.class)
    public void foodWrongType() {
        new CarbonCalculator(1).food(100, 0);
    }

    @Test
    public void foodMeatTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(3833, (int)calc.food(100,1));
        Assert.assertEquals(1738, (int)calckg.food(100, 1));
    }

    @Test
    public void foodCerealTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(1956, (int)calc.food(100,2));
        Assert.assertEquals(887, (int)calckg.food(100, 2));
    }

    @Test
    public void foodDairyTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(5045, (int)calc.food(100,3));
        Assert.assertEquals(2288, (int)calckg.food(100, 3));
    }

    @Test
    public void foodFruitsVegetablesTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(3104, (int)calc.food(100,4));
        Assert.assertEquals(1408, (int)calckg.food(100, 4));
    }

    @Test
    public void foodDiningOutTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(971, (int)calc.food(100,5));
        Assert.assertEquals(440, (int)calckg.food(100, 5));
    }

    @Test
    public void foodOtherTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(1232, (int)calc.food(100,6));
        Assert.assertEquals(559, (int)calckg.food(100, 6));
    }

    @Test(expected = IllegalArgumentException.class)
    public void serviceWrongType() {
        new CarbonCalculator(1).servicesAndGoods(100, 5);
    }

    @Test
    public void clothingTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(1151, (int)calc.servicesAndGoods(100,1));
        Assert.assertEquals(522, (int)calckg.servicesAndGoods(100, 1));
    }

    @Test
    public void appliancesTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(1211, (int)calc.servicesAndGoods(100,2));
        Assert.assertEquals(549, (int)calckg.servicesAndGoods(100, 2));
    }

    @Test
    public void otherGoodsTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(892, (int)calc.servicesAndGoods(100,3));
        Assert.assertEquals(404, (int)calckg.servicesAndGoods(100, 3));
    }

    @Test
    public void servicesTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(469, (int)calc.servicesAndGoods(100,4));
        Assert.assertEquals(213, (int)calckg.servicesAndGoods(100, 4));
    }

    @Test
    public void poundsToKilograms() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(45, (int)calc.poundsToKilograms(100));
    }

}