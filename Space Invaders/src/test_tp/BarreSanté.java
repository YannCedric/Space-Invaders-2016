/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_tp;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComponent;


/**
 *
 * @author Yann
 */
public class BarreSanté extends JComponent{
    private Image zero = Toolkit.getDefaultToolkit().getImage("bossHealth/0.png");
    private Image un = Toolkit.getDefaultToolkit().getImage("bossHealth/1.png");
    private Image deux = Toolkit.getDefaultToolkit().getImage("bossHealth/2.png");
    private Image trois = Toolkit.getDefaultToolkit().getImage("bossHealth/3.png");
    private Image quatre = Toolkit.getDefaultToolkit().getImage("bossHealth/4.png");
    private Image cinq = Toolkit.getDefaultToolkit().getImage("bossHealth/5.png");
    private Image six = Toolkit.getDefaultToolkit().getImage("bossHealth/6.png");
    private Image sept = Toolkit.getDefaultToolkit().getImage("bossHealth/7.png");
    private Image huit = Toolkit.getDefaultToolkit().getImage("bossHealth/8.png");
    private int compte;
    
    private int santé = 8;
    /**
     * Constructeur de la barre de santé
     * @param cmpt 
     */
    public BarreSanté(int cmpt) {
        setSize(40,40);
        compte = cmpt;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
    
        switch (santé) {
        case 0:
            g.drawImage(zero, 0, 0, this);
        case 1:
            g.drawImage(un, 0, 0, this);
            break;
        case 2:
            g.drawImage(deux, 0, 0, this);
            break;
        case 3:
            g.drawImage(trois, 0, 0, this);
            break;
        case 4:
            g.drawImage(quatre, 0, 0, this);
            break;
        case 5:
            g.drawImage(cinq, 0, 0, this);
            break;
        case 6:
            g.drawImage(six, 0, 0, this);
            break;
        case 7:
            g.drawImage(sept, 0, 0, this);
            break;
        case 8:
            g.drawImage(huit, 0, 0, this);
            break;
        
    }
       
    }

    /**
     * Permet de donner une valeur à la santé du boss
     * @param santé santé du boss
     */
    public void setSanté(int santé) {
        this.santé = santé;
    }

    /**
     * Definit la valeur du compteur
     * @param cmp nombre de fois que la barre atteind zero
     */
    public void setCompte(int cmp) {
        compte = compte + cmp;
    }
    
    
    
    
}
