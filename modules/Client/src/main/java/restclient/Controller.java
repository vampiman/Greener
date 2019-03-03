package restclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;


public class Controller {
    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private PasswordField pass;

    @FXML
    private PasswordField re_pass;

    @FXML
    private Button sign_up_personal1;

    @FXML
    private Button sign_up_utilities2;

    @FXML
    private Button sign_up_tansportation3;

    @FXML
    private Button sign_up_diet4;

    @FXML
    private Button sign_up_goods5;

    @FXML
    protected void handleSignUpPersonalAction(ActionEvent event) throws IOException {

        Parent sign_up_personal_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource("utilities.fxml"));
        Scene sign_up_personal_page_scene = new Scene(sign_up_personal_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(sign_up_personal_page_scene);
        app_stage.show();
    }

    @FXML
    protected void handleSignUpUtilitiesAction(ActionEvent event) throws IOException {


        Parent sign_up_utilities_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource("transportation.fxml"));
        Scene sign_up_utilities_page_scene = new Scene(sign_up_utilities_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(sign_up_utilities_page_scene);
        app_stage.show();
    }

    @FXML
    protected void handleSignUpTransportationAction(ActionEvent event) throws IOException {


        Parent sign_up_transportation_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource("diet.fxml"));
        Scene sign_up_transportation_page_scene = new Scene(sign_up_transportation_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(sign_up_transportation_page_scene);
        app_stage.show();
    }

    @FXML
    protected void handleSignUpDietAction(ActionEvent event) throws IOException {


        Parent sign_up_diet_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource("goods.fxml"));
        Scene sign_up_diet_page_scene = new Scene(sign_up_diet_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(sign_up_diet_page_scene);
        app_stage.show();
    }

    @FXML
    protected void handleSignUpGoodsAction(ActionEvent event) throws IOException {


        Parent sign_up_goods_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource("logIn.fxml"));
        Scene sign_up_goods_page_scene = new Scene(sign_up_goods_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(sign_up_goods_page_scene);
        app_stage.show();
    }
}
