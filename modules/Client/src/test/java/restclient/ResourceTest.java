package restclient;

import org.junit.Assert;
import org.junit.Test;

public class ResourceTest {

    /**
     * Test for the "total" field getter.
     */
    @Test
    public void getTotal() {
        Resource vm = new Resource();
        vm.setTotal_Meals(2);
        Assert.assertEquals(vm.getTotal_Meals(),2);
    }

    /**
     * Test for the "total" field setter.
     */
    @Test
    public void setTotal() {
        Resource vm = new Resource();
        vm.setTotal_Meals(3);
        Assert.assertEquals(vm.getTotal_Meals(),3);
    }
}