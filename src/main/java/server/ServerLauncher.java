package server;

import java.io.Console;
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

            System.out.println("Server is running...");
            server.handleLoadCourses(cours);
            server.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}