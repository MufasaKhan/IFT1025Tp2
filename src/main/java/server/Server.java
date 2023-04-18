package server;

import javafx.util.Pair;
import server.models.Course;
import server.models.RegistrationForm;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

/**
 * Cette classe contient le code pour le serveur. Elle recoit les requetes et fait le traitement.
 */

public class Server {
    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;
    private ArrayList<Course> courseList = new ArrayList<Course>();

    /**
     * cette methode cree un serveur et gere les evenements.
     * @param port numero du port sur lequel le serveur doit etre cree.
     * @throws IOException
     */
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

    /**
     * cette methode accepte les requetes des clients. Elle ecoute les commande envoyes et deconnecte le client apres l'execution
     */
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

    /**
     * methode qui lit les commande des client et recupere les informations. Les commandes sont ensuite passées à
     * alertHandlers qui poursuit la suite du traitement
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     * methode qui separe une ligne de texte en commandes et argument a passer dans le programme.
     * @param line information entrée par l'utilisateur une commande et un argument
     * @return une commande et son argument sont retournées par la methode
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     * methode qui deconnecte le client.
     * @throws IOException
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * methode qui appelle une fonction selon la commande entrée et l'argument donné en parametre
     * @param cmd commande entree par l'utilisateur.
     * @param arg session que l'utilisateur souhaite afficher.
     */
    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     * Lire un fichier texte contenant des informations sur les cours et les transofmer en liste d'objets 'Course'.
     * La méthode filtre les cours par la session spécifiée en argument.
     * Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     *
     * @param arg la session pour laquelle on veut récupérer la liste des cours
     * @throws Exception si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux
     */



    public void handleLoadCourses(String arg) {
        try {

            arg = "";
            courseList.clear();
            arg = objectInputStream.readObject().toString();
            int valeur = Integer.parseInt(arg);
            if (!(valeur >= 1 && valeur <= 3))
                throw new IllegalArgumentException("Votre choix n'est pas dans la liste");
            String code, sessionChoisis, nom, sessionCours;
            String[] sessions = {" ", "Automne", "Hiver", "Ete"};
            sessionChoisis = sessions[Integer.valueOf(arg)];
            // recherche le chemin du jar
            String jarPath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();

            File file = new File(jarPath + File.separator + "cours.txt");
            if(!file.exists()){
                throw new FileNotFoundException();
            }

            Scanner ligne = new Scanner(new InputStreamReader(new FileInputStream(file)));

            while (ligne.hasNextLine()) {
                String s = ligne.nextLine();
                String[] mot = s.split("\\s+");
                sessionCours = mot[mot.length - 1];
                code = mot[mot.length - 3];
                nom = mot[mot.length - 2];
                if (sessionChoisis.equals(sessionCours)) {
                    Course course = new Course(nom, code, sessionChoisis);
                    courseList.add(course);

                }
            }
            objectOutputStream.writeObject(courseList);
            objectOutputStream.flush();
            courseList.clear();
        } catch (Exception e) {
            try {

                objectOutputStream.writeObject(courseList);
            }catch (IOException f){
                f.printStackTrace();

            }
        }
    }



    /**
     * Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     * et renvoyer un message de confirmation au client.
     *
     * @throws Exception si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        try {
            RegistrationForm formeRecu = (RegistrationForm) objectInputStream.readObject();
            String jarPath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
            File file = new File(jarPath + File.separator + "inscription.txt");
            if(!file.exists()){
                throw new FileNotFoundException();
            }

            FileWriter writer = new FileWriter(file);
            PrintWriter pw = new PrintWriter(writer);
            Course cours = formeRecu.getCourse();
            String aEcrire = cours.getSession() + " \t" + cours.getCode() + " \t" + formeRecu.getMatricule() + " \t"+
                    formeRecu.getPrenom()+ " \t"
                    + formeRecu.getNom()+ " \t" + formeRecu.getEmail();
            pw.println(aEcrire);
            pw.close();
            writer.close();
            objectOutputStream.writeObject("Inscription reussite !");



        } catch (Exception e) {
            try{
            objectOutputStream.writeObject("Erreur lors de l'inscription !");

        } catch (IOException l){
            l.printStackTrace();}
        }

    }
}

