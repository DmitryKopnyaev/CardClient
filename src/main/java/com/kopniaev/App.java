package com.kopniaev;

import com.kopniaev.model.User;
import com.kopniaev.repository.UserRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class App extends Application {
    private static Scene scene;
    private static Preferences preferences = Preferences.userRoot();

    @Override
    public void start(Stage stage) throws IOException {
        String login = getPreferences("login");
        String password = getPreferences("password");

        if (login != null || password != null) {
            User user = null;
            try {
                user = new UserRepository(login, password).getUser();
            } catch (Exception e) {
                scene = new Scene(loadFXML("authorization"), 640, 480);
            }
            if (user != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
                scene = new Scene(loader.load());
                MainController controller = loader.getController();
                controller.initData(user);
            }
        } else
            scene = new Scene(loadFXML("authorization"), 640, 480);

        stage.setScene(scene);
        stage.show();
    }

    public static void setPreferences(String key, String value) {
        preferences.put(key, value);
    }

    public static String getPreferences(String key) {
        return preferences.get(key, null);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }

}