/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunterclub;

import static hunterclub.FXMLDocumentController.ser;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import javax.swing.text.html.HTML;
import org.controlsfx.control.Notifications;
import tn.pidev.entities.Bareme;
import tn.pidev.services.BaremeService;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class EditBaremeController implements Initializable {

    @FXML
    private TextField fxmin;
    @FXML
    private TextField fxmax;
    @FXML
    private TextField fxnote;
    private AnchorPane content;
    @FXML
    private Button close;
     private BorderPane borderpane;
    @FXML
    private AnchorPane content1;
    @FXML
    private Label id_verifier_min;
    @FXML
    private Label id_verifier_max;
    @FXML
    private Label id_verifier_note;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        fxmin.setText(String.valueOf(FXMLDocumentController.Bareme_modif.getMin()));
        fxmax.setText(String.valueOf(FXMLDocumentController.Bareme_modif.getMax()));
        fxnote.setText(String.valueOf(FXMLDocumentController.Bareme_modif.getNote()));

    }

    @FXML
    private void UpdateBareme(ActionEvent event) {
        AnchorPane newLoadedPane = null;
        FXMLDocumentController.Bareme_modif.setMin(parseInt(fxmin.getText()));
        FXMLDocumentController.Bareme_modif.setMax(parseInt(fxmax.getText()));
        FXMLDocumentController.Bareme_modif.setNote(parseInt(fxnote.getText()));
        BaremeService ser = new BaremeService();
       
            if (((fxmin.getText().isEmpty()) == false) && ((fxmax.getText().isEmpty() == false)) && (fxnote.getText().isEmpty() == false)
                && (Integer.parseInt(fxmin.getText()) > Integer.parseInt(fxmax.getText()) == false) && (fxmin.getText().matches("[0-9]*") == true)
                && (fxmax.getText().matches("[0-9]*") == true) && (fxnote.getText().matches("[0-9]*") == true)
              ) {
                try {
                    ser.update(FXMLDocumentController.Bareme_modif);
                } catch (SQLException ex) {
                    Logger.getLogger(EditBaremeController.class.getName()).log(Level.SEVERE, null, ex);
                }
             Notifications notificationBuilder = Notifications.create().title("Bareme modifié").text("avec succés").position(Pos.TOP_RIGHT).hideAfter(Duration.seconds(5));
        notificationBuilder.show();
        } 
        else {
            BoxBlur blur = new BoxBlur(3, 3, 3);
            content.setEffect(blur);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Repetez svp");
            alert.setHeaderText("champs non validés");
            alert.setContentText("Verifiez vos champs svp!!");
            alert.showAndWait();
            content.setEffect(null);
            System.out.println("nest pas possible");
        }
        
        
        
        try {
            newLoadedPane = FXMLLoader.load(getClass().getResource("/hunterclub/FXMLDocument.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(EditJournalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        content.getChildren().clear();
        content.getChildren().add(newLoadedPane);  
             
       

    }

    @FXML
    private void closeBareme(ActionEvent event) {
        fxmax.clear();
        fxmin.clear();
        fxnote.clear();
        id_verifier_max.setVisible(false);
        id_verifier_min.setVisible(false);
        id_verifier_min.setVisible(false);
    }

    @FXML
    private void verifier_min(KeyEvent event) {
         if (fxmin.getText().isEmpty()) {
            id_verifier_min.setText("champ vide!");
            id_verifier_min.setTextFill(Color.RED);
            id_verifier_min.setVisible(true);
        } else if (ser.rechercherparMin(Integer.parseInt(fxmin.getText()))) {
            id_verifier_min.setText("min déja existe!");
            id_verifier_min.setTextFill(Color.RED);
            id_verifier_min.setVisible(true);
        } else {
            System.out.println();
            id_verifier_min.setText("Champ remplie!");
            id_verifier_min.setTextFill(Color.GREEN);
            id_verifier_min.setVisible(true);

        }
    }

    @FXML
    private void verifier_max(KeyEvent event) {
        if (fxmax.getText().isEmpty()) {
            id_verifier_max.setText("champ vide!");
            id_verifier_max.setTextFill(Color.RED);
            id_verifier_max.setVisible(true);
        } else if (Integer.parseInt(fxmin.getText()) > Integer.parseInt(fxmax.getText()) == true) {
            id_verifier_max.setText("Max doit étre superieur à Min");
            id_verifier_max.setTextFill(Color.RED);
            id_verifier_max.setVisible(true);

        } else if (!fxmax.getText().matches("[0-9]*")) {
            id_verifier_max.setText("sasir un nombre!");
            id_verifier_max.setTextFill(Color.RED);
            id_verifier_max.setVisible(true);
        } else if (ser.rechercherparMax(Integer.parseInt(fxmax.getText()))) {
            id_verifier_max.setText("max déja existe!");
            id_verifier_max.setTextFill(Color.RED);
            id_verifier_max.setVisible(true);
        } else {
            id_verifier_max.setText("Champ remplie!");
            id_verifier_max.setTextFill(Color.GREEN);
            id_verifier_max.setVisible(true);

        }
    }

    @FXML
    private void verifier_note(KeyEvent event) {
        if (fxnote.getText().isEmpty()) {
            id_verifier_note.setText("champ vide!");
            id_verifier_note.setTextFill(Color.RED);
            id_verifier_note.setVisible(true);
        } else if (!fxnote.getText().matches("[0-9]*")) {
            id_verifier_note.setText("sasir un nombre!");
            id_verifier_note.setTextFill(Color.RED);
            id_verifier_note.setVisible(true);

        } else if (ser.rechercherparNote(Integer.parseInt(fxnote.getText()))) {
            id_verifier_note.setText("note déja existe!");
            id_verifier_note.setTextFill(Color.RED);
            id_verifier_note.setVisible(true);
        } else {
            id_verifier_note.setText("Champ remplie!");
            id_verifier_note.setTextFill(Color.GREEN);
            id_verifier_note.setVisible(true);

        }
    }

    

}
