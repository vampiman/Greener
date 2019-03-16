package GUI;

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
        if (nameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Log-in Error!",
                    "Please enter your username");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Log-in Error!",
                    "Please enter a password");
            return;
        }

        Parent addActivityPageParent = FXMLLoader.load(getClass().getClassLoader()
                .getResource("dashboard.fxml"));
        Scene addActivityPageScene = new Scene(addActivityPageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(addActivityPageScene);
        appStage.show();
    }

    public static class AlertHelper {

        /**
         * The method which gives alert with a specific message.
         * @param alertType the type of the alert
         * @param owner the owner of the alert
         * @param title the title of the alert
         * @param message the message of the alert
         */
        public static void showAlert(Alert.AlertType alertType,
                                     Window owner, String title, String message) {
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
        Parent addActivityPageParent = FXMLLoader.load(getClass().getClassLoader()
                .getResource("signup.fxml"));
        Scene addActivityPageScene = new Scene(addActivityPageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(addActivityPageScene);
        appStage.show();
    }

    @FXML
    protected void handleDashboardButtonAction(ActionEvent event) throws IOException {
        Parent dashboardPageParent = FXMLLoader.load(getClass().getClassLoader()
                .getResource("dashboard.fxml"));
        Scene dashboardPageScene = new Scene(dashboardPageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(dashboardPageScene);
        appStage.show();
    }

    @FXML
    protected void handleActivitiesButtonAction(ActionEvent event) throws IOException {
        Parent groupsPageParent = FXMLLoader.load(getClass().getClassLoader()
                .getResource("activities.fxml"));
        Scene groupsPageScene = new Scene(groupsPageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(groupsPageScene);
        appStage.show();
    }

    @FXML
    protected void handleGroupsButtonAction(ActionEvent event) throws IOException {
        Parent groupsPageParent = FXMLLoader.load(getClass().getClassLoader()
                .getResource("groups.fxml"));
        Scene groupsPageScene = new Scene(groupsPageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(groupsPageScene);
        appStage.show();
    }

    @FXML
    protected void handleAchievementsButtonAction(ActionEvent event) throws IOException {
        Parent achievementsPageParent = FXMLLoader.load(getClass().getClassLoader()
                .getResource("achievements.fxml"));
        Scene achievementsPageScene = new Scene(achievementsPageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(achievementsPageScene);
        appStage.show();
    }

    @FXML
    protected void handleScoreboardButtonAction(ActionEvent event) throws IOException {
        Parent scoreboardPageParent = FXMLLoader.load(getClass().getClassLoader()
                .getResource("scoreboard.fxml"));
        Scene scoreboardPageScene = new Scene(scoreboardPageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scoreboardPageScene);
        appStage.show();
    }

    @FXML
    protected void handleYouButtonAction(ActionEvent event) throws IOException {
        Parent youPageParent = FXMLLoader.load(getClass().getClassLoader()
                .getResource("youPage.fxml"));
        Scene youPageScene = new Scene(youPageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(youPageScene);
        appStage.show();
    }

    @FXML
    protected void handleAddActivityButtonAction(ActionEvent event) throws IOException {
        Parent addActivityPageParent = FXMLLoader.load(getClass().getClassLoader()
                .getResource("addActivity.fxml"));
        Scene addActivityPageScene = new Scene(addActivityPageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(addActivityPageScene);
        appStage.show();
    }

    @FXML
    public void handleExitButtonAction(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

    }

    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) throws IOException {
        Parent addActivityPageParent = FXMLLoader.load(getClass().getClassLoader()
                .getResource("loginPage.fxml"));
        Scene addActivityPageScene = new Scene(addActivityPageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(addActivityPageScene);
        appStage.show();
    }


}
