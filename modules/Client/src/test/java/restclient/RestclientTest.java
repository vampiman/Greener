package restclient;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class RestclientTest {


    @Test
    public void main() {
    }

    /**
     * Tests the equality of input and a pre-set string.
     * Expects true.
     */
    @Test
    public void getUserName() {
        InputStream in = new ByteArrayInputStream("test".getBytes());

        System.setIn(in);

        Assert.assertEquals("test", Restclient.getUserName());
    }
}