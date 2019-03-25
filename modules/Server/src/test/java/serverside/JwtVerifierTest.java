package serverside;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
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
import java.util.Calendar;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class JwtVerifierTest {
    private String correctCredentials, incorrectCredentials,
            validToken, invalidTokenOld, invalidTokenWrongSubject;

    @Mock
    ContainerRequestContext crcValid;
    ContainerRequestContext crcInvalid;
    ContainerRequestContext crcCreds;
    ContainerRequestContext crcBadCreds;
    UriInfo ui;
    MultivaluedMap headers;
    MultivaluedMap credHeaders;

    @InjectMocks
    JwtVerifier jv = new JwtVerifier();

    @Before
    public void setup() {
        String password = DigestUtils.sha256Hex("password");
        correctCredentials = Jwts.builder()
                .signWith(KeyGen.KEY)
                .claim("email", "nstruharova@tudelft.nl")
                .claim("password", password)
                .compact();

        incorrectCredentials = Jwts.builder()
                .signWith(KeyGen.KEY)
                .claim("username", "Naty")
                .claim("password", "321")
                .compact();

        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH) + 1);
        validToken = Jwts.builder()
                .setSubject("username")
                .setExpiration(today.getTime())
                .signWith(KeyGen.KEY_VALIDATE)
                .compact();

        Calendar lastYear = Calendar.getInstance();
        lastYear.set(Calendar.YEAR, 2018);
        invalidTokenOld = Jwts.builder()
                .signWith(KeyGen.KEY_VALIDATE)
                .setSubject("user")
                .setExpiration(lastYear.getTime())
                .compact();

        invalidTokenWrongSubject = Jwts.builder()
                .signWith(KeyGen.KEY_VALIDATE)
                .setSubject("badsubject")
                .compact();

        // Section for filter() method
        crcValid = mock(ContainerRequestContext.class);
        crcInvalid = mock(ContainerRequestContext.class);
        crcCreds = mock(ContainerRequestContext.class);
        crcBadCreds = mock(ContainerRequestContext.class);
        ui = mock(UriInfo.class);
        Mockito.when(ui.getPath()).thenReturn("localproduce");

        headers = mock(MultivaluedMap.class);
        when(headers.get("Authorization")).thenReturn(Collections.singletonList("Bearer " + validToken));

        credHeaders = mock(MultivaluedMap.class);
        credHeaders = mock(MultivaluedMap.class);
        when(credHeaders.get("Authorization")).thenReturn(Collections.singletonList("Bearer " + correctCredentials));

        try {
            Mockito.when(ui.getRequestUri()).thenReturn(new URI("test/localproduce"));
        } catch (URISyntaxException e) {
            System.out.println("wrong uri");
        }

        Mockito.when(crcValid.getHeaders()).thenReturn(headers);
        Mockito.when(crcValid.getUriInfo()).thenReturn(ui);
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
        
    }

    @Test
    public void verifyJwtTestOK() {
        Assert.assertEquals("OK", JwtVerifier.verifyJwt(validToken));
    }

    @Test
    public void verifyJwtTestNotOK() {
        Assert.assertEquals("NOT OK", JwtVerifier.verifyJwt(invalidTokenWrongSubject));
    }

    @Test
    public void verifyJwtTestInvalidSignature() {
        Assert.assertEquals(SignatureException.class, JwtVerifier.verifyJwt(correctCredentials).getClass());
        Assert.assertNotEquals("OK", JwtVerifier.verifyJwt(correctCredentials));
    }

    @Test
    public void verifyJwtTestExpired() {
        Assert.assertEquals(ExpiredJwtException.class, JwtVerifier.verifyJwt(invalidTokenOld).getClass());
        Assert.assertNotEquals("OK", JwtVerifier.verifyJwt(invalidTokenOld));
    }

    @Test
    public void issueJwtTestOK() {
        Assert.assertEquals(validToken, JwtVerifier.issueJwt(correctCredentials));
    }

    @Test
    public void issueJwtTestNotOK() {
        Assert.assertNotEquals(validToken, JwtVerifier.issueJwt(incorrectCredentials));
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
