module com.example.snakefx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.snakefx to javafx.fxml;
    exports com.example.snakefx;
}