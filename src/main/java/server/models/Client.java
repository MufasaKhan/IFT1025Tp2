package server;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String commande = scanner.nextLine();
        String choix = "";
        while (true) {
            try {
                if (choix.equals("1") ){
                    commande = "CHARGER";
                } else if (choix.equals("2") || commande.equals("INSCRIRE")) {
                    inscription();
                }
                Socket client = new Socket("127.0.0.1", 1337);
                ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
                outputStream.writeObject(commande);
                outputStream.flush();
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                while (true) {
                    String line;
                    while ((line = input.readLine()) != null) {
                        System.out.println(line);
                        if (!input.ready() && line.equals("")) {
                            System.out.print("> Choix: ");
                            choix = scanner.nextLine();
                            outputStream.writeObject(choix);
                            outputStream.flush();
                            break;
                        }

                    }

                }
            } catch (IOException e) {

            }

        }


    }

    private static void inscription() {

        Scanner inscription = new Scanner(System.in);
        System.out.print("Veuillez saisir votre prénom: ");
        String prenom = inscription.nextLine();
        System.out.print("Veuillez saisir votre nom: ");
        String nom = inscription.nextLine();
        System.out.print("Veuillez saisir votre email: ");
        String email = inscription.nextLine();
        System.out.print("Veuillez saisir votre matricule: ");
        String matricule = inscription.nextLine();
        System.out.print("Veuillez saisir le code du cours: ");
        String coursChoix = inscription.nextLine();
        Course choix = null;

        RegistrationForm registrationForm = new RegistrationForm(prenom,nom,email,matricule,choix);
        registrationForm.toString();
    }
}
