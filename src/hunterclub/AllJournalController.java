/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunterclub;

import static hunterclub.FXMLDocumentController.Bareme_modif;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tn.pidev.entities.Animal;
import tn.pidev.entities.Journal;
import tn.pidev.entities.Lieu;
import tn.pidev.entities.user;
import tn.pidev.services.JournalService;
import tn.pidev.utils.MyConnection;
import tn.pidev.utils.Vars;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class AllJournalController implements Initializable {

    @FXML
    private TableColumn<Journal, String> coluser;
    @FXML
    private TableColumn<Journal, String> colanimal;
    @FXML
    private TableColumn<Journal, String> collieu;
    @FXML
    private TableColumn<Journal, String> coldate;
    @FXML
    private TableColumn<Journal, Integer> colnbchasse;
    @FXML
    private TableView<Journal> fxafficher;
    @FXML
    private TextField searchfield;
    @FXML
    private DatePicker datepicker;
    @FXML
    private Button exporttoXL;
    @FXML
    private Button fxmail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficherjournal();
        datepicker.getEditor().addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {

            if (event.getCode().equals(KeyCode.UP)) {

                datepicker.setValue(datepicker.valueProperty().get().plusDays(1));
                event.consume();

            } else if (event.getCode().equals(KeyCode.DOWN)) {

                datepicker.setValue(datepicker.valueProperty().get().minusDays(1));
                event.consume();

            }

        });
    }

    @FXML
    private void afficherjournal() {
        try {
            JournalService ser = new JournalService();
            List<Journal> list = ser.readAll();
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
            coluser.setCellValueFactory(data -> {
                Journal order = data.getValue();
                user person = order.getUser_id();
                String age = person.getUsername();
                return new ReadOnlyStringWrapper(age);
            });
            colnbchasse.setCellValueFactory(new PropertyValueFactory<>("nbChasse"));
            coldate.setCellValueFactory(new PropertyValueFactory<>("date"));

            fxafficher.setItems(oList);
        } catch (SQLException ex) {
            Logger.getLogger(AllJournalController.class.getName()).log(Level.SEVERE, null, ex);
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
    JournalService ser = new JournalService();
    public static Journal journal = new Journal();
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet res;
    private Connection con = MyConnection.getInstance().getCnx();

    @FXML
    private void ChercherDate(ActionEvent event) {
        try {
            JournalService ser = new JournalService();
            java.util.Date date = java.util.Date.from(datepicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            List<Journal> list = ser.readByDate(sqlDate);

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
            coluser.setCellValueFactory(data -> {
                Journal order = data.getValue();
                user person = order.getUser_id();
                String age = person.getUsername();
                return new ReadOnlyStringWrapper(age);
            });
            colnbchasse.setCellValueFactory(new PropertyValueFactory<>("nbChasse"));
            coldate.setCellValueFactory(new PropertyValueFactory<>("date"));

            fxafficher.setItems(oList);
        } catch (SQLException ex) {
            Logger.getLogger(AllJournalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void Mailing(MouseEvent event) throws Exception {
        fxmail.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    JournalService ser = new JournalService();
                    Journal ls = new Journal();
                    ls = fxafficher.getSelectionModel().getSelectedItem();
                    ser.findUSerById_user(ls.getUser_id().getId());
                    System.out.println(ls.getUser_id().getEmail());
                    JavaMailUtilsController mail = new JavaMailUtilsController();
                    mail.sendMail(ser.findiduserbyiduserInterfaceMail(ls.getUser_id().getId()));
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("information Dialog");
                alert.setHeaderText(null);
                
                alert.setContentText("Un mail est envoyé à l'utilisateur: "+ ls.getUser_id().getUsername()+" contenant son score !!");
                alert.showAndWait();
                    
                    
                    System.err.println(ser.findiduserbyiduserInterfaceMail(ls.getUser_id().getId()));
                } catch (SQLException ex) {
                    Logger.getLogger(AllJournalController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(AllJournalController.class.getName()).log(Level.SEVERE, null, ex);
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

    @FXML
    private void date(MouseEvent event) {
        LocalDate minDate = LocalDate.of(2020, 1, 1);
        LocalDate maxDate = LocalDate.now();
        datepicker.setDayCellFactory(d -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
            }
        });
    }

    @FXML
    private void excel(ActionEvent event) {
        exporttoXL.setOnAction(e -> {
            try {
                java.util.Date date = java.util.Date.from(datepicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                String query = "select * from journale where date = "+"'"+sqlDate+"'";

                pst = con.prepareStatement(query);
                res = pst.executeQuery();

                XSSFWorkbook bk = new XSSFWorkbook();
                XSSFSheet sheet = bk.createSheet("Daily journal");
                XSSFRow header = sheet.createRow(0);
                header.createCell(0).setCellValue("username ");
                header.createCell(1).setCellValue("Lieu ");
                header.createCell(2).setCellValue("Animal ");
                header.createCell(3).setCellValue("Nombre de Chasse ");
                header.createCell(4).setCellValue("Date Journal");
                int index = 1;
                   
                while (res.next()) {
                     XSSFRow row = sheet.createRow(index);
                    user user = new user();
                    String requete = "select username from user where id =" + res.getInt("user_id");
                    PreparedStatement pre = con.prepareStatement(requete);
                    ResultSet restt = pre.executeQuery();
                    while (restt.next()) {
                       user.setUsername(restt.getString("username"));
                       row.createCell(0).setCellValue(restt.getString("username"));
                       user.setId(res.getInt("user_id"));
                    }
                    String req = "select a.nom from animal a where a.id =" + res.getInt("animal_id");
                    PreparedStatement pst = con.prepareStatement(req);
                    ResultSet result = pst.executeQuery();
                    String requet = "select l.nom from lieu l where l.id =" + res.getInt("lieu_id");
                    PreparedStatement pstt = con.prepareStatement(requet);
                    ResultSet rest = pstt.executeQuery();
                    while (rest.next()) {
                        Lieu lieu = new Lieu();
                        lieu.setId(res.getInt("lieu_id"));
                        row.createCell(1).setCellValue(rest.getString("nom"));
                    }
                    
                    while (result.next()) {
                        Animal animal = new Animal();
                        animal.setId(res.getInt("animal_id"));  
                        row.createCell(2).setCellValue(result.getString("nom"));
                    }
      
                   
                    row.createCell(3).setCellValue(res.getString("nbchasse"));
                    row.createCell(4).setCellValue(res.getString("date"));
                    index++;
                }
                
                FileOutputStream fileOut = new FileOutputStream("DailyJournal.xlsx");
                bk.write(fileOut);
                fileOut.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("information Dialog");
                alert.setHeaderText(null);
                
                alert.setContentText("les journaux de la date "+ sqlDate+" sont  telechargés!!");
                alert.showAndWait();
            } catch (SQLException | FileNotFoundException ex) {
                Logger.getLogger(AllJournalController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AllJournalController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        );
       if (datepicker.getValue()==null) {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("Aucune date est saisie  ");
            alert.setContentText("SVP entrez une date dans le datepicker.");
            alert.showAndWait();
        }  
    }

}
