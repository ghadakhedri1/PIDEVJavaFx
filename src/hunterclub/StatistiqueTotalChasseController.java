/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunterclub;

import java.awt.Color;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import tn.pidev.entities.Animal;
import tn.pidev.entities.Journal;
import tn.pidev.entities.Lieu;
import tn.pidev.entities.user;
import tn.pidev.services.JournalService;
import tn.pidev.utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class StatistiqueTotalChasseController implements Initializable {

    @FXML
    private BarChart<String, Number> BarChart;
private Connection con = MyConnection.getInstance().getCnx();

    
    @FXML
    private BarChart<String, Number> barchart2;
    @FXML
    private AnchorPane scene;
    @FXML
    private TableView<Journal> fxafficher;
    @FXML
    private TableColumn<Journal, String> colanimal;
    @FXML
    private TableColumn<Journal, String> collieu;
    @FXML
    private TableColumn<Journal, String> coldate;
    @FXML
    private TableColumn<Journal, Integer> colnbchasse;
    @FXML
    private TableColumn<Journal, String> fxid;
    @FXML
    private TextField searchfield;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
           
                
                String req = " SELECT sum(j.nbchasse),u.username,j.*,u.* FROM journale j join user u WHERE j.user_id= u.id GROUP by j.user_id LIMIT 5";
                XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
                CategoryAxis xAxis = new CategoryAxis();
                xAxis.setLabel("Nom Utilisateur");
                NumberAxis yAxis = new NumberAxis();
                yAxis.setLabel("Total Chasse");
                BarChart.setTitle("Top 5 Chasseurs ");
                xAxis.setTickLabelFont( new Font("Arial", 15));
                xAxis.setTickMarkVisible(true);
                xAxis.setTickLabelRotation(90);
                BarChart.setLegendSide(Side.LEFT);
                
                try {
                    PreparedStatement ste = (PreparedStatement) con.prepareStatement(req);
                    ResultSet rs = ste.executeQuery();
                    while (rs.next()) {
                        dataSeries1.getData().add(new XYChart.Data<>(rs.getString(2), rs.getInt(1)));
                    }
                    BarChart.getData().add(dataSeries1);
                } catch (SQLException ex) {
                    Logger.getLogger(StatistiqueController.class.getName()).log(Level.SEVERE, null, ex);
                }
 
                String req1 = " SELECT count(*) from user";
                XYChart.Series<String, Number> serie = new XYChart.Series<String, Number>();
               CategoryAxis xAxis1 = new CategoryAxis();
                xAxis1.setLabel("Année");
                NumberAxis yAxis1 = new NumberAxis();
                yAxis1.setLabel("Nombre User");
                barchart2.setTitle("Nombre User ");
                xAxis.setTickLabelFont( new Font("Arial", 15));
                xAxis.setTickMarkVisible(true);
                xAxis.setTickLabelRotation(90);
                barchart2.setLegendSide(Side.LEFT);
                
                
            
                try {
                    PreparedStatement ste = (PreparedStatement) con.prepareStatement(req1);
                    ResultSet rs = ste.executeQuery();
                    while (rs.next()) {
                        serie.getData().add(new XYChart.Data<>("2019", 0));
                        serie.getData().add(new XYChart.Data<>("2020", rs.getInt(1)));
                        
                         
                    }
                      
        
                    barchart2.getData().add(serie);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(StatistiqueController.class.getName()).log(Level.SEVERE, null, ex);
                }
 

    } 

   
    
    

    

   
    
    }

    
  
                  
    
       
    
 
       
                
                
                
                
                
                
                
                
                
           
           
               
                
                
                
                
                
                
                
                
                
                
                
            

        
    

