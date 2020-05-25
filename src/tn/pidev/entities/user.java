/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.pidev.entities;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author ghada
 */
public class user  {
    
    private int id;
    private String username;
    private String usernameCanonical;
    private String email;
    private String emailCanonical;
    private String Roles;
    private int enabled = 1;
    private String salt;
    private String password;
    private Date lastLogin;
    private String NumeroTelephone;
    private String cin;
    private String Adresse;

    public user() {
    }
public user(int id)
{
    this.id=id;
}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        this.usernameCanonical =username;
    }

    public String getUsernameCanonical() {
        return usernameCanonical;
    }

    public void setUsernameCanonical(String usernameCanonical) {
        this.usernameCanonical = usernameCanonical;
         this.username = usernameCanonical;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.emailCanonical = email;
    }

    public String getEmailCanonical() {
        return emailCanonical;
    }

    public void setEmailCanonical(String emailCanonical) {
        this.emailCanonical = emailCanonical;
        this.email=emailCanonical;
    }

    public int isEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getNumeroTelephone() {
        return NumeroTelephone;
    }

    public void setNumeroTelephone(String NumeroTelephone) {
        this.NumeroTelephone = NumeroTelephone;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String Adresse) {
        this.Adresse = Adresse;
    }

    public String getRoles() {
        return Roles;
    }

    public void setRoles(String Roles) {
        this.Roles = Roles;
    }

    public int getEnabled() {
        return enabled;
    }
    
     public void SetEnabled(int Enabled) {
         this.enabled=Enabled;
    }


    @Override
    public String toString() {
        return "user{" + "id=" + id + ", username=" + username + ", email=" + email + ", NumeroTelephone=" + NumeroTelephone + ", cin=" + cin + ", Adresse=" + Adresse + '}';
    }
    
}
