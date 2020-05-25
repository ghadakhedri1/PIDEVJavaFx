/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.pidev.utils;

import tn.pidev.entities.user;

/**
 *
 * @author DELL
 */
public class Vars {
    private static user CurrentUser;

    public static user getCurrentUser() {
        return CurrentUser;
    }
    

    public static void setCurrentUser(user CurrentUser) {
        Vars.CurrentUser = CurrentUser;
    }
    
    
}
