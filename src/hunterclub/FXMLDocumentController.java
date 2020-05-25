/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunterclub;

import static com.sun.jmx.snmp.ThreadContext.contains;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.activation.DataSource;
import org.controlsfx.control.Notifications;
import tn.pidev.entities.Bareme;
import tn.pidev.entities.Journal;
import tn.pidev.services.BaremeService;
import tn.pidev.services.JournalService;
import tn.pidev.utils.MyConnection;

/**
 *
 * @author ghada
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField fxmin;
    @FXML
    private TextField fxmax;
    @FXML
    private TextField fxnote;
    @FXML
    private TableView<Bareme> fxAfficher;
    private TableColumn<Bareme, Integer> colid;
    @FXML
    private TableColumn<Bareme, Integer> colmin;
    @FXML
    private TableColumn<Bareme, Integer> colmac;
    @FXML
    private TableColumn<Bareme, Integer> colnote;
    private Connection con = MyConnection.getInstance().getCnx();
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet res;
    ObservableList<Bareme> obl = FXCollections.observableArrayList();
    public static Bareme Bareme_modif = new Bareme();
    @FXML
    private Button fxupdate;
    @FXML
    private AnchorPane content;
    @FXML
    private TextField searchfield;
    @FXML
    private Label id_verifier_min;
    @FXML
    private Label id_verifier_max;
    @FXML
    private Label id_verifier_note;
    @FXML
    private Button fxsupprimer;
    @FXML
    private Button fxvider;

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            AfficherBareme();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static BaremeService ser = new BaremeService();

    @FXML
    private void AjouterBareme(ActionEvent event) {

        Bareme b = new Bareme();
        if (((fxmin.getText().isEmpty()) == false) && ((fxmax.getText().isEmpty() == false)) && (fxnote.getText().isEmpty() == false)
                && (Integer.parseInt(fxmin.getText()) > Integer.parseInt(fxmax.getText()) == false) && (fxmin.getText().matches("[0-9]*") == true)
                && (fxmax.getText().matches("[0-9]*") == true) && (fxnote.getText().matches("[0-9]*") == true)
                && (ser.rechercherparMin(Integer.parseInt(fxmin.getText())) == false) && (ser.rechercherparMax(Integer.parseInt(fxmax.getText())) == false)
                && (ser.rechercherparNote(Integer.parseInt(fxnote.getText())) == false)&& (ser.Max(Integer.parseInt(fxmin.getText())) == false)
                && (ser.Note(Integer.parseInt(fxnote.getText())) == false)) {
            b.setMin(parseInt(fxmin.getText()));
            b.setMax(parseInt(fxmax.getText()));
            b.setNote(parseInt(fxnote.getText()));
            try {
                ser.ajouter(b);
                Notifications notificationBuilder = Notifications.create().title("Bareme Ajoutée").text("avec succés").position(Pos.TOP_RIGHT).hideAfter(Duration.seconds(5));
                notificationBuilder.show();
                fxmax.clear();
                fxmin.clear();
                fxnote.clear();
                id_verifier_max.setVisible(false);
                id_verifier_min.setVisible(false);
                id_verifier_note.setVisible(false);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                obl.clear();
                AfficherBareme(event);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
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

    }

    @FXML
    private void AfficherBareme(ActionEvent event) throws SQLException {
        BaremeService ser = new BaremeService();
        List<Bareme> list = ser.readAll();
        ObservableList<Bareme> oList = FXCollections.observableArrayList(list);

        colmin.setCellValueFactory(new PropertyValueFactory<>("min"));
        colmac.setCellValueFactory(new PropertyValueFactory<>("max"));
        colnote.setCellValueFactory(new PropertyValueFactory<>("note"));
        fxAfficher.setItems(oList);

    }

    private void AfficherBareme() throws SQLException {
        BaremeService ser = new BaremeService();
        List<Bareme> list = ser.readAll();
        ObservableList<Bareme> oList = FXCollections.observableArrayList(list);

        colmin.setCellValueFactory(new PropertyValueFactory<>("min"));
        colmac.setCellValueFactory(new PropertyValueFactory<>("max"));
        colnote.setCellValueFactory(new PropertyValueFactory<>("note"));
        fxAfficher.setItems(oList);

    }

    @FXML
    private void UpdateBareme(ActionEvent event) {
        fxupdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Bareme_modif = fxAfficher.getSelectionModel().getSelectedItem();
                AnchorPane newLoadedPane = null;
                try {
                    newLoadedPane = FXMLLoader.load(getClass().getResource("/hunterclub/EditBareme.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    obl.clear();
                    AfficherBareme(event);
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Scene scene = new Scene(newLoadedPane);
                Stage stage = new Stage();
                stage.setTitle("Modifier Bareme");
                stage.setScene(scene);
                stage.show();

            }

        });
        if (fxAfficher.getSelectionModel().getSelectedIndex() < 0) {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("Aucune Date est entrée ");
            alert.setContentText("SVP entrer une date dans la zone recherche.");
            alert.showAndWait();
        }
    }

    @FXML
    private void SupprimerBareme(ActionEvent event) {

        fxsupprimer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Suppression");
            alert.setHeaderText("Suppression de la ligne  selectionée ");
            alert.setContentText("voulez-vous vraiment supprimer cette ligne du table.");
            alert.showAndWait();
                ObservableList<Bareme> selectedRows, Bareme;
                Bareme = fxAfficher.getItems();
                selectedRows = fxAfficher.getSelectionModel().getSelectedItems();
                for (Bareme b : selectedRows) {
                    Bareme.remove(colid);

                    BaremeService ser = new BaremeService();
                    try {
                        
                        ser.delete(b);
                        Notifications notificationBuilder = Notifications.create().title("Bareme Supprimé").text("avec succés").position(Pos.TOP_RIGHT).hideAfter(Duration.seconds(5));
                        notificationBuilder.show();
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        obl.clear();
                        AfficherBareme(event);
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });
        if (fxAfficher.getSelectionModel().getSelectedIndex() < 0) {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("Aucune ligne est selectionée ");
            alert.setContentText("SVP sélectionner une ligne du table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void TrierBareme(ActionEvent event) throws SQLException {
        BaremeService ser = new BaremeService();
        List<Bareme> list = ser.readTrier();
        ObservableList<Bareme> oList = FXCollections.observableArrayList(list);

        colmin.setCellValueFactory(new PropertyValueFactory<>("min"));
        colmac.setCellValueFactory(new PropertyValueFactory<>("max"));
        colnote.setCellValueFactory(new PropertyValueFactory<>("note"));
        fxAfficher.setItems(oList);

    }

    @FXML
    private void RechercherBareme(ActionEvent event) {
        ObservableList data = fxAfficher.getItems();
        searchfield.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                fxAfficher.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<Bareme> subentries = FXCollections.observableArrayList();

            long count = fxAfficher.getColumns().stream().count();
            for (int i = 0; i < fxAfficher.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + fxAfficher.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(fxAfficher.getItems().get(i));
                        break;
                    }
                }
            }
            fxAfficher.setItems(subentries);
        });

    }
    Bareme b = new Bareme();

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
        }else if (ser.Max(Integer.parseInt(fxmin.getText()))==true) {
            id_verifier_min.setText("min doit étre superieur ");
            id_verifier_min.setTextFill(Color.RED);
            id_verifier_min.setVisible(true);
        }
        else {
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
        } else if (Integer.parseInt(fxmin.getText()) >= Integer.parseInt(fxmax.getText()) == true) {
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
        } else if (ser.Note(Integer.parseInt(fxnote.getText()))==true) {
            id_verifier_note.setText("note doit étre superieur à la précedente ");
            id_verifier_note.setTextFill(Color.RED);
            id_verifier_note.setVisible(true);
        }
        else {
            id_verifier_note.setText("Champ remplie!");
            id_verifier_note.setTextFill(Color.GREEN);
            id_verifier_note.setVisible(true);

        }
    }

    @FXML
    private void viderBareme(ActionEvent event) {
        fxvider.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
        fxmax.clear();
        fxmin.clear();
        fxnote.clear();
        id_verifier_max.setVisible(false);
        id_verifier_min.setVisible(false);
        id_verifier_note.setVisible(false);
            }
        });
        if (fxmax.getText().isEmpty()&& fxnote.getText().isEmpty()&&fxmin.getText().isEmpty()) {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Champs Déja vides  ");
            alert.setContentText("SVP Vérifier les champs.");
            alert.showAndWait();
        }
        
    }
}
