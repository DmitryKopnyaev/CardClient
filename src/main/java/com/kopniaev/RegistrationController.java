package com.kopniaev;

import com.kopniaev.model.User;
import com.kopniaev.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {

    @FXML
    public TextField textFieldLogin;
    public TextField textFieldPassword;
    public TextField textFieldName;


    public void buttonOk(ActionEvent actionEvent) {
        String login = textFieldLogin.getText();
        String password = textFieldPassword.getText();
        String name = textFieldName.getText();
        if (login.isEmpty())
            App.showAlert("Error", "Login is empty", Alert.AlertType.WARNING);
        else if (password.isEmpty())
            App.showAlert("Error", "Password is empty", Alert.AlertType.WARNING);
        else if (name.isEmpty()) {
            App.showAlert("Error", "Name is empty", Alert.AlertType.WARNING);
        } else {
            try {
                new UserRepository(new User(login, password, name));
                Stage stage = (Stage) textFieldName.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("authorization.fxml"));
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                App.showAlert("Error", e.getMessage(), Alert.AlertType.WARNING);
            }
        }
    }

    public void buttonCancel(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) textFieldName.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("authorization.fxml"));
        stage.setScene(new Scene(loader.load()));
    }
}
