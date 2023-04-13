package server;

import server.models.Course;
import server.models.RegistrationForm;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Controleur {
    private clientView view;
    ObjectOutputStream outputStream;
    private Socket client;
    private RegistrationForm registrationForm;
    private Course course;

    public Controleur(){

    }

    public void charger(String session){

        try {

            Socket client = new Socket("127.0.0.1", 1337);
            String connect = "CHARGER";
            outputStream = new ObjectOutputStream(client.getOutputStream());
            outputStream.writeObject("connect");
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
