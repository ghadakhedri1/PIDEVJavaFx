/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.pidev.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.pidev.entities.Bareme;
import tn.pidev.utils.MyConnection;

/**
 *
 * @author ghada
 */
public class BaremeService implements IService<Bareme> {

    private Connection con;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet res;

    public BaremeService() {
        con = MyConnection.getInstance().getCnx();

    }

    @Override
    public void ajouter(Bareme t) throws SQLException {
        ste = con.createStatement();
        String requeteInsert = "INSERT INTO `hunterclub`.`bareme` (`id`, `note`, `min`, `max`)  VALUES (NULL, '" + t.getNote() + "', '" + t.getMin() + "', '" + t.getMax() + "');";
        try {
            ste = con.createStatement();
            ste.executeUpdate(requeteInsert);

        } catch (SQLException ex) {
            Logger.getLogger(BaremeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try {
            String requete = " delete from `hunterclub`.`bareme` where id='" + id + "'";
            pst = con.prepareStatement(requete);
            ste = con.createStatement();
            ste.executeUpdate(requete);

        } catch (SQLException ex) {
            Logger.getLogger(BaremeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (pst.executeUpdate() > 0) {
            System.out.println("Bareme supprimé");
        } else {
            System.out.println("Bareme non supprimé");
        }
    }
    
    public void delete(Bareme b) throws SQLException {
        try {
            String req = "DELETE FROM `bareme` WHERE `bareme`.`id` = ?";
            PreparedStatement ste = con.prepareStatement(req);
            ste.setInt(1, b.getId());
            ste.executeUpdate();
            System.out.println("Stage supprimé");

        } catch (SQLException ex) {
            Logger.getLogger(BaremeService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @Override
    public void update(Bareme t, int id) throws SQLException {
        try {
            String requete = " update `hunterclub`.`bareme` set note=? , min=? , max=?   where id='" + id + "'";
            pst = con.prepareStatement(requete);
            pst.setInt(1, t.getNote());
            pst.setInt(2, t.getMin());
            pst.setInt(3, t.getMax());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BaremeService.class.getName()).log(Level.SEVERE, null, ex);
        }
         if (pst.executeUpdate() > 0) {
            System.out.println("Bareme modifié");
        } else {
            System.out.println("Bareme non modifié");
        }
    }
     public void update(Bareme t) throws SQLException {
        try {
            String requete = " update `hunterclub`.`bareme` set note=? , min=? , max=?   where id =" + t.getId();
            pst = con.prepareStatement(requete);
            pst.setInt(1, t.getNote());
            pst.setInt(2, t.getMin());
            pst.setInt(3, t.getMax());
            
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BaremeService.class.getName()).log(Level.SEVERE, null, ex);
        }
         if (pst.executeUpdate() > 0) {
            System.out.println("Bareme modifié");
        } else {
            System.out.println("Bareme non modifié");
        }
    }


    @Override
    public List<Bareme> readAll() throws SQLException {
        List<Bareme> arr = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from `hunterclub`.`bareme`");
        while (rs.next()) {
            int id = rs.getInt(1);
            int note = rs.getInt("note");
            int min = rs.getInt("min");
            int max = rs.getInt("max");
            Bareme b = new Bareme(id, note, min, max);
            arr.add(b);
        }
        System.out.println(arr);
        
        return arr;
    }

    @Override
    public Bareme readById(int id) throws SQLException {
        Bareme a = null;
        String requete = " select* from `hunterclub`.`bareme`  where id='" + id + "'";
        try {

            ste = con.createStatement();
            res = ste.executeQuery(requete);
            if (res.next()) {
                a = new Bareme(res.getInt(1), res.getInt(2), res.getInt(3), res.getInt(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaremeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(a);
        return a;
    }

    @Override
    public List<Bareme> readTrier() throws SQLException {
        List<Bareme> arr = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from `hunterclub`.`bareme` ORDER BY note DESC");
        while (rs.next()) {
            int id = rs.getInt(1);
            int note = rs.getInt("note");
            int min = rs.getInt("min");
            int max = rs.getInt("max");
            Bareme b = new Bareme(id, note, min, max);
            arr.add(b);
        }
        System.out.println(arr);
        return arr;
    }
    public boolean rechercherparMin(int min) {
        boolean test = false;
        
        try {

            
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from `hunterclub`.`bareme` where min="+min);
            rs.last();
            if (rs.getRow() != 0) {
                test = true;
               
            } else {
                test = false;
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaremeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }
    public boolean Max(int max) {
       boolean test = false ;
        
        try {

            
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from bareme where max>="+max);
            rs.last();
            if (rs.getRow() != 0) {
                test = true;
               
            } else {
                test = false;
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaremeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }
    public boolean Note(int note) {
       boolean test = false ;
        
        try {

            
        ste = con.createStatement();
    ResultSet rs = ste.executeQuery("select * from bareme where note>="+note);
            rs.last();
            if (rs.getRow() != 0) {
                test = true;
               
            } else {
                test = false;
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaremeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }
    public boolean rechercherparMax(int max) {
             boolean test = false;
        
        try {

            
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from `hunterclub`.`bareme` where max="+max);
            rs.last();
            if (rs.getRow() != 0) {
                test = true;
               
            }
            
            
            else {
                test = false;
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaremeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }
      public boolean rechercherparNote(int note) {
             boolean test = false;
        
        try {

            
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from `hunterclub`.`bareme` where note="+note);
            rs.last();
            if (rs.getRow() != 0) {
                test = true;
               
            } else {
                test = false;
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaremeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }


}
