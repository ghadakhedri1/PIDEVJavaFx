/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunterclub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.pidev.services.UserService;
import tn.pidev.utils.Vars;

/**
 *
 * @author ghada
 */
public class HunterClub extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        UserService Us =new UserService();
        Vars.setCurrentUser(Us.findUserbyId(3));
        
        System.out.println(Vars.getCurrentUser());
        Parent root = FXMLLoader.load(getClass().getResource("Connect.fxml")); 
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
