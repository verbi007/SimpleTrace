package org.jumbo.simpletrace;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("simpleJaegerTrace.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 740, 469);
        stage.getIcons().add(new Image(StartApplication.class.getResource("/img/Background.png").toString()));


        stage.setTitle("SJT");
        stage.setScene(scene);
        stage.setResizable(false);


        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}