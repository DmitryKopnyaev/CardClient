package com.kopniaev;

import com.kopniaev.model.Card;
import com.kopniaev.model.Category;
import com.kopniaev.model.User;
import com.kopniaev.repository.CardRepository;
import com.kopniaev.repository.CategoryRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AddCardController {

    private User user;

    @FXML
    public ComboBox<Category> comboBoxCategories;
    public TextArea textAreaQuestion;
    public TextArea textAreaAnswer;

    public void buttonOk(ActionEvent actionEvent) {
        String answer = textAreaAnswer.getText();
        String question = textAreaQuestion.getText();
        Category selectedCategory = comboBoxCategories.getSelectionModel().getSelectedItem();

        if (selectedCategory == null) {
            App.showAlert("Error", "Category not selected", Alert.AlertType.WARNING);
            return;
        }
        if (answer == null) {
            App.showAlert("Error", "Answer is empty", Alert.AlertType.WARNING);
            return;
        }
        if (question == null) {
            App.showAlert("Error", "Question is empty", Alert.AlertType.WARNING);
            return;
        }
        try {
            Card card = new Card();
            card.setQuestion(question);
            card.setAnswer(answer);
            new CardRepository(selectedCategory.getId(), card);
            this.buttonCancel(new ActionEvent());
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    public void buttonCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) textAreaQuestion.getScene().getWindow();
        stage.close();
    }

    public void initData(User user) {
        this.user = user;
        try {
            List<Category> categories = new CategoryRepository(this.user.getId(), "GET").getCategories();
            this.comboBoxCategories.setItems(FXCollections.observableList(categories));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void comboBoxCategories(ActionEvent actionEvent) {
    }
}
