package serverside;

import cn.hutool.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    //    /**
    //     * Calculates the CO2 emissions based on your electricity consumption per month.
    //     *
    //     * @param amount Cost of the monthly electricity bill
    //     * @param electricityPrice Price per kWh
    //     * @return CO2 emissions per year (in pounds or grams)
    //     */
    //    public double electricity(double amount, double electricityPrice) {
    //        double electricityFactor = 1.37;
    //        double averageElectricityPrice = 0.2;
    //
    //        if (electricityPrice == 0) {
    //            electricityPrice = averageElectricityPrice;
    //        }
    //        double toReturn = (amount / electricityPrice) * electricityFactor * 12;
    //
    //        if (metrics == 2) {
    //            return poundsToKilograms(toReturn);
    //        }
    //
    //        return toReturn;
    //
    //    }
    //
    //    /**
    //     * Natural gas CO2 emissions.
    //     *
    //     * @param amount Monthly natural gas cost
    //     * @param naturalGasPrice Price per thousand cubic feet
    //     * @return CO2 emissions per year (in pounds or grams)
    //     */
    //    public double naturalGas(double amount, double naturalGasPrice) {
    //        double naturalGasFactor = 120.61;
    //        double averageNaturalGasPrice = 14.14;
    //
    //        if (naturalGasPrice == 0) {
    //            naturalGasPrice = averageNaturalGasPrice;
    //        }
    //
    //        double toReturn = (amount / naturalGasPrice) * naturalGasFactor * 12;
    //
    //        if (metrics == 2) {
    //            return poundsToKilograms(toReturn);
    //        }
    //
    //        return toReturn;
    //    }
    //
    //    /**
    //     * Fuel Oil CO2 emissions.
    //     * @param amount Monthly fuel oil cost
    //     * @param oilFuelPrice Price per gallon
    //     * @return CO2 emissions per year (in pounds or grams)
    //     */
    //
    //    public double fuelOil(double amount, double oilFuelPrice) {
    //        double oilFuelFactor = 22.37;
    //        double averageOilFuelPrice = 2.88;
    //
    //        if (oilFuelPrice == 0) {
    //            oilFuelPrice = averageOilFuelPrice;
    //        }
    //
    //        double toReturn = (amount / oilFuelPrice) * oilFuelFactor * 12;
    //
    //        if (metrics == 2) {
    //            return poundsToKilograms(toReturn);
    //        }
    //
    //        return toReturn;
    //    }
    //
    //    /**
    //     * Propane CO2 emissions.
    //     *
    //     * @param amount Propane monthly cost
    //     * @param propanePrice Price per gallon
    //     * @return CO2 emissions per year (in grams or pounds)
    //     */
    //    public double propane(double amount, double propanePrice) {
    //        double propaneFactor = 12.17;
    //        double averagePropanePrice = 2.76;
    //
    //        if (propanePrice == 0) {
    //            propanePrice = averagePropanePrice;
    //        }
    //
    //        double toReturn = (amount / propanePrice) * propaneFactor * 12;
    //
    //        if (metrics == 2) {
    //            return poundsToKilograms(toReturn);
    //        }
    //
    //        return toReturn;
    //    }

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
     * Pounds to kilograms converter.
     * @param pounds Quantity in pounds
     * @return Quantity in kilograms
     */
    public double poundsToKilograms(double pounds) {
        return pounds * 0.45359237;
    }


    /**
     * Method calculates the CO2 saved from using solar panels.
     * @param kwhSaved Kilowatts-hour saved
     * @return Quantity of CO2 saved in kilograms
     */
    public double solarPanel(double kwhSaved) {

        //Europe' average kgs per kwh of electricity
        double electricityFactor = 0.51;

        return 0.51 * kwhSaved;

    }

    /**
     *  Converter for the home heat consumption. Coverts the kWh
     *  into CO2 for electric boilers.
     * @return grams of CO2 per kWh for electric based boiler.
     */
    public static double energyToCarbonElectric() {
        //expected value in 2020
        return 250.0;
    }

    /**
     *  Converter for the home heat consumption. Coverts the kWh
     *  into CO2 for non-electric boilers.
     * @return grams of CO2 per kWh for non-electric based boiler.
     */
    public static double energyToCarbonNonElectric() {
        //Oil boilers: 430.0
        //Gas boilers: 295.0
        //Bio-sourced gases: 60.0
        //Biomass boilers: 102.5
        //average:
        return 221.875;
    }

    /**
     * Calculated the grams of CO2 equal to the given kWh.
     * @param energyKiloWattHourAverage amount of kWh consumed by user originally.
     * @param energyKiloWattHourCurrent amount of kWh consumed by user now.
     * @param energyType type of energy to
     *                   power the boiler (electric or non-electric).
     * @return CO2 saved with the given kWh in kg.
     */
    public int homeHeatConsumptionSaved(double energyKiloWattHourAverage,
                                           double energyKiloWattHourCurrent, String energyType) {
        double energyKiloWattHour = energyKiloWattHourAverage - energyKiloWattHourCurrent;

        if (energyType.equalsIgnoreCase("Electric")) {
            double co2 = energyToCarbonElectric();
            return (int)(energyKiloWattHour * co2) / 1000;
        }

        double co2 = energyToCarbonNonElectric();
        return (int)(energyKiloWattHour * co2) / 1000;
    }

    /** Calculated how many kg of carbon dioxide is saved during travelling
     * a certain distance by using public transport instead of the car.
     * @param typeCar the type of the car of the user.
     * @param typePublicTransport the type of public transport the user has used.
     * @param distance the distance the user has travelled.
     * @return the saved amount of kg of carbon dioxide by using the specified type
     *         of public transport instead of using the specified type of car.
     */
    public double publicTransportCalculator(String typeCar,
                                            String typePublicTransport, double distance) {

        distance = kilometersToMiles(distance);

        String vehicle = "";

        Form formCar = new Form();
        formCar.param("household_size", "4");
        formCar.param("home_type", "3");

        if (typeCar.equals("Fossil")) {
            vehicle = "5";
        } else {
            throw new IllegalArgumentException("Please insert a valid car type!");
        }

        formCar.param("vehicle_type[]", vehicle);
        formCar.param("vehicle_mileage[]", Double.toString(distance));


        Form formPublicTransport = new Form();
        formPublicTransport.param("household_size", "4");
        formPublicTransport.param("home_type", "3");

        if (typePublicTransport.equals("CityBus")) {
            formPublicTransport.param("bus_city", Double.toString(distance));
        } else if (typePublicTransport.equals("IntercityBus")) {
            formPublicTransport.param("bus_inter", Double.toString(distance));
        } else if (typePublicTransport.equals("Subway")) {
            formPublicTransport.param("subway", Double.toString(distance));
        } else if (typePublicTransport.equals("Train")) {
            formPublicTransport.param("train", Double.toString(distance));
        } else {
            throw new IllegalArgumentException(
                    "Please insert a valid public transport type!");
        }


        double carbonCar = carbonFootprintApi(formCar);
        double carbonPublicTransport = carbonFootprintApi(formPublicTransport) / 52.177;

        double savedInLbs = carbonCar - carbonPublicTransport;

        double savedInKilogram = savedInLbs * 0.45359237;
        return savedInKilogram;
    }

    //    /** Calculated how many kg of carbon dioxide is saved during travelling
    //     * a certain distance by using public transport instead of the car.
    //     * @param typeCar the type of the car of the user.
    //     * @param typePublicTransport the type of public transport the user has used.
    //     * @param distance the distance the user has travelled.
    //     * @return the saved amount of kg of carbon dioxide by using the specified type
    //     *         of public transport instead of using the specified type of car.
    //     */
    //    public double publicTransportCalculator(String typeCar,
    //                                            String typePublicTransport, double distance) {
    //
    //        distance = kilometersToMiles(distance);
    //
    //        String vehicle = "";
    //
    //        Form formCar = new Form();
    //        formCar.param("household_size", "4");
    //        formCar.param("home_type", "3");
    //
    //        switch (typeCar) {
    //            case "Fossil":
    //                vehicle = "5";
    //                break;
    //            default:
    //                throw new IllegalArgumentException("Please insert a valid car type!");
    //        }
    //
    //
    //        formCar.param("vehicle_type[]", vehicle);
    //        formCar.param("vehicle_mileage[]", Double.toString(distance));
    //
    //
    //        Form formPublicTransport = new Form();
    //        formPublicTransport.param("household_size", "4");
    //        formPublicTransport.param("home_type", "3");
    //
    //        switch (typePublicTransport) {
    //            case "CityBus": formPublicTransport
    //              .param("bus_city", Double.toString(distance));
    //                break;
    //            case "IntercityBus": formPublicTransport
    //              .param("bus_inter", Double.toString(distance));
    //                break;
    //            case "Subway": formPublicTransport
    //              .param("subway", Double.toString(distance));
    //                break;
    //            case "Train": formPublicTransport
    //              .param("train", Double.toString(distance));
    //                break;
    //            default: throw new IllegalArgumentException(
    //                    "Please insert a valid public transport type!");
    //        }
    //
    //        double carbonCar = carbonFootprintApi(formCar);
    //        double carbonPublicTransport = (carbonFootprintApi(formPublicTransport) / 52.177);
    //
    //        double savedInLbs = carbonCar - carbonPublicTransport;
    //
    //        double savedInKilogram = savedInLbs * 0.45359237;
    //        return savedInKilogram;
    //    }

    /**
     * Calculates the CO2 saved by biking.
     * @param type the type of car user is substituting
     * @param mileage the distance traveled by bike
     * @return CO2 saved by biking
     */
    public double bike(String type, double mileage) {
        mileage = kilometersToMiles(mileage);

        boolean error = false;

        String vehicle = "";

        String transport = "";

        Form form = null;

        if (type.equals("Hybrid")) {
            vehicle = "1";
        }

        if (type.equals("Fossil")) {
            vehicle = "5";
        }

        if (type.equals("Electric")) {
            vehicle = "3";
        }

        if (type.equals("Motorcycle")) {
            vehicle = "10";
        }

        if (type.equals("Bus")) {
            form = new Form();
            form.param("bus_city", Double.toString(mileage));
        }

        if (type.equals("Subway")) {
            form = new Form();
            form.param("subway", Double.toString(mileage));
        }

        if (type.equals("Train")) {
            form = new Form();
            form.param("train", Double.toString(mileage));
        }

        if (vehicle.equals("") && form == null) {
            throw new IllegalArgumentException("Please insert a valid type of transport!");
        }

        if (form == null) {
            form = new Form();
        }
        form.param("household_size", "4");
        form.param("home_type", "3");

        if (!vehicle.equals("")) {
            form.param("vehicle_type[]", vehicle);
            form.param("vehicle_mileage[]", Double.toString(mileage));
            return carbonFootprintApi(form) * 0.45359237;
        }

        return (carbonFootprintApi(form) / 52) * 0.45359237;

    }


    /**
     * Calculates the carbon footprint for the public transport through an web API.
     * @param form contains fields for the calculation of the carbon footprint.
     * @return integer with the carbon footprint.
     */
    public double carbonFootprintApi(Form form) {
        Client client = ClientBuilder.newClient();
        WebTarget wt = client.target("http://carbonfootprint.c2es.org/api/footprint");

        Response resp = wt.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                        Response.class);



        String result = resp.readEntity(String.class);

        JSONObject jo = new JSONObject(result);
        return jo.getByPath("data.footprint", Double.class);
    }

    /**
     * Converts kilometers to miles.
     * @param kilometers to be converted to miles.
     * @return the corresponding amount of miles.
     */
    public double kilometersToMiles(double kilometers) {
        return  kilometers * 0.621371192;
    }

    /**
     * Get the CO2 emission of the veganmeal feature.
     *
     * @param amount Kilograms of meal type
     * @param type Meal type
     * @return 10 times the CO2 emission
     */
    public double veganmeal_Calculator(double amount, String type) {

        double sort = 0;

        if (type.equals("Fruit")) {
            //fruit
            sort = 1.1;
        } else if (type.equals("Dairy")) {
            //dairy
            sort = 7.7;
        } else if (type.equals("Vegetables")) {
            //vegetables
            sort = 2.0;
        } else if (type.equals("Meat")) {
            //meat
            sort = 21.3;
        } else if (type.equals("Eggs")) {
            sort = 4.8;
        } else {
            throw new IllegalArgumentException("Please insert a valid type of Food!");
        }
        return amount * sort;
    }


    /**
     * Get the CO2 emission of the localproduce feature.
     *
     * @param amount  of meals
     * @param type   of meal
     * @return 10 times the CO2 emission
     */
    public double localproduce_Calculator(double amount, String type) {

        double sort = 0;

        if (type.equals("Fruit")) {
            //fruit
            sort = 1.1 * 0.07;
        } else if (type.equals("Dairy")) {
            //dairy
            sort = 7.7 * 0.07;
        } else if (type.equals("Vegetables")) {
            //vegetables
            sort = 2.0 * 0.07;
        } else if (type.equals("Meat")) {
            //meat
            sort = 21.3 * 0.07;
        } else if (type.equals("Eggs")) {
            sort = 4.8 * 0.07;
        } else {
            throw new IllegalArgumentException("Please insert a valid type of Food!");
        }

        return amount * sort;
    }

    //    public static void main(String[] args) {
    //        CarbonCalculator cc = new CarbonCalculator(2);
    //        System.out.println(cc.bike("Hybrid", 1));
    //    }

}
