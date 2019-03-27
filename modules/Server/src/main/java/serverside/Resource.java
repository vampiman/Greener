package serverside;

public class Resource {

    int total;
    int distance;
    int percentage;
    int produce;
    String publicTransportType;
    String carType;
    int savedPublicTransport;
    int averageHeatConsumption;
    int currentHeatConsumption;
    int savedHeatConsumption;
    String energyType;
    String token;

    public Resource(){

    }

    public int getTotal_Meals() {
        return total;
    }

    public void setTotal_Meals(int total) {
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

    public int getTotal_Produce() {
        return produce;
    }

    public void setTotal_Produce(int produce) {
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

}

