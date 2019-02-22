package Server;

import org.junit.Assert;
import org.junit.Test;



public class MyResourceTest {

    @Test
    public void getIt() {
        Assert.assertEquals("Got it!", new MyResource().getIt());
    }
}