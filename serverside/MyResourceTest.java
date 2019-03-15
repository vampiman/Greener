package serverside;

import org.junit.Assert;
import org.junit.Test;

public class MyResourceTest {

    @Test
    public void getIt() {
        Assert.assertEquals("Got it!", new MyResource().getIt());
    }


    /**
     * Tests equality between the MyResource.connection() method output
     * and a pre-set string.
     * Expects true.
     */
    @Test
    public void connectionCount() {
        MyResource res = new MyResource();
        int count = res.getCount() + 1;

        Assert.assertEquals("Our services were accessed " + count
                + " time(s) today!", res.connection());
    }
}