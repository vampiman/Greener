//package server;
//
//import cn.hutool.json.JSON;
//import cn.hutool.json.JSONObject;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.Result;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import javax.naming.NamingException;
//import javax.ws.rs.core.Response;
//import java.sql.*;
//
//import static org.powermock.api.mockito.PowerMockito.mockStatic;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(VeganMeal.class)
//public class VeganMealTest {
//
//
//
//    @Mock private Connection mockConnection;
//    @Mock private Statement mockStatement;
//    @Mock private ResultSet rs;
//
//    @InjectMocks private VeganMeal veganMeal;
//
//    @Before
//    public void setUp() throws SQLException {
//        MockitoAnnotations.initMocks(this);
//        mockConnection = Mockito.mock(Connection.class);
//        mockStatement = Mockito.mock(Statement.class);
//        rs = Mockito.mock(ResultSet.class);
//
//    }
//
////    @Test
////    public void postIt() throws SQLException, ClassNotFoundException {
////        veganMeal = new VeganMeal();
////        mockStatic(DriverManager.class);
////        Mockito.when(DriverManager.getConnection("jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false","sammy","temporary")).thenReturn(mockConnection);
////        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
////        veganMeal.postIt();
////
////    }
////
////    @Test
////    public void getAll() throws SQLException, ClassNotFoundException, NamingException {
////        veganMeal = new VeganMeal();
////        mockStatic(DriverManager.class);
////        Mockito.when(DriverManager.getConnection("jdbc:mysql://localhost:3306/greener?autoReconnect=true&useSSL=false","sammy","temporary")).thenReturn(mockConnection);
////        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
////        Mockito.when(mockStatement.executeQuery("SELECT Vegan_meal FROM person WHERE Name = 'Robert'")).thenReturn(rs);
////        Mockito.when(rs.getInt("Vegan_meal")).thenReturn(1);
////        Mockito.when(rs.next()).thenReturn(true);
////        Response value = veganMeal.getAll();
////
////
////        System.out.println(value.getEntity());
////        Assert.assertEquals(value.getEntity().toString(), "{\"total\":1}");
//////        Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
////    }
//}