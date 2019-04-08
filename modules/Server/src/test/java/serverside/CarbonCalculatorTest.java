package serverside;

import org.junit.Assert;
import org.junit.Test;


public class CarbonCalculatorTest {


    /**
     *  Test for the electricity consumption.
     */
    //    @Test
    //    public void electricityTestDefault() {
    //        CarbonCalculator calc = new CarbonCalculator(1);
    //        CarbonCalculator calckg = new CarbonCalculator(2);
    //
    //        Assert.assertEquals(8220, (int)calc.electricity(100,0));
    //        Assert.assertEquals(3728, (int)calckg.electricity(100, 0));
    //    }
    //
    //    /**
    //     *  Test for the Gas consumption.
    //     */
    //    @Test
    //    public void naturalGasTestDefault() {
    //        CarbonCalculator calc = new CarbonCalculator(1);
    //        CarbonCalculator calckg = new CarbonCalculator(2);
    //
    //        Assert.assertEquals(10235, (int)calc.naturalGas(100,0));
    //        Assert.assertEquals(4642, (int)calckg.naturalGas(100, 0));
    //    }
    //
    //    /**
    //     *  Test for fuel Oil consumption.
    //     */
    //    @Test
    //    public void fuelOilTestDefault() {
    //        CarbonCalculator calc = new CarbonCalculator(1);
    //        CarbonCalculator calckg = new CarbonCalculator(2);
    //
    //        Assert.assertEquals(9320, (int)calc.fuelOil(100,0));
    //        Assert.assertEquals(4227, (int)calckg.fuelOil(100, 0));
    //    }
    //
    //    /**
    //     *  Test for propane consumption.
    //     */
    //    @Test
    //    public void propaneTestDefault() {
    //        CarbonCalculator calc = new CarbonCalculator(1);
    //        CarbonCalculator calckg = new CarbonCalculator(2);
    //
    //        Assert.assertEquals(5291, (int)calc.propane(100,0));
    //        Assert.assertEquals(2400, (int)calckg.propane(100, 0));
    //    }

