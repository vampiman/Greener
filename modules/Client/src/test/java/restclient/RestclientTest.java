package restclient;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;




public class RestclientTest {


    @Test
    public void main() {
    }

    /**
     * Test for inserting the username.
     */
    @Test
    public void getUserName() {
        InputStream in = new ByteArrayInputStream("test".getBytes());

        System.setIn(in);

        assertEquals("test", Restclient.getUserName());
    }
}