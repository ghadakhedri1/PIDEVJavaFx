/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunterclub;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tn.pidev.entities.Animal;
import tn.pidev.entities.Journal;
import tn.pidev.entities.Lieu;
import tn.pidev.services.BaremeService;
import tn.pidev.services.JournalService;
import tn.pidev.utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class EditJournalController implements Initializable {

    @FXML
    private AnchorPane content;
    @FXML
    private ComboBox<Animal> fxanimal;
    @FXML
    private TextField fxnbchasse;
    @FXML
    private ComboBox<Lieu> fxlieu;
    @FXML
    private DatePicker fxdate;
    @FXML
    private TextArea fxdescription;
    @FXML
    private Button close;
    @FXML
    private Button image;
    @FXML
    private Label id_verifier_animal;
    @FXML
    private Label id_verifier_nbchasse;
    @FXML
    private Label id_verifier_lieu;
    @FXML
    private Label id_verifier_date;
    @FXML
    private Label lmla;
   private Connection con = MyConnection.getInstance().getCnx();

    /**
     * Initializes the controller class.
     */
    @Override
    @SuppressWarnings("empty-statement")
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fillcomboAnimal();
            fillcomboLieu();
        } catch (SQLException ex) {
            Logger.getLogger(EditJournalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date deadlineDatePrompt = (java.sql.Date) AddJournalController.Journal_modif.getDate();
       fxdate.setValue(deadlineDatePrompt.toLocalDate());
        String a = AddJournalController.Journal_modif.getAnimal().getNom_animal();
        int b= fxanimal.getItems().indexOf(a);
        fxanimal.getSelectionModel().select(b);
        fxdescription.setText(String.valueOf(AddJournalController.Journal_modif.getDescription()));
        String l = AddJournalController.Journal_modif.getLieu().getNom_lieu();
        int ll= fxlieu.getItems().indexOf(l);
        fxlieu.getSelectionModel().select(ll);
        fxnbchasse.setText(String.valueOf(AddJournalController.Journal_modif.getNbChasse())); 
      
 
       
    }
     ObservableList combolieu = FXCollections.observableArrayList();

    public void fillcomboLieu() throws SQLException {

        PreparedStatement pst;
        String query = "SELECT id,nom  from lieu  ";
        pst = con.prepareStatement(query);

        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Lieu a = new Lieu(rs.getInt("id"), rs.getString("nom"));

            combolieu.add(a.getNom_lieu());
            fxlieu.setItems(combolieu);
            
        }

    }
