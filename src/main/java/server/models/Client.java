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
        String session = "";


        while (true) {
            try {
                Socket client = new Socket("127.0.0.1", 1337);
                ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
                ArrayList<Course> receivedObjectList = new ArrayList<>();
                while(true) {
                    if (choix.equals("1") || commande.equals("CHARGER")) {
                        commande = "CHARGER";
                        System.out.println("*** Bienvenue au portail d'inscription de l'UDEM ***");
                        System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste de cours :");
                        System.out.println(" 1. Automne \n 2. Hiver \n 3. Ete");
                        System.out.print("> Choix: ");
                        session = scanner.nextLine();
                        outputStream.writeObject(commande);
                        outputStream.writeObject(session);
                        outputStream.flush();
                        commande ="";
                        receivedObjectList.clear();
                        break;


                    } else if (choix.equals("2") || commande.equals("INSCRIRE")) {
                        commande = "INSCRIRE";
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
                        Course cours = null;
                        RegistrationForm registrationForm = new RegistrationForm(prenom, nom, email, matricule, cours);
                        registrationForm.toString();

                    }
                }


                         receivedObjectList = (ArrayList<Course>) inputStream.readObject();
                        for (Course obj : receivedObjectList) {
                            System.out.println(obj.toString());
                    }


                System.out.println("1. Consulter les cours offerts pour une autre session \n2. Inscription a un cours");
                System.out.print(">Choix : ");
                commande = "";
                choix = scanner.nextLine();


            } catch (IOException e) {

            } catch
            (ClassNotFoundException e) {
            }

        }





    }
}
