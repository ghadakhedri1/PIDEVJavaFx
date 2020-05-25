/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunterclub;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.pidev.services.JournalService;
import tn.pidev.utils.MyConnection;
import tn.pidev.utils.Vars;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class StatistiqueController implements Initializable {

    @FXML
    private LineChart<String, Integer> LineChart;
    private Connection con = MyConnection.getInstance().getCnx();
    @FXML
    private PieChart PieChart;
    @FXML
    private Button fxbutton;
    @FXML
    private ImageView fximage;
    @FXML
    private TextField string;
        VoiceManager vm;
    Voice v;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String req = " SELECT date,nbchasse FROM `journale` where user_id="+Vars.getCurrentUser().getId()+" and month(date) BETWEEN month(now()) and month(now()+1) order by date";
        XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
        try {
            PreparedStatement ste = (PreparedStatement) con.prepareStatement(req);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }
            LineChart.getData().add(series);
        } catch (SQLException ex) {
            Logger.getLogger(StatistiqueController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        JournalService ser = new JournalService();
        try {

            pieChartData.add(new PieChart.Data("TotalChasse", ser.nbChasseTotal(Vars.getCurrentUser().getId())));
            pieChartData.add(new PieChart.Data("Target", 100));
            PieChart.setData(pieChartData);
            //PieChart.setLabelLineLength(10);
           PieChart.setStartAngle(50);
            pieChartData.forEach(data
                    -> data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), " ", data.pieValueProperty(), " Animal"
                            )
                    )
            );
        } catch (SQLException ex) {
            Logger.getLogger(StatistiqueController.class.getName()).log(Level.SEVERE, null, ex);
        }
 
       fxbutton.setGraphic(fximage);
      
    }

    @FXML
    private void afficherscore(ActionEvent event) {
         fxbutton.setOnAction(new EventHandler<ActionEvent>() {
    @Override public void handle(ActionEvent e) {
        AnchorPane newLoadedPane = null;
        try {
            
                    newLoadedPane = FXMLLoader.load(getClass().getResource("/hunterclub/Trophy.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(AddJournalController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Scene scene = new Scene(newLoadedPane);
                Stage stage = new Stage();
                stage.setTitle("Afficher Trophé ");
                stage.setScene(scene);
                stage.show();
        
    }
});


         
       
    }

    @FXML
    private void speek(ActionEvent event) {
        String speekString = string.getText();
            System.setProperty("mbrola.base", "C:\\Users\\ghada\\Desktop\\google api\\mbrola");
            vm=VoiceManager.getInstance();
            v =vm.getVoice("mbrola_us1");
            v.allocate();
            v.speak(speekString);
    }

}
