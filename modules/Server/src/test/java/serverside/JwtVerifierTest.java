package serverside;

import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Calendar;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class JwtVerifierTest {
    private String correctCredentials, incorrectCredentials, incorrectCredentials2,
            validToken, invalidTokenOld;

    @Mock
    ContainerRequestContext crcValid;
    ContainerRequestContext crcInvalid;
    ContainerRequestContext crcCreds;
    ContainerRequestContext crcBadCreds;
    UriInfo ui;
    MultivaluedMap headers;
    MultivaluedMap credHeaders;
    Connection dbConnect;
    ResultSet rs;
    Statement st;

    @InjectMocks
    JwtVerifier jv = new JwtVerifier();

    @Before
    public void setup() throws SQLException {
        String password = DigestUtils.sha256Hex("somepassword");
        correctCredentials = Jwts.builder()
                .signWith(JwtVerifier.KEY)
                .claim("Email", "robert@yahoo.com")
                .claim("Password", password)
                .compact();

        incorrectCredentials = Jwts.builder()
                .signWith(JwtVerifier.KEY)
                .claim("Email", "natalia@gmail.com")
                .claim("Password", "321")
                .compact();

        incorrectCredentials2 = Jwts.builder()
                .signWith(JwtVerifier.KEY)
                .claim("Email", "nat@gmail.com")
                .claim("Password", "321")
                .compact();

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR, today.get(Calendar.HOUR) + 1);
        validToken = Jwts.builder()
                .setSubject("robert@yahoo.com")
                .setExpiration(today.getTime())
                .signWith(JwtVerifier.KEY_VALIDATE)
                .compact();

        Calendar lastYear = Calendar.getInstance();
        lastYear.set(Calendar.YEAR, 2018);
        invalidTokenOld = Jwts.builder()
                .signWith(JwtVerifier.KEY_VALIDATE)
                .setSubject("rob")
                .setExpiration(lastYear.getTime())
                .compact();

        crcValid = mock(ContainerRequestContext.class);
        crcInvalid = mock(ContainerRequestContext.class);
        crcCreds = mock(ContainerRequestContext.class);
        crcBadCreds = mock(ContainerRequestContext.class);
        ui = mock(UriInfo.class);

        headers = mock(MultivaluedMap.class);
        when(headers.get("Authorization")).thenReturn(Collections.singletonList("Bearer " + validToken));

        credHeaders = mock(MultivaluedMap.class);
        credHeaders = mock(MultivaluedMap.class);
        when(credHeaders.get("Authorization")).thenReturn(Collections.singletonList("Bearer " + correctCredentials));

        try {
            Mockito.when(ui.getRequestUri()).thenReturn(new URI("test/localproduce/testing"));
        } catch (URISyntaxException e) {
            System.out.println("wrong uri");
        }

        Mockito.when(crcValid.getHeaders()).thenReturn(headers);
        Mockito.when(crcValid.getUriInfo()).thenReturn(ui);
        Mockito.when(ui.getPath()).thenReturn("/");
        Mockito.when(crcValid.getHeaders().get("Authorization"))
                .thenReturn(Collections.singletonList("Bearer " + validToken));

        Mockito.when(crcInvalid.getHeaders()).thenReturn(headers);
        Mockito.when(crcInvalid.getUriInfo()).thenReturn(ui);
        Mockito.when(crcInvalid.getHeaders().get("Authorization"))
                .thenReturn(Collections.singletonList("Bearer " + invalidTokenOld));

        Mockito.when(crcCreds.getHeaders()).thenReturn(credHeaders);
        Mockito.when(crcCreds.getUriInfo()).thenReturn(ui);
        Mockito.when(crcCreds.getHeaders().get("Authorization"))
                .thenReturn(Collections.singletonList("Bearer " + correctCredentials));

        Mockito.when(crcBadCreds.getHeaders()).thenReturn(headers);
        Mockito.when(crcBadCreds.getUriInfo()).thenReturn(ui);
        Mockito.when(crcBadCreds.getHeaders().get("Authorization"))
                .thenReturn(Collections.singletonList("Bearer " + incorrectCredentials));

        dbConnect = mock(Connection.class);
        rs = mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(true);
        Mockito.when(rs.getString("Password")).thenReturn(password);
        st = mock(Statement.class);
        Mockito.when(dbConnect.createStatement()).thenReturn(st);
        Mockito.when(st.executeQuery(anyString())).thenReturn(rs);
    }

    @Test
    public void verifyJwtTestOK() {
        Assert.assertEquals("robert@yahoo.com", jv.verifyJwt(validToken));
    }

    @Test
    public void verifyJwtTestInvalidSignature() {
        Assert.assertEquals("ERROR", jv.verifyJwt(incorrectCredentials));
    }

    @Test
    public void verifyJwtTestExpired() {
        Assert.assertEquals("ERROR", jv.verifyJwt(invalidTokenOld));
    }

    @Test
    public void issueJwtTestOK() {
        Assert.assertEquals(validToken, jv.issueJwt(correctCredentials));
    }

    @Test
    public void issueJwtTestNotOK() {
        Assert.assertEquals("ERROR", jv.issueJwt(incorrectCredentials));
    }

    @Test
    public void issueJwtTestSQLException() {
        Assert.assertEquals("ERROR", jv.issueJwt(incorrectCredentials2));
    }

    @Test
    public void filterTestOK1() {
        jv.filter(crcValid);
        Mockito.verify(crcValid).getUriInfo();
        Mockito.verify(headers).get("Authorization");
        Mockito.verify(ui).getPath();
    }

    @Test
    public void filterTestOK2() {
        jv.filter(crcCreds);
        Mockito.verify(crcCreds).getUriInfo();
        Mockito.verify(credHeaders).get("Authorization");
        Mockito.verify(ui).getPath();
        Mockito.verify(credHeaders).add(anyString(), anyString());
    }

    @Test
    public void filterTestNotOK() {
        jv.filter(crcInvalid);
        Mockito.verify(crcInvalid).getUriInfo();
        Mockito.verify(headers).get("Authorization");
        Mockito.verify(ui).getPath();
    }

    @Test
    public void filterTestNotOK2() {
        jv.filter(crcBadCreds);
        Mockito.verify(crcBadCreds).getUriInfo();
        Mockito.verify(headers).get("Authorization");
        Mockito.verify(ui).getPath();
    }
}
