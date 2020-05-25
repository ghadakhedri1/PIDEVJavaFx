/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.pidev.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.pidev.entities.Animal;
import tn.pidev.entities.Bareme;
import tn.pidev.entities.Evenement;
import tn.pidev.entities.Journal;
import tn.pidev.entities.Lieu;
import tn.pidev.entities.user;
import tn.pidev.utils.MyConnection;
import tn.pidev.utils.Vars;

/**
 *
 * @author ghada
 */
public class JournalService implements IService<Journal> {

    private Connection con;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet res;

    public JournalService() {
        con = MyConnection.getInstance().getCnx();
    }
//ajouter Journal

    @Override
    public void ajouter(Journal t) throws SQLException {
        ste = con.createStatement();

        String sql = "INSERT INTO `journale`( `user_id`, `evenement_id`, `animal_id`, `nbchasse`, `lieu_id`, `date`, `description`, `idchasseur`, `image`) "
                + "VALUES ('" + t.getUser_id().getId() + "', '" + t.getEvenement().getId() + "', '"
                + t.getAnimal().getId() + "', '" + t.getNbChasse() + "', '" + t.getLieu().getId() + "', '" + t.getDate() + "', '" + t.getDescription() + "', '" + t.getId_chasseur() + "', '" + t.getImage() + "');";

        try {
            ste = con.createStatement();
            ste.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(BaremeService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//supprimer journal

    public void delete(Journal j) throws SQLException {
        try {
            String req = "DELETE FROM `journale` WHERE `journale`.`id` = ?";
            PreparedStatement ste = con.prepareStatement(req);
            ste.setInt(1, j.getId());
            ste.executeUpdate();
            System.out.println("journal supprimé");

        } catch (SQLException ex) {
            Logger.getLogger(BaremeService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void delete(int id) throws SQLException {
        try {
            String requete = " delete from `hunterclub`.`journale` where id='" + id + "'";
            pst = con.prepareStatement(requete);
            ste = con.createStatement();
            ste.executeUpdate(requete);

        } catch (SQLException ex) {
            Logger.getLogger(JournalService.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (pst.executeUpdate() > 0) {
            System.out.println("Journal supprimé");
        } else {
            System.out.println("Journal non supprimé");
        }
    }
//modifier journal

    @Override
    public void update(Journal t, int id) throws SQLException {
        try {
            String requete = " update `hunterclub`.`journale` set animal_id=? , nbchasse=? , lieu_id=? ,description=?,image=?  where id='" + id + "'";
            pst = con.prepareStatement(requete);
            pst.setInt(1, t.getAnimal().getId());
            pst.setInt(2, t.getNbChasse());
            pst.setInt(3, t.getLieu().getId());
            pst.setString(4, t.getDescription());
            pst.setString(5, t.getImage());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BaremeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (pst.executeUpdate() > 0) {
            System.out.println("Journal modifier");
        } else {
            System.out.println("Journal non modifier");
        }
    }

    public void update(Journal t) throws SQLException {
        try {
            String requete = " update `hunterclub`.`journale` set animal_id=? , nbchasse=? , lieu_id=? ,description=?,date=? ,image=? where id=" + t.getId();
            pst = con.prepareStatement(requete);
            pst.setInt(1, t.getAnimal().getId());
            pst.setInt(2, t.getNbChasse());
            pst.setInt(3, t.getLieu().getId());
            pst.setString(4, t.getDescription());
            pst.setDate(5, (Date) t.getDate());
             pst.setString(6, t.getImage());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BaremeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (pst.executeUpdate() > 0) {
            System.out.println("Journal modifier");
        } else {
            System.out.println("Journal non modifier");
        }
    }
//lister tout les journaux

    @Override
    public List<Journal> readAll() throws SQLException {
        List<Journal> journals = new ArrayList<Journal>();

       String req1 = "SELECT * FROM `journale`";
         PreparedStatement preparedStatement = con.prepareStatement(req1);

        Journal r = null;
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            user user = new user();
            String requete="select username from user where id ="+result.getInt("user_id");
            PreparedStatement pre = con.prepareStatement(requete);
            ResultSet restt = pre.executeQuery();
        while (restt.next()) {
            
            user.setUsername(restt.getString("username"));
        } 
            Evenement event = new Evenement();
            user.setId(result.getInt("user_id"));
            event.setId(result.getInt("evenement_id"));
            Animal animal = new Animal();
            Lieu lieu = new Lieu();
            String req="select a.nom from animal a where a.id ="+result.getInt("animal_id");
            PreparedStatement pst = con.prepareStatement(req);
            ResultSet res = pst.executeQuery();
            String requet="select l.nom from lieu l where l.id ="+result.getInt("lieu_id");
            PreparedStatement pstt = con.prepareStatement(requet);
            ResultSet rest = pstt.executeQuery();
        while (res.next()) {
            animal.setNom_animal(res.getString("nom"));
        } 
        while (rest.next()) {
            lieu.setNom_lieu(rest.getString("nom"));
        }
             //animal=animalService.getAnimalById(result.getInt("animal_id"));
           //type de retour animal parametre intid 
            animal.setId(result.getInt("animal_id"));  
            lieu.setId(result.getInt("lieu_id"));
            r = new Journal(result.getInt(1), user, event, result.getInt("nbchasse"), animal, lieu, result.getDate("date"), result.getString("description"), result.getString("image"), result.getInt("idchasseur"));
            journals.add(r);}
        System.out.println(journals);
        return journals;
    }
//chercher journal par id

    @Override
    public Journal readById(int id) throws SQLException {
        String req1 = "SELECT * FROM journale WHERE id=?";
        PreparedStatement steprep = con.prepareStatement(req1);
        steprep.setInt(1, id);
        ResultSet result = steprep.executeQuery();

        Journal r = new Journal();
        if (result.first()) {
            user user = new user();
            Evenement event = new Evenement();
              Animal animal = new Animal();
            Lieu lieu = new Lieu();
            animal.setId(result.getInt("animal_id"));
            lieu.setId(result.getInt("lieu_id"));
            user.setId(result.getInt("user_id"));
            event.setId(result.getInt("evenement_id"));
            r = new Journal(id, user, event, result.getInt("nbchasse"), animal, lieu, result.getDate("date"), result.getString("description"), result.getString("image"), result.getInt("idchasseur"));

        }
        System.out.println(r);
        return r;
    }

    public List<Journal> readByDate(Date date) throws SQLException {
      
        List<Journal> journals = new ArrayList<Journal>();

        String req1 = "SELECT * FROM `journale` where date="+"'"+date+"';";
        PreparedStatement preparedStatement = con.prepareStatement(req1);

        Journal r = null;
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            user user = new user();
            Evenement event = new Evenement();
            user.setId(result.getInt("user_id"));
            event.setId(result.getInt("evenement_id"));
            Animal animal = new Animal();
            Lieu lieu = new Lieu();
            String req="select a.nom from animal a where a.id ="+result.getInt("animal_id");
            PreparedStatement pst = con.prepareStatement(req);
            ResultSet res = pst.executeQuery();
            String requet="select l.nom from lieu l where l.id ="+result.getInt("lieu_id");
            PreparedStatement pstt = con.prepareStatement(requet);
            ResultSet rest = pstt.executeQuery();
        while (res.next()) {
            animal.setNom_animal(res.getString("nom"));
        } 
        while (rest.next()) {
            lieu.setNom_lieu(rest.getString("nom"));
        }
         
            String requete="select username from user where id ="+result.getInt("user_id");
            PreparedStatement pre = con.prepareStatement(requete);
            ResultSet restt = pre.executeQuery();
        while (restt.next()) {
            user.setUsername(restt.getString("username"));
        } 
             //animal=animalService.getAnimalById(result.getInt("animal_id"));
           //type de retour animal parametre intid 
            animal.setId(result.getInt("animal_id"));  
            lieu.setId(result.getInt("lieu_id"));
            r = new Journal(result.getInt(1), user, event, result.getInt("nbchasse"), animal, lieu, result.getDate("date"), result.getString("description"), result.getString("image"), result.getInt("idchasseur"));
            journals.add(r);

        }
        System.out.println(journals);
        return journals;
    }
// tester si journal existe 

    public boolean existJournal(int id) throws SQLException {
        return readById(id) != null;
    }
//nombre total de chasse pour user 

    public int nbChasseTotalParUser(int id) throws SQLException {
        int nb = 0;
        String req1 = "SELECT sum(nbchasse) FROM `journale` where user_id=?";
        PreparedStatement preparedStatement = con.prepareStatement(req1);
        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        if (result.first()) {
            nb = result.getInt(1);
        }
        System.out.println("total chasse pour user est : " + nb);
        return nb;
    }

    public int nbChasseTotal(int id ) throws SQLException {
        int nb = 0;
        String req1 = "SELECT sum(nbchasse) FROM `journale` where user_id=?";
        PreparedStatement preparedStatement = con.prepareStatement(req1);
        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        if (result.first()) {
            nb = result.getInt(1);
        }
        System.out.println("total chasse pour user est : " + nb);
        return nb;
    }

    public String nbChasseTotal1(int id ) throws SQLException {
        int nb = 0;
        String req1 = "SELECT sum(nbchasse) FROM `journale` where user_id=?";
        PreparedStatement preparedStatement = con.prepareStatement(req1);
        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        if (result.first()) {
            nb = result.getInt(1);
        }
        System.out.println("total chasse pour user est : " + nb);
        return Integer.toString(nb);
    }

//tri par date  
    public List<Journal> readTrierParUser(int id) throws SQLException {
        List<Journal> journals = new ArrayList<Journal>();

        String req1 = "SELECT * FROM `journale` where user_id ="+id+" ORDER BY date DESC";
        PreparedStatement preparedStatement = con.prepareStatement(req1);
        System.err.println(id);
        Journal r = null;
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
             user user = Vars.getCurrentUser();
            Evenement event = new Evenement();
            user.setId(result.getInt("user_id"));
            event.setId(result.getInt("evenement_id"));
            Animal animal = new Animal();
            Lieu lieu = new Lieu();
            String req="select a.nom from animal a where a.id ="+result.getInt("animal_id");
            PreparedStatement pst = con.prepareStatement(req);
            ResultSet res = pst.executeQuery();
            String requet="select l.nom from lieu l where l.id ="+result.getInt("lieu_id");
            PreparedStatement pstt = con.prepareStatement(requet);
            ResultSet rest = pstt.executeQuery();
        while (res.next()) {
            animal.setNom_animal(res.getString("nom"));
        } 
        while (rest.next()) {
            lieu.setNom_lieu(rest.getString("nom"));
        }
             //animal=animalService.getAnimalById(result.getInt("animal_id"));
           //type de retour animal parametre intid 
            animal.setId(result.getInt("animal_id"));  
            lieu.setId(result.getInt("lieu_id"));
            r = new Journal(result.getInt(1), user, event, result.getInt("nbchasse"), animal, lieu, result.getDate("date"), result.getString("description"), result.getString("image"), result.getInt("idchasseur"));
            journals.add(r);

        }
        System.out.println(journals);
        return journals;
    }

    public List<Journal> readTrier() throws SQLException {
        List<Journal> journals = new ArrayList<Journal>();

        String req1 = "SELECT * FROM `journale` ORDER BY date DESC";
        PreparedStatement preparedStatement = con.prepareStatement(req1);

        Journal r = null;
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
             user user = new user();
            Evenement event = new Evenement();
            user.setId(result.getInt("user_id"));
            event.setId(result.getInt("evenement_id"));
            Animal animal = new Animal();
            Lieu lieu = new Lieu();
            animal.setId(result.getInt("animal_id"));
            lieu.setId(result.getInt("lieu_id"));
            r = new Journal(result.getInt(1), user, event, result.getInt("nbchasse"), animal, lieu, result.getDate("date"), result.getString("description"), result.getString("image"), result.getInt("idchasseur"));
            journals.add(r);

        }
        System.out.println(journals);
        return journals;
    }

//lister les journaux par user id
    public List<Journal> getAllParUserID(int id ) throws SQLException {

        List<Journal> journals = new ArrayList<Journal>();

        String req1 = "SELECT * FROM `journale` where user_id="+Vars.getCurrentUser().getId();
        PreparedStatement preparedStatement = con.prepareStatement(req1);
       
        Journal r = null;
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            user user = Vars.getCurrentUser();
            Evenement event = new Evenement();
            user.setId(result.getInt("user_id"));
            event.setId(result.getInt("evenement_id"));
            Animal animal = new Animal();
            Lieu lieu = new Lieu();
            String req="select a.nom from animal a where a.id ="+result.getInt("animal_id");
            PreparedStatement pst = con.prepareStatement(req);
            ResultSet res = pst.executeQuery();
            String requet="select l.nom from lieu l where l.id ="+result.getInt("lieu_id");
            PreparedStatement pstt = con.prepareStatement(requet);
            ResultSet rest = pstt.executeQuery();
        while (res.next()) {
            animal.setNom_animal(res.getString("nom"));
        } 
        while (rest.next()) {
            lieu.setNom_lieu(rest.getString("nom"));
        }
             //animal=animalService.getAnimalById(result.getInt("animal_id"));
           //type de retour animal parametre intid 
            animal.setId(result.getInt("animal_id"));  
            lieu.setId(result.getInt("lieu_id"));
            r = new Journal(result.getInt(1), user, event, result.getInt("nbchasse"), animal, lieu, result.getDate("date"), result.getString("description"), result.getString("image"), result.getInt("idchasseur"));
            journals.add(r);

        }
        System.out.println(journals);
        return journals;
    }
//nb des journaux par user 

    public int nbJournalParUser(int id) throws SQLException {
        int nb = 0;

        String req1 = "SELECT count(*) FROM `journale` where user_id=?";
        PreparedStatement preparedStatement = con.prepareStatement(req1);
        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        if (result.first()) {
            nb = result.getInt(1);
        }
        System.out.println("total journal pour user est : " + nb);
        return nb;
    }

    //nombre des users 
    public int nbUser() throws SQLException {
        int nb = 0;

        String req1 = "SELECT count(*) FROM user ";
        PreparedStatement preparedStatement = con.prepareStatement(req1);

        ResultSet result = preparedStatement.executeQuery();
        if (result.first()) {
            nb = result.getInt(1);
        }
        System.out.println("Nombre total des  user est : " + nb);
        return nb;
    }

   

    public int CalculBareme(int nb) throws SQLException {
        int note = 0;
        String req = "SELECT note FROM bareme b WHERE b.min <= " + nb + " and b.max >=" + nb + ";";
        PreparedStatement preparedStatement = con.prepareStatement(req);
        ResultSet result = preparedStatement.executeQuery();
        if (result.first()) {
            note = result.getInt(1);
        }
        System.out.println(note);
        return note;

    }

       public String CalculScore1(int id ) throws SQLException {
        int score = 0;
        int totalChasse = nbChasseTotalParUser( Vars.getCurrentUser().getId());
        int note = CalculBareme(totalChasse);
        int nbCompetition = 0;
        String req1 = "SELECT COUNT(j.id) as nbCompetion,j.*,e.*,te.* FROM journale j join evenement e join type_events te WHERE j.user_id ="+id+" AND e.Type=te.id and j.evenement_id=e.id and te.type='competition'";
        PreparedStatement preparedStatement = con.prepareStatement(req1);
        ResultSet result = preparedStatement.executeQuery();
        if (result.first()) {
            nbCompetition = result.getInt("nbCompetion");
            //System.out.println("nb competition"+result.getInt("nbCompetion"));
            //System.out.println("id user "+id);

        }

        if (nbCompetition >= 1 && nbCompetition <= 3) {
            score += 3 + note;
        } else if (nbCompetition >= 4) {
            score += 5 + note;
        } else {
            score += note;
        }

        System.out.println("score est : " + score);

        return Integer.toString(score);
    }
  public String findiduserbyiduserInterfaceMail(int iduser) throws SQLException {

        try {
        String requete = "SELECT email FROM user INNER JOIN journale ON journale.user_id=? AND user.id=?";

            PreparedStatement pst = con.prepareStatement(requete);
            pst.setInt(1, iduser);
            pst.setInt(2, iduser);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                user u = new user();
                u.setEmail(rs.getString(1));
                System.out.println(u.getEmail());

                return u.getEmail();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        String u = "null";
        return u;
    }
    //chercher user par id
    public String findUSerById_user(int id) throws SQLException {
        String username = null;
        String req1 = "SELECT username FROM user where id = ?";
        PreparedStatement preparedStatement = con.prepareStatement(req1);
      preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        if (result.first()) {
            username = result.getString(1);
        }

        return username;
    }

    public Boolean FindjournalByDate(String Date,int id ) throws SQLException {
        String req = "SELECT * FROM `journale` WHERE user_id=? and date='" + Date + "'";
        PreparedStatement preparedStatement = con.prepareStatement(req);
        preparedStatement.setInt(1, id);
        ResultSet res = ste.executeQuery(req);
        Journal com = null;
        String date = null;
        while (res.next()) {
            date = res.getString(2);
            if (Date.equals(date)) {
                return true;

            }

            System.out.println(date);
        }
        return false;
    }

}
