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
        re.setTotal_Meals(2);
        Assert.assertEquals(re.getTotal_Meals(),2);
    }

    /**
     * Test for the "total" field setter.
     */
    @Test
    public void setTotal_Meals() {
        Resource re = new Resource();
        re.setTotal_Meals(3);
        Assert.assertEquals(re.getTotal_Meals(),3);
    }

    /**
     * Test for the "total" field getter.
     */
    @Test
    public void getTotal_Distance() {
        Resource re = new Resource();
        re.setTotal_Distance(2);
        Assert.assertEquals(re.getTotal_Distance(),2);
    }

    /**
     * Test for the "total" field setter.
     */
    @Test
    public void setTotal_Distance() {
        Resource re = new Resource();
        re.setTotal_Distance(3);
        Assert.assertEquals(re.getTotal_Distance(),3);
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
        vm.setTotal_Produce(2);
        Assert.assertEquals(vm.getTotal_Produce(),2);
    }

    /**
     * Test for the "total" field setter.
     */
    @Test
    public void setTotal_Produce() {
        Resource vm = new Resource();
        vm.setTotal_Produce(3);
        Assert.assertEquals(vm.getTotal_Produce(),3);
    }

}