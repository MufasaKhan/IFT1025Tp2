package server;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import server.models.Course;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class clientView {
    private final int SCENE_WIDTH = 500;
    private final int SCENE_HEIGHT = 500;
    private final Scene scene;
    private Course course;
    private Controleur controleur =  new Controleur();

    private final StackPane root = new StackPane();
    private final Button charger = new Button("Charger");
    private final ChoiceBox<String> sessions = new ChoiceBox<>();
    private final TableView<Course> tableView = new TableView<>();

    private final Text fomulaire = new Text("Formulaire d'inscription");

    public clientView() {

        charger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Clicked");
                    int choix = sessions.getSelectionModel().getSelectedIndex() + 1;
                    controleur.charger(Integer.toString(choix));


            }
        });
        tableView.setMaxWidth(200);
        tableView.setMaxHeight(300);
        tableView.setTranslateX(-100);
        tableView.setTranslateY(-60);
        TableColumn<Course, String> code = new TableColumn<>("Code");
        TableColumn<Course, String> cours = new TableColumn<>("Cours");
        code.setMaxWidth(150);

        tableView.getColumns().addAll(code,cours);
        sessions.getItems().addAll("Automne", "Hiver", "Ete");
        sessions.setValue("Automne");
        sessions.setTranslateX(-100);
        sessions.setTranslateY(140);
        charger.setTranslateY(140);
        fomulaire.setTranslateX(140);
        fomulaire.setTranslateY(-200);
        root.getChildren().addAll(tableView,charger,sessions,fomulaire);
        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);



    }
    public Scene getScene(){
        return scene;
    }

}


