package server;

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



}
