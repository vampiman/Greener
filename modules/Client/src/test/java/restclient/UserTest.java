package restclient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.when;
import static restclient.User.KEY;

import cn.hutool.json.JSONObject;
import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;








public class UserTest {

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

    private JSONObject jo;

    /**
     * Method for preparing the mocks.
     */
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

        when(client.target(any(String.class))).thenReturn(target);
        when(target.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.header(eq("Authorization"), any(String.class))).thenReturn(builder);
        //REGISTER
        when(builder.post(Entity.json(any()))).thenReturn(response);
        //LOGIN
        when(builder.get(Response.class)).thenReturn(response);

        when(response.readEntity(JSONObject.class)).thenReturn(jo);

    }

    /**
     * Method that tests the "token" field getter.
     */
    @Test
    public void getToken() {
        User user = new User("someEmail", "somePassword");
        user.setToken("abc");
        Assert.assertEquals("abc", user.getToken());
    }

    /**
     * Method that tests the "token" field setter.
     */
    @Test
    public void setToken() {
        User user = new User("someEmail", "somePassword");
        user.setToken("def");
        Assert.assertEquals("def", user.getToken());
    }

    /**
     * Method that tests the "credentials" field getter.
     */
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

    /**
     * Method that tests the "credentials" field setter.
     */
    @Test
    public void setCredentials() {
        User user = new User("someEmail", "somePassword");
        user.setCredentials("test");
        Assert.assertEquals("test", user.getCredentials());
    }

    /**
     * Method that tests the "client" field getter.
     */
    @Test
    public void getClient() {
        User user = new User("someEmail", "somePassword");
        Client client = ClientBuilder.newClient();
        user.setClient(client);
        Assert.assertSame(client, user.getClient());
    }

    /**
     * Method that tests the login request with null token.
     */
    @Test
    public void login() {
        Assert.assertTrue(mockUser.login(null));

        jo.append("tok", "abs");
        mockUser.setToken(null);
        Mockito.verify(client).target(any(String.class));
        Mockito.verify(response).readEntity(JSONObject.class);
    }

    /**
     * Method that tests the login request with a token.
     */
    @Test
    public void loginNotNull() {
        Assert.assertTrue(mockUser.login("Something"));
        Mockito.verify(client).target(any(String.class));
        Mockito.verify(response).readEntity(JSONObject.class);
    }

    /**
     * Method that tests the register request.
     */
    @Test
    public void registerCorrect() {
        when(response.getStatus()).thenReturn(200);

        mockUser.register("Nat", "mymail@gmail.com", "pwd");
        Mockito.verify(client).target(any(String.class));
        Mockito.verify(response).readEntity(JSONObject.class);
    }

    /**
     * Method that tests the register request.
     */
    @Test
    public void registerNull() {
        when(response.getStatus()).thenReturn(500);
        Assert.assertNull(
                mockUser.register("Nat", "mymail@gmail.com", "pwd"));
        Mockito.verify(client).target(any(String.class));
        Mockito.verify(response).getStatus();
    }

    /**
     * Method that tests the wrong email register request.
     */
    @Test(expected = IllegalArgumentException.class)
    public void registerWrongMail() {
        mockUser.register("Nat", "hello", "pwd");
    }

    /**
     * Method that tests the formAuth with a null token.
     */
    @Test
    public void formAuthHeaderTokenNull() {
        User user = new User("mail", "123");
        String auth = user.formAuthHeader();
        Assert.assertEquals(auth, user.getCredentials());
    }

    /**
     * Method that tests the formAuth with a non-null token.
     */
    @Test
    public void formAuthHeaderTokenNotNull() {
        User user = new User("mail", "123");
        user.setToken("token");
        Assert.assertEquals(user.formAuthHeader(), user.getToken());
    }

    /**
     * Method that tests the adjustToken with a token.
     */
    @Test
    public void adjustTokenTokenPresent() {
        User user = new User("", "");
        JSONObject jo = new JSONObject();
        jo.append("token", "[[abc]]");
        user.adjustToken(jo);
        Assert.assertEquals("abc", user.getToken());
    }

    /**
     * Method that tests the adjustToken with a missing token.
     */
    @Test
    public void adjustTokenTokenNotPresent() {
        User user = new User("", "");
        JSONObject jo = new JSONObject();
        jo.append("tok", "abs");
        user.setToken("1");
        user.adjustToken(jo);
        Assert.assertEquals("1", user.getToken());
    }

    /**
     * Method that tests the adjustToken with a missing token
     * but something existing.
     */
    @Test
    public void adjustTokenTokenNotPresentSecond() {
        User user = new User("", "");
        JSONObject jo = new JSONObject();
        jo.append("tok", "abs");
        user.adjustToken(jo);
        Assert.assertNull(user.getToken());
    }


}