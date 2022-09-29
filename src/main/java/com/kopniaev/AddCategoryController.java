package com.kopniaev;

import com.kopniaev.model.Category;
import com.kopniaev.model.User;
import com.kopniaev.repository.CategoryRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCategoryController {

    @FXML
    public TextField textFieldName;

    private User user = null;

    public void buttonOk(ActionEvent actionEvent) {
        String name = this.textFieldName.getText();
        if (!name.isEmpty()) {
            try {
                Category category = new Category();
                category.setName(name);
                new CategoryRepository(this.user.getId(), category, "POST");
                Stage currentStage = (Stage) textFieldName.getScene().getWindow();
                currentStage.close();

            } catch (IOException e) {
                App.showAlert("Error", e.getMessage(), Alert.AlertType.WARNING);
            }
        } else {
            App.showAlert("Error", "Name is empty", Alert.AlertType.WARNING);
        }
    }

    public void buttonCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) textFieldName.getScene().getWindow();
        stage.close();
    }

    public void initData(User user) {
        this.user = user;
    }
}
