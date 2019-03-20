package serverside;

/**
 *  Class implementing a carbon footprint calculator that takes in consideration
 *  different sides of day-by-day life, like Housing, Transport, Food Consumption and
 *  other Goods/Services.
 */
public class CarbonCalculator {


    private double gramToPound = 0.0022;

    // 1 for pounds, 2 for grams
    private int metrics;

    /**
     * Constructor for tha CarbonCalculator class that must be used to select the desired
     * metric system.
     *
     * @param metrics type of metric system, 1 for pounds, 2 for grams
     */
    public CarbonCalculator(int metrics) {
        this.metrics = metrics;
    }

    /**
     * Calculates the CO2 emissions based on your electricity consumption per month.
     *
     * @param amount Cost of the monthly electricity bill
     * @param electricityPrice Price per kWh
     * @return CO2 emissions per year (in pounds or grams)
     */
    public double electricity(double amount, double electricityPrice) {
        double electricityFactor = 1.37;
        double averageElectricityPrice = 0.2;

        if (electricityPrice == 0) {
            electricityPrice = averageElectricityPrice;
        }
        double toReturn = (amount / electricityPrice) * electricityFactor * 12;

        if (metrics == 2) {
            return poundsToKilograms(toReturn);
        }

        return toReturn;

    }

    /**
     * Natural gas CO2 emissions.
     *
     * @param amount Monthly natural gas cost
     * @param naturalGasPrice Price per thousand cubic feet
     * @return CO2 emissions per year (in pounds or grams)
     */
    public double naturalGas(double amount, double naturalGasPrice) {
        double naturalGasFactor = 120.61;
        double averageNaturalGasPrice = 14.14;

        if (naturalGasPrice == 0) {
            naturalGasPrice = averageNaturalGasPrice;
        }

        double toReturn = (amount / naturalGasPrice) * naturalGasFactor * 12;

        if (metrics == 2) {
            return poundsToKilograms(toReturn);
        }

        return toReturn;
    }

    /**
     * Fuel Oil CO2 emissions.
     * @param amount Monthly fuel oil cost
     * @param oilFuelPrice Price per gallon
     * @return CO2 emissions per year (in pounds or grams)
     */

    public double fuelOil(double amount, double oilFuelPrice) {
        double oilFuelFactor = 22.37;
        double averageOilFuelPrice = 2.88;

        if (oilFuelPrice == 0) {
            oilFuelPrice = averageOilFuelPrice;
        }

        double toReturn = (amount / oilFuelPrice) * oilFuelFactor * 12;

        if (metrics == 2) {
            return poundsToKilograms(toReturn);
        }

        return toReturn;
    }

    /**
     * Propane CO2 emissions.
     *
     * @param amount Propane monthly cost
     * @param propanePrice Price per gallon
     * @return CO2 emissions per year (in grams or pounds)
     */
    public double propane(double amount, double propanePrice) {
        double propaneFactor = 12.17;
        double averagePropanePrice = 2.76;

        if (propanePrice == 0) {
            propanePrice = averagePropanePrice;
        }

        double toReturn = (amount / propanePrice) * propaneFactor * 12;

        if (metrics == 2) {
            return poundsToKilograms(toReturn);
        }

        return toReturn;
    }

    /**
     * Personal vehicle CO2 emissions.
     * @param amount Miles per week
     * @param milesPerGallon Fuel efficiency (miles per gallon)
     * @return CO2 emissions per year (in grams or pounds)
     */
    public double vehicle(double amount, double milesPerGallon) {
        double vehiclePerGallon = 19.4;
        double vehicleGreenhouse = 1.0526315;


        double toReturn = ((amount * 52.117) / milesPerGallon)
                            * vehiclePerGallon * vehicleGreenhouse;

        if (metrics == 2) {
            return poundsToKilograms(toReturn);
        }

        return toReturn;
    }

    /**
     * Public Transport CO2 emissions.
     * @param amount Miles per year
     * @return CO2 emissions per year (in grams or pounds)
     */
    public double publicTransport(double amount) {
        //double publicPerGallon = 12.48;
        //double averageMilesPerGallon = 38.3;
        double co2PerMile = 0.357;

        double toReturn = amount * co2PerMile;

        if (metrics == 2) {
            return poundsToKilograms(toReturn);
        }

        return toReturn;
    }

