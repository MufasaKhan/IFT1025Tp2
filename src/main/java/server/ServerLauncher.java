package server;

import java.io.Console;
import java.net.Socket;
import java.util.Scanner;

public class ServerLauncher {
    public final static int PORT = 1337;

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(PORT);
            System.out.println("Choisissez une session \n 1.Automne \n 2. Hiver \n 3. Ete");
            Scanner s = new Scanner(System.in);
            String cours = s.nextLine();
            int valeur = Integer.parseInt(cours);
            if(!(valeur >= 1 && valeur <= 3)) throw new IllegalArgumentException("Votre choix n'est pas dans la liste");

            System.out.println("Server is running...");
            server.handleLoadCourses(cours);
            server.run();
            Socket client = new Socket("127.0.0.1",1337);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}