package serverside;

import cn.hutool.json.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.security.SignatureException;

import java.util.Calendar;
import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class JwtVerifier implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer "; // for JWT

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
                    String token = issueJwt(authToken);
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
    public static Object verifyJwt(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(KeyGen.KEY_VALIDATE)
                    .parseClaimsJws(token);

            String username = claims.getBody().getSubject();
            if (username.equals("username")) {
                return "OK";
            } else {
                return "NOT OK";
            }
        } catch (SignatureException se) {
            return se;
        } catch (IncorrectClaimException ice) {
            return ice;
        } catch (ExpiredJwtException eje) {
            return eje;
        } catch (MissingClaimException mce) {
            return mce;
        }

    }

    /**
     * Method issues a valid token if the credentials match the ones in the database.
     * @param credentials received from the Authorization header
     * @return Object: JWT as an encoded String if credentials are verified, Exception if not
     */
    public static String issueJwt(String credentials) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .require("email", "nstruharova@tudelft.nl")
                    .require("password", "123")
                    .setSigningKey(KeyGen.KEY)
                    .parseClaimsJws(credentials);
        } catch (IncorrectClaimException ice) {
            return "NO";
        } catch (SignatureException se) {
            return "NO";
        } catch (ExpiredJwtException eje) {
            return "NO";
        } catch (MissingClaimException mce) {
            return "NO";
        }

        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH) + 1);

        return Jwts.builder()
                .setSubject("username")
                .setExpiration(today.getTime())
                .signWith(KeyGen.KEY_VALIDATE)
                .compact();
    }
}
