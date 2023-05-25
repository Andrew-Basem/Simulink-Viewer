module com.example.simulink {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.simulink to javafx.fxml;
    exports com.example.simulink;
}