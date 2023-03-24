package server;


import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerLauncher {
    public final static int PORT = 1337;

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(PORT);
            Socket client = new Socket("127.0.0.1",PORT);
            System.out.println("*** Bienvenue au portail d'inscription de l'UDEM ***");
            System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste de cours :");
            System.out.println(" 1. Automne \n 2. Hiver \n 3. Ete");
            ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();
            System.out.println("> Choix: " + message);
            int valeur = Integer.parseInt(message);
            if(!(valeur >= 1 && valeur <= 3)) throw new IllegalArgumentException("Votre choix n'est pas dans la liste");
            outputStream.writeObject(message);
            outputStream.flush();
            System.out.println("Server is running...");
            server.run();




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}