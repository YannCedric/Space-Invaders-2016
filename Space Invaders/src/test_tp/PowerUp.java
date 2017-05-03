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
public class PowerUp extends JComponent{

private Image pUp1 = Toolkit.getDefaultToolkit().getImage("PUp/up1.png");
private Image pUp1_ = Toolkit.getDefaultToolkit().getImage("PUp/up1+.png");
private Image pUp2 = Toolkit.getDefaultToolkit().getImage("PUp/up2.png");
private Image pUp2_ = Toolkit.getDefaultToolkit().getImage("PUp/up2+.png");
private Image pUp3 = Toolkit.getDefaultToolkit().getImage("PUp/up3.png");
private Image pUp3_ = Toolkit.getDefaultToolkit().getImage("PUp/up3+.png");

int typePowerUP ,cmpt=0, deltaX, deltaY=0;
private boolean animation = true;

/**
 * Constructeur de Bonis
 * @param type type de boni
 * @param dX deplacement en x
 * @param dY deplacement en y
 */
    public PowerUp(int type, int dX, int dY) {
        typePowerUP = type+1;
        deltaX = dX;
        
        setSize(40,40);
        
    }
    
    /**
     * Retourne le type de boni
     * @return 
     */
    public int getTypePowerUP() {
        return typePowerUP;
    }

    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        
        if (typePowerUP == 1) {
            
            if (animation == true) {
              g.drawImage(pUp1, 0, 0, this);
              
              cmpt++;
              if (cmpt==20) {
               animation = false;
               cmpt = 0;
            }
              
            } else { 
              g.drawImage(pUp1_, 0, 0, this); 
              
              cmpt++;
              if (cmpt==20) {
               animation = true;
               cmpt = 0;}  
              
            }
            
        } else if (typePowerUP == 2) {
            
            if (animation == true) {
              g.drawImage(pUp2, 0, 0, this); 
              
              cmpt++;
              if (cmpt==20) {
               animation = false;
               cmpt = 0;}  
              
              
            } else { 
              g.drawImage(pUp2_, 0, 0, this); 
              
              cmpt++;
              if (cmpt==20) {
               animation = true;
               cmpt = 0;}  
              
               }
            
        } else if (typePowerUP == 2) {
            
            if (animation == true) {
              g.drawImage(pUp3, 0, 0, this); 
              
              cmpt++;
              if (cmpt==20) {
               animation = false;
               cmpt = 0;}  
              
            } else { 
              g.drawImage(pUp3_, 0, 0, this); 
              
              cmpt++;
              if (cmpt==20) {
               animation = true;
               cmpt = 0;}  
               }
        }
    }
    
    /**
     * Methode pour deplacer le boni
     */
    public void bouger(){
        setLocation(getX()+deltaX , getY()+deltaY);
        
        
    }
    
}