    /**
     *  Test for personal vehicle CO2 emissions.
     */
    @Test
    public void vehicle() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(5321, (int)calc.vehicle(100,20));
        Assert.assertEquals(2413, (int)calckg.vehicle(100, 20));
    }

    /**
     *  Test for public transport CO2 emissions.
     */
    @Test
    public void publicTransport() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(1785, (int)calc.publicTransport(5000));
        Assert.assertEquals(809, (int)calckg.publicTransport(5000));
    }

    /**
     *  Test for air-based travel CO2 emissions.
     */
    @Test
    public void airTravel() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(5592, (int)calc.airTravel(5000));
        Assert.assertEquals(2536, (int)calckg.airTravel(5000));
    }


    /**
     * Test for veganmeal CO2 emissions type 1 (fruit).
     */
    @Test
    public void veganmealCalculatorTest1() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(11, (int) calc.veganmeal_Calculator(10, "Fruit"));
    }

    /**
     * Test for veganmeal CO2 emissions type 2 (dairy).
     */
    @Test
    public void veganmealCalculatorTest2() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(7, (int) calc.veganmeal_Calculator(1, "Dairy"));
    }

    /**
     * Test for veganmeal CO2 emissions type 3 (vegetables).
     */
    @Test
    public void veganmealCalculatorTest3() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(2, (int) calc.veganmeal_Calculator(1, "Vegetables"));
    }

    /**
     * Test for veganmeal CO2 emissions type 4 (meat).
     */
    @Test
    public void veganmealCalculatorTest4() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(21, (int) calc.veganmeal_Calculator(1, "Meat"));
    }

    /**
     * Test for veganmeal CO2 emissions type 5 (eggs).
     */
    @Test
    public void veganmealCalculatorEggsTest() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(48, (int) (calc.veganmeal_Calculator(1, "Eggs") * 10));
    }

    /**
     * Test for veganmeal CO2 emissions, case when the type is illegal.
     */
    @Test(expected = IllegalArgumentException.class)
    public void veganmealWrongType() {
        new CarbonCalculator(1).veganmeal_Calculator(100, "Something else");
    }


    /**
     * Test for localproduce CO2 emissions type 1 (fruit).
     */
    @Test
    public void localproduce_CalculatorTest1() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(77, (int) (calc.localproduce_Calculator(10, "Fruit") * 100));
    }

    /**
     * Test for localproduce CO2 emissions type 2 (dairy).
     */
    @Test
    public void localproduceCalculatorTest2() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(5, (int) calc.localproduce_Calculator(10, "Dairy"));
    }

    /**
     * Test for localproduce CO2 emissions type 3 (vegetables).
     */
    @Test
    public void localproduceCalculatorTest3() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(14, (int) (calc.localproduce_Calculator(10, "Vegetables") * 10));
    }

    /**
     * Test for localproduce CO2 emissions type 4 (meat).
     */
    @Test
    public void localproduceCalculatorTest4() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(14, (int) calc.localproduce_Calculator(10, "Meat"));
    }

    /**
     * Test for localproduce CO2 emissions type 4 (eggs).
     */
    @Test
    public void localproduceCalculatorEggs() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(33, (int) calc.localproduce_Calculator(100, "Eggs"));
    }

    /**
     * Test for localproduce CO2 emissions, case when the type is illegal.
     */
    @Test(expected = IllegalArgumentException.class)
    public void localproduce_wrongType() {
        new CarbonCalculator(1).localproduce_Calculator(100, "Something else");
    }

    /**
     *  Test for the conversion from pounds to kilograms.
     */
    @Test
    public void poundsToKilograms() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(45, (int)calc.poundsToKilograms(100));
    }

    /**
     * Test if the right integer is returned by the method.
     */
    @Test
    public void heatConsumptionElectric() {
        Assert.assertEquals(250.0, CarbonCalculator.energyToCarbonElectric(), 0.001);
    }

    /**
     * Test if the right integer is returned by the method.
     */
    @Test
    public void heatConsumptionNonElectric() {
        Assert.assertEquals(221.875, CarbonCalculator.energyToCarbonNonElectric(), 0.001);
    }

    /**
     * Test the heat consumption saved for the electric energy source.
     */
    @Test
    public void homeHeatConsumptionSavedNonElectric() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(44, calc.homeHeatConsumptionSaved(1000.0,
                800.0, "Non-Electric"));
    }

    /**
     * Test the heat consumption saved for the non-electric energy source.
     */
    @Test
    public void homeHeatConsumptionSavedElectric() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(50, calc.homeHeatConsumptionSaved(1000.0,
                800.0, "Electric"));
    }


    /**
     * Tests if the calculations of the saved carbon dioxide of
     * the fossil car option is correct in relation to the website
     * from which the API is used.
     */
    @Test
    public void publicTransportCalculatorFossilTest() throws Exception {
        CarbonCalculator calc = new CarbonCalculator(1);
        String pt = "CityBus" ;
        String carType = "Fossil";

        //value for comparison are calculated wth help from from the website of the API used.
        Assert.assertEquals(calc.publicTransportCalculator(carType, pt, 161),
                484 / 52.177 * 0.45359237, 0.2);
    }

    /**
     * Tests if the calculations of the saved carbon dioxide of
     * the intercity bus option is correct in relation to the website
     * from which the API is used.
     */
    @Test
    public void publicTransportCalculatorFossilIntercityBusTest() throws Exception {
        CarbonCalculator calc = new CarbonCalculator(1);
        String pt = "IntercityBus" ;
        String carType = "Fossil";

        //value for comparison are calculated with help from from the website of the API used.
        Assert.assertEquals(16, (int)calc.publicTransportCalculator(carType, pt, 100));
    }

    /**
     * Tests if the calculations of the saved carbon dioxide of
     * the subway option is correct in relation to the website
     * from which the API is used.
     */
    @Test
    public void publicTransportCalculatorFossilSubwayTest() throws Exception {
        CarbonCalculator calc = new CarbonCalculator(1);
        String pt = "Subway" ;
        String carType = "Fossil";

        //value for comparison are calculated with help from from the website of the API used.
        Assert.assertEquals(11, (int)calc.publicTransportCalculator(carType, pt, 100));
    }

    /**
     * Tests if the calculations of the saved carbon dioxide of
     * the train option is correct in relation to the website
     * from which the API is used.
     */
    @Test
    public void publicTransportCalculatorFossilTrainTest() throws Exception {
        CarbonCalculator calc = new CarbonCalculator(1);
        String pt = "Train" ;
        String carType = "Fossil";

        //value for comparison are calculated with help from from the website of the API used.
        Assert.assertEquals(9, (int)calc.publicTransportCalculator(carType, pt, 100));
    }

    /**
     * Tests if an IllegalArgumentException is created when an
     * incorrect string is used for public transport.
     */
    @Test(expected = IllegalArgumentException.class)
    public void publicTransportCalculatorException1() {
        CarbonCalculator calc = new CarbonCalculator(1);
        String pt = "someString" ;
        String carType = "Fossil";
        calc.publicTransportCalculator(carType, pt, 161);
    }


    /**
     * Tests if an IllegalArgumentException is created when an
     * incorrect string is used for the type of the car.
     */
    @Test(expected = IllegalArgumentException.class)
    public void publicTransportCalculatorException2() {
        CarbonCalculator calc = new CarbonCalculator(1);
        String pt = "CityBus";
        String carType = "someString";
        calc.publicTransportCalculator(carType, pt, 161);
    }


    @Test
    public void bikeHybridTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        Assert.assertEquals(15, (int)calc.bike("Hybrid", 100));
    }

    @Test
    public void bikeElectricTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        Assert.assertEquals(11, (int)calc.bike("Electric", 100));
    }

    @Test
    public void bikeFossilTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        Assert.assertEquals(21, (int)calc.bike("Fossil", 100));
    }

    @Test
    public void bikeMotorcycleTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        Assert.assertEquals(11, (int)calc.bike("Motorcycle", 100));
    }

    @Test
    public void bikeBusTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        Assert.assertEquals(18, (int)calc.bike("Bus", 100));
    }

    @Test
    public void bikeSubwayTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        Assert.assertEquals(10, (int)calc.bike("Subway", 100));
    }

    @Test
    public void bikeTrainTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        Assert.assertEquals(11, (int)calc.bike("Train", 100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void bikeException() {
        CarbonCalculator calc = new CarbonCalculator(1);
        calc.bike("Alternative", 100);
    }

    @Test
    public void solarPanelsTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        Assert.assertEquals(255, (int)calc.solarPanel(500));
    }
}

