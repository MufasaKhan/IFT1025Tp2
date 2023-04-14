package server;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import server.models.Course;

import java.util.ArrayList;


public class clientView {
    private final int SCENE_WIDTH = 500;
    private final int SCENE_HEIGHT = 500;
    private final Scene scene;
    private Course course;
    private Controleur controleur =  new Controleur();

    private final StackPane root = new StackPane();
    private final Button charger = new Button("Charger");
    private final ChoiceBox<String> sessions = new ChoiceBox<>();
    private final TableView<Course> tableView = new TableView<Course>();
    private ArrayList<Course> listCours;

    private final Text fomulaire = new Text("Formulaire d'inscription");

    public clientView() {

        TableColumn<Course, String> codeCol = new TableColumn<>("Code");
        TableColumn<Course, String> coursCol = new TableColumn<>("Cours");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        coursCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.getColumns().addAll(codeCol,coursCol);



        charger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Clicked");
                    int choix = sessions.getSelectionModel().getSelectedIndex() + 1;
                    controleur.charger(Integer.toString(choix));
                    listCours = controleur.getListCours();
                tableView.setItems(FXCollections.observableArrayList(listCours));




            }
        });
        tableView.setMaxWidth(200);
        tableView.setMaxHeight(300);
        tableView.setTranslateX(-100);
        tableView.setTranslateY(-60);
        codeCol.setMaxWidth(150);


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
    public void updateCours (ArrayList<Course> cours){
        tableView.setItems(FXCollections.observableArrayList(cours));

    }

    public Scene getScene(){
        return scene;
    }

}


