/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.pidev.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.pidev.entities.user;
import tn.pidev.utils.MyConnection;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class UserService {
    private Connection cnx ;
  
  
  public UserService(){
      cnx = MyConnection.getInstance().getCnx();
      
  }
  
  
    public void ajouterUser(user U){
        Date d = new  Date();
        String da = d.getYear()+"-"+d.getMonth()+"-"+d.getDay()+"-"+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
      try {
          String req = "INSERT INTO user(username, username_canonical, email, email_canonical, enabled, password, roles, NumeroTelephone, cin, Adresse,last_login)"
                  + "  VALUES('"+U.getUsername()+"','"+U.getUsernameCanonical()+"','"+U.getEmail()+"','"+U.getEmailCanonical()+"','"+U.getEnabled()+"','"+U.getPassword()+"','"+U.getRoles()+"','"+U.getNumeroTelephone()+"','"+U.getCin()+"','"+U.getAdresse()+"','"+da+"');";
        //  System.out.println(req);
          Statement st = cnx.createStatement();
          st.executeUpdate(req);
          System.out.println("ajoute group  Reussie !");
      } catch (SQLException ex) {
          System.out.println(ex);
      }
      
  }
  
     public List<user> afficherAll(){
       List<user> listU = new ArrayList<>();
         String req = "select * from user ";
       Statement st ;
       
      try {
          st = cnx.createStatement();
          ResultSet r1 = st.executeQuery(req);
          while (r1.next()) {              
              user  u = new user();
               u.setId(r1.getInt(1));
                  u.setUsername(r1.getString(2));
                  u.setEmail(r1.getString(4));
                  u.setNumeroTelephone(r1.getString(14));
                  u.setCin(r1.getString(15));
                  u.setAdresse(r1.getString(16));
              listU.add(u);
              }
         // System.out.println(listU);
          
      } catch (SQLException ex) {
          Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
      }
       return listU;
   }
  
  public user findUserbyId(int id ){
      user u = new user();
       String req = "select * from user where id = "+id;
       Statement st ;
      try {
          st = cnx.createStatement();
          ResultSet r1 = st.executeQuery(req);
          while (r1.next()) {              
               u.setId(r1.getInt("id"));
                  u.setUsername(r1.getString("username"));
                  u.setEmail(r1.getString(4));
                  u.setNumeroTelephone(r1.getString(14));
                  u.setCin(r1.getString(15));
                  u.setAdresse(r1.getString(16));
              }
    //      System.out.println(u);
          
      } catch (SQLException ex) {
          Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
      }
      return u;
  }
  
}
