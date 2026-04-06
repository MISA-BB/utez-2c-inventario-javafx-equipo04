module com.example.tienditamelilu {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tienditamelilu to javafx.fxml;
    exports com.example.tienditamelilu;
    opens com.example.tienditamelilu.Controllers to javafx.fxml;
    exports com.example.tienditamelilu.Controllers;
    opens com.example.tienditamelilu.Services to javafx.fxml;
    exports com.example.tienditamelilu.Services;
}