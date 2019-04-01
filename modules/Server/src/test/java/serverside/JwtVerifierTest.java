//package serverside;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.SignatureException;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PowerMockIgnore;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.core.MultivaluedMap;
//import javax.ws.rs.core.UriInfo;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.sql.*;
//import java.util.Calendar;
//import java.util.Collections;
//
//import static org.mockito.Mockito.*;
//import static serverside.JwtVerifier.KEY;
//import static serverside.JwtVerifier.KEY_VALIDATE;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(JwtVerifier.class)
//public class JwtVerifierTest {
//    private String correctCredentials, correctToken;
//
//    @Mock
//    Connection mockConnection;
//    Statement mockStatement;
//    ResultSet mockRS;
//
//    @InjectMocks
//    JwtVerifier jv = new JwtVerifier();
//
//    @Before
//    public void setup() {
//        String password = DigestUtils.sha256Hex("password");
//        correctCredentials = Jwts.builder()
//                .claim("Email", "nstruharova@tudelft.nl")
//                .claim("Password", password)
//                .signWith(KEY)
//                .compact();
//
//        correctToken = Jwts.builder()
//                .setSubject("nstruharova@tudelft.nl")
//                .signWith(KEY_VALIDATE)
//                .compact();
//
//        String url = "jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false";
//        String user = "sammy";
//        String pass = "temporary";
//
//        try {
//            PowerMockito.mockStatic(DriverManager.class);
//            PowerMockito.when(DriverManager.getConnection(url, user, pass)).thenReturn(mockConnection);
//            mockConnection = mock(Connection.class);
//            mockStatement = mock(Statement.class);
//            mockRS = mock(ResultSet.class);
//            when(mockConnection.createStatement()).thenReturn(mockStatement);
//            when(mockStatement.executeQuery(any(String.class))).thenReturn(mockRS);
//            when(mockRS.getString("Password")).thenReturn(password);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public void issueJwtToken() {
//        try {
//            jv.issueJwt(correctCredentials);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
