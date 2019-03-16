package server;

import cn.hutool.json.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class JwtVerifier implements ContainerRequestFilter {

    public static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    public static final String AUTHORIZATION_HEADER_PREFIX = "Bearer "; // for JWT
    public static final long CLOCK_SKEW = 3 * 60; //in seconds

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (requestContext.getUriInfo().getPath().contains("localproduce")) {
            List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
            if (authHeader != null && authHeader.size() > 0) {
                String authToken = authHeader.get(0);
                authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
                if (verifyJwt(authToken).equals("OK")) {
                    return;
                } else {
                    System.out.println("SECRET TOKEN IS " + authToken);
                    String token = issueToken(authToken);
                    if (token.equals("NO")) {
                        JSONObject jo = new JSONObject();
                        jo.append("Error", "Can't get in");
                        Response res = Response.status(Response.Status.UNAUTHORIZED)
                                .entity(jo).build();
                        requestContext.abortWith(res);
                    } else {
                        requestContext.getHeaders().add("Token", token);
                        return;
                    }
                }
            }
        }
    }


    /**
     * Method verifies whether the token is valid (and was issued by the server).
     * @param token obtained from Authorization header (either token or credentials)
     * @return "OK" if token is validated, Exception if it's not
     */
    public Object verifyJwt(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setAllowedClockSkewSeconds(CLOCK_SKEW)
                    .requireSubject("id")
                    .setSigningKey(KeyGen.KEY_VALIDATE)
                    .parseClaimsJws(token);
            System.out.println("NO PROBLEM WITH VALIDATION");
        } catch (SignatureException se) {
            return se;
        } catch (InvalidClaimException ice) {
            return ice;
        } catch (ExpiredJwtException eje) {
            return eje;
        }
        System.out.println("OK");
        return "OK";
    }

    /**
     * Method issues a valid token if the credentials match the ones in the database.
     * @param credentials received from the Authorization header
     * @return Object: JWT as an encoded String if credentials are verified, Exception if not
     */
    public String issueToken(String credentials) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setAllowedClockSkewSeconds(CLOCK_SKEW)
                    .require("username", "Nat")
                    .require("password", "123")
                    .setSigningKey(KeyGen.KEY)
                    .parseClaimsJws(credentials);
        } catch (InvalidClaimException ice) {
            return "NO";
        } catch (SignatureException se) {
            return "NO";
        } catch (ExpiredJwtException eje) {
            return "NO";
        }

        Calendar today = Calendar.getInstance();
        today.set(Calendar.YEAR, 2020);

        return Jwts.builder()
                .setSubject("id")
                .setExpiration(today.getTime())
                .signWith(KeyGen.KEY_VALIDATE)
                .compact();
    }
}
