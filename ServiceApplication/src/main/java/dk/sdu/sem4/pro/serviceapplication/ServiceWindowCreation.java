package dk.sdu.sem4.pro.serviceapplication;

import dk.sdu.sem4.pro.common.services.IProduction;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class ServiceWindowCreation {
    private final Pane ServiceWindow = new Pane();

    public Pane createServiceWindow() {
        ServiceWindow.setPrefSize(400, 600);
        BackgroundFill bgFill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        Background bg = new Background(bgFill);
        ServiceWindow.setBackground(bg);
        ServiceWindow.autosize();
        EventHandler<MouseEvent> startEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (IProduction service : getProduction()) {
                    service.startProduction();
                }
            }
        };
        EventHandler<MouseEvent> stopEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (IProduction service : getProduction()) {
                    service.stopProduction();
                }
            }
        };
        EventHandler<MouseEvent> resumeEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (IProduction service : getProduction()) {
                    service.resumeProduction();
                }
            }
        };
        EventHandler<MouseEvent> exitEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                javafx.application.Platform.exit();
            }
        };
        double buttonWidth = 60;
        double buttonHeight = 30;
        double spacing = 10;
        Button StartButton = new Button("Start");
        StartButton.setId("StartButton");
        StartButton.setPrefWidth(buttonWidth);
        StartButton.setPrefHeight(buttonHeight);
        StartButton.setTranslateX(10);
        StartButton.setTranslateY(StartButton.getHeight());
        StartButton.setOnMouseClicked(startEvent);
        Button StopButton = new Button("Stop");
        StopButton.setId("StopButton");
        StopButton.setPrefWidth(buttonWidth);
        StopButton.setPrefHeight(buttonHeight);
        StopButton.setTranslateX(StartButton.getTranslateX()+buttonWidth+spacing);
        StopButton.setTranslateY(StopButton.getHeight());
        StopButton.setOnMouseClicked(stopEvent);
        Button ResumeButton = new Button("Resume");
        ResumeButton.setId("ResumeButton");
        ResumeButton.setPrefWidth(buttonWidth);
        ResumeButton.setPrefHeight(buttonHeight);
        ResumeButton.setTranslateX(StartButton.getTranslateX()+buttonWidth+spacing);
        ResumeButton.setTranslateY(ResumeButton.getHeight());
        StopButton.setOnMouseClicked(resumeEvent);
        Button ExitButton = new Button("Exit");
        ExitButton.setId("ExitButton");
        ExitButton.setPrefWidth(buttonWidth);
        ExitButton.setPrefHeight(buttonHeight);
        ExitButton.setTranslateX(StartButton.getTranslateX()+buttonWidth+spacing);
        ExitButton.setTranslateY(ExitButton.getHeight());
        ExitButton.setOnMouseClicked(exitEvent);
        Text text = new Text(ServiceWindow.getWidth()/2,ServiceWindow.getHeight()/2,"Opperations Manager");
        ServiceWindow.getChildren().add(text);
        ServiceWindow.getChildren().add(StartButton);
        ServiceWindow.getChildren().add(StopButton);
        ServiceWindow.getChildren().add(ResumeButton);
        ServiceWindow.getChildren().add(ExitButton);
        return ServiceWindow;
    }

    private Collection<? extends IProduction> getProduction() {
        return ServiceLoader.load(IProduction.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
