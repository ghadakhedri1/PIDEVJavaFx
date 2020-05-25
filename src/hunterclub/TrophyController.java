/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunterclub;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import tn.pidev.services.JournalService;
import tn.pidev.utils.Vars;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
/**
 * FXML Controller class
 *
 * @author ghada
 */
public class TrophyController implements Initializable {

    @FXML
    private Label idscore;
    @FXML
    private Button fxbutton;
    @FXML
    private Label idtotalchasse;
    @FXML
    private Label labelnbchasse;
    @FXML
    private Label labelscore;
    @FXML
    private ImageView fximage;
    private MediaPlayer mediaplayer;
    @FXML
    private Label idlabel;
    @FXML
    private Label labelLavel;
    @FXML
    private Label labelname;
    @FXML
    private Label idname;
    @FXML
    private Button btn_stop;
    @FXML
    private Button btn_pause;
    @FXML
    private Button btn_play;
    @FXML
    private ImageView playimg;
    @FXML
    private ImageView pauseimg;
    @FXML
    private ImageView stopimg;

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn_play.setGraphic(playimg);
        btn_pause.setGraphic(pauseimg);
        btn_stop.setGraphic(stopimg);
          btn_play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               mediaplayer.play();
            }
        });
            btn_pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               mediaplayer.pause();
            }
        });
            btn_stop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               mediaplayer.stop();
            }
        });
        
         Media media = new Media("file:///C:/songs/sand.mp3");
      
        mediaplayer = new MediaPlayer(media);
        //mediaplayer.setAutoPlay(true);
        
        
    }    

    @FXML
    private void afficherscore(ActionEvent event) {
        
         fxbutton.setOnAction(new EventHandler<ActionEvent>() {
    @Override public void handle(ActionEvent e) {
        try {
            
            labelscore.setText("Score:");
            labelnbchasse.setText("Total Chasse:");
            labelLavel.setText("Niveau:");
            labelname.setText("UserName:");
            JournalService ser = new JournalService();
            idscore.setText(ser.CalculScore1(Vars.getCurrentUser().getId()));
            idname.setText(Vars.getCurrentUser().getUsername());
            if(Integer.parseInt(ser.CalculScore1(Vars.getCurrentUser().getId()).toString())>0 && Integer.parseInt(ser.CalculScore1(Vars.getCurrentUser().getId()).toString())<=5)
            {
                idlabel.setText("Initiation");
            }
            else if (Integer.parseInt(ser.CalculScore1(Vars.getCurrentUser().getId()).toString())>5 && Integer.parseInt(ser.CalculScore1(Vars.getCurrentUser().getId()).toString())<=15)
            {
                idlabel.setText("Débutant");
            } 
             else if (Integer.parseInt(ser.CalculScore1(Vars.getCurrentUser().getId()).toString())>15 && Integer.parseInt(ser.CalculScore1(Vars.getCurrentUser().getId()).toString())<=25)
            {
                idlabel.setText("Intérmediaire");
            } 
             else 
            {
                idlabel.setText("Avancé");
            } 
            idtotalchasse.setText(ser.nbChasseTotal1(Vars.getCurrentUser().getId()));
        } catch (SQLException ex) {
            Logger.getLogger(StatistiqueController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
    }
});


         
       
    }

    }
    

