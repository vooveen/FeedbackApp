/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feedback;
import interfaces.accueil;
import javax.swing.JOptionPane;

/**
 *
 * @author vooveen
 */
public class Feedback {
    public static void main(String[] args) /*throws IOException*/ {
        // TODO code application logic here
        /*String macc = "CC-B8-A8-FB-88-98";
        if(functions.getdata().equals(macc)){
            accueil main = new accueil();
            main.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Vous ne pouvez pas lancer l'application", "Erreur", JOptionPane.ERROR_MESSAGE);
        }*/
        accueil main = new accueil();
        main.setVisible(true);
            
    }
}
