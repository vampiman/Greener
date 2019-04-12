package serverside;

import org.junit.Assert;
import org.junit.Test;

public class ResourceTest {

    /**
     * Test for the "total" field getter.
     */
    @Test
    public void getTotal_Meals() {
        Resource re = new Resource();
        re.setTotal_Meals(2.0);
        Assert.assertEquals(re.getTotal_Meals().intValue(), 2);
    }

    /**
     * Test for the "total" field setter.
     */
    @Test
    public void setTotal_Meals() {
        Resource re = new Resource();
        re.setTotal_Meals(3.0);
        Assert.assertEquals(re.getTotal_Meals().intValue(),3);
    }

    /**
     * Test for the "total" field getter.
     */
    @Test
    public void getTotal_Distance() {
        Resource re = new Resource();
        re.setTotal_Distance(2.0);
        Assert.assertEquals(re.getTotal_Distance().intValue(),2);
    }

    /**
     * Test for the "total" field setter.
     */
    @Test
    public void setTotal_Distance() {
        Resource re = new Resource();
        re.setTotal_Distance(3.0);
        Assert.assertEquals(re.getTotal_Distance().intValue(),3);
    }

    /**
     * Test for the "total" field getter.
     */
    @Test
    public void getTotal_Percentage() {
        Resource re = new Resource();
        re.setTotal_Percentage(2);
        Assert.assertEquals(re.getTotal_Percentage(),2);
    }

    /**
     * Test for the "total" field setter.
     */
    @Test
    public void setTotal_Percentage() {
        Resource re = new Resource();
        re.setTotal_Percentage(3);
        Assert.assertEquals(re.getTotal_Percentage(),3);
    }

    /**
     * Test for the "total" field getter.
     */


    @Test
    public void getTotal_Produce() {
        Resource vm = new Resource();
        vm.setTotal_Produce(2.0);
        Assert.assertEquals(vm.getTotal_Produce().intValue(),2);
    }

    /**
     * Test for the "total" field setter.
     */
    @Test
    public void setTotal_Produce() {
        Resource vm = new Resource();
        vm.setTotal_Produce(3.0);
        Assert.assertEquals(vm.getTotal_Produce().intValue(),3);
    }

    /**
     * Test for the "local" field getter.
     */
    @Test
    public void getLocalSaved() {
        Resource re = new Resource();
        re.setLocalSaved(2.0);
        Assert.assertEquals(re.getLocalSaved().intValue(), 2);
    }

    /**
     * Test for the "local" field setter.
     */
    @Test
    public void setLocalSaved() {
        Resource re = new Resource();
        re.setLocalSaved(3.0);
        Assert.assertEquals(re.getLocalSaved().intValue(), 3);
    }

    /**
     * Test for the "bike" field getter.
     */
    @Test
    public void getBikeSaved() {
        Resource re = new Resource();
        re.setBikeSaved(2.0);
        Assert.assertEquals(re.getBikeSaved().intValue(), 2);
    }

    /**
     * Test for the "bike" field setter.
     */
    @Test
    public void setBikeSaved() {
        Resource re = new Resource();
        re.setBikeSaved(3.0);
        Assert.assertEquals(re.getBikeSaved().intValue(), 3);
    }

    /**
     * Test for the "co2Saved" field getter.
     */
    @Test
    public void getCo2Saved() {
        Resource re = new Resource();
        re.setCo2Saved(2.0);
        Assert.assertEquals(re.getCo2Saved().intValue(), 2);
    }

    /**
     * Test for the "co2Saved" field setter.
     */
    @Test
    public void setCo2Saved() {
        Resource re = new Resource();
        re.setCo2Saved(3.0);
        Assert.assertEquals(re.getCo2Saved().intValue(), 3);
    }

    /**
     * Test for the "Vegan" field getter.
     */
    @Test
    public void getMealType() {
        Resource re = new Resource();
        re.setMealType("Meat");
        Assert.assertEquals(re.getMealType(), "Meat");
    }

