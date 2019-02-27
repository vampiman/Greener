package server;

public class CarbonCalculator {


    private double gramToPound = 0.0022;




    // 1 for pounds, 2 for grams
    private int metrics;

    public CarbonCalculator(int metrics) {
        this.metrics = metrics;
    }

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

    public double propane(double amount, double propanePrice) {
        double propaneFactor = 12.17;
        double averagePropanePrice = 2.88;

        if (propanePrice == 0) {
            propanePrice = averagePropanePrice;
        }

        double toReturn = (amount / propanePrice) * propaneFactor * 12;

        if (metrics == 2) {
            return poundsToKilograms(toReturn);
        }

        return toReturn;
    }

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


    public double publicTransport(double amount) {
        double publicPerGallon = 19.4;
        double averageMilesPerGallon = 38.3;

        double toReturn = (amount / averageMilesPerGallon) * publicPerGallon;

        if (metrics == 2) {
            return poundsToKilograms(toReturn);
        }

        return toReturn;
    }


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
            case 4: specificFactor = 368;
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


    public double poundsToKilograms(double pounds) {
        return pounds * 0.45359237;
    }



    public static void main(String[] args){

    }
}
