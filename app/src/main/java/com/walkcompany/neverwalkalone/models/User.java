package com.walkcompany.neverwalkalone.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    private String prenom;
    private String nom;
    private String sexe;
    private String dateDeNaissance;
    private String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String prenom, String nom, String dateDeNaissance,String sexe,String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateDeNaissance= dateDeNaissance;
        this.sexe = sexe;

    }
    public User(String nom,String email) {
        this.nom = nom;
        this.email = email;
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

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
// [END blog_user_class]
