package serverside;

import com.sun.xml.internal.ws.client.RequestContext;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Calendar;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static serverside.JwtVerifier.KEY;
import static serverside.JwtVerifier.KEY_VALIDATE;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.crypto.*"})
@PrepareForTest(JwtVerifier.class)
public class JwtVerifierTest {
    private String correctCredentials, correctToken, oldToken;
    private String password;

    @Mock
    Connection mockConnection;
    Statement mockStatement;
    ResultSet mockRS;
    RequestContext mockRC;


    @InjectMocks
    JwtVerifier jv = new JwtVerifier();

    @Before
    public void setup() {
        password = DigestUtils.sha256Hex("password");
        correctCredentials = Jwts.builder()
                .claim("Email", "nstruharova@tudelft.nl")
                .claim("Password", password)
                .signWith(KEY)
                .compact();

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR, today.get(Calendar.HOUR) + 1);
        correctToken = Jwts.builder()
                .setSubject("nstruharova@tudelft.nl")
                .setExpiration(today.getTime())
                .signWith(KEY_VALIDATE)
                .compact();

        Calendar yesterday = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH) - 1);
        oldToken = Jwts.builder()
                .setSubject("nstruharova@tudelft.nl")
                .setExpiration(yesterday.getTime())
                .signWith(KEY_VALIDATE)
                .compact();

        String url = "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false";
        String user = "sammy";
        String pass = "temporary";

        try {
            mockStatic(DriverManager.class);
            mockConnection = mock(Connection.class);
            mockStatement = mock(Statement.class);
            mockRS = mock(ResultSet.class);
            when(DriverManager.getConnection(url, user, pass)).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(any(String.class))).thenReturn(mockRS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void issueJwtToken() {
        try {
            when(mockRS.getString("Password")).thenReturn(password);
            Assert.assertEquals(correctToken, jv.issueJwt(correctCredentials));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void issueJwtToken2() {
        try {
            when(mockRS.getString("Password")).thenReturn("wrongpassword");
            Assert.assertEquals("ERROR", jv.issueJwt(correctCredentials));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void verifyJwtTokenCorrect() {
        Assert.assertEquals("nstruharova@tudelft.nl", jv.verifyJwt(correctToken));
    }

    @Test
    public void verifyJwtTokenIncorrect() {
        Assert.assertEquals("ERROR", jv.verifyJwt(oldToken));
    }

    @Test
    public void filterVerifySuccess() {
       // Assert.assertEquals("ERROR", filter());
    }

    @Test
    public void filterIssueSuccess() {
        Assert.assertEquals("ERROR", jv.verifyJwt(oldToken));
    }

    @Test
    public void filterIssueFail() {
        Assert.assertEquals("ERROR", jv.verifyJwt(oldToken));
    }
}
