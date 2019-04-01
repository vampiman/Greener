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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
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
    private String correctCredentials, incorrectCredentials, correctToken, oldToken;
    private String password;

    @Mock
    private Connection mockConnection;
    private Statement mockStatement;
    private ResultSet mockRS;
    private ContainerRequestContext mockRC;
    private MultivaluedMap mockMM;
    private UriInfo mockURI;

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

        incorrectCredentials = Jwts.builder()
                .claim("Email", "nstruharova@tudelft.nl")
                .claim("Password", "pwd")
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
            mockConnection = mock(Connection.class);
            mockStatement = mock(Statement.class);
            mockRS = mock(ResultSet.class);
            mockRC = mock(ContainerRequestContext.class);
            mockMM = mock(MultivaluedMap.class);
            mockURI = mock(UriInfo.class);
            mockStatic(DriverManager.class);
            when(DriverManager.getConnection(url, user, pass)).thenReturn(mockConnection);

            when(mockRC.getUriInfo()).thenReturn(mockURI);
            when(mockURI.getPath()).thenReturn("test/testing");
            when(mockRC.getHeaders()).thenReturn(mockMM);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(any(String.class))).thenReturn(mockRS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void issueJwtToken() {
        try {
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(any(String.class))).thenReturn(mockRS);
            when(mockRS.getString("Password")).thenReturn(password);
            Assert.assertNotEquals("ERROR", jv.issueJwt(correctCredentials));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void issueJwtToken2() {
        try {
            when(mockStatement.executeQuery(any(String.class))).thenReturn(mockRS);
            when(mockRS.getString("Password")).thenReturn("wrongpassword");
            Assert.assertEquals("ERROR", jv.issueJwt(incorrectCredentials));
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
        when(mockMM.get("Authorization")).thenReturn(Collections.singletonList(correctToken));
        jv.filter(mockRC);
    }

    @Test
    public void filterVerifyFail() {
        when(mockMM.get("Authorization")).thenReturn(Collections.singletonList(oldToken));
        jv.filter(mockRC);
        ArgumentCaptor<Response> argumentCaptor = ArgumentCaptor.forClass(Response.class);
        verify(mockRC).abortWith(argumentCaptor.capture());
    }
    @Test
    public void filterIssueSuccess() {
        try {
            when(mockRS.getString("Password")).thenReturn(password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        when(mockMM.get("Authorization")).thenReturn(Collections.singletonList(correctCredentials));
        jv.filter(mockRC);
    }

    @Test
    public void filterIssueFail() {
        try {
            when(mockRS.getString("Password")).thenReturn("whatever");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        when(mockMM.get("Authorization")).thenReturn(Collections.singletonList(incorrectCredentials));
        jv.filter(mockRC);
    }

    @Test
    public void filterIssueFailSQLExc() {
        try {
            when(mockStatement.executeQuery(any(String.class))).thenThrow(SQLException.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        when(mockMM.get("Authorization")).thenReturn(Collections.singletonList(incorrectCredentials));
        jv.filter(mockRC);

        ArgumentCaptor<Response> argumentCaptor = ArgumentCaptor.forClass(Response.class);
        verify(mockRC).abortWith(argumentCaptor.capture());
    }
}
