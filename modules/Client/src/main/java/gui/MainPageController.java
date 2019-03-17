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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import restclient.VeganMeal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.ws.rs.client.ClientBuilder;




public class MainPageController {
    @FXML
    private Button dashboardButton;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private StackPane parentContainer;
    @FXML
    private TextField kilometers;
    @FXML
    private Button addButtonPublicTransport;
    @FXML
    private ChoiceBox publicTransport;
    @FXML
    private Button addButtonTemperature;
    @FXML
    private TextField beforeTemperature;
    @FXML
    private TextField afterTemperature;
    @FXML
    private Button addButtonSolarPanel;
    @FXML
    private TextField electricityPercentage;
    @FXML
    private Button addButtonVeganMeal;
    @FXML
    private TextField mealPortion;
    @FXML
    private TextField todaysTip;
    @FXML
    private TextField activities;


    @FXML
    private void textGenerator(ActionEvent event) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("deneme.txt"));
        List<String> lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        String text = lines.get(1);
        System.out.println(todaysTip.getText() + " is the current text");
        todaysTip.setText(text);
        System.out.println(todaysTip.getText() + " is the last text");
    }

    @FXML
    private void handleDashboardButtonAction() throws IOException {
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


    @FXML
    private void handleActivitiesButtonAction(ActionEvent event) throws IOException {
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

    @FXML
    private void handleGroupsButtonAction(ActionEvent event) throws IOException {
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

    @FXML
    private void handleAchievementsButtonAction(ActionEvent event) throws IOException {
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

    @FXML
    private void handleScoreboardButtonAction(ActionEvent event) throws IOException {
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

    @FXML
    private void handleYouButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("youPage.fxml"));
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

    @FXML
    private void handleAddActivityButtonAction(ActionEvent event) throws IOException {
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

    @FXML
    private void handleLogoutButtonAction(ActionEvent event) throws IOException {
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

    @FXML
    public void handleExitButtonAction(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

    }

    @FXML
    private void handleVegetarianMealButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("veganMeal.fxml"));
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

    @FXML
    private void handleLocalProductButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("localProduct.fxml"));
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

    @FXML
    private void handlePublicTransportButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader()
                .getResource("publicTransport.fxml"));
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

    @FXML
    private void handleBikeButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader()
                .getResource("bike.fxml"));
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

    @FXML
    private void handleTemperatureButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("temperature.fxml"));
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

    @FXML
    private void handleSolarPanelButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("solarPanel.fxml"));
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

    @FXML
    private void handleAddPublicTransportButtonAction(ActionEvent event) throws IOException {
        String numKilometers = null;
        String option;
        Window owner = addButtonPublicTransport.getScene().getWindow();
        if (kilometers.getText().isEmpty()) {
            LoginPageController.AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                    "Please enter the number of kilometers which you travelled");
            return;
        } else {
            numKilometers = kilometers.getText();
            System.out.println(kilometers.getText() + " kilometers");
        }
        if (publicTransport.getValue() == null) {
            LoginPageController.AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                    "Please enter the type of public transport");
            return;
        } else {
            option = publicTransport.getValue().toString();
            System.out.println(option);
        }


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

    @FXML
    private void handleAddTemperatureButtonAction(ActionEvent event) throws IOException {
        Window owner = addButtonTemperature.getScene().getWindow();
        if (beforeTemperature.getText().isEmpty()) {
            LoginPageController.AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                    "Please enter the temperature before decreasing");
            return;
        } else {
            System.out.println(beforeTemperature.getText() + " °C");
        }
        if (afterTemperature.getText().isEmpty()) {
            LoginPageController.AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                    "Please enter the temperature after decreasing");
            return;
        } else {
            System.out.println(afterTemperature.getText() + " °C");
        }
        Parent root = FXMLLoader.load(getClass()
                .getClassLoader().getResource("addActivity.fxml"));
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

    @FXML
    private void handleAddSolarPanelButtonAction(ActionEvent event) throws IOException {
        Window owner = addButtonSolarPanel.getScene().getWindow();
        if (electricityPercentage.getText().isEmpty()) {
            LoginPageController.AlertHelper.showAlert(
                    Alert.AlertType.ERROR, owner, "Unfilled field!",
                    "Please enter the percentage of decrease in your electricity consumption");
            return;
        } else {
            System.out.println("% " + electricityPercentage.getText());
        }
        Parent root = FXMLLoader.load(getClass()
                .getClassLoader().getResource("addActivity.fxml"));
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

    @FXML
    private void handleAddVeganMealButtonAction(ActionEvent event) throws IOException {
        Window owner = addButtonVeganMeal.getScene().getWindow();
        if (mealPortion.getText().isEmpty()) {
            LoginPageController.AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                    "Please enter how much vegan meal you had");
            return;
        } else {
            int portions = Integer.parseInt(mealPortion.getText());
            System.out.println(portions);
            new VeganMeal(ClientBuilder.newClient()).sendVeganMeal(portions);

        }

        Parent root = FXMLLoader.load(getClass().getClassLoader()
                .getResource("addActivity.fxml"));
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