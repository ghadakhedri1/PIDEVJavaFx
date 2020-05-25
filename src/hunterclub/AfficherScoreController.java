/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunterclub;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.pidev.services.JournalService;
import tn.pidev.utils.Vars;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class AfficherScoreController implements Initializable {

    @FXML
    private Label idscore;
    @FXML
    private Label idtotalchasse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void afficherscore(ActionEvent event) {
        JournalService ser = new JournalService();
        
        try {
            idscore.setText(ser.CalculScore1(Vars.getCurrentUser().getId()));
            idtotalchasse.setText(ser.nbChasseTotal1(Vars.getCurrentUser().getId()));
        } catch (SQLException ex) {
            Logger.getLogger(AfficherScoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
