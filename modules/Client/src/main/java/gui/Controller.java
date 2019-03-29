package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private ChoiceBox energyType;

    @FXML
    private GridPane achievementsGrid;

    @FXML
    private PieChart pieChart;

    @FXML
    private Button youButton;

    @FXML
    private Label youLabel;

    @FXML
    private Button addActivityButton;

    @FXML
    private Label addActivityLabel;

    @FXML
    private Button friendsButton;

    @FXML
    private Label friendsLabel;

    @FXML
    private Button addFriendButton;

    @FXML
    private Label addFriendLabel;

    @FXML
    private Button achievementsButton;

    @FXML
    private Label achievementsLabel;

    @FXML
    private Button activitiesButton;

    @FXML
    private Label activitiesLabel;


    @FXML
    private void initialize() throws FileNotFoundException {
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
        if (achievementsGrid != null) {
            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data("Vegan Meal", 100),
                            new PieChart.Data("Public Transport", 200),
                            new PieChart.Data("Home Temperature", 50),
                            new PieChart.Data("Bike", 75),
                            new PieChart.Data("Local Product", 110),
                            new PieChart.Data("Solar Panel", 300)
                            );
            pieChart.setTitle("SCORE DISTRIBUTION");
            pieChart.setMaxSize(1000, 1000);
            pieChart.setData(pieChartData);
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
        loadPage(event, "fxml/dashboard.fxml");
    }

    @FXML
    private void loadMenuPage(ActionEvent event) throws IOException {
        loadPage(event, "fxml/dashboard.fxml");
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
        loadPage(event, "fxml/loginPage.fxml");
    }

    @FXML
    public void handleDashboardButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "fxml/dashboard.fxml");
    }

    @FXML
    protected void handleActivitiesButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "fxml/activities.fxml");
    }

    @FXML
    protected void handleFriendsButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "fxml/addFriends.fxml");
    }

    @FXML
    protected void handleAchievementsButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "fxml/achievements.fxml");
    }

    @FXML
    protected void handleScoreboardButtonAction(ActionEvent event) throws IOException {
        String[][] friends = {{"Mayasa", "2500"}, {"Irem", "1500"}, {"Natalia", "1000"},
                              {"Mayasa", "2500"}, {"Irem", "1500"},
                              {"Mayasa", "2500"}, {"Irem", "1500"}, {"Natalia", "1000"}};
        loadFriends(event, friends);
    }

    @FXML
    protected void handleYouButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "fxml/you.fxml");
    }

    @FXML
    protected void handleAddActivityButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "fxml/addActivity.fxml");
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
        loadPage(event, "fxml/veganMeal.fxml");
    }

    @FXML
    private void handlePublicTransportButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "fxml/publicTransport.fxml");
    }

    @FXML
    private void handleTemperatureButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "fxml/temperature.fxml");
    }

    @FXML
    private void handleSolarPanelButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "fxml/solarPanel.fxml");
    }

    @FXML
    private void handleBikeButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "fxml/bike.fxml");
    }

    @FXML
    private void handleLocalProductButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "fxml/localProduct.fxml");
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
                Double numberOfKilometers = Double.parseDouble(kilometers.getText());
                String typeOfCar = carType.getValue().toString();
                String publictransportType = publicTransport.getValue().toString();
            } catch (NumberFormatException e) {
                AlertHelper
                        .showAlert(Alert.AlertType.ERROR, owner, "Wrong input type!",
                                "Please enter a "
                                        + "double number to indicate number of kilometers you go");
                return;
            }
        }
        loadPage(event, "fxml/addActivity.fxml");
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
                Double before = Double.parseDouble(beforeTemperature.getText());
                Double after = Double.parseDouble(afterTemperature.getText());
                String typeOfEnergy = (String) energyType.getValue();
            } catch (NumberFormatException e) {
                AlertHelper
                        .showAlert(Alert.AlertType.ERROR, owner, "Wrong input type!",
                                "Please enter a double number to indicate your home's temperature");
                return;
            }
        }
        loadPage(event, "fxml/addActivity.fxml");
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
        loadPage(event, "fxml/addActivity.fxml");
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
        loadPage(event, "fxml/addActivity.fxml");
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
            String code = friendCode.getText();
        }
        String[][] friends = {{"Mayasa", "2500"}, {"Irem", "1500"}, {"Natalia", "1000"},
                              {"Mayasa", "2500"}, {"Irem", "1500"},
                              {"Mayasa", "2500"}, {"Irem", "1500"}, {"Natalia", "1000"}};
        loadFriends(event, friends);
    }

    @FXML
    private void loadFriends(ActionEvent event, String[][] friends) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        GridPane root = new GridPane();
        ScrollPane scrollPane = new ScrollPane();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        final int numCols = 2 ;
        final int numRows = friends.length + 1 ;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setHgrow(Priority.NEVER);
            if (i == 0) {
                colConst.setPrefWidth(200);
            } else {
                colConst.setPrefWidth(screenBounds.getWidth() - 200);
            }
            root.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setVgrow(Priority.NEVER);
            if (i >= 1) {
                rowConst.setPrefHeight(143);
                root.getRowConstraints().add(rowConst);
                Text text = new Text();
                text.setText("Name: " + friends[i - 1][0] + "\n" + "Score: " + friends[i - 1][1]);
                text.setFont(Font.font("Comic Sans MS"));
                ImageView image = new ImageView("images/userImage.jpg");
                image.setFitWidth(117);
                image.setFitHeight(108);
                root.add(image, 0, i);
                root.add(text, 1, i);
                root.setHalignment(image, HPos.RIGHT);
                root.setValignment(image, VPos.CENTER);
                root.setStyle("-fx-background-color: #00ffbc;");
                root.setHgap(40); //horizontal gap in pixels
            } else {
                rowConst.setPrefHeight(71);
                root.getRowConstraints().add(rowConst);
                Text text = new Text();
                text.setText("SCOREBOARD");
                text.setFont(Font.font("Comic Sans MS", 30));
                root.add(text, 1, i);
                Button button = new Button();
                button.setText("BACK");
                button.setStyle("-fx-background-color: #000000; -fx-text-fill: #00ffbc;");
                button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: "
                        + "linear-gradient(#000000, grey); -fx-text-fill: #00ffbc"));
                button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #000000; "
                        + "-fx-text-fill: #00ffbc;"));
                button.setOnAction(value ->  {
                    try {
                        Parent addPageParent = FXMLLoader.load(getClass().getClassLoader()
                                .getResource("fxml/dashboard.fxml"));
                        Scene addPageScene = new Scene(addPageParent);
                        stage.setScene(addPageScene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                button.setLayoutX(250);
                button.setLayoutY(220);
                root.add(button, 0, i);
                root.setMargin(button, new Insets(5, 0, 0, 20));
            }
        }
        scrollPane.setContent(root);
        stage.setScene(new Scene(scrollPane, 600, 500));
        stage.show();
    }

    @FXML
    private void handleAddLocalProductButtonAction(ActionEvent event) throws IOException {
        loadPage(event, "fxml/addActivity.fxml");
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
