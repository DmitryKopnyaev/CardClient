module com.kopniaev {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.rmi;
    requires java.prefs; //добавил сам

    opens com.kopniaev to javafx.fxml;
    exports com.kopniaev;
    exports com.kopniaev.repository;
    exports com.kopniaev.model;
    exports com.kopniaev.util;
}
