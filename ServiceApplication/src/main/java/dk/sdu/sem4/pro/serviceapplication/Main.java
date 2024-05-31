package dk.sdu.sem4.pro.serviceapplication;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static java.util.stream.Collectors.toList;

public class Main extends Application {

    public static void main(String[] args) {
        launch(Main.class, args);
    }

    @Override
    public void start(Stage window) throws Exception {
        ServiceWindowCreation serviceWindowCreation = new ServiceWindowCreation();
        Scene scene = new Scene(serviceWindowCreation.createServiceWindow());
        window.setScene(scene);
        window.setTitle("Operation Manager");
        window.show();
    }
}