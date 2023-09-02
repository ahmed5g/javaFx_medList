package tn.esprit.medlist.Core.Models;

public class Patient {

    private int id;
    private String nom;
    private String prenom;
    private String email;
    private int contactNumber;

    private String Symptoms;


    public Patient(int id, String nom, String prenom, String email, int contactNumber, String symptoms) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.contactNumber = contactNumber;
        this.Symptoms = symptoms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getSymptoms() {
        return Symptoms;
    }

    public void setSymptoms(String symptoms) {
        Symptoms = symptoms;
    }
}
