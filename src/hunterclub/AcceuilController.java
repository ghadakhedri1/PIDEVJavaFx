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
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class AcceuilController implements Initializable {

    private BorderPane content;
    private VBox idafficher;
    private AnchorPane rootPane;
    @FXML
    private BorderPane borderpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borderpane.getStyleClass().add("borderpane");
        loadUI("AddJournal");
    }    



    private void loadSecond(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("AddJournal.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    private void close(MouseEvent event) {
        Stage stage =(Stage) borderpane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void journal(MouseEvent event) {
        loadUI("AddJournal");
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
    private void trophy(MouseEvent event) {
        loadUI("Statistique");
    }
}
