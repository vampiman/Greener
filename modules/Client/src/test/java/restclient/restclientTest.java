package restclient;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class restclientTest {


    @Test
    public void main() {
    }

    @Test
    public void getUserName() {
        InputStream in = new ByteArrayInputStream("test".getBytes());

        System.setIn(in);

        assertEquals("test", restclient.getUserName());
    }
}