    /**
     * Air Travel CO2 emissions.
     * @param milesPerYear Miles traveled per year
     * @return CO2 emissions per year (in grams or pounds)
     */
    public double airTravel(double milesPerYear) {
        double airPerMile = 223;
        double wellToPumpFactor = 1.2;
        double radiativeForcing = 1.9;


        double toReturn = milesPerYear * (airPerMile * wellToPumpFactor * radiativeForcing)
                            * gramToPound;

        if (metrics == 2) {
            return poundsToKilograms(toReturn);
        }

        return toReturn;
    }

    /**
     * Food CO2 emissions.
     * @param amount Monthly cost for the particular type of food
     * @param type Food type (1 for meat, 2 for cereals, 3 for dairy, 4 for fruit.
     *            5 for eating out and 6 for other food)
     * @return CO2 emissions per year (in grams or pounds)
     */
    public double food(double amount, int type) {
        if (type > 6 || type < 1) {
            throw new IllegalArgumentException("Please insert a valid type of Food!");
        }

        double specificFactor = 0;

        switch (type) {
            // meatFactor = 1452
            case 1: specificFactor = 1452;
            break;
            // cerealsFactor = 741
            case 2: specificFactor = 741;
            break;
            // dairyFactor = 1911
            case 3: specificFactor = 1911;
            break;
            // fruitFactor = 1176
            case 4: specificFactor = 1176;
            break;
            // eatingOutFactor = 368
            case 5: specificFactor = 368;
            break;
            // otherFoodFactor = 467
            case 6: specificFactor = 467;
            break;
            default: break;
        }

        double toReturn = (amount * specificFactor * 12) * gramToPound;

        if (metrics == 2) {
            return poundsToKilograms(toReturn);
        }

        return toReturn;
    }

    /**
     * Services and Goods CO2 emissions.
     * @param amount Monthly cost for the particular type of goods/services
     * @param type Type of goods/services (1 for clothing, 2 for furniture/appliances,
     *            3 for other goods and 4 for general services)
     * @return CO2 emissions per year (in grams or pounds)
     */
    public double servicesAndGoods(double amount, int type) {
        if (type > 4 || type < 1) {
            throw new IllegalArgumentException("Please insert a valid type of Service/Goods!");
        }

        double specificFactor = 0;

        switch (type) {
            // clothingFactor = 436
            case 1: specificFactor = 436;
            break;
            // furnishHouseholdFactor = 459
            case 2: specificFactor = 459;
            break;
            // otherGoodsFactor = 338
            case 3: specificFactor = 338;
            break;
            // serviceFactor = 178
            case 4: specificFactor = 178;
            break;
            default: break;
        }

        double toReturn = (amount * specificFactor * 12) * gramToPound;

        if (metrics == 2) {
            return poundsToKilograms(toReturn);
        }

        return toReturn;
    }

    /**
     * Pounds to kilograms converter.
     * @param pounds Quantity in pounds
     * @return Quantity in kilograms
     */
    public double poundsToKilograms(double pounds) {
        return pounds * 0.45359237;
    }
    
    /**
     * Solar panel calculator.
     * @param Kwh consumption per month
     * @return amount of pounds you use more than average
     */
    public double SolarPanel_Points_Calculator (double Kwh) {

        double ElectricityFactor = 1.37;
        double AVG_Kwh_per_month = 3340;

        double Delta = AVG_Kwh_per_month - Kwh;
        double toReturn = Delta * ElectricityFactor;

        if (metrics == 2) {
            return poundsToKilograms(toReturn);
        }

        return toReturn;

    }
    
     /**
     * get the CO2 emission of the localproduce feature
     * @param amount
     * @param type of meal
     * @return 10 times the CO2 emission
     */
    public double Veganmeal_Calculator(double amount, int type){
        if (type > 4 || type < 1) {
            throw new IllegalArgumentException("Please insert a valid type of Food!");
        }

        double sort = 0;

        switch (type) {
            case 1:
                //fruit
                sort = 0.42;
                break;
            case 2:
                //dairy
                sort = 7.7;
                break;
            case 3:
                //vegetables
                sort = 2.0;
                break;
            case 4:
                //meat
                sort = 21.3;
                break;
            default:
                break;
        }
        return amount * sort * 10;
    }




}
