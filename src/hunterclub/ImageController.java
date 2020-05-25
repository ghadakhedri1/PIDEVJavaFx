/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunterclub;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import tn.pidev.entities.Journal;
import tn.pidev.services.JournalService;
import tn.pidev.utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class ImageController implements Initializable {

    @FXML
    private Rectangle pc;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet res;
    private Connection con = MyConnection.getInstance().getCnx();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        BufferedImage bufferedImage = null;
        String qc1 = null;
        try {

            String req = "SELECT image  from journale where  id =" + AddJournalController.j.getId() + ";";
            pst = con.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                qc1 = rs.getString("image");

            }

            if (qc1 == null) {
                bufferedImage = ImageIO.read(new File("C:\\xampp\\htdocs\\PIDEV\\web\\uploads\\no_image.jpeg"));
            } else {
                bufferedImage = ImageIO.read(new File("C:\\xampp\\htdocs\\PIDEV\\web\\uploads\\" + qc1));
            }
//             System.out.println(bufferedImage);

        } catch (IOException ex) {
            Logger.getLogger(AddJournalController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddJournalController.class.getName()).log(Level.SEVERE, null, ex);
        }

        WritableImage imageImported = SwingFXUtils.toFXImage(bufferedImage, null);
        System.out.println(imageImported.toString());
        //   image_connect.setImage(imageImported);
        pc.setFill(new ImagePattern(imageImported));
        final ScaleTransition scaleAnimation = new ScaleTransition(Duration.seconds(2), pc);
        scaleAnimation.setCycleCount(TranslateTransition.INDEFINITE);
        scaleAnimation.setAutoReverse(true);
        scaleAnimation.setFromX(0.5);
        scaleAnimation.setToX(2);
        scaleAnimation.setFromY(0.5);
        scaleAnimation.setToY(2);
        scaleAnimation.setByX(2);
        scaleAnimation.setByY(2);
        scaleAnimation.setInterpolator(Interpolator.LINEAR);
        scaleAnimation.play();

    }

}
