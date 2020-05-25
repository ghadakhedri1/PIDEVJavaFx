/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.pidev.entities;

/**
 *
 * @author ghada
 */
public class Evenement {
    private int id;
    private user user;
    private TypeEvents type_events;

    public Evenement() {
    }

    public Evenement(int id) {
        this.id = id;
    }

    public Evenement(int id, user user, TypeEvents type_events) {
        this.id = id;
        this.user = user;
        this.type_events = type_events;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }

    public TypeEvents getType_events() {
        return type_events;
    }

    public void setType_events(TypeEvents type_events) {
        this.type_events = type_events;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", type_events=" + type_events + '}';
    }
    
    
    
}
