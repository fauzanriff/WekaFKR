/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekafkr;

import wekafkr.controller.Menu;

/**
 *
 * @author fauzanrifqy
 */
public class WekaFKR {

    /**
     * @param args the command line arguments
     */
    
    private static Menu menu = new Menu();
    
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        while(!menu.stopped()){
            menu.askSelection();
            menu.goToSelection();
        }
    }
}
