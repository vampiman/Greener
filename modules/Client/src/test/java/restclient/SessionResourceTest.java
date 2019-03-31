package restclient;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SessionResourceTest {

    @Test
    public void getEmail() {
        SessionResource re = new SessionResource();
        re.setEmail("email");
        Assert.assertEquals(re.getEmail(), "email");
    }

    @Test
    public void setEmail() {
        SessionResource re = new SessionResource();
        re.setEmail("someemail");
        Assert.assertEquals(re.getEmail(), "someemail");
    }

    @Test
    public void getPassword() {
        SessionResource re = new SessionResource();
        re.setPassword("password");
        Assert.assertEquals(re.getPassword(), "password");
    }

    @Test
    public void setPassword() {
        SessionResource re = new SessionResource();
        re.setPassword("somepassword");
        Assert.assertEquals(re.getPassword(), "somepassword");
    }

    @Test
    public void getName() {
        SessionResource re = new SessionResource();
        re.setName("Name");
        Assert.assertEquals(re.getName(), "Name");
    }

    @Test
    public void setName() {
        SessionResource re = new SessionResource();
        re.setName("someName");
        Assert.assertEquals(re.getName(), "someName");
    }

    @Test
    public void getToken() {
        SessionResource re = new SessionResource();
        re.setToken("Token");
        Assert.assertEquals(re.getToken(), "Token");
    }

    @Test
    public void setToken() {
        SessionResource re = new SessionResource();
        re.setToken("someToken");
        Assert.assertEquals(re.getToken(), "someToken");
    }
}