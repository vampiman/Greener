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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import restclient.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DashboardPageController {
    @FXML
    private Button dashboardButton;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private StackPane parentContainer;
    @FXML
    private TextField todaysTip;


    private boolean checkToken() throws IOException {
        String token = "";
        File file = new File("test.txt");
        boolean fileExists = file.exists();

        if (fileExists) {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null)
                token = st;
        }

        User user = new User("", "");
        if (user.login(token)) {
            return true;
        }
        return false;
    }

    @FXML
    private void initialize() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("tips.txt"));
        List<String> lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        Random rn = new Random();
        int randomNum = (rn.nextInt() & Integer.MAX_VALUE) % lines.size();
        String text = lines.get(randomNum);
        todaysTip.setText(text);
    }

    @FXML
    private void handleDashboardButtonAction() throws IOException {
        if (checkToken()) {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("dashboard.fxml"));
            Scene scene = dashboardButton.getScene();
            root.translateXProperty().set(scene.getHeight());
            parentContainer.getChildren().add(root);
            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> {
                parentContainer.getChildren().remove(anchorRoot);
            });
            timeline.play();
        }
    }


    @FXML
    private void handleActivitiesButtonAction(ActionEvent event) throws IOException {
        if (checkToken()) {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("activities.fxml"));
            Scene scene = dashboardButton.getScene();
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
    }

    @FXML
    private void handleGroupsButtonAction(ActionEvent event) throws IOException {
        if (checkToken()) {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("groups.fxml"));
            Scene scene = dashboardButton.getScene();
            root.translateXProperty().set(scene.getHeight());

            parentContainer.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> {
                parentContainer.getChildren().remove(anchorRoot);
            });
            timeline.play();
        }
    }

    @FXML
    private void handleAchievementsButtonAction(ActionEvent event) throws IOException {
        if (checkToken()) {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("achievements.fxml"));
            Scene scene = dashboardButton.getScene();
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
    }

    @FXML
    private void handleScoreboardButtonAction(ActionEvent event) throws IOException {
        if (checkToken()) {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("scoreboard.fxml"));
            Scene scene = dashboardButton.getScene();
            root.translateXProperty().set(scene.getHeight());

            parentContainer.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> {
                parentContainer.getChildren().remove(anchorRoot);
            });
            timeline.play();
        }
    }

    @FXML
    private void handleYouButtonAction(ActionEvent event) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("youPage.fxml"));
        if (!checkToken()) {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("loginPage.fxml"));
            Scene scene = dashboardButton.getScene();
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

    }

    @FXML
    private void handleAddActivityButtonAction(ActionEvent event) throws IOException {
        if (checkToken()) {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("addActivity.fxml"));
            Scene scene = dashboardButton.getScene();
            root.translateXProperty().set(scene.getHeight());

            parentContainer.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> {
                parentContainer.getChildren().remove(anchorRoot);
            });
            timeline.play();
        }
    }

    @FXML
    private void handleLogoutButtonAction(ActionEvent event) throws IOException {
        if (checkToken()) {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("loginPage.fxml"));
            Scene scene = dashboardButton.getScene();
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
    }

    @FXML
    public void handleExitButtonAction(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

    }

}