/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.pidev.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ghada
 */
public class MyConnection {

    private String url = "jdbc:mysql://localhost:3306/hunterclub";
    private String login = "root";
    private String mdp = "";
    private Connection cnx;
    private static MyConnection instance;

    public Connection getCnx() {
        return cnx;
    }

    public void setCnx(Connection cnx) {
        this.cnx = cnx;
    }

    private MyConnection() {//une seule connexion dans une application et limité nombre de cnx

        try {
            cnx = DriverManager.getConnection(url, login, mdp);
            System.out.println("Connexion etablie!");
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    public static MyConnection getInstance() {
        if (instance == null) {
            instance = new MyConnection();
        }

        return instance;
    }

}
