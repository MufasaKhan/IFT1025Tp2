package server;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class clientGraphique extends Application {




    private final clientView clientView = new clientView();


    public void start(Stage stage) throws IOException {
        stage.setTitle("Inscription UdeM");
        stage.setScene(clientView.getScene());
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}
