package serverside;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static serverside.JwtVerifier.KEY;
import static serverside.JwtVerifier.KEY_VALIDATE;

import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collections;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;






@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.crypto.*"})
@PrepareForTest(JwtVerifier.class)
public class JwtVerifierTest {

    private String correctCredentials;
    private String incorrectCredentials;
    private String correctToken;
    private String oldToken;
    private String password;

    @Mock
    private Connection mockConnection;
    private Statement mockStatement;
    private ResultSet mockRS;
    private ContainerRequestContext mockRC;
    private MultivaluedMap mockMM;
    private UriInfo mockUri;

    @InjectMocks
    private JwtVerifier jv = new JwtVerifier();

    /**
     * Method preparing the mocks.
     */
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
            mockUri = mock(UriInfo.class);
            mockStatic(DriverManager.class);
            when(DriverManager.getConnection(url, user, pass)).thenReturn(mockConnection);

            when(mockRC.getUriInfo()).thenReturn(mockUri);
            when(mockUri.getPath()).thenReturn("test/testing");
            when(mockRC.getHeaders()).thenReturn(mockMM);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(any(String.class))).thenReturn(mockRS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method that tests the issuing of a jwt token.
     */
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

    /**
     * Method that tests the issuing of a jwt token with a wrong password.
     */
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

    /**
     * Method that tests the verification of a valid token.
     */
    @Test
    public void verifyJwtTokenCorrect() {
        Assert.assertEquals("nstruharova@tudelft.nl", jv.verifyJwt(correctToken));
    }

    /**
     * Method that tests the verification of an invalid token.
     */
    @Test
    public void verifyJwtTokenIncorrect() {
        Assert.assertEquals("ERROR", jv.verifyJwt(oldToken));
    }

    /**
     * Method that tests the successful filtering of a token.
     */
    @Test
    public void filterVerifySuccess() {
        when(mockMM.get("Authorization")).thenReturn(Collections.singletonList(correctToken));
        jv.filter(mockRC);
    }

    /**
     * Method that tests a failing filtering of a token.
     */
    @Test
    public void filterVerifyFail() {
        when(mockMM.get("Authorization")).thenReturn(Collections.singletonList(oldToken));
        jv.filter(mockRC);
        ArgumentCaptor<Response> argumentCaptor = ArgumentCaptor.forClass(Response.class);
        verify(mockRC).abortWith(argumentCaptor.capture());
    }

    /**
     * Method that tests a filter with issues.
     */
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

    /**
     * Method that tests a failing filter.
     */
    @Test
    public void filterIssueFail() {
        try {
            when(mockRS.getString("Password")).thenReturn("whatever");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        when(mockMM.get("Authorization"))
                .thenReturn(Collections.singletonList(incorrectCredentials));
        jv.filter(mockRC);
    }

    //    @Test
    //    public void filterIssueFailSQLExc() {
    //        try {
    //            when(mockStatement.executeQuery(any(String.class))).thenThrow(SQLException.class);
    //        } catch (SQLException e) {
    //            e.printStackTrace();
    //        }
    //        when(mockMM.get("Authorization"))
    //        .thenReturn(Collections.singletonList(incorrectCredentials));
    //        jv.filter(mockRC);
    //
    //        ArgumentCaptor<Response> argumentCaptor = ArgumentCaptor.forClass(Response.class);
    //        verify(mockRC).abortWith(argumentCaptor.capture());
    //    }
}
