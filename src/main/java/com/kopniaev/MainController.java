package com.kopniaev;

import com.kopniaev.model.Card;
import com.kopniaev.model.Category;
import com.kopniaev.model.User;
import com.kopniaev.repository.CardRepository;
import com.kopniaev.repository.CategoryRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class MainController {


    private User user = null;

    @FXML
    public ComboBox<Category> comboBoxCategory;
    public ListView<Card> listViewCard;
    public TextArea textAreaQuestion;
    public TextArea textAreaAnswer;

    @FXML
    public void initialize() {
    }

    public void buttonAddCard(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addCard.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddCardController cardController = loader.getController();
        cardController.initData(this.user);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        this.updateCardsList();
    }

    public void buttonDeleteCard(ActionEvent actionEvent) {
        Card selectedCard = this.listViewCard.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            try {
                new CardRepository(selectedCard.getId(), "DELETE");
                this.updateCardsList();
                this.textAreaAnswer.setText("");
                this.textAreaQuestion.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            App.showAlert("Error", "Card not selected", Alert.AlertType.ERROR);
        }
    }

    public void buttonAddCategory(ActionEvent actionEvent) {
        Stage stageAddCategory = new Stage();
        FXMLLoader loaderAddCategory = new FXMLLoader(getClass().getResource("addCategory.fxml"));
        try {
            Parent root = loaderAddCategory.load();
            stageAddCategory.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddCategoryController addCategoryController = loaderAddCategory.getController();
        addCategoryController.initData(this.user);
        stageAddCategory.initModality(Modality.APPLICATION_MODAL);
        stageAddCategory.showAndWait();

        this.updateCategoriesList();
    }

    public void buttonDeleteCategory(ActionEvent actionEvent) {
        Category selectedCategory = this.comboBoxCategory.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            try {
                new CategoryRepository(selectedCategory.getId(), "DELETE");
                this.updateCategoriesList();
            } catch (IOException e) {
                App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else App.showAlert("Error", "No categories selected", Alert.AlertType.ERROR);
    }

    public void initData(User user) {
        this.user = user;
        updateCategoriesList();
    }

    public void comboBoxCategorySelected(ActionEvent actionEvent) {
        this.updateCardsList();
        textAreaAnswer.setText("");
        textAreaQuestion.setText("");
    }

    public void updateCategoriesList() {
        try {
            List<Category> categories = new CategoryRepository(this.user.getId(), "GET").getCategories();
            this.comboBoxCategory.setItems(FXCollections.observableList(categories));
        } catch (IOException e) {
            App.showAlert("Error", "Cant update categories list", Alert.AlertType.ERROR);
        }
    }

    public void updateCardsList() {
        Category selectedCategory = this.comboBoxCategory.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            try {
                List<Card> cards = new CardRepository(selectedCategory.getId(), "GET").getCards();
                this.listViewCard.setItems(FXCollections.observableList(cards));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void listViewCardOnMouseClicked(MouseEvent mouseEvent) {
        Card selectedCard = this.listViewCard.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            this.textAreaQuestion.setText(selectedCard.getQuestion());
            this.textAreaAnswer.setText(selectedCard.getAnswer());
        } else
            App.showAlert("Error", "Card not selected", Alert.AlertType.ERROR);
    }

    public void buttonUpdateCard(ActionEvent actionEvent) {
        Card selectedCard = this.listViewCard.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            selectedCard.setQuestion(this.textAreaQuestion.getText());
            selectedCard.setAnswer(this.textAreaAnswer.getText());
            try {
                CardRepository put = new CardRepository(selectedCard, "PUT");
                Card card = put.getCard();
                this.updateCardsList();
                this.textAreaQuestion.setText(card.getQuestion());
                this.textAreaAnswer.setText(card.getAnswer());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            App.showAlert("Error", "Card not selected", Alert.AlertType.ERROR);
    }

    public void buttonExit(ActionEvent actionEvent) {
        Stage stage = (Stage) textAreaAnswer.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("authorization.fxml"));
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            App.setPreferences("login", "");
            App.setPreferences("password", "");
        }
    }
}
