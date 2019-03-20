package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private void loadMenuPageLogin(ActionEvent event) throws IOException {
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

        loadPage(event, "menu.fxml");

    }

    @FXML
    private void loadMenuPage(ActionEvent event) throws IOException {
        loadPage(event, "menu.fxml");

    }

    @FXML
    private void handleSignUpButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "signup.fxml");
    }
    @FXML
    private void handleSignUpPersonalAction(ActionEvent event) throws IOException {
        loadPage(event, "loginPage.fxml");
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
    protected void initialize(ActionEvent event) throws IOException {
        loadPage(event, "menuPage.fxml");
    }

    @FXML
    public void handleDashboardButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "dashboard.fxml");
    }

    @FXML
    protected void handleActivitiesButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "activities.fxml");
    }

    @FXML
    protected void handleAchievementsButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "achievements.fxml");
    }

    @FXML
    protected void handleScoreboardButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "scoreboard.fxml");
    }

    @FXML
    protected void handleYouButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "you.fxml");
    }

    @FXML
    protected void handleAddActivityButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "addActivity.fxml");
    }

    @FXML
    public void handleExitButtonAction(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

    }

    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "loginPage.fxml");
    }

    @FXML
    protected void loadPage(ActionEvent event, String fileName) throws IOException {
        Parent addPageParent = FXMLLoader.load(getClass().getClassLoader()
                .getResource(fileName));
        Scene addPageScene = new Scene(addPageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(addPageScene);
        appStage.show();
    }

    @FXML
    private void handleVegetarianMealButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "veganMeal.fxml");
    }

    @FXML
    private void handlePublicTransportButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "publicTransport.fxml");
    }

    @FXML
    private void handleTemperatureButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "temperature.fxml");
    }

    @FXML
    private void handleSolarPanelButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "solarPanel.fxml");
    }

    @FXML
    private void handleBikeButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "bike.fxml");
    }

    @FXML
    private void handleLocalProductButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "localProduct.fxml");
    }

    @FXML
    private void handleAddPublicTransportButtonAction(ActionEvent event) throws IOException {
//        String numKilometers = null;
//        String option;
//        Window owner = addButtonPublicTransport.getScene().getWindow();
//        if (kilometers.getText().isEmpty()) {
//            LoginPageController.AlertHelper
//                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
//                            "Please enter the number of kilometers which you travelled");
//            return;
//        } else {
//            numKilometers = kilometers.getText();
//            System.out.println(kilometers.getText() + " kilometers");
//        }
//        if (publicTransport.getValue() == null) {
//            LoginPageController.AlertHelper
//                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
//                            "Please enter the type of public transport");
//            return;
//        } else {
//            option = publicTransport.getValue().toString();
//            System.out.println(option);
//        }
        loadPage(event, "addActivity.fxml");
    }

    @FXML
    private void handleAddTemperatureButtonAction(ActionEvent event) throws IOException {
//        Window owner = addButtonTemperature.getScene().getWindow();
//        if (beforeTemperature.getText().isEmpty()) {
//            AlertHelper
//                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
//                            "Please enter the temperature before decreasing");
//            return;
//        } else {
//            System.out.println(beforeTemperature.getText() + " °C");
//        }
//        if (afterTemperature.getText().isEmpty()) {
//            Controller.AlertHelper
//                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
//                            "Please enter the temperature after decreasing");
//            return;
//        } else {
//            System.out.println(afterTemperature.getText() + " °C");
//        }
            loadPage(event, "addActivity.fxml");
    }
//
    @FXML
    private void handleAddSolarPanelButtonAction(ActionEvent event) throws IOException {
//        Window owner = addButtonSolarPanel.getScene().getWindow();
//        if (electricityPercentage.getText().isEmpty()) {
//            Controller.AlertHelper.showAlert(
//                    Alert.AlertType.ERROR, owner, "Unfilled field!",
//                    "Please enter the percentage of decrease in your electricity consumption");
//            return;
//        } else {
//            System.out.println("% " + electricityPercentage.getText());
//        }
        loadPage(event, "addActivity.fxml");
    }

    @FXML
    private void handleAddVeganMealButtonAction(ActionEvent event) throws IOException {
//        Window owner = addButtonVeganMeal.getScene().getWindow();
//        if (mealPortion.getText().isEmpty()) {
//            LoginPageController.AlertHelper
//                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
//                            "Please enter how much vegan meal you had");
//            return;
//        } else {
//            int portions = Integer.parseInt(mealPortion.getText());
//            System.out.println(portions);
//            new VeganMeal(ClientBuilder.newClient()).sendVeganMeal(portions);
//
//        }
//
        loadPage(event, "addActivity.fxml");
    }

    @FXML
    private void handleAddLocalProductButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "addActivity.fxml");
    }

}
