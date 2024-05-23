package dk.sdu.sem4.pro.serviceapplication;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    private final Pane ServiceWindow = new Pane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        ServiceWindow.setPrefSize(400, 600);
        BackgroundFill bgFill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        Background bg = new Background(bgFill);
        ServiceWindow.setBackground(bg);
        ServiceWindow.autosize();
        Text text = new Text(10,20,"Opperations Manager");
        ServiceWindow.getChildren().add(text);
        Scene scene = new Scene(ServiceWindow);
        window.setScene(scene);
        window.setTitle("Operation Manager");
        window.show();
    }
}