module com.textprocessor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.textprocessor to javafx.fxml;
    exports com.textprocessor;
    exports com.textprocessor.controller;
    opens com.textprocessor.controller to javafx.fxml;
    exports com.textprocessor.model;
    opens com.textprocessor.model to javafx.fxml;
}