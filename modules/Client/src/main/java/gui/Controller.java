package gui;

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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Controller {
    @FXML
    private AnchorPane holderPane;
    @FXML
    private TextField todaysTip;


    @FXML
    protected void initialize(ActionEvent event) throws IOException {
        Parent groupsPageParent = FXMLLoader.load(getClass().getClassLoader()
                .getResource("menu.fxml"));
        Scene groupsPageScene = new Scene(groupsPageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(groupsPageScene);
        appStage.show();
    }

    @FXML
    protected void handleDashboardButtonAction(ActionEvent event) throws IOException {
        holderPane.getChildren().add(FXMLLoader.load(getClass().getClassLoader()
                .getResource("dashboard.fxml")));

    }

    @FXML
    protected void handleActivitiesButtonAction(ActionEvent event) throws IOException {
        holderPane.getChildren().add(FXMLLoader.load(getClass().getClassLoader()
                .getResource("activities.fxml")));
    }

    @FXML
    protected void handleGroupsButtonAction(ActionEvent event) throws IOException {

        holderPane.getChildren().add(FXMLLoader.load(getClass().getClassLoader()
                .getResource("groups.fxml")));

    }


    @FXML
    protected void handleAchievementsButtonAction(ActionEvent event) throws IOException {
        holderPane.getChildren().add(FXMLLoader.load(getClass().getClassLoader()
                .getResource("achievements.fxml")));
    }

    @FXML
    protected void handleScoreboardButtonAction(ActionEvent event) throws IOException {
        holderPane.getChildren().add(FXMLLoader.load(getClass().getClassLoader()
                .getResource("scoreboard.fxml")));
    }

    @FXML
    protected void handleYouButtonAction(ActionEvent event) throws IOException {
        holderPane.getChildren().add(FXMLLoader.load(getClass().getClassLoader()
                .getResource("youPage.fxml")));
    }

    @FXML
    protected void handleAddActivityButtonAction(ActionEvent event) throws IOException {
        holderPane.getChildren().add(FXMLLoader.load(getClass().getClassLoader()
                .getResource("addActivity.fxml")));
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
