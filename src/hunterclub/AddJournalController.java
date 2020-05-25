/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunterclub;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.converter.DefaultStringConverter;
import javax.imageio.ImageIO;
import tn.pidev.entities.Journal;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.table.TableFilter;
import sun.util.calendar.CalendarUtils;
import tn.pidev.entities.Animal;
import tn.pidev.entities.Evenement;
import tn.pidev.entities.Lieu;
import tn.pidev.entities.TypeEvents;
import tn.pidev.entities.user;
import tn.pidev.services.JournalService;
import tn.pidev.services.UserService;
import tn.pidev.utils.MyConnection;
import tn.pidev.utils.Vars;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class AddJournalController implements Initializable {

    @FXML
    private AnchorPane content;
    @FXML
    private ComboBox<String> fxanimal;
    @FXML
    private TextField fxnbchasse;
    @FXML
    private ComboBox<String> fxlieu;
    @FXML
    private DatePicker fxdate;
    @FXML
    private TextArea fxdescription;
    private TableColumn<Journal, Integer> fxid;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet res;
    private Connection con = MyConnection.getInstance().getCnx();
    ObservableList<Journal> obl = FXCollections.observableArrayList();
    @FXML
    private TableView<Journal> fxafficher;
    @FXML
    private TableColumn<Journal, String> colanimal;

    @FXML
    private TableColumn<Journal, String> collieu;
    @FXML
    private TableColumn<Journal, String> coldate;
    @FXML
    private TableColumn<Journal, String> coldesc;
    final ObservableList options = FXCollections.observableArrayList();
    @FXML
    private Button btnsupprimer;
    @FXML
    private Label labelsupp;
    @FXML
    private Button btnupdate;
    @FXML
    private ImageView updateimg;
    @FXML
    private Label id_verifier_animal;
    @FXML
    private Label id_verifier_nbchasse;
    @FXML
    private Label id_verifier_lieu;
    @FXML
    private TableColumn<Journal, Integer> colnbchasse;
    @FXML
    private ImageView deleteimage;
    @FXML
    private TextField searchfield;
    @FXML
    private Label id_verifier_date;
    @FXML
    private ImageView trierimage;
    @FXML
    private Button btn_tri;
    @FXML
    private Label modifylabel;
    @FXML
    private Button image;
    @FXML
    private Label lmla;
    @FXML
    private ComboBox<String> typeevent;
    @FXML
    private Label idcombo;
    @FXML
    private Button btn_details;
    @FXML
    private Label trierLabel;
    @FXML
    private Button cmItem2;
    @FXML
    private Label fxcount;

    @FXML
    private ProgressBar progressBar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            btnupdate.setGraphic(updateimg);
            btnsupprimer.setGraphic(deleteimage);
            btn_tri.setGraphic(trierimage);
            JournalService ser = new JournalService();

            progressBar.setProgress(0.15);

            afficherjournal();

            fillcombo();
            fillcomboAnimal();
            fillcomboLieu();
        } catch (SQLException ex) {
            Logger.getLogger(AddJournalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    ObservableList combo = FXCollections.observableArrayList();

    public void fillcombo() throws SQLException {

        PreparedStatement pst;
        String query = "SELECT e.* ,te.type as nom from evenement e join type_events te where te.id=e.Type";
        pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            user user = new user();
            TypeEvents events = new TypeEvents();
            user.setId(rs.getInt("user_id"));
            events.setId(rs.getInt("type"));
            events.setType(rs.getString("nom"));
            Evenement e = new Evenement(rs.getInt("id"), user, events);
            combo.add(e.getType_events().getType());
            typeevent.setItems(combo);
            typeevent.setPromptText("---Selectionner Type Evenement---");
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
            fxanimal.setPromptText("---Selectionner Animal---");
        }

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
            fxlieu.setPromptText("---Selectionner Lieu---");
        }
    }

    @FXML
    private void AjouterJournal(ActionEvent event) throws SQLException {

        JournalService ser = new JournalService();
        Journal j = new Journal();
        if (((fxnbchasse.getText().isEmpty() == false) && (fxnbchasse.getText().matches("[0-9]*") == true)
                && (fxnbchasse.getText().matches("[0]*") == false)
                && (typeevent.getSelectionModel().isEmpty() == false) && (fxanimal.getSelectionModel().isEmpty() == false)
                && (fxlieu.getSelectionModel().isEmpty() == false))) {

            j.setNbChasse(parseInt(fxnbchasse.getText()));
            j.setDescription(fxdescription.getText());
            UserService Us = new UserService();

            j.setUser_id(Vars.getCurrentUser());

            String req = "SELECT e.id ,te.type from evenement e join type_events te where te.id=e.Type and te.type = " + "'" + typeevent.getSelectionModel().getSelectedItem() + "'" + ";";
            pst = con.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int d = rs.getInt("id");

                j.setEvenement(new Evenement(d));
            }
            String reqanimal = "SELECT a.id ,a.nom from animal a where a.nom =  " + "'" + fxanimal.getSelectionModel().getSelectedItem() + "'" + ";";
            pst = con.prepareStatement(reqanimal);

            ResultSet rs1 = pst.executeQuery();
            while (rs1.next()) {

                int dd = rs1.getInt("id");
                j.setAnimal(new Animal(dd));
            }
            String reqlieu = "SELECT l.id ,l.nom from lieu l where l.nom =  " + "'" + fxlieu.getSelectionModel().getSelectedItem() + "'" + ";";
            pst = con.prepareStatement(reqlieu);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                int d1 = result.getInt("id");
                j.setLieu(new Lieu(d1));
            }
            j.setImage(lmla.getText());
            j.setId_chasseur(Vars.getCurrentUser().getId());
            java.util.Date date = java.util.Date.from(fxdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            j.setDate(sqlDate);

            try {
                ser.ajouter(j);

                progressBar = new ProgressBar(0.25F);
                progressBar.setProgress(ser.nbJournalParUser(Vars.getCurrentUser().getId()));

                fxcount.setText(Integer.toString(ser.nbJournalParUser(Vars.getCurrentUser().getId())));
                System.out.println("Journal Ajoutée");
                Notifications notificationBuilder = Notifications.create().title("Journal Ajoutée").text("avec succés").position(Pos.TOP_RIGHT).hideAfter(Duration.seconds(5));
                notificationBuilder.show();
                fxnbchasse.clear();
                fxdescription.clear();
                fxdate.setValue(null);
                id_verifier_animal.setVisible(false);
                id_verifier_nbchasse.setVisible(false);
                id_verifier_date.setVisible(false);
                id_verifier_lieu.setVisible(false);
                idcombo.setVisible(false);
                lmla.setVisible(false);

                afficherjournal();
            } catch (SQLException ex) {
                Logger.getLogger(AddJournalController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void afficherjournal(ActionEvent event) throws SQLException {
        JournalService ser = new JournalService();
        List<Journal> list = ser.getAllParUserID(Vars.getCurrentUser().getId());
        ObservableList<Journal> oList = FXCollections.observableArrayList(list);
        colanimal.setCellValueFactory(data -> {
            Journal order = data.getValue();
            Animal person = order.getAnimal();
            String age = person.getNom_animal();
            return new ReadOnlyStringWrapper(age);
        });
        collieu.setCellValueFactory(data -> {
            Journal order = data.getValue();
            Lieu person = order.getLieu();
            String age = person.getNom_lieu();
            return new ReadOnlyStringWrapper(age);
        });
        colnbchasse.setCellValueFactory(new PropertyValueFactory<>("nbChasse"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        coldesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        fxafficher.setItems(oList);

    }

    private void afficherjournal() throws SQLException {
        JournalService ser = new JournalService();
        List<Journal> list = ser.getAllParUserID(Vars.getCurrentUser().getId());
        ObservableList<Journal> oList = FXCollections.observableArrayList(list);
        colanimal.setCellValueFactory(data -> {
            Journal order = data.getValue();
            Animal person = order.getAnimal();
            String age = person.getNom_animal();
            return new ReadOnlyStringWrapper(age);
        });
        collieu.setCellValueFactory(data -> {
            Journal order = data.getValue();
            Lieu person = order.getLieu();
            String age = person.getNom_lieu();
            return new ReadOnlyStringWrapper(age);
        });
        colnbchasse.setCellValueFactory(new PropertyValueFactory<>("nbChasse"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        coldesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        fxafficher.setItems(oList);
        fxcount.setText(Integer.toString(ser.nbJournalParUser(Vars.getCurrentUser().getId())));
    }

    @FXML
    private void supprimerJournal(ActionEvent event) {
        btnsupprimer.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e) {
                
                labelsupp.setText("Supprimer!");
                ObservableList<Journal> selectedRows, Journal;
                Journal = fxafficher.getItems();
                selectedRows = fxafficher.getSelectionModel().getSelectedItems();
                for (Journal j : selectedRows) {
                    Journal.remove(fxid);
                    JournalService ser = new JournalService();
                    try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Suppression");
                alert.setHeaderText("Suppression de la ligne  selectionée ");
                alert.setContentText("voulez-vous vraiment supprimer cette ligne du table.");
                alert.showAndWait();
                        ser.delete(j);

                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        obl.clear();
                        afficherjournal(event);
                        Notifications notificationBuilder = Notifications.create()
                                .title("stage supprimé")
                                .text("BYE BYE")
                                .hideAfter(Duration.seconds(5))
                                .position(Pos.TOP_RIGHT);
                        notificationBuilder.show();

                        notificationBuilder.show();
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

        });
        if (fxafficher.getSelectionModel().getSelectedIndex() < 0) {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("Aucune ligne est selectionée ");
            alert.setContentText("SVP sélectionner une ligne du table.");
            alert.showAndWait();
        }
    }
    public static Journal Journal_modif = new Journal();

    @FXML
    private void updateJournal(ActionEvent event) {
        btnupdate.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                modifylabel.setText("Update!");
                Journal_modif = fxafficher.getSelectionModel().getSelectedItem();
                AnchorPane newLoadedPane = null;
                try {
                    newLoadedPane = FXMLLoader.load(getClass().getResource("/hunterclub/EditJournal.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(AddJournalController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    obl.clear();
                    afficherjournal();
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }

                Scene scene = new Scene(newLoadedPane);
                Stage stage = new Stage();
                stage.setTitle("Modifier Journal");
                stage.setScene(scene);
                stage.show();

            }

        });
        if (fxafficher.getSelectionModel().getSelectedIndex() < 0) {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("Aucune ligne est selectionée ");
            alert.setContentText("SVP sélectionner une ligne du table.");
            alert.showAndWait();
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
    private void deleteJournal(ActionEvent event) {
        JournalService ser = new JournalService();
        ObservableList<Journal> selectedRows, Journal;
        Journal = fxafficher.getItems();
        selectedRows = fxafficher.getSelectionModel().getSelectedItems();
        for (Journal j : selectedRows) {
            Journal.remove(fxid);

            try {
                ser.delete(j);
                fxcount.setText(Integer.toString(ser.nbJournalParUser(Vars.getCurrentUser().getId())));
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                obl.clear();
                afficherjournal(event);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void RechercherBareme(ActionEvent event) {
        ObservableList data = fxafficher.getItems();
        searchfield.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                fxafficher.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<Journal> subentries = FXCollections.observableArrayList();

            long count = fxafficher.getColumns().stream().count();
            for (int i = 0; i < fxafficher.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + fxafficher.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(fxafficher.getItems().get(i));
                        break;
                    }
                }
            }
            fxafficher.setItems(subentries);
        });

    }

    @FXML
    private void TrierJournal(ActionEvent event) {
        btn_tri.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                trierLabel.setText("Trier!");
                try {
                    JournalService ser = new JournalService();
                    List<Journal> list = ser.readTrierParUser(Vars.getCurrentUser().getId());
                    ObservableList<Journal> oList = FXCollections.observableArrayList(list);
                    colanimal.setCellValueFactory(data -> {
                        Journal order = data.getValue();
                        Animal person = order.getAnimal();
                        String age = person.getNom_animal();
                        return new ReadOnlyStringWrapper(age);
                    });
                    collieu.setCellValueFactory(data -> {
                        Journal order = data.getValue();
                        Lieu person = order.getLieu();
                        String age = person.getNom_lieu();
                        return new ReadOnlyStringWrapper(age);
                    });
                    colnbchasse.setCellValueFactory(new PropertyValueFactory<>("nbChasse"));
                    coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
                    coldesc.setCellValueFactory(new PropertyValueFactory<>("description"));
                    fxafficher.setItems(oList);
                } catch (SQLException ex) {
                    Logger.getLogger(AddJournalController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    @FXML
    private void imagechooser(ActionEvent event) {
        FileChooser fc = new FileChooser();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selectionner une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        String path = "C:\\xampp\\htdocs\\PIDEV\\web\\uploads";
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
    private void verif_combo(ActionEvent event) {
        if (((typeevent.getSelectionModel().isEmpty()))) {
            idcombo.setText("champ vide!");
            idcombo.setTextFill(Color.RED);
            idcombo.setVisible(true);
        } else {
            idcombo.setText("Champ Choisie!");
            idcombo.setTextFill(Color.GREEN);
            idcombo.setVisible(true);
        }
    }

    @FXML
    private void vider(ActionEvent event) {
        fxnbchasse.clear();
        fxdescription.clear();
        fxdate.setValue(null);
        id_verifier_animal.setVisible(false);
        id_verifier_nbchasse.setVisible(false);
        id_verifier_date.setVisible(false);
        id_verifier_lieu.setVisible(false);
        idcombo.setVisible(false);
        lmla.setVisible(false);

    }

    public static Journal j = new Journal();

    @FXML
    private void details(ActionEvent event) {

        btn_details.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                j = fxafficher.getSelectionModel().getSelectedItem();

                AnchorPane newLoadedPane = null;
                try {
                    newLoadedPane = FXMLLoader.load(getClass().getResource("/hunterclub/image.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(AddJournalController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    obl.clear();
                    afficherjournal();
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Scene scene = new Scene(newLoadedPane);
                Stage stage = new Stage();
                stage.setTitle("Image ");
                stage.setScene(scene);
                stage.show();

            }
        });
        if (fxafficher.getSelectionModel().getSelectedIndex() < 0) ;
        {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);

            alert.setTitle("No Selection");
            alert.setHeaderText("Aucune ligne est selectionée ");
            alert.setContentText("SVP sélectionner une ligne du table.");

            alert.showAndWait();
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
    private void verifier_date(InputMethodEvent event) {
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

    @FXML
    private void verif_animal(ActionEvent event) {
        if (((fxanimal.getSelectionModel().isEmpty()))) {
            id_verifier_animal.setText("champ vide!");
            id_verifier_animal.setTextFill(Color.RED);
            id_verifier_animal.setVisible(true);
        } else {
            id_verifier_animal.setText("Champ Choisie!");
            id_verifier_animal.setTextFill(Color.GREEN);
            id_verifier_animal.setVisible(true);
        }
    }

    @FXML
    private void verif_lieu(ActionEvent event) {
        if (((fxlieu.getSelectionModel().isEmpty()))) {
            id_verifier_lieu.setText("champ vide!");
            id_verifier_lieu.setTextFill(Color.RED);
            id_verifier_lieu.setVisible(true);
        } else {
            id_verifier_lieu.setText("Champ Choisie!");
            id_verifier_lieu.setTextFill(Color.GREEN);
            id_verifier_lieu.setVisible(true);
        }
    }

    @FXML
    private void print(ActionEvent event) {
        Printer printer = Printer.getDefaultPrinter();
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(fxafficher);
            if (success) {
                job.endJob();
            }
        }
    }

    @FXML
    private void countJournal(MouseEvent event) {
    }
}
