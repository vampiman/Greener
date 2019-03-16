package serverside;

import org.junit.Assert;
import org.junit.Test;

public class VeganMealResourceTest {

    /**
     * Test for the "total" field getter.
     */
    @Test
    public void getTotal() {
        VeganMealResource vm = new VeganMealResource();
        vm.setTotal(2);
        Assert.assertEquals(vm.getTotal(),2);
    }

    /**
     * Test for the "total" field setter.
     */
    @Test
    public void setTotal() {
        VeganMealResource vm = new VeganMealResource();
        vm.setTotal(3);
        Assert.assertEquals(vm.getTotal(),3);
    }
}
