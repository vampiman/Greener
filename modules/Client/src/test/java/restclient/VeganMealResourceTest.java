package restclient;

import org.junit.Assert;
import org.junit.Test;

public class VeganMealResourceTest {

    @Test
    public void getTotal() {
        VeganMealResource vm = new VeganMealResource();
        vm.setTotal(2);
        Assert.assertEquals(vm.getTotal(),2);
    }

    @Test
    public void setTotal() {
        VeganMealResource vm = new VeganMealResource();
        vm.setTotal(3);
        Assert.assertEquals(vm.getTotal(),3);
    }
}