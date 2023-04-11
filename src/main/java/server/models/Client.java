package server;
import server.models.Course;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    public static void main(String[] args) {
        try {

            Socket client = new Socket("127.0.0.1", 1337);
            ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
            Scanner scanner = new Scanner(System.in);
            String commande = scanner.nextLine();
            outputStream.writeObject(commande);
            outputStream.flush();
            ArrayList<Course> receivedObjectList = (ArrayList<Course>) inputStream.readObject();

            for (Course obj : receivedObjectList) {
                System.out.println(obj.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
