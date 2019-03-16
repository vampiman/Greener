package server;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class JwtVerifierTest {
    String correctCredentials, incorrectCredentials, validToken, invalidToken;

    @Before
    public void setup() {
        correctCredentials = Jwts.builder()
                .signWith(KeyGen.KEY)
                .claim("username", "Nat")
                .claim("password", "123")
                .compact();

        incorrectCredentials = Jwts.builder()
                .signWith(KeyGen.KEY)
                .claim("username", "Naty")
                .claim("password", "321")
                .compact();

        validToken = Jwts.builder()
                .signWith(KeyGen.KEY_VALIDATE)
                .setSubject("id")
                .compact();

        Calendar lastYear = Calendar.getInstance();
        lastYear.set(Calendar.YEAR, 2018);
        invalidToken = Jwts.builder()
                .signWith(KeyGen.KEY_VALIDATE)
                .setSubject("id")
                .setExpiration(lastYear.getTime())
                .compact();
    }

    @Test
    public void verifyJwtTestOK() {
        Assert.assertEquals("OK", JwtVerifier.verifyJwt(validToken));
    }

    @Test
    public void verifyJwtTestInvalidSignature() {
        Assert.assertEquals(SignatureException.class, JwtVerifier.verifyJwt(correctCredentials).getClass());
        Assert.assertNotEquals("OK", JwtVerifier.verifyJwt(correctCredentials));
    }

    @Test
    public void verifyJwtTestExpired() {
        Assert.assertEquals(ExpiredJwtException.class, JwtVerifier.verifyJwt(invalidToken).getClass());
        Assert.assertNotEquals("OK", JwtVerifier.verifyJwt(invalidToken));
    }

    @Test
    public void issueJwtTestOK() {
        Assert.assertEquals(validToken, JwtVerifier.issueJwt(correctCredentials));
    }

    @Test
    public void issueJwtTestNotOK() {
        Assert.assertNotEquals(validToken, JwtVerifier.issueJwt(incorrectCredentials));
    }
}
