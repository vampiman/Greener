package serverside;

public class Resource {

    int total;
    int distance;
    int percentage;
    int produce;
    int publicTransport;
    int heatConsumption;

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

    public int getTotal_publicTransport() {
        return publicTransport;
    }

    public void setTotal_publicTransport(int publicTransport) {
        this.publicTransport = publicTransport;
    }

    public int getTotal_heatConsumption() {
        return heatConsumption;
    }

    public void setTotal_heatConsumption(int heatConsumption) {
        this.heatConsumption = heatConsumption;
    }


}