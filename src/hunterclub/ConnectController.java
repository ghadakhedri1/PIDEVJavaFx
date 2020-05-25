/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunterclub;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class ConnectController implements Initializable {

    @FXML
    private VBox btn_user;
    @FXML
    private Button btn_admin;
    @FXML
    private AnchorPane content;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void user(ActionEvent event) {   
         
        try {
            FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource("/hunterclub/Acceuil.fxml"));
            Parent root1=(Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
                    
        } catch (IOException ex) {
            Logger.getLogger(EditJournalController.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }

    @FXML
    private void admin(ActionEvent event) {
        try {
            FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource("/hunterclub/AcceuilAdmin.fxml"));
            Parent root1=(Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
                    
        } catch (IOException ex) {
            Logger.getLogger(EditJournalController.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }
    
}
