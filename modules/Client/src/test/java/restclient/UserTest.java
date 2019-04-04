package restclient;

import cn.hutool.json.JSONObject;
import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import static restclient.User.KEY;


public class UserTest {

    private JSONObject jo;

    @Mock
    WebTarget target;

    @Mock
    Invocation.Builder builder;

    @Mock
    Client client;

    @Mock
    Response response;

    @InjectMocks
    User mockUser;

    @Before
    public void setup() {
        jo = new JSONObject();
        jo.append("token", "123");

        mockUser = new User("mymail@gmail.com", "pwd");
        client = Mockito.mock(Client.class);
        mockUser.setClient(client);

        target = Mockito.mock(WebTarget.class);
        builder = Mockito.mock(Invocation.Builder.class);
        response = Mockito.mock(Response.class);

        Mockito.when(client.target(any(String.class))).thenReturn(target);
        Mockito.when(target.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        Mockito.when(builder.header(eq("Authorization"), any(String.class))).thenReturn(builder);
        //REGISTER
        Mockito.when(builder.post(Entity.json(any()))).thenReturn(response);
        //LOGIN
        Mockito.when(builder.get(Response.class)).thenReturn(response);

        Mockito.when(response.readEntity(JSONObject.class)).thenReturn(jo);

    }

    @Test
    public void getToken() {
        User user = new User("someEmail", "somePassword");
        user.setToken("abc");
        Assert.assertEquals("abc", user.getToken());
    }

    @Test
    public void setToken() {
        User user = new User("someEmail", "somePassword");
        user.setToken("def");
        Assert.assertEquals("def", user.getToken());
    }

    @Test
    public void getCredentials() {
        User user = new User("someEmail", "somePassword");
        String hashedPassword = DigestUtils.sha256Hex("somePassword");
        String expected = Jwts.builder()
                .claim("Email", "someEmail")
                .claim("Password", hashedPassword)
                .signWith(KEY)
                .compact();
        Assert.assertEquals(expected, user.getCredentials());
    }

    @Test
    public void setCredentials() {
        User user = new User("someEmail", "somePassword");
        user.setCredentials("test");
        Assert.assertEquals("test", user.getCredentials());
    }

    @Test
    public void getClient() {
        User user = new User("someEmail", "somePassword");
        Client client = ClientBuilder.newClient();
        user.setClient(client);
        Assert.assertSame(client, user.getClient());
    }

    @Test
    public void login() {
        Assert.assertTrue(mockUser.login(null));
        Mockito.verify(client).target(any(String.class));
        Mockito.verify(response).readEntity(JSONObject.class);
    }

    @Test
    public void registerCorrect() {
        mockUser.register("Nat", "mymail@gmail.com", "pwd");
        Mockito.verify(client).target(any(String.class));
        Mockito.verify(response).readEntity(JSONObject.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerWrongMail() {
        mockUser.register("Nat", "hello", "pwd");
    }

    @Test
    public void formAuthHeaderTokenNull() {
        User user = new User("mail", "123");
        String auth = user.formAuthHeader();
        Assert.assertEquals(auth, user.getCredentials());
    }

    @Test
    public void formAuthHeaderTokenNotNull() {
        User user = new User("mail", "123");
        user.setToken("token");
        Assert.assertEquals(user.formAuthHeader(), user.getToken());
    }

    @Test
    public void adjustTokenTokenPresent() {
        User user = new User("", "");
        JSONObject jo = new JSONObject();
        jo.append("token", "[[abc]]");
        user.adjustToken(jo);
        Assert.assertEquals("abc", user.getToken());
    }

    @Test
    public void adjustTokenTokenNotPresent() {
        User user = new User("", "");
        JSONObject jo = new JSONObject();
        jo.append("tok", "abs");
        user.adjustToken(jo);
        Assert.assertNull(user.getToken());
    }

}