/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.pidev.entities;

import java.util.Date;


/**
 *
 * @author ghada
 */
public class Journal {

    private int id;
    private user user_id;
    private Evenement evenement;
    private int nbChasse;
    private Animal animal;
    private Lieu lieu;
    private Date date;
    private String description;
    private String image;
    private int id_chasseur;
    

    public Journal(user user_id, Evenement evenement, int nbChasse, Animal animal, Lieu lieu, Date date, String description, String image, int id_chasseur) {
        this.user_id = user_id;
        this.evenement = evenement;
        this.nbChasse = nbChasse;
        this.animal = animal;
        this.lieu = lieu;
        this.date = date;
        this.description = description;
        this.image = image;
        this.id_chasseur = id_chasseur;
    }

    public Journal(int id, user user_id, Evenement evenement, int nbChasse, Animal animal, Lieu lieu, Date date, String description, String image, int id_chasseur) {
        this.id = id;
        this.user_id = user_id;
        this.evenement = evenement;
        this.nbChasse = nbChasse;
        this.animal = animal;
        this.lieu = lieu;
        this.date = date;
        this.description = description;
        this.image = image;
        this.id_chasseur = id_chasseur;
    }

   

    

    @Override
    public String toString() {
        return "Journal{" + "id=" + id + ", user_id=" + user_id + ", evenement=" + evenement + ", nbChasse=" + nbChasse + ", animal=" + animal + ", lieu=" + lieu + ", date=" + date + ", description=" + description + ", image=" + image + ", id_chasseur=" + id_chasseur + '}';
    }

    

    public int getId_chasseur() {
        return id_chasseur;
    }

    public void setId_chasseur(int id_chasseur) {
        this.id_chasseur = id_chasseur;
    }

    
   

    public int getNbChasse() {
        return nbChasse;
    }

    public void setNbChasse(int nbChasse) {
        this.nbChasse = nbChasse;
    }

   

    public user getUser_id() {
        return user_id;
    }

    public void setUser_id(user user_id) {
        this.user_id = user_id;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public Journal(int nbChasse, Animal animal, Lieu lieu,  String description, String image) {
        this.nbChasse = nbChasse;
        this.animal = animal;
        this.lieu = lieu;
        
        this.description = description;
        this.image = image;
    }
    

    public Journal() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    


}
