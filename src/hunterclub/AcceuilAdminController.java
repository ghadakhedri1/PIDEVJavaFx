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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class AcceuilAdminController implements Initializable {

    @FXML
    private BorderPane borderpane;
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadUI("StatistiqueTotalChasse");
    }    
     private void loadUI(String ui)
    {
        Parent root = null;
        try {
            root =FXMLLoader.load(getClass().getResource(ui+".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(AcceuilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderpane.setCenter(root);
    }

    @FXML
    private void journal(MouseEvent event) {
        loadUI("AllJournal");
    }

    @FXML
    private void bareme(MouseEvent event) throws IOException {
         loadUI("FXMLDocument");
    }

    @FXML
    private void Acceuil(MouseEvent event) {
          loadUI("StatistiqueTotalChasse");
    }

    
}
