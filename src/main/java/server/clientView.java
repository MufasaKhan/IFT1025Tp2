package server;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import server.models.Course;
import server.models.RegistrationForm;

import java.util.ArrayList;


public class clientView {
    private final int SCENE_WIDTH = 500;
    private final int SCENE_HEIGHT = 500;
    private final Scene scene;
    private Course course;
    private Controleur controleur =  new Controleur();
    TextField prenom = new TextField();
    TextField nom = new TextField();
    TextField email = new TextField();
    TextField matricule = new TextField();
    VBox informations = new VBox(10);
    Label pr = new Label("Prenom");
    Label na = new Label("Nom");
    Label em = new Label("Email");
    Label ma = new Label("Matricule");

    private final StackPane root = new StackPane();
    private final Button charger = new Button("Charger");
    private final Button inscription = new Button("Inscription");
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
        inscription.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String pren = prenom.getText();

            }
        });
        tableView.setMaxWidth(220);
        tableView.setMaxHeight(300);
        tableView.setTranslateX(-100);
        tableView.setTranslateY(-60);
        codeCol.setMaxWidth(160);
        coursCol.setMinWidth(160);
        informations.getChildren().addAll(em,na,pr,prenom,nom,email,matricule,ma);
        pr.setTranslateX(-50);
        pr.setTranslateY(30);
        na.setTranslateY(95);
        na.setTranslateX(-50);
        em.setTranslateX(-50);
        em.setTranslateY(155);
        ma.setTranslateX(-60);
        ma.setTranslateY(-30);
        informations.setMaxWidth(120);
        informations.setTranslateX(145);
        informations.setTranslateY(90);

        sessions.getItems().addAll("Automne", "Hiver", "Ete");
        sessions.setValue("Automne");
        sessions.setTranslateX(-100);
        sessions.setTranslateY(140);
        charger.setTranslateY(140);
        inscription.setTranslateX(170);
        inscription.setTranslateY(140);
        fomulaire.setTranslateX(140);
        fomulaire.setTranslateY(-200);
        root.getChildren().addAll(informations,tableView,charger,inscription,sessions,fomulaire);
        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);



    }
    public void updateCours (ArrayList<Course> cours){
        tableView.setItems(FXCollections.observableArrayList(cours));

    }


    public Scene getScene(){
        return scene;
    }

}