    /**
     * Test for the "Vegan" field setter.
     */
    @Test
    public void setMealType() {
        Resource re = new Resource();
        re.setMealType("Rice");
        Assert.assertEquals(re.getMealType(), "Rice");
    }

    /**
     * Test for the "CarType" field getter.
     */
    @Test
    public void getCarType() {
        Resource re = new Resource();
        re.setCarType("Fossil");
        Assert.assertEquals(re.getCarType(), "Fossil");
    }

    /**
     * Test for the "CarType" field setter.
     */
    @Test
    public void setCarType() {
        Resource re = new Resource();
        re.setCarType("Hybrid");
        Assert.assertEquals(re.getCarType(), "Hybrid");
    }

    /**
     * Test for the "PublicTransport" field getter.
     */
    @Test
    public void getSavedPublicTransport() {
        Resource re = new Resource();
        re.setSavedPublicTransport(2.0);
        Assert.assertEquals(re.getSavedPublicTransport().intValue(), 2);
    }

    /**
     * Test for the "PublicTransport" field setter.
     */
    @Test
    public void setSavedPublicTransport() {
        Resource re = new Resource();
        re.setSavedPublicTransport(3.0);
        Assert.assertEquals(re.getSavedPublicTransport().intValue(), 3);
    }

    /**
     * Test for the "AverageHeat" field getter.
     */
    @Test
    public void getAverageHeatConsumption() {
        Resource re = new Resource();
        re.setAverageHeatConsumption(100);
        Assert.assertEquals(re.getAverageHeatConsumption(), 100);
    }

    /**
     * Test for the "AverageHeat" field setter.
     */
    @Test
    public void setAverageHeatConsumption() {
        Resource re = new Resource();
        re.setAverageHeatConsumption(200);
        Assert.assertEquals(re.getAverageHeatConsumption(), 200);
    }

    /**
     * Test for the "CurrentHeat" field getter.
     */
    @Test
    public void getCurrentHeatConsumption() {
        Resource re = new Resource();
        re.setCurrentHeatConsumption(200);
        Assert.assertEquals(re.getCurrentHeatConsumption(), 200);
    }

    /**
     * Test for the "CurrentHeat" field setter.
     */
    @Test
    public void setCurrentHeatConsumption() {
        Resource re = new Resource();
        re.setCurrentHeatConsumption(100);
        Assert.assertEquals(re.getCurrentHeatConsumption(), 100);
    }

    /**
     * Test for the "EnergyType" field getter.
     */
    @Test
    public void getEnergyType() {
        Resource re = new Resource();
        re.setEnergyType("Electric");
        Assert.assertEquals(re.getEnergyType(), "Electric");
    }

    /**
     * Test for the "EnergyType" field setter.
     */
    @Test
    public void setEnergyType() {
        Resource re = new Resource();
        re.setEnergyType("Non-Electric");
        Assert.assertEquals(re.getEnergyType(), "Non-Electric");
    }

    /**
     * Test for the "token" field getter.
     */
    @Test
    public void getToken() {
        Resource re = new Resource();
        re.setToken("token");
        Assert.assertEquals(re.getToken(), "token");
    }

    /**
     * Test for the "token" field setter.
     */
    @Test
    public void setToken() {
        Resource re = new Resource();
        re.setToken("sometoken");
        Assert.assertEquals(re.getToken(), "sometoken");
    }

    /**
     * Test for the "savedHeat" field getter.
     */
    @Test
    public void getSavedHeatConsumption() {
        Resource re = new Resource();
        re.setSavedHeatConsumption(50.0);
        Assert.assertEquals(re.getSavedHeatConsumption().intValue(), 50);
    }

    /**
     * Test for the "savedHeat" field setter.
     */
    @Test
    public void setSavedHeatConsumption() {
        Resource re = new Resource();
        re.setSavedHeatConsumption(100.0);
        Assert.assertEquals(re.getSavedHeatConsumption().intValue(), 100);
    }

    /**
     * Test for the "kwh" field getter.
     */
    @Test
    public void getKwh() {
        Resource re = new Resource();
        re.setKwh(100);
        Assert.assertEquals(re.getKwh(), 100);
    }

    /**
     * Test for the "kwh" field setter.
     */
    @Test
    public void setKwh() {
        Resource re = new Resource();
        re.setKwh(200);
        Assert.assertEquals(re.getKwh(), 200);
    }

    /**
     * Test for the "solar" field getter.
     */
    @Test
    public void getSavedSolar() {
        Resource re = new Resource();
        re.setSavedSolar(200.0);
        Assert.assertEquals(re.getSavedSolar().intValue(), 200);
    }

    /**
     * Test for the "solar" field setter.
     */
    @Test
    public void setSavedSolar() {
        Resource re = new Resource();
        re.setSavedSolar(100.0);
        Assert.assertEquals(re.getSavedSolar().intValue(), 100);
    }

    /**
     * Test for the "mealType2" field getter.
     */
    @Test
    public void getMealType2() {
        Resource re = new Resource();
        re.setMealType2("Meat");
        Assert.assertEquals(re.getMealType2(), "Meat");
    }

    /**
     * Test for the "mealType2" field setter.
     */
    @Test
    public void setMealType2() {
        Resource re = new Resource();
        re.setMealType2("Dairy");
        Assert.assertEquals(re.getMealType2(), "Dairy");
    }

    /**
     * Test for the "userName" field getter.
     */
    @Test
    public void getUserName() {
        Resource re = new Resource();
        re.setUserName("Name");
        Assert.assertEquals(re.getUserName(), "Name");
    }

    /**
     * Test for the "userName" field setter.
     */
    @Test
    public void setUserName() {
        Resource re = new Resource();
        re.setUserName("someName");
        Assert.assertEquals(re.getUserName(), "someName");
    }

    /**
     * Test for the "friendsNo" field getter.
     */
    @Test
    public void getFriendsNo() {
        Resource re = new Resource();
        re.setFriendsNo(5);
        Assert.assertEquals(re.getFriendsNo(), 5);
    }

    /**
     * Test for the "friendsNo" field setter.
     */
    @Test
    public void setFriendsNo() {
        Resource re = new Resource();
        re.setFriendsNo(1);
        Assert.assertEquals(re.getFriendsNo(), 1);
    }

    /**
     * Test for the "email" field getter.
     */
    @Test
    public void getEmail() {
        Resource re = new Resource();
        re.setEmail("email");
        Assert.assertEquals(re.getEmail(), "email");
    }

    /**
     * Test for the "email" field setter.
     */
    @Test
    public void setEmail() {
        Resource re = new Resource();
        re.setEmail("someemail");
        Assert.assertEquals(re.getEmail(), "someemail");
    }

    /**
     * Test for the "level" field getter.
     */
    @Test
    public void getLevel() {
        Resource re = new Resource();
        re.setLevel(2);
        Assert.assertEquals(re.getLevel(), 2);
    }

    /**
     * Test for the "level" field setter.
     */
    @Test
    public void setLevel() {
        Resource re = new Resource();
        re.setLevel(3);
        Assert.assertEquals(re.getLevel(), 3);
    }

    /**
     * Test for the "achievements" field getter.
     */
    @Test
    public void getAchievements() {
        Resource re = new Resource();
        re.setAchievements("000");
        Assert.assertEquals(re.getAchievements(), "000");
    }

    /**
     * Test for the "achievements" field setter.
     */
    @Test
    public void setAchievements() {
        Resource re = new Resource();
        re.setAchievements("010");
        Assert.assertEquals(re.getAchievements(), "010");
    }

    /**
     * Test for the "publicTransport" field getter.
     */
    @Test
    public void getPublicTransportType() {
        Resource re = new Resource();
        re.setPublicTransportType("Bus");
        Assert.assertEquals(re.getPublicTransportType(), "Bus");
    }

    /**
     * Test for the "publicTransport" field setter.
     */
    @Test
    public void setPublicTransportType() {
        Resource re = new Resource();
        re.setPublicTransportType("Car");
        Assert.assertEquals(re.getPublicTransportType(), "Car");
    }
}