package restclient;

import org.junit.Assert;
import org.junit.Test;

public class SessionResourceTest {

    /**
     * Test for the "email" field getter.
     */
    @Test
    public void getEmail() {
        SessionResource re = new SessionResource();
        re.setEmail("email");
        Assert.assertEquals(re.getEmail(), "email");
    }

    /**
     * Test for the "email" field setter.
     */
    @Test
    public void setEmail() {
        SessionResource re = new SessionResource();
        re.setEmail("someemail");
        Assert.assertEquals(re.getEmail(), "someemail");
    }

    /**
     * Test for the "password" field getter.
     */
    @Test
    public void getPassword() {
        SessionResource re = new SessionResource();
        re.setPassword("password");
        Assert.assertEquals(re.getPassword(), "password");
    }

    /**
     * Test for the "password" field setter.
     */
    @Test
    public void setPassword() {
        SessionResource re = new SessionResource();
        re.setPassword("somepassword");
        Assert.assertEquals(re.getPassword(), "somepassword");
    }

    /**
     * Test for the "name" field getter.
     */
    @Test
    public void getName() {
        SessionResource re = new SessionResource();
        re.setName("Name");
        Assert.assertEquals(re.getName(), "Name");
    }

    /**
     * Test for the "name" field setter.
     */
    @Test
    public void setName() {
        SessionResource re = new SessionResource();
        re.setName("someName");
        Assert.assertEquals(re.getName(), "someName");
    }

    /**
     * Test for the "token" field getter.
     */
    @Test
    public void getToken() {
        SessionResource re = new SessionResource();
        re.setToken("Token");
        Assert.assertEquals(re.getToken(), "Token");
    }

    /**
     * Test for the "token" field setter.
     */
    @Test
    public void setToken() {
        SessionResource re = new SessionResource();
        re.setToken("someToken");
        Assert.assertEquals(re.getToken(), "someToken");
    }
}