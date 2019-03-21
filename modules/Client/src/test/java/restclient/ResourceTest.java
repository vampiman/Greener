package restclient;

import org.junit.Assert;
import org.junit.Test;

public class ResourceTest {

    /**
     * Test for the "total" field getter.
     */
    @Test
    public void getTotal_Maels() {
        Resource vm = new Resource();
        vm.setTotal_Meals(2);
        Assert.assertEquals(vm.getTotal_Meals(),2);
    }

    /**
     * Test for the "total" field setter.
     */
    @Test
    public void setTotal_Meals() {
        Resource vm = new Resource();
        vm.setTotal_Meals(3);
        Assert.assertEquals(vm.getTotal_Meals(),3);
    }

    /**
     * Test for the "total" field getter.
     */
    @Test
    public void getTotal_Prduce() {
        Resource vm = new Resource();
        vm.setTotal_Produce(2);
        Assert.assertEquals(vm.getTotal_Produce(),2);
    }

    /**
     * Test for the "total" field getter.
     */
    @Test
    public void getTotal_Distance() {
        Resource vm = new Resource();
        vm.setTotal_Distance(2);
        Assert.assertEquals(vm.getTotal_Distance(),2);
    }

    /**
     * Test for the "total" field getter.
     */
    @Test
    public void getTotal_Percentage() {
        Resource vm = new Resource();
        vm.setTotal_Percentage(2);
        Assert.assertEquals(vm.getTotal_Percentage(),2);
    }

}