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
            ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            String commande = scanner.nextLine();
            outputStream.writeObject(commande);
            System.out.println("Server is running...");
            server.run();





        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}