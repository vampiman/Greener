package restclient;

import cn.hutool.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class UserTest {

    private JSONObject jo;

    public void prepareJson() {

    }

    @Mock
    WebTarget target;

    @Mock
    Invocation.Builder builder;

    @Mock
    Client client;

    @Mock
    Response response;

    @InjectMocks
    User user;

    @Before
    public void setup() {
        client = mock(Client.class);
        User user = new User("someEmail", "somePassword");

        client = mock(Client.class);
        target = mock(WebTarget.class);
        builder = mock(Invocation.Builder.class);
        response = mock(Response.class);

        when(client.target(anyString())).thenReturn(target);
        when(target.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get(Response.class)).thenReturn(response);
        when(response.readEntity(JSONObject.class)).thenReturn(jo);
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
    public void login() {
    }

    @Test
    public void register() {
    }

    @Test
    public void formAuthHeader() {
    }

    @Test
    public void adjustToken() {
    }
}