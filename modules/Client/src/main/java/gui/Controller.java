package gui;

import cn.hutool.json.JSONObject;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.chart.PieChart;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import restclient.CompactClient;
import restclient.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Controller {

    private static DecimalFormat decim = new DecimalFormat(".##");

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
    private ChoiceBox mealTypes;

    @FXML
    private TextField beforeTemperature;

    @FXML
    private TextField afterTemperature;

    @FXML
    private TextField electricityAmount;

    @FXML
    private TextField kilometers;

    @FXML
    private ChoiceBox publicTransport;

    @FXML
    private ChoiceBox carType;

    @FXML
    private GridPane todaysTip;

    @FXML
    private GridPane todaysTip2;

    @FXML
    private Text todaysTipText;

    @FXML
    private Text tip;

    @FXML
    private TextField friendCode;

    @FXML
    private ChoiceBox energyType;

    @FXML
    private GridPane achievementsGrid;

    @FXML
    private PieChart pieChart;

    @FXML
    private TextField bikeKilometers;

    @FXML
    private ChoiceBox transportType;

    @FXML
    private VBox vbox;

    @FXML
    private Parent fxml;

    @FXML
    private AnchorPane root;

    @FXML
    private Button signupButton;

    @FXML
    private Label message;

    @FXML
    private TextField amountVegetarianMeal;

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
    private Button logoutButton;

    @FXML
    private Button exitButton;

    @FXML
    private ChoiceBox productCategory;

    @FXML
    private TextField amountLocalProduct;

    @FXML
    private Label nameLabel;

    @FXML
    private GridPane menuPane;

    @FXML
    private Text usernameField;

    @FXML
    private Label userName;

    @FXML
    private Text noOfFriendsField;

    @FXML
    private Text emailField;

    @FXML
    private Text co2Field;

    @FXML
    private GridPane youPagePane;

    @FXML
    private ImageView ach0;
    @FXML
    private ImageView ach1;
    @FXML
    private ImageView ach2;

    @FXML
    private ImageView ach3;
    @FXML
    private ImageView ach4;
    @FXML
    private ImageView ach5;

    @FXML
    private ImageView ach6;
    @FXML
    private ImageView ach7;
    @FXML
    private ImageView ach8;

    @FXML
    private ImageView ach9;
    @FXML
    private ImageView ach10;
    @FXML
    private ImageView ach11;

    @FXML
    private ImageView ach12;
    @FXML
    private ImageView ach13;
    @FXML
    private ImageView ach14;

    @FXML
    private ImageView ach15;
    @FXML
    private ImageView ach16;
    @FXML
    private ImageView ach17;

    @FXML
    private ImageView ach18;
    @FXML
    private ImageView ach19;
    @FXML
    private ImageView ach20;

    @FXML
    private ImageView ach21;
    @FXML
    private ImageView ach22;
    @FXML
    private ImageView ach23;

    @FXML
    private GridPane addActivityPane;

    @FXML
    private Button addVeganMealButton;

    @FXML
    private Button addLocalProductButton;

    @FXML
    private Button addTemperatureButton;

    @FXML
    private Button addSolarPanelButton;

    @FXML
    private Button addPublicTransportButton;

    @FXML
    private Button addBikeButton;

    @FXML
    private TextArea veganLocalLabel;

    @FXML
    private TextArea solarHomeLabel;

    @FXML
    private TextArea publicBikeLabel;

    @FXML
    private Text levelField;


    @FXML
    private void handleAddBikeButtonAction(ActionEvent event) throws IOException {
        Window owner = addButton.getScene().getWindow();
        if (transportType.getValue() == null) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                            "Please enter type of your transportation");
            return;
        } else if (bikeKilometers.getText().isEmpty()) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                            "Please enter the number of kilometers which you travelled");
            return;
        }
        try {
            Double numberOfKilometers = Double.parseDouble(bikeKilometers.getText());
            String typeOfTransport = transportType.getValue().toString();
        } catch (NumberFormatException e) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Wrong input type!",
                            "Please enter a "
                                    + "double number to indicate number of kilometers you go");
            return;
        }

        if (Double.parseDouble(bikeKilometers.getText()) <= 10) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Invalid Input!",
                            "Please enter a "
                                    + "number of kilometres bigger than 10!");
            return;
        } else if (Double.parseDouble(bikeKilometers.getText()) >= 5000) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Wait a second!",
                            "We think that amount should be lower than 5000!");
            return;
        }

        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
            return;
        }
        cc.postBiker(transportType.getValue().toString(),
                (int) Double.parseDouble(bikeKilometers.getText()));
        loadPage(event, "fxml/addActivity.fxml");
    }

    @FXML
    private void loadFriends(ActionEvent event, String[][] friends) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        GridPane root = new GridPane();
        root.setStyle("-fx-background-color: #91cb3e;");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        final int numCols = 2;
        final int numRows = friends.length + 1;
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
            if (i < 1) {
                rowConst.setPrefHeight(71);
                root.getRowConstraints().add(rowConst);
                Text text = new Text();
                text.setText("SCOREBOARD");
                text.setTextAlignment(TextAlignment.CENTER);
                text.setFont(Font.font("System Bold", 30));
                root.add(text, 1, i);
                Button button = new Button();
                button.setText("BACK");
                button.setStyle("-fx-background-color: #000000; -fx-text-fill: #91cb3e;");
                button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: "
                        + "linear-gradient(#000000, grey); -fx-text-fill: #91cb3e"));
                button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #000000; "
                        + "-fx-text-fill: #91cb3e;"));
                button.setOnAction(value -> {
                    try {
                        Parent addPageParent = FXMLLoader.load(getClass().getClassLoader()
                                .getResource("fxml/menu.fxml"));
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
            } else {
                if (numRows > 3) {
                    rowConst.setPrefHeight(143);
                    root.getRowConstraints().add(rowConst);
                    Text text = new Text();
                    text.setText("Name: " + friends[i - 1][0] + "\n" + "CO2 Saved: "
                            + decim.format(Double.parseDouble(friends[i - 1][1])));
                    ImageView image = new ImageView("images/human.png");
                    image.setPreserveRatio(true);
                    image.setFitWidth(117);
                    image.setFitHeight(108);
                    root.add(image, 0, i);
                    root.add(text, 1, i);
                    root.setHalignment(image, HPos.RIGHT);
                    root.setValignment(image, VPos.CENTER);
                    root.setStyle("-fx-background-color: #91cb3e;");
                    root.setHgap(40); //horizontal gap in pixels
                }
            }
        }
        if (friends.length == 2 || friends.length == 1 || friends.length == 0) {
            for (int i = 0; i < friends.length; i++) {
                RowConstraints rowConst = new RowConstraints();
                rowConst.setVgrow(Priority.NEVER);
                rowConst.setPrefHeight(143);
                root.getRowConstraints().add(rowConst);
                Text text = new Text();
                text.setText("Name: " + friends[i][0] + "\n" + "CO2 Saved: "
                        + friends[i][1]);
                ImageView image = new ImageView("images/human.png");
                image.setPreserveRatio(true);
                image.setFitWidth(117);
                image.setFitHeight(108);
                root.add(image, 0, i + 1);
                root.add(text, 1, i + 1);
                root.setHalignment(image, HPos.RIGHT);
                root.setValignment(image, VPos.CENTER);
                root.setStyle("-fx-background-color: #91cb3e;");
                root.setHgap(40); //horizontal gap in pixels
            }
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPrefHeight(429 - 143 * friends.length);
            rowConst.setVgrow(Priority.NEVER);
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: #91cb3e;");
            root.getRowConstraints().add(rowConst);
            root.add(pane, 1, friends.length + 1);
        }
        scrollPane.setContent(root);
        stage.setScene(new Scene(scrollPane, 600, 500));
        stage.show();
    }

    @FXML
    private void loadFriends(String[][] friends) {
        GridPane root = new GridPane();
        root.setGridLinesVisible(true);
        final int numCols = 2;
        final int numRows = friends.length;
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
    private void initialize() throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);
        if (todaysTip != null) {
            Scanner scanner = new Scanner(new File("tips.txt"));
            List<String> lines = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            tip.toFront();
            todaysTipText.toFront();
            Random rn = new Random();
            int randomNum = (rn.nextInt() & Integer.MAX_VALUE) % lines.size();
            String text = lines.get(randomNum);
            todaysTipText.setText(text);
        }
        if (menuPane != null) {
            JSONObject details = cc.getPersonalInfo();
            nameLabel.setText("Hello " + details.get("userName").toString());
        }
        if (youPagePane != null) {
            levelField.setText("" + cc.getLevel());
            JSONObject details = cc.getPersonalInfo();
            userName.setText(details.get("userName").toString() + "'s page");
            usernameField.setText(details.get("userName").toString());
            noOfFriendsField.setText(details.get("friendsNo").toString());
            emailField.setText(details.get("email").toString());
            co2Field.setText(decim.format(Double.parseDouble(details.get("co2Saved").toString())));
        }

        if (addActivityPane != null) {
            veganLocalLabel.setDisable(true);
            veganLocalLabel.setStyle("-fx-opacity: 1;");
            publicBikeLabel.setDisable(true);
            publicBikeLabel.setStyle("-fx-opacity: 1");
            solarHomeLabel.setDisable(true);
            solarHomeLabel.setStyle("-fx-opacity: 1");
            addVeganMealButton.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            veganLocalLabel.setText("Eat vegan meal");
                        }
                    });
            addVeganMealButton.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            veganLocalLabel.setText("");
                        }
                    });

            addLocalProductButton.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            veganLocalLabel.setText("Use local product");
                        }
                    });
            addLocalProductButton.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            veganLocalLabel.setText("");
                        }
                    });
            addSolarPanelButton.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            solarHomeLabel.setText("Install a solar panel");
                        }
                    });
            addSolarPanelButton.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            solarHomeLabel.setText("");
                        }
                    });
            addTemperatureButton.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            solarHomeLabel.setText("Decrease the temperature of your home");
                        }
                    });
            addTemperatureButton.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            solarHomeLabel.setText("");
                        }
                    });
            addPublicTransportButton.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            publicBikeLabel.setText("Use public transportation");
                        }
                    });
            addPublicTransportButton.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            publicBikeLabel.setText("");
                        }
                    });
            addBikeButton.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            publicBikeLabel.setText("Use bike ");
                        }
                    });
            addBikeButton.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            publicBikeLabel.setText("");
                        }
                    });
        }
    }

    //    @FXML
    //    private void loadMenuPageLogin(ActionEvent event) throws IOException {
    //        Window owner = loginButton.getScene().getWindow();
    //        if (nameField.getText().isEmpty()) {
    //            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Log-in Error!",
    //                    "Please enter your username");
    //            return;
    //        }
    //        if (passwordField.getText().isEmpty()) {
    //            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Log-in Error!",
    //                    "Please enter a password");
    //            return;
    //        }
    //
    //        User user = new User(nameField.getText(), passwordField.getText());
    //        boolean authenticated = false;
    //        authenticated = user.login(null);
    //        String token = user.getToken();
    //
    //        if (authenticated) {
    //            PrintWriter pw = new PrintWriter("test.txt", "UTF-8");
    //            pw.println(token);
    //            pw.close();
    //
    //            loadPage(event, "fxml/menu.fxml");
    //        } else {
    //            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Authentication failed!",
    //                    "Please enter credentials again.");
    //        }
    //    }

    @FXML
    private void loadMenuPage(ActionEvent event) throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/menu.fxml");
        }
    }

    @FXML
    private void handleSignUpButtonAction(ActionEvent event) throws IOException {
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
        try {
            if (user.register(signupName.getText(), email.getText(), password.getText())
                    == null) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Invalid Sign-up!",
                        "This email is already registered.");
                return;
            }
        } catch (IllegalArgumentException e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Sign-up Error!",
                    e.getMessage());
            return;
        }

        handleLogoutButtonAction(event);
    }

    @FXML
    private void loadSigninPage(ActionEvent event) throws IOException {
        fxml = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/signin.fxml"));
        vbox.getChildren().removeAll();
        vbox.getChildren().setAll(fxml);

        final Timeline timeline = new Timeline();
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(vbox.translateXProperty(), 0);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    @FXML
    private void loadSignupPage(ActionEvent event) throws IOException {
        fxml = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/signup.fxml"));
        vbox.getChildren().removeAll();
        vbox.getChildren().setAll(fxml);

        final Timeline timeline = new Timeline();
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(vbox.translateXProperty(), -520);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
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
    private void handleBackToMenuAction(ActionEvent event) throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/menu.fxml");
        }
    }

    @FXML
    private void handleAch1Action(ActionEvent event) throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/achievements1.fxml");
        }
    }

    @FXML
    private void handleAch2Action(ActionEvent event) throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/achievements2.fxml");
        }
    }

    @FXML
    private void handleAch3Action(ActionEvent event) throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/achievements3.fxml");
        }
    }

    private void loadAchievements(int page, Stage appStage) throws IOException {
        int start = 0;
        int end = 0;
        switch (page) {
            case 1:
                start = 0;
                end = 8;
                break;
            case 2:
                start = 9;
                end = 17;
                break;
            case 3:
                start = 18;
                end = 23;
                break;
            default:
                start = 0;
                end = 23;
                break;
        }

        Scene scene = appStage.getScene();
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        String bits = cc.getAchievements();

        //        String bits = "010000000100000000100000000";
        for (int i = start; i <= end; i++) {
            char charc = bits.charAt(i);
            boolean cond = charc == '0';
            if (cond) {
                String str = "ach" + i;

                ImageView view = (ImageView) scene.lookup("#" + str);
                view.setOpacity(0.2);
            }
        }
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
        try {
            if (user.register(signupName.getText(), email.getText(), password.getText())
                    == null) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Invalid Sign-up!",
                        "This email is already registered.");
                return;
            }
        } catch (IllegalArgumentException e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Sign-up Error!",
                    e.getMessage());
            return;
        }

        loadPage(event, "fxml/loginPage.fxml");
    }

    @FXML
    protected void handleActivitiesButtonAction(ActionEvent event) throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            BorderPane root = new BorderPane();
            root.setStyle("-fx-background-color: #91cb3e;");
            root.setPadding(new Insets(20, 20, 20, 20));
            Button button = new Button();
            button.setText("BACK");
            button.setStyle("-fx-background-color: #000000; -fx-text-fill: #91cb3e;");
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: "
                    + "linear-gradient(#000000, grey); -fx-text-fill: #91cb3e"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #000000; "
                    + "-fx-text-fill: #91cb3e;"));
            button.setOnAction(value -> {
                try {
                    Parent addPageParent = FXMLLoader.load(getClass().getClassLoader()
                            .getResource("fxml/menu.fxml"));
                    Scene addPageScene = new Scene(addPageParent);
                    stage.setScene(addPageScene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            button.setLayoutX(250);
            button.setLayoutY(220);
            root.setTop(button);
            JSONObject info = cc.getStats();
            //            System.out.println(info.toJSONString(10));
            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data("Vegan Meal",
                                    Double.parseDouble(
                                            decim.format(Double.parseDouble(
                                            info.get("total_Meals").toString())))),
                            new PieChart.Data("Public Transport",
                                    Double.parseDouble(
                                            decim.format(Double.parseDouble(
                                            info.get("savedPublicTransport").toString())))),
                            new PieChart.Data("Home Temperature",
                                    Double.parseDouble(
                                            decim.format(Double.parseDouble(
                                            info.get("savedHeatConsumption").toString())))),
                            new PieChart.Data("Bike",
                                    Double.parseDouble(
                                            decim.format(Double.parseDouble(
                                            info.get("bikeSaved").toString())))),
                            new PieChart.Data("Local Product",
                                    Double.parseDouble(
                                            decim.format(Double.parseDouble(
                                            info.get("localSaved").toString())))),
                            new PieChart.Data("Solar Panel",
                                    Double.parseDouble(
                                            decim.format(Double.parseDouble(
                                            info.get("savedSolar").toString()))))
                    );

            final PieChart chart = new PieChart(pieChartData);
            chart.setLabelsVisible(false);
            chart.setTitle("SCORE DISTRIBUTION");

            final Label caption = new Label("");
            caption.setStyle("-fx-font: 20 System;");
            double total = 0;
            for (final PieChart.Data data : chart.getData()) {
                total += data.getPieValue();
            }
            final double totalAmount = total;
            Group chartWithCaption = new Group(chart, caption);
            for (final PieChart.Data data : chart.getData()) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                Point2D locationInScene =
                                        new Point2D(event.getSceneX(), event.getSceneY());
                                Point2D locationInParent =
                                        chartWithCaption.sceneToLocal(locationInScene);

                                caption.relocate(
                                        locationInParent.getX() + 20,
                                        locationInParent.getY());

                                caption.setText(String.valueOf(Math
                                        .round((data.getPieValue() / totalAmount) * 100)) + "%");
                            }
                        });
                data.setName(data.getName() + ": " + data.getPieValue() + " kg");
            }
            root.setCenter(chartWithCaption);
            stage.setScene(new Scene(root, 600, 500));
            stage.show();
        }
    }

    @FXML
    protected void handleAddFriendsButtonAction(ActionEvent event) throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/addFriends.fxml");
        }
    }

    @FXML
    protected void handleAchievementsButtonAction(ActionEvent event) throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        cc.getAchievements();

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/achievements1.fxml");
        }
    }

    @FXML
    protected void handleFriendsButtonAction(ActionEvent event) throws IOException {

        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            String[][] friends = cc.getAllFriends();
            loadFriends(event, friends);
        }
    }

    @FXML
    protected void handleYouButtonAction(ActionEvent event) throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/mainLogin.fxml");
        } else {
            loadPage(event, "fxml/you.fxml");
        }
    }

    @FXML
    protected void handleAddActivityButtonAction(ActionEvent event) throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/addActivity.fxml");
        }
    }

    @FXML
    public void handleExitButtonAction(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

    }

    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader
                .load(getClass().getClassLoader().getResource("fxml/mainLogin.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    @FXML
    protected void loadPage(ActionEvent event, String fileName) throws IOException {
        Parent addPageParent = FXMLLoader.load(getClass().getClassLoader()
                .getResource(fileName));
        Scene addPageScene = new Scene(addPageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(addPageScene);
        appStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        appStage.setX((primScreenBounds.getWidth() - appStage.getWidth()) / 2);
        appStage.setY((primScreenBounds.getHeight() - appStage.getHeight()) / 2);

        if (fileName.equals("fxml/achievements1.fxml")) {
            loadAchievements(1, appStage);
        } else if (fileName.equals("fxml/achievements2.fxml")) {
            loadAchievements(2, appStage);
        } else if (fileName.equals("fxml/achievements3.fxml")) {
            loadAchievements(3, appStage);
        }
    }

    @FXML
    private void handleVegetarianMealButtonAction(ActionEvent event) throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/veganMeal.fxml");
        }
    }

    @FXML
    private void handlePublicTransportButtonAction(ActionEvent event) throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/publicTransport.fxml");
        }
    }

    @FXML
    private void handleTemperatureButtonAction(ActionEvent event) throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/temperature.fxml");
        }
    }

    @FXML
    private void handleSolarPanelButtonAction(ActionEvent event) throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/solarPanel.fxml");
        }
    }

    @FXML
    private void handleBikeButtonAction(ActionEvent event) throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/bike.fxml");
        }
    }

    @FXML
    private void handleLocalProductButtonAction(ActionEvent event) throws IOException {
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/localProduct.fxml");
        }
    }

    @FXML
    private void handleAddPublicTransportButtonAction(ActionEvent event) throws IOException {
        Double numberOfKilometers = null;
        String typeOfCar = null;
        String publictransportType = null;
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
        }

        try {
            numberOfKilometers = Double.parseDouble(kilometers.getText());
            typeOfCar = carType.getValue().toString();
            publictransportType = publicTransport.getValue().toString();


        } catch (NumberFormatException e) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Wrong input type!",
                            "Please enter a "
                                    + "double number to indicate number of kilometers you go");
            return;
        }

        if (Double.parseDouble(kilometers.getText()) <= 10) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Invalid field!",
                            "Please enter a number of kilometres bigger than 10");
            return;
        } else if (Double.parseDouble(kilometers.getText()) > 20000) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Slow down!",
                            "Please input values lower than 20000!");
            return;
        }

        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            cc.postPublicTransport(typeOfCar, publictransportType, numberOfKilometers);
            loadPage(event, "fxml/addActivity.fxml");
        }

    }

    @FXML
    private void handleAddTemperatureButtonAction(ActionEvent event) throws IOException {
        Window owner = addButton.getScene().getWindow();
        if (energyType.getValue() == null) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                            "Please enter your energy type");
            return;
        }

        if (beforeTemperature.getText().isEmpty()) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                            "Please enter the temperature before decreasing");
            return;
        }
        try {
            int before = Integer.parseInt(beforeTemperature.getText());
            int after = Integer.parseInt(afterTemperature.getText());
            String typeOfEnergy = (String) energyType.getValue();

            File file = new File("test.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            CompactClient cc = new CompactClient(file, br);
            cc.postHeatConsumption(before, after, typeOfEnergy);
        } catch (NumberFormatException e) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Wrong input type!",
                            "Please enter an integer number to indicate your home's temperature");
            return;
        }

        if (afterTemperature.getText().isEmpty()) {
            Controller.AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                            "Please enter the temperature after decreasing");
            return;
        } else if (Double.parseDouble(beforeTemperature.getText()) <= 0
                || Double.parseDouble(afterTemperature.getText()) <= 0) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Invalid value!",
                            "Please input values higher than 0!");
            return;
        } else if (Double.parseDouble(beforeTemperature.getText())
                - Double.parseDouble(afterTemperature.getText()) <= 0) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Invalid values!",
                            "Difference between the values can't be lower than 0!");
            return;
        } else if (Double.parseDouble(beforeTemperature.getText())
                - Double.parseDouble(afterTemperature.getText()) >= 20000) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Difference too big",
                            "Difference too big, are you heating a whole hotel?");
            return;
        }
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            loadPage(event, "fxml/addActivity.fxml");
        }
    }

    @FXML
    private void handleAddSolarPanelButtonAction(ActionEvent event) throws IOException {
        Window owner = addButton.getScene().getWindow();
        if (electricityAmount.getText().isEmpty()) {
            Controller.AlertHelper.showAlert(
                    Alert.AlertType.ERROR, owner, "Unfilled field!",
                    "Please enter the total kWh generated by your solar panels!");
            return;
        }
        try {
            Double percentage = Double.parseDouble(electricityAmount.getText());
        } catch (NumberFormatException e) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Wrong input type!",
                            "Please enter a double "
                                    + "number to indicate your electricity percentage");
            return;
        }

        if (Double.parseDouble(electricityAmount.getText()) <= 0) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Invalid field!",
                            "Number of kWh can't be negative or 0!");
            return;
        } else if (Double.parseDouble(electricityAmount.getText()) > 50000) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Over 50000 kilowatts!",
                            "Are you powering up a factory? If you don't, please input "
                                    + "a value lower than 50000 kilowatts");
            return;
        }
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            cc.postSolar((int) Double.parseDouble(electricityAmount.getText()));
            loadPage(event, "fxml/addActivity.fxml");
        }
    }

    @FXML
    private void handleAddVeganMealButtonAction(ActionEvent event) throws IOException {
        Window owner = addButton.getScene().getWindow();
        if (mealTypes.getValue() == null || mealTypes.getValue().toString().isEmpty()) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                            "Please enter the amount of kilograms!");
            return;
        }

        try {
            String type = mealTypes.getValue().toString();
            double portions = Double.parseDouble(amountVegetarianMeal.getText());
        } catch (NumberFormatException e) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Wrong input type!",
                            "Please enter a number to indicate your meal portion!");
            return;
        }

        if (Double.parseDouble(amountVegetarianMeal.getText()) <= 0) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Invalid Field!",
                            "Kilograms can't be negative or 0!");
            return;
        } else if (Double.parseDouble(amountVegetarianMeal.getText()) > 20) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Lower than 20 kg!",
                            "As long as you are not a bear, we think that amount is a bit "
                                    + "too big!");
            return;
        }

        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            cc.postMeal(Double.parseDouble(amountVegetarianMeal.getText()),
                    "Meat", mealTypes.getValue().toString());
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
        }
        //         else {
        //            try {
        //                int code = Integer.parseInt(friendCode.getText());
        //                //new VeganMeal(ClientBuilder.newClient()).sendVeganMeal(portions);
        //            } catch (NumberFormatException e) {
        //                AlertHelper
        //                        .showAlert(Alert.AlertType.ERROR, owner, "Wrong input type!",
        //                                "Please enter a number to indicate your friend's code");
        //                return;
        //            }
        //        }
        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            JSONObject jo = cc.followUser(friendCode.getText());
            if (jo.get("status").toString().equals("Success")) {
                AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Success",
                        "You are now following this person!");
            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Not possible!",
                        jo.get("status").toString());
            }
        }
        //String[][] friends = {{"Mayasa", "2500"}, {"Irem", "1500"}, {"Natalia", "1000"}};
        //loadFriends(friends);

    }

    @FXML
    private void handleAddLocalProductButtonAction(ActionEvent event) throws IOException {
        Window owner = addButton.getScene().getWindow();
        if (productCategory.getValue() == null || productCategory.getValue().toString().isEmpty()) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                            "Please choose a product type");
            return;
        }

        try {
            String type = productCategory.getValue().toString();
            double portions = Double.parseDouble(amountLocalProduct.getText());
        } catch (NumberFormatException e) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Wrong input type!",
                            "Please enter a number to indicate the amount in kilograms");
            return;
        }

        if (Double.parseDouble(amountLocalProduct.getText()) <= 0) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Unfilled field!",
                            "Kilograms can't be negative!");
            return;
        } else if (Double.parseDouble(amountLocalProduct.getText()) > 100) {
            AlertHelper
                    .showAlert(Alert.AlertType.ERROR, owner, "Kilograms over 100!",
                            "We know you love local, but please input a value lower "
                                    + "than 100 kilograms");
            return;
        }

        File file = new File("test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        CompactClient cc = new CompactClient(file, br);

        File toRead = new File("test.txt");
        BufferedReader got = new BufferedReader(new FileReader(toRead));
        User usr = new User("", "");
        if (!cc.checkToken(toRead, got, usr)) {
            loadPage(event, "fxml/loginPage.fxml");
        } else {
            cc.postLocalProduce(Double.parseDouble(amountLocalProduct.getText()),
                    productCategory.getValue().toString());
            loadPage(event, "fxml/addActivity.fxml");
        }
    }

    public static class AlertHelper {

        /**
         * The method which gives alert with a specific message.
         *
         * @param alertType the type of the alert
         * @param owner     the owner of the alert
         * @param title     the title of the alert
         * @param message   the message of the alert
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
    private void showYouLabel() {
        youLabel.setVisible(true);
    }

    @FXML
    private void hideYouLabel() {
        youLabel.setVisible(false);
    }

    @FXML
    private void showActivitiesLabel() {
        activitiesLabel.setVisible(true);
    }

    @FXML
    private void hideActivitiesLabel() {
        activitiesLabel.setVisible(false);
    }

    @FXML
    private void showAddActivityLabel() {
        addActivityLabel.setVisible(true);
    }

    @FXML
    private void hideAddActivityLabel() {
        addActivityLabel.setVisible(false);
    }

    @FXML
    private void showFriendsLabel() {
        friendsLabel.setVisible(true);
    }

    @FXML
    private void hideFriendsLabel() {
        friendsLabel.setVisible(false);
    }

    @FXML
    private void showAddFriendLabel() {
        addFriendLabel.setVisible(true);
    }

    @FXML
    private void hideAddFriendLabel() {
        addFriendLabel.setVisible(false);
    }

    @FXML
    private void showAchievementsLabel() {
        achievementsLabel.setVisible(true);
    }

    @FXML
    private void hideAchievementsLabel() {
        achievementsLabel.setVisible(false);
    }


}
