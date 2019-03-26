package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.image.ImageView;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import restclient.CompactClient;
import restclient.User;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Controller {
    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private TextField signupName;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField rePassword;

    @FXML
    private Button addButton;

    @FXML
    private TextField mealPortion;

    @FXML
    private TextField beforeTemperature;

    @FXML
    private TextField afterTemperature;

    @FXML
    private ChoiceBox energyType;

    @FXML
    private TextField electricityPercentage;

    @FXML
    private TextField kilometers;

    @FXML
    private ChoiceBox publicTransport;

    @FXML
    private ChoiceBox carType;

    @FXML
    private Text todaysTip;

    @FXML
    private TextField friendCode;

    @FXML
    private TextArea activities;

    @FXML
    private GridPane scorePane;

    @FXML
    private void initialize() throws IOException {
        if (todaysTip != null) {
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

        if (activities != null) {
            String text = "You had 0 vegan meals" + "\n"
                    + "You biked 0 kilometers\n"
                    + "You decreased 0% of your electricity consumption\n"
                    + "You bought 0 local product\n"
                    + "You travelled 0 kilometers by public transport\n"
                    + "You decreased your home's temperature 0 °C";
            activities.setText(text);
        }
    }

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

        User user = new User(nameField.getText(), passwordField.getText());
        boolean authenticated = false;
        authenticated = user.login(null);
        String token = user.getToken();

        if (authenticated) {
            PrintWriter pw = new PrintWriter("test.txt", "UTF-8");
            pw.println(token);
            pw.close();

            loadPage(event, "fxml/menu.fxml");
        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Authentication failed!",
                    "Please enter credentials again.");
        }
    }

    @FXML
    private void loadMenuPage(ActionEvent event) throws IOException {
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/menu.fxml");
        }
    }

    @FXML
    private void handleSignUpButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "fxml/signup.fxml");
    }

    @FXML
    private void handleSignUpPersonalAction(ActionEvent event) throws IOException {
        Window owner = signupName.getScene().getWindow();
        if (signupName.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Sign-up Error!",
                    "Please enter your username");
            return;
        }
        if (email.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Sign-up Error!",
                    "Please enter your email address");
            return;
        }
        if (password.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Sign-up Error!",
                    "Please enter your password");
            return;
        }
        if (rePassword.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Sign-up Error!",
                    "Please retype your password");
            return;
        }
        if (!password.getText().equals(rePassword.getText())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Sign-up Error!",
                    "Please enter the same password for password fields");
            return;
        }

        User user = new User(email.getText(), password.getText());
        user.register(signupName.getText(), email.getText(), password.getText());

        loadPage(event, "fxml/loginPage.fxml");
    }

    @FXML
    protected void handleActivitiesButtonAction(ActionEvent event) throws IOException {
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/activities.fxml");
        }
    }

    @FXML
    protected void handleFriendsButtonAction(ActionEvent event) throws IOException {
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/addFriends.fxml");
        }
    }

    @FXML
    protected void handleAchievementsButtonAction(ActionEvent event) throws IOException {
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/achievements.fxml");
        }
    }

    @FXML
    protected void handleScoreboardButtonAction(ActionEvent event) throws IOException {
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/scoreboard.fxml");
        }
    }

    @FXML
    protected void handleYouButtonAction(ActionEvent event) throws IOException {
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/you.fxml");
        }
    }

    @FXML
    protected void handleAddActivityButtonAction(ActionEvent event) throws IOException {
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/addActivity.fxml");
        }
    }

    @FXML
    public void handleExitButtonAction(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

    }

    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "fxml/loginPage.fxml");
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
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/veganMeal.fxml");
        }
    }

    @FXML
    private void handlePublicTransportButtonAction(ActionEvent event) throws IOException {
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/publicTransport.fxml");
        }
    }

    @FXML
    private void handleTemperatureButtonAction(ActionEvent event) throws IOException {
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/temperature.fxml");
        }
    }

    @FXML
    private void handleSolarPanelButtonAction(ActionEvent event) throws IOException {
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/solarPanel.fxml");
        }
    }

    @FXML
    private void handleBikeButtonAction(ActionEvent event) throws IOException {
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/bike.fxml");
        }
    }

    @FXML
    private void handleLocalProductButtonAction(ActionEvent event) throws IOException {
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/localProduct.fxml");
        }
    }

    @FXML
    private void handleAddPublicTransportButtonAction(ActionEvent event) throws IOException {
        Window owner = addButton.getScene().getWindow();
        if (carType.getValue() == null) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                            "Please enter type of your car");
            return;
        } else if (publicTransport.getValue() == null) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                            "Please enter the type of public transport");
            return;
        } else if (kilometers.getText().isEmpty()) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                            "Please enter the number of kilometers which you travelled");
            return;
        } else {
            try {
                int numberOfKilometers = Integer.parseInt(kilometers.getText());
                String typeOfCar = carType.getValue().toString();
                String publictransportType = publicTransport.getValue().toString();

                CompactClient cc = new CompactClient();
                cc.postPublicTransport(typeOfCar, publictransportType, numberOfKilometers);
            } catch (NumberFormatException e) {
                AlertHelper
                        .showAlert(Alert.AlertType.ERROR, owner, "Wrong input type!",
                                "Please enter a "
                                        + "double number to indicate number of kilometers you go");
                return;
            }

            CompactClient cc = new CompactClient();
            if (!cc.checkToken()) {
                loadPage(event, "fxml/loginPage.fxml");
            } else {
                loadPage(event, "fxml/addActivity.fxml");
            }
        }
    }

    @FXML
    private void handleAddTemperatureButtonAction(ActionEvent event) throws IOException {
        Window owner = addButton.getScene().getWindow();
        if (beforeTemperature.getText().isEmpty()) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                            "Please enter the temperature before decreasing");
            return;
        } else if (afterTemperature.getText().isEmpty()) {
            Controller.AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                            "Please enter the temperature after decreasing");
            return;
        } else if (energyType.getValue() == null) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                            "Please enter your energy type");
            return;
        } else {
            try {
                int before = Integer.parseInt(beforeTemperature.getText());
                int after = Integer.parseInt(afterTemperature.getText());
                String typeOfEnergy = (String) energyType.getValue();

                CompactClient cc = new CompactClient();
                cc.postHeatConsumption(before, after, typeOfEnergy);
            } catch (NumberFormatException e) {
                AlertHelper
                        .showAlert(Alert.AlertType.ERROR, owner, "Wrong input type!",
                                "Please enter a double number to indicate your home's temperature");
                return;
            }
        }
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/addActivity.fxml");
        }
    }

    @FXML
    private void handleAddSolarPanelButtonAction(ActionEvent event) throws IOException {
        Window owner = addButton.getScene().getWindow();
        if (electricityPercentage.getText().isEmpty()) {
            Controller.AlertHelper.showAlert(
                    Alert.AlertType.ERROR, owner, "Unfilled field!",
                    "Please enter the percentage of decrease in your electricity consumption");
            return;
        } else {
            try {
                Double percentage = Double.parseDouble(electricityPercentage.getText());
            } catch (NumberFormatException e) {
                AlertHelper
                        .showAlert(Alert.AlertType.ERROR, owner, "Wrong input type!",
                                "Please enter a double "
                                        + "number to indicate your electricity percentage");
                return;
            }
        }
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/addActivity.fxml");
        }
    }

    @FXML
    private void handleAddVeganMealButtonAction(ActionEvent event) throws IOException {
        Window owner = addButton.getScene().getWindow();
        if (mealPortion.getText().isEmpty()) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                            "Please enter how much vegan meal you had");
            return;
        } else {
            try {
                int portions = Integer.parseInt(mealPortion.getText());
                //            new VeganMeal(ClientBuilder.newClient()).sendVeganMeal(portions);
            } catch (NumberFormatException e) {
                AlertHelper
                        .showAlert(Alert.AlertType.ERROR, owner, "Wrong input type!",
                                "Please enter a number to indicate your meal portion");
                return;
            }
        }
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/addActivity.fxml");
        }
    }

    @FXML
    private void handleAddFriendButtonAction(ActionEvent event) throws IOException {
        Window owner = addButton.getScene().getWindow();
        if (friendCode.getText().isEmpty()) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                            "Please enter the code of yor friend");
            return;
        } else {
            try {
                int code = Integer.parseInt(friendCode.getText());
                //            new VeganMeal(ClientBuilder.newClient()).sendVeganMeal(portions);
            } catch (NumberFormatException e) {
                AlertHelper
                        .showAlert(Alert.AlertType.ERROR, owner, "Wrong input type!",
                                "Please enter a number to indicate your friend's code");
                return;
            }
        }
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/scoreboard.fxml");
        }
        //String[][] friends = {{"Mayasa", "2500"}, {"Irem", "1500"}, {"Natalia", "1000"}};
        //loadFriends(friends);

    }

    @FXML
    private void loadFriends(String[][] friends) {
        GridPane root = new GridPane();
        root.setGridLinesVisible(true);
        final int numCols = 2 ;
        final int numRows = friends.length ;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setHgrow(Priority.NEVER);
            colConst.setPercentWidth(100.0 / numCols);
            root.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            Text text = new Text();
            text.setText("Name: " + friends[i][0] + "\n" + "Score: " + friends[i][1]);
            text.setFont(Font.font("Comic Sans MS"));
            rowConst.setVgrow(Priority.NEVER);
            rowConst.setPercentHeight(25);
            root.getRowConstraints().add(rowConst);
            ImageView image = new ImageView("images/userImage.jpg");
            image.setFitWidth(117);
            image.setFitHeight(108);
            root.add(image, 0, i);
            root.add(text, 1, i);
            root.setHalignment(image, HPos.RIGHT);
            root.setValignment(image, VPos.CENTER);

        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        Stage stage = new Stage();
        stage.setScene(new Scene(scrollPane, 600, 500));
        stage.show();
    }

    @FXML
    private void handleAddLocalProductButtonAction(ActionEvent event) throws IOException {
        CompactClient cc = new CompactClient();
        if (!cc.checkToken()) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/addActivity.fxml");
        }
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
