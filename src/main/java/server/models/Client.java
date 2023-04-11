package server;
import server.models.Course;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public static void main(String[] args) {
        while (true) {
            try {
                Socket client = new Socket("127.0.0.1", 1337);
                ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
                Scanner scanner = new Scanner(System.in);
                String commande = scanner.nextLine();
                outputStream.writeObject(commande);
                outputStream.flush();
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                while (true) {
                    String line;
                    while ((line = input.readLine()) != null) {
                        System.out.println(line);

                        if (!input.ready() && line.equals("")) {
                            System.out.print("> Choix: ");
                            String session = scanner.nextLine();
                            outputStream.writeObject(session);
                            outputStream.flush();
                            break;
                        }

                    }


                }
            } catch (IOException e) {



            }
        }
    }
}
