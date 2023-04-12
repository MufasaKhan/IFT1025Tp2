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
        String operation = "";
        ArrayList<Course> receivedObjectList = new ArrayList<>();

        while (true) {
            try {
                Socket client = new Socket("127.0.0.1", 1337);
                ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());

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
                        operation = "CHARGER";
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
                        for (Course cour : receivedObjectList) {
                            if (cour.getCode().equals(coursChoix)) {
                                cours = cour;

                            }
                        }
                        RegistrationForm registrationForm = new RegistrationForm(prenom,nom,email,matricule,cours);
                        System.out.println(registrationForm.toString());
                        outputStream.writeObject(commande);
                        outputStream.writeObject(registrationForm);
                        outputStream.flush();
                        commande ="";
                        choix = "";
                        operation = "INSCRIRE";
                        break;

                    }
                }
                        if(operation.equals("CHARGER")) {
                            receivedObjectList = (ArrayList<Course>) inputStream.readObject();
                            System.out.println("Les cours offerts sont :");
                            for (Course obj : receivedObjectList) {
                                System.out.println(obj.toString());
                                operation = "";
                            }
                        }else if (operation.equals("INSCRIRE")) {
                            String message = inputStream.readObject().toString();
                            System.out.println(message);
                            operation = "";
                        }


                System.out.println("1. Consulter les cours offerts pour une autre session \n2. Inscription a un cours");
                System.out.print(">Choix : ");
                commande = "";
                choix = scanner.nextLine();


            } catch (IOException e) {
                e.printStackTrace();

            } catch
            (ClassNotFoundException e) {
            }

        }





    }
}
