package server;

import javafx.scene.control.Alert;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Controleur {
    private clientView view;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    private Socket client;
    private RegistrationForm registrationForm;
    private Course course;
    private ArrayList<Course> listCours;

    public Controleur(){

    }

    public ArrayList<Course> getListCours() {
        return listCours;
    }

    public void charger(String session){

        try {
            Socket client = new Socket("127.0.0.1", 1337);
            String connect = "CHARGER";
            outputStream = new ObjectOutputStream(client.getOutputStream());
            inputStream = new ObjectInputStream(client.getInputStream());
            outputStream.writeObject(connect);
            outputStream.writeObject(session);
            outputStream.flush();
            listCours = (ArrayList<Course>) inputStream.readObject();
            getListCours();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void inscrire(String prenom,String nom, String email, String matricule,Course course1 ) {
        try {
            String ins = "INSCRIRE";
            Socket client = new Socket("127.0.0.1", 1337);
            outputStream = new ObjectOutputStream(client.getOutputStream());
            inputStream = new ObjectInputStream(client.getInputStream());
            registrationForm = new RegistrationForm(prenom,nom,email,matricule,course1);
            outputStream.writeObject(ins);
            outputStream.writeObject(registrationForm);
            outputStream.flush();

        }catch (Exception e){

        }
    }
    public boolean verifierForme(String prenom,String nom,String email, String matricule, Course course){
        if(prenom.isEmpty() || nom.isEmpty()){
            alert("Nom ou prenom vide");
            return false;
        }
        if(!email.matches("[\\w.]+@[\\w.]+\\.\\w+")){
            alert("email invalide");
            return false;
        }
        if(course == null){
            alert("Vous n'avez pas selectionne un cour");
            return false;
        }
        return true;
    }
    public void alert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
