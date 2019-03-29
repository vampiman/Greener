package serverside;

import org.junit.Assert;
import org.junit.Test;



public class CarbonCalculatorTest {

    /**
     *  Test for the electricity consumption.
     */
    @Test
    public void electricityTestDefault() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(8220, (int)calc.electricity(100,0));
        Assert.assertEquals(3728, (int)calckg.electricity(100, 0));
    }

    /**
     *  Test for the Gas consumption.
     */
    @Test
    public void naturalGasTestDefault() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(10235, (int)calc.naturalGas(100,0));
        Assert.assertEquals(4642, (int)calckg.naturalGas(100, 0));
    }

    /**
     *  Test for fuel Oil consumption.
     */
    @Test
    public void fuelOilTestDefault() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(9320, (int)calc.fuelOil(100,0));
        Assert.assertEquals(4227, (int)calckg.fuelOil(100, 0));
    }

    /**
     *  Test for propane consumption.
     */
    @Test
    public void propaneTestDefault() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(5291, (int)calc.propane(100,0));
        Assert.assertEquals(2400, (int)calckg.propane(100, 0));
    }

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
     *  Test for wrong input type when calculating food CO2 emissions.
     */
    @Test(expected = IllegalArgumentException.class)
    public void foodWrongType() {
        new CarbonCalculator(1).food(100, 0);
    }

    /**
     * Test for food CO2 emissions type 1(meat).
     */
    @Test
    public void foodMeatTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(3833, (int)calc.food(100,1));
        Assert.assertEquals(1738, (int)calckg.food(100, 1));
    }

    /**
     *  Test for food CO2 emissions type 2(cereals).
     */
    @Test
    public void foodCerealTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(1956, (int)calc.food(100,2));
        Assert.assertEquals(887, (int)calckg.food(100, 2));
    }

    /**
     *  Test for food CO2 emissions type 3(dairy).
     */
    @Test
    public void foodDairyTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(5045, (int)calc.food(100,3));
        Assert.assertEquals(2288, (int)calckg.food(100, 3));
    }

    /**
     *  Test for food CO2 emissions type 4(fruits and vegetables).
     */
    @Test
    public void foodFruitsVegetablesTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(3104, (int)calc.food(100,4));
        Assert.assertEquals(1408, (int)calckg.food(100, 4));
    }

    /**
     *  Test for food CO2 emissions type 5(Dining Out).
     */
    @Test
    public void foodDiningOutTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(971, (int)calc.food(100,5));
        Assert.assertEquals(440, (int)calckg.food(100, 5));
    }

    /**
     *  Test for food CO2 emissions type 6(other food).
     */
    @Test
    public void foodOtherTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(1232, (int)calc.food(100,6));
        Assert.assertEquals(559, (int)calckg.food(100, 6));
    }

    /**
     *  Test for goods and services CO2 emissions, case when the type is illegal.
     */
    @Test(expected = IllegalArgumentException.class)
    public void serviceWrongType() {
        new CarbonCalculator(1).servicesAndGoods(100, 5);
    }

    /**
     *  Test for goods and services CO2 emissions type 1(clothing).
     */
    @Test
    public void clothingTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(1151, (int)calc.servicesAndGoods(100,1));
        Assert.assertEquals(522, (int)calckg.servicesAndGoods(100, 1));
    }

    /**
     *  Test for goods and services CO2 emissions type 2(appliances and furniture).
     */
    @Test
    public void appliancesTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(1211, (int)calc.servicesAndGoods(100,2));
        Assert.assertEquals(549, (int)calckg.servicesAndGoods(100, 2));
    }

    /**
     *  Test for goods and services CO2 emissions type 3(other goods).
     */
    @Test
    public void otherGoodsTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(892, (int)calc.servicesAndGoods(100,3));
        Assert.assertEquals(404, (int)calckg.servicesAndGoods(100, 3));
    }

    /**
     *  Test for goods and services CO2 emissions type 4(services).
     */
    @Test
    public void servicesTest() {
        CarbonCalculator calc = new CarbonCalculator(1);
        CarbonCalculator calckg = new CarbonCalculator(2);

        Assert.assertEquals(469, (int)calc.servicesAndGoods(100,4));
        Assert.assertEquals(213, (int)calckg.servicesAndGoods(100, 4));
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
     * Test for veganmeal CO2 emissions type 1 (fruit).
     */
    @Test
    public void Veganmeal_CalculatorTest1() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(4, (int) calc.veganmeal_Calculator(10, "Fruit"));
    }

    /**
     * Test for veganmeal CO2 emissions type 2 (dairy).
     */
    @Test
    public void Veganmeal_CalculatorTest2() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(7, (int) calc.veganmeal_Calculator(1, "Dairy"));
    }

    /**
     * Test for veganmeal CO2 emissions type 3 (vegetables).
     */
    @Test
    public void Veganmeal_CalculatorTest3() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(2, (int) calc.veganmeal_Calculator(1, "Vegetables"));
    }

    /**
     * Test for veganmeal CO2 emissions type 4 (meat).
     */
    @Test
    public void Veganmeal_CalculatorTest4() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(21, (int) calc.veganmeal_Calculator(1, "Meat"));
    }

    /**
     * Test for veganmeal CO2 emissions, case when the type is illegal.
     */
    @Test(expected = IllegalArgumentException.class)
    public void Veganmeal_wrongType() {
        new CarbonCalculator(1).veganmeal_Calculator(100, "Something else");
    }


    /**
     * Test for localproduce CO2 emissions type 1 (fruit).
     */
    @Test
    public void Localproduce_CalculatorTest1() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(3, (int) calc.localproduce_Calculator(10, "Fruit"));
    }

    /**
     * Test for localproduce CO2 emissions type 2 (dairy).
     */
    @Test
    public void Localproduce_CalculatorTest2() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(7, (int) calc.localproduce_Calculator(1, "Dairy"));
    }

    /**
     * Test for localproduce CO2 emissions type 3 (vegetables).
     */
    @Test
    public void Localproduce_CalculatorTest3() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(1, (int) calc.localproduce_Calculator(1, "Vegetables"));
    }

    /**
     * Test for localproduce CO2 emissions type 4 (meat).
     */
    @Test
    public void Localproduce_CalculatorTest4() {
        CarbonCalculator calc = new CarbonCalculator(1);

        Assert.assertEquals(19, (int) calc.localproduce_Calculator(1, "Meat"));
    }

    /**
     * Test for localproduce CO2 emissions, case when the type is illegal.
     */
    @Test(expected = IllegalArgumentException.class)
    public void Localproduce_wrongType() {
        new CarbonCalculator(1).localproduce_Calculator(100, "Something else");
    }
}