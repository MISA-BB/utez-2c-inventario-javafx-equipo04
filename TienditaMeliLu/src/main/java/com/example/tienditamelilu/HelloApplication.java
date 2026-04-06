package com.example.tienditamelilu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Views/Inicio-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 540);
        stage.setTitle("Tiendita MeliLu");
        stage.setScene(scene);
        stage.show();
    }
}
