package server;

import org.junit.Assert;
import org.junit.Test;



public class MyResourceTest {

    @Test
    public void getIt() {
        Assert.assertEquals("Got it!", new MyResource().getIt());
    }

    @Test
    public void connectionCount() {
        MyResource res = new MyResource();
        int count = res.getCount() + 1;

        Assert.assertEquals("Our services were accessed " + count + " time(s) today!", res.connection());
    }
}