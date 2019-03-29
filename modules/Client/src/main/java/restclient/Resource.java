package restclient;

public class Resource {

    Double total;
    int distance;
    int percentage;
    Double produce;
    int kwh;
    int savedSolar;
    String publicTransportType;
    String carType;
    int savedPublicTransport;
    int averageHeatConsumption;
    int currentHeatConsumption;
    int savedHeatConsumption;
    String mealType;
    String mealType2;
    String energyType;
    String token;

    public Resource(){

    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public Double getTotal_Meals() {
        return total;
    }

    public void setTotal_Meals(Double total) {
        this.total = total;
    }

    public int getTotal_Distance() {
        return distance;
    }

    public void setTotal_Distance(int distance) {
        this.distance = distance;
    }

    public int getTotal_Percentage() {
        return percentage;
    }

    public void setTotal_Percentage(int percentage) {
        this.percentage = percentage;
    }

    public Double getTotal_Produce() {
        return produce;
    }

    public void setTotal_Produce(Double produce) {
        this.produce = produce;
    }

    public String getPublicTransportType() {
        return publicTransportType;
    }

    public void setPublicTransportType(String publicTransportType) {
        this.publicTransportType = publicTransportType;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public int getSavedPublicTransport() {
        return savedPublicTransport;
    }

    public void setSavedPublicTransport(int savedPublicTransport) {
        this.savedPublicTransport = savedPublicTransport;
    }

    public int getAverageHeatConsumption() {
        return averageHeatConsumption;
    }

    public void setAverageHeatConsumption(int averageHeatConsumption) {
        this.averageHeatConsumption = averageHeatConsumption;
    }

    public int getCurrentHeatConsumption() {
        return currentHeatConsumption;
    }

    public void setCurrentHeatConsumption(int heatConsumption) {
        this.currentHeatConsumption = heatConsumption;
    }

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(String energyType) {
        this.energyType = energyType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSavedHeatConsumption() {
        return savedHeatConsumption;
    }

    public void setSavedHeatConsumption(int savedHeatConsumption) {
        this.savedHeatConsumption = savedHeatConsumption;
    }


    public int getKwh() {
        return kwh;
    }

    public void setKwh(int kwh) {
        this.kwh = kwh;
    }

    public int getSavedSolar() {
        return savedSolar;
    }

    public void setSavedSolar(int savedSolar) {
        this.savedSolar = savedSolar;
    }


    public String getMealType2() {
        return mealType2;
    }

    public void setMealType2(String mealType2) {
        this.mealType2 = mealType2;
    }

}

