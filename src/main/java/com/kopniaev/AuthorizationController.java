package com.kopniaev;

import com.kopniaev.model.User;
import com.kopniaev.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthorizationController {

    @FXML
    public TextField textFieldLogin;
    public TextField textFieldPassword;

    public void buttonRegistration(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) textFieldLogin.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("registration.fxml"));
        Scene sceneReg = new Scene(loader.load());
        stage.setScene(sceneReg);
    }

    public void buttonSignIn(ActionEvent actionEvent) {
        String login = textFieldLogin.getText();
        String password = textFieldPassword.getText();

        if (login.isEmpty())
            App.showAlert("Error", "Login is empty", Alert.AlertType.WARNING);
        else if (password.isEmpty())
            App.showAlert("Error", "Password is empty", Alert.AlertType.WARNING);
        else {
            try {
                User user = new UserRepository(login, password).getUser();

                Stage stageAuthorization = (Stage) textFieldLogin.getScene().getWindow();
                stageAuthorization.close();

                Stage stageMain = new Stage();
                FXMLLoader loaderMain = new FXMLLoader(getClass().getResource("main.fxml"));
                Parent root = loaderMain.load();
                stageMain.setScene(new Scene(root));

                MainController mainController = loaderMain.getController();
                mainController.initData(user);
                stageMain.show();

                App.setPreferences("login", login);
                App.setPreferences("password", password);

                textFieldLogin.setText("");
                textFieldPassword.setText("");
            } catch (IOException e) {
                App.showAlert("Error", e.getMessage(), Alert.AlertType.INFORMATION);
            }
        }
    }
}
