package server.models;

import java.io.Serializable;

public class RegistrationForm implements Serializable {
    private String prenom;
    private String nom;
    private String email;
    private String matricule;
    private Course course;

    public RegistrationForm(String prenom, String nom, String email, String matricule, Course course) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.matricule = matricule;
        this.course = course;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "InscriptionForm{" + "prenom='" + prenom + '\'' + ", nom='" + nom + '\'' + ", email='" + email + '\'' + ", matricule='" + matricule + '\'' + ", course='" + course + '\'' + '}';
    }
    public boolean verifierForme(String prenom,String nom,String email, String matricule, Course course){
        if(prenom.isEmpty() || nom.isEmpty()){
            System.out.println("Nom ou prenom vide");
            return false;
        }
        if(!email.matches("[\\w.]+@[\\w.]+\\.\\w+")){
            System.out.println("email invalide");
            return false;
        }
        if(course == null){
            System.out.println("Vous n'avez pas selectionne un cour");
            return false;
        }
        if(!matricule.matches("\\d{6}")){
            System.out.println("Mauvais format de matricule. Le matricule doit etre composé de 6 chiffres");
        }
        return true;
    }
}
