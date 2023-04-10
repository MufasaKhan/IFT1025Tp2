package server;

import javafx.util.Pair;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Server {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     Lire un fichier texte contenant des informations sur les cours et les transofmer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     @param arg la session pour laquelle on veut récupérer la liste des cours
     @throws Exception si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux
     */
    ArrayList<Course> courseList = new ArrayList<Course>();
    public void handleLoadCourses(String arg) {
        try{
        System.out.println("*** Bienvenue au portail d'inscription de l'UDEM ***");
        System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste de cours :");
        System.out.println(" 1. Automne \n 2. Hiver \n 3. Ete");
        System.out.print("> Choix: ");
        Scanner scanner = new Scanner(System.in);
        arg = scanner.nextLine();
        int valeur = Integer.parseInt(arg);
        if(!(valeur >= 1 && valeur <= 3)) throw new IllegalArgumentException("Votre choix n'est pas dans la liste");

        String cheminCours = "/cours.txt";
        String code,sessionChoisis,nom,sessionCours;
        String[] sessions = {" ","Automne","Hiver","Ete"};
        sessionChoisis = sessions[Integer.valueOf(arg)];

            InputStream inputStream = getClass().getResourceAsStream(cheminCours);
            Scanner ligne = new Scanner(inputStream);
            System.out.println("Les cours offerts pendant la session " + sessionChoisis + " sont :");
            int compteur = 1;
            while(ligne.hasNextLine()) {
                String s = ligne.nextLine();
                String[] mot = s.split("\\s+");
                sessionCours = mot[mot.length - 1];
                code = mot[mot.length -3];
                nom = mot[mot.length-2];
                if(sessionChoisis.equals(sessionCours)){
                    Course course = new Course(nom,code,sessionChoisis);
                    courseList.add(course);
                    System.out.println(compteur + ". " + nom + " "  + code);
                    compteur += 1;
                }
                objectOutputStream.writeObject(courseList);
                objectOutputStream.flush();

            }


            ligne.close();

        }catch (IOException e){
            throw new RuntimeException("Erreur lors de l'ouverture du fichier",e);
        }


    }




    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     @throws Exception si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        Scanner inscription = new Scanner(System.in);
        System.out.print("Veuillez saisir votre prénom: ");
        String prenom = inscription.next();
        System.out.print("Veuillez saisir votre nom: ");
        String nom = inscription.next();
        System.out.print("Veuillez saisir votre email: ");
        String email = inscription.next();
        System.out.print("Veuillez saisir votre matricule: ");
        String matricule  = inscription.next();
        System.out.print("Veuillez saisir le code du cours: ");
        String coursChoix = inscription.next();
        Course choix = null;
        for(Course course: courseList ){
            if (course.getCode().contains(coursChoix)){
                choix = course;
            }else{
                System.out.println("Le cours ne fait pas partie de la liste.");

            }
        }
        RegistrationForm registrationForm = new RegistrationForm(prenom,nom,email,matricule,choix);




        System.out.println(registrationForm.toString());
        // TODO: implémenter cette méthode
    }

}

