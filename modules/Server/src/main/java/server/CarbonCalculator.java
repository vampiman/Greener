package server;

public class CarbonCalculator {

    private double electricityPrice = 0.2;
    private double naturalGasFactor = 120.61;
    private double naturalGasPrice = 14.14;
    private double oilFuelFactor = 22.37;
    private double oilFuelPrice = 2.88;
    private double propaneFactor = 12.17;
    private double propanePrice = 2.88;
    private double vehiclePerGallon = 19.4;
    private double vehicleGreenhouse = 1.0526315;
    private double publicPerGallon = 19.4;
    private double gramToPound = 0.0022;
    private double airPerMile = 223;
    private double wellToPumpFactor = 1.2;
    private double radiativeForcing = 1.9;
    private double meatFactor = 1452;
    private double cerealsFactor = 741;
    private double dairyFactor = 1911;
    private double fruitFactor = 368;
    private double otherFoodFactor = 467;
    private double clothingFactor = 436;
    private double furnishHouseholdFactor = 459;
    private double otherGoodsFactor = 338;
    private double serviceFactor = 178;


    // 1 for euro, 2 for dollar
    private int currency;
    // 1 for pounds, 2 for grams
    private int metrics;

    public CarbonCalculator(int currency, int metrics) {
        this.currency = currency;
        this.metrics = metrics;
    }

    public double electricity(double amount, double electricityPrice) {
        double electricityFactor = 1.37;

        if (currency == 1) {
            amount = convertToDollar(amount);
        }
        if (electricityPrice == 0) {
            electricityPrice = this.electricityPrice;
        }
        double toReturn = (amount / electricityPrice) * electricityFactor * 12;

        if (metrics == 2) {
            return poundsToGrams(toReturn);
        }

        return toReturn;

    }

    public double naturalGas(double amount, double naturalGasPrice) {
        if (currency == 1) {
            amount = convertToDollar(amount);
        }

        if (naturalGasPrice == 0) {
            naturalGasPrice = this.naturalGasPrice;
        }

        double toReturn = (amount / naturalGasPrice) * naturalGasFactor * 12;

        if (metrics == 2) {
            return poundsToGrams(toReturn);
        }

        return toReturn;
    }

    public double fuelOil(double amount, double oilFuelPrice) {
        if (currency == 1) {
            amount = convertToDollar(amount);
        }

        if (oilFuelPrice == 0) {
            oilFuelPrice = this.oilFuelPrice;
        }

        double toReturn = (amount / oilFuelPrice) * oilFuelFactor * 12;

        if (metrics == 2) {
            return poundsToGrams(toReturn);
        }

        return toReturn;
    }

    public double propane(double amount, double propanePrice) {
        if (currency == 1) {
            amount = convertToDollar(amount);
        }
        if (propanePrice == 0) {
            propanePrice = this.propanePrice;
        }

        double toReturn = (amount / propanePrice) * propaneFactor * 12;

        if (metrics == 2) {
            return poundsToGrams(toReturn);
        }

        return toReturn;
    }

    public double vehicle(double amount, double milesPerGallon) {
        if (currency == 1) {
            amount = convertToDollar(amount);
        }

        double toReturn = ((amount * 52.117) / milesPerGallon) * 19.4 * vehicleGreenhouse;

        if (metrics == 2) {
            return poundsToGrams(toReturn);
        }

        return toReturn;
    }

    //TO DO LATER
    //public double publicTransport(double amount)

    public double airTravel(double milesPerYear) {


        double toReturn = milesPerYear * (airPerMile * wellToPumpFactor * radiativeForcing) * gramToPound;

        if (metrics == 2) {
            return poundsToGrams(toReturn);
        }

        return toReturn;
    }

    public double poundsToGrams(double pounds) {
        return pounds * 2;
    }



    public double convertToDollar(double amount) {
        return amount*1.137436;
    }
}
