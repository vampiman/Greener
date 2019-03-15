package gui;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;

public class LoginPageController {

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Button signUpPageButton;

    @FXML
    private GridPane anchorRoot;

    @FXML
    private GridPane parentContainer;

    @FXML
    private AnchorPane rootContainer;



    @FXML
    private void loadSecond(ActionEvent event) throws IOException {
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

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("dashboard.fxml"));
        Scene scene = loginButton.getScene();
        root.translateYProperty().set(scene.getHeight());

        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(anchorRoot);
        });
        timeline.play();
    }

    @FXML
    private void handleSignUpButtonAction(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("signUp.fxml"));
        Scene scene = signUpButton.getScene();
        root.translateYProperty().set(scene.getHeight());

        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(anchorRoot);
        });
        timeline.play();
    }

    @FXML
    private void handleSignUpPageButtonAction(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("loginPage.fxml"));
        Scene scene = signUpPageButton.getScene();
        root.translateYProperty().set(scene.getHeight());

        rootContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            rootContainer.getChildren().remove(anchorRoot);
        });
        timeline.play();
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

}

