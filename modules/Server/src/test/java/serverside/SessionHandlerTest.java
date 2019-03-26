package serverside;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SessionHandlerTest {

    @Test
    public void inviteGenerator() {
        String a = new SessionHandler().inviteGenerator();

        Assert.assertEquals(15, a.length());
    }

    @Test
    public void register() {
    }

    @Test
    public void login() {
    }
}