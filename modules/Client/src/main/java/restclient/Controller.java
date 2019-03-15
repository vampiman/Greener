package restclient;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;

public class Controller {
    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private AnchorPane activitiesAnchorPane;


    @FXML
    protected void handleLoginButtonAction(ActionEvent event) throws IOException {
        Window owner = loginButton.getScene().getWindow();
        if(nameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Log-in Error!",
                    "Please enter your username");
            return;
        }
        if(passwordField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Log-in Error!",
                    "Please enter a password");
            return;
        }

        Parent add_activity_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource("dashboard.fxml"));
        Scene add_activity_page_scene = new Scene(add_activity_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(add_activity_page_scene);
        app_stage.show();
    }

    public static class AlertHelper {

        public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.initOwner(owner);
            alert.show();
        }
    }

    @FXML
    protected void handleSignupButtonAction(ActionEvent event) throws IOException {
        Parent add_activity_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource("signup.fxml"));
        Scene add_activity_page_scene = new Scene(add_activity_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(add_activity_page_scene);
        app_stage.show();
    }

    @FXML
    protected void handleDashboardButtonAction(ActionEvent event) throws IOException {
        Parent dashboard_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource("dashboard.fxml"));
        Scene dashboard_page_scene = new Scene(dashboard_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(dashboard_page_scene);
        app_stage.show();
    }

    @FXML
    protected void handleActivitiesButtonAction(ActionEvent event) throws IOException {
        Parent groups_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource("activities.fxml"));
        Scene groups_page_scene = new Scene(groups_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(groups_page_scene);
        app_stage.show();
    }

    @FXML
    protected void handleGroupsButtonAction(ActionEvent event) throws IOException {
        Parent groups_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource("groups.fxml"));
        Scene groups_page_scene = new Scene(groups_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(groups_page_scene);
        app_stage.show();
    }

    @FXML
    protected void handleAchievementsButtonAction(ActionEvent event) throws IOException {
        Parent achievements_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource("achievements.fxml"));
        Scene achievements_page_scene = new Scene(achievements_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(achievements_page_scene);
        app_stage.show();
    }

    @FXML
    protected void handleScoreboardButtonAction(ActionEvent event) throws IOException {
        Parent scoreboard_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource("scoreboard.fxml"));
        Scene scoreboard_page_scene = new Scene(scoreboard_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scoreboard_page_scene);
        app_stage.show();
    }
    @FXML
    protected void handleYouButtonAction(ActionEvent event) throws IOException {
        Parent you_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource("youPage.fxml"));
        Scene you_page_scene = new Scene(you_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(you_page_scene);
        app_stage.show();
    }

    @FXML
    protected void handleAddActivityButtonAction(ActionEvent event) throws IOException {
        Parent add_activity_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource("addActivity.fxml"));
        Scene add_activity_page_scene = new Scene(add_activity_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(add_activity_page_scene);
        app_stage.show();
    }

    @FXML
    public void handleExitButtonAction(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

    }

    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) throws IOException {
        Parent add_activity_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource("loginPage.fxml"));
        Scene add_activity_page_scene = new Scene(add_activity_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(add_activity_page_scene);
        app_stage.show();
    }


}