ObservableList combo1 = FXCollections.observableArrayList();

    public void fillcomboAnimal() throws SQLException {
        PreparedStatement pst;
        String query = "SELECT id,nom from animal ";
        pst = con.prepareStatement(query);

        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Animal a = new Animal(rs.getInt("id"), rs.getString("nom"));
            combo1.add(a.getNom_animal());
            fxanimal.setItems(combo1);
            
        }

    }
    @FXML
    private void UpdateJournal(ActionEvent event) {
        try {       
            AddJournalController.Journal_modif.setNbChasse(parseInt(fxnbchasse.getText()));
            AddJournalController.Journal_modif.setDescription(fxdescription.getText());
            AddJournalController.Journal_modif.setImage(lmla.getText());
            java.util.Date date = java.util.Date.from(fxdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            AddJournalController.Journal_modif.setDate(sqlDate);
            String reqanimal = "SELECT a.id ,a.nom from animal a where a.nom =  " + "'" + fxanimal.getSelectionModel().getSelectedItem() + "'" + ";";  
            PreparedStatement pst = con.prepareStatement(reqanimal);
            ResultSet rs1 = pst.executeQuery();
            while (rs1.next()) {

                int dd = rs1.getInt("id");
              AddJournalController.Journal_modif.setAnimal(new Animal(dd));
            }
             String reqlieu = "SELECT l.id ,l.nom from lieu l where l.nom =  " + "'" + fxlieu.getSelectionModel().getSelectedItem() + "'" + ";";
            pst = con.prepareStatement(reqlieu);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                int d1 = result.getInt("id");
                AddJournalController.Journal_modif.setLieu(new Lieu(d1));
            }
            JournalService ser = new JournalService();
            if (((fxnbchasse.getText().isEmpty() == false) && (fxnbchasse.getText().matches("[1-9]*") == true)
               && (fxnbchasse.getText().matches("[0]*") == false) && (fxanimal.getSelectionModel().isEmpty() == false)
                && (fxlieu.getSelectionModel().isEmpty() == false))) {
            try {
                ser.update(AddJournalController.Journal_modif);
                Notifications notificationBuilder = Notifications.create().title("Journal Modifié").text("avec succés").position(Pos.TOP_RIGHT).hideAfter(Duration.seconds(5));
                notificationBuilder.show();
            } catch (SQLException ex) {
                Logger.getLogger(EditBaremeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            AnchorPane newLoadedPane = null;    
            try {
                newLoadedPane = FXMLLoader.load(getClass().getResource("/hunterclub/Acceuil.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(EditJournalController.class.getName()).log(Level.SEVERE, null, ex);
            }
            content.getChildren().clear();
            content.getChildren().add(newLoadedPane);}
            else{
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
        } catch (SQLException ex) {
            Logger.getLogger(EditJournalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
   }
public static Journal j = new Journal();
    @FXML
    private void viderJournal(ActionEvent event) {
       fxnbchasse.clear();
        fxdescription.clear();
        fxdate.setValue(null);
        id_verifier_animal.setVisible(false);
        id_verifier_nbchasse.setVisible(false);
        id_verifier_date.setVisible(false);
        id_verifier_lieu.setVisible(false);
        
        lmla.setVisible(false);
        
    }

    @FXML
    private void imagechooser(ActionEvent event) {
        FileChooser fc = new FileChooser();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selectionner une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        String path = "C:\\xampp\\htdocs\\img";
        lmla.setText(file.getName());
        if (file != null) {
            try {
                Files.copy(file.toPath(), new File(path + "\\" + file.getName()).toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void verifier_nbchasse(KeyEvent event) {
         if (fxnbchasse.getText().isEmpty()) {
            id_verifier_nbchasse.setText("champ vide!");
            id_verifier_nbchasse.setTextFill(Color.RED);
            id_verifier_nbchasse.setVisible(true);

        } else if (!fxnbchasse.getText().matches("[0-9]*") && (fxnbchasse.getText().matches("[0]*") == true)) {
            id_verifier_nbchasse.setText("sasir un nombre!");
            id_verifier_nbchasse.setTextFill(Color.RED);
            id_verifier_nbchasse.setVisible(true);

        } else if (Integer.parseInt(fxnbchasse.getText()) > 20) {
            System.out.println(fxnbchasse.getText());
            id_verifier_nbchasse.setText("nombre maximal 20!");
            id_verifier_nbchasse.setTextFill(Color.RED);
            id_verifier_nbchasse.setVisible(true);

        } else {
            id_verifier_nbchasse.setText("Champ remplie!");
            id_verifier_nbchasse.setTextFill(Color.GREEN);
            id_verifier_nbchasse.setVisible(true);

        }
    }

    @FXML
    private void date(MouseEvent event) {
         LocalDate minDate = LocalDate.of(2020, 1, 1);
        LocalDate maxDate = LocalDate.now();
        fxdate.setDayCellFactory(d -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
            }
        });
    }

    @FXML
    private void verif_date(MouseEvent event) {
        if (fxdate.getValue() == null) {
            id_verifier_date.setText("champ vide!");
            id_verifier_date.setTextFill(Color.RED);
            id_verifier_date.setVisible(true);
        } else {
            id_verifier_date.setText("Champ remplie!");
            id_verifier_date.setTextFill(Color.GREEN);
            id_verifier_date.setVisible(true);

        }
    }

   
}
