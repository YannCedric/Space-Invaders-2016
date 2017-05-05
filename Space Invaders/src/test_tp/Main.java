
package test_tp;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Yann
 */
public class Main {
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String unLook = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
        
        try {
            UIManager.setLookAndFeel(unLook);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        
        
        JOptionPane.showMessageDialog(null, "Bienvenu dans Space Invader!");
        Fenetre fenetre = new Fenetre();
        
    }
    
}
