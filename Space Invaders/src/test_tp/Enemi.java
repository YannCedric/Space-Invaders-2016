/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_tp;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.JComponent;

/**
 *
 * @author Yann
 */
public class Enemi extends JComponent{ //nmi/monstre1.gif
private int type;
private Image nmi = Toolkit.getDefaultToolkit().getImage("Enemies/2.png");
private Image nmi2 = Toolkit.getDefaultToolkit().getImage("Enemies/3.png");
private Image nmi3 = Toolkit.getDefaultToolkit().getImage("Enemies/4.png");

private Image nmiS = Toolkit.getDefaultToolkit().getImage("Enemies/2'.png");
private Image nmi2S = Toolkit.getDefaultToolkit().getImage("Enemies/3'.png");
private Image nmi3S = Toolkit.getDefaultToolkit().getImage("Enemies/4'.png");

private boolean anim = false;


private Image boom = Toolkit.getDefaultToolkit().getImage("Animations/boom.png");

private int deltaX , deltaY, maxYBound, minYBound,cmptY;
private boolean isShot = false;

    /**
     * Constructeur d'enemis
     * @param typ type d'enemi
     * @param vitesse vitesse en x
     * @param vitesseY vitesse en Y
     * @param pointY position en y
     */
    public Enemi(int typ, int vitesse, int vitesseY, int pointY) {
        
        Random rdm = new Random();
        maxYBound = pointY + 80;
        minYBound = pointY- 80;
        //deltaX = rdm.nextInt(vitesse)-3;
        deltaX = vitesse;
        deltaY = vitesseY;
        setSize(50,50);
        type = typ;
       
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        if (type == 0) { //g.drawImage(nmi, 0, 0, this);
           
            if (anim == true) {
                g.drawImage(nmiS, 0, 0, this);
            } else
            {g.drawImage(nmi, 0, 0, this);}
            
        }
        else if (type == 1) { //g.drawImage(nmi2, 0, 0, this);
            if (anim == true) {
                g.drawImage(nmi2S, 0, 0, this);
            } else
            g.drawImage(nmi2, 0, 0, this);
        }
        else if (type == 2) { //g.drawImage(nmi3, 0, 0, this);
            if (anim == true) {
                g.drawImage(nmi3S, 0, 0, this);
            } else
            g.drawImage(nmi3, 0, 0, this);
        }
        
        if(isShot == true){
            g.drawImage(boom, 0, 0, this);
        }
        
        anim =! anim;                   
    }

   /**
    * Permet de bouger les enemis dependament du type
    * @param boundX limite inferieure de la fenetre
    * @param boundY limite inferieure de la fenetre
    */
    public void bouger(int boundX, int boundY){
        if (type ==1){
            bouger1(boundX, boundY);
        }
        else if(type == 2){
             bouger2(boundX, boundY);
        }
        else if(type == 0){
             bouger3(boundX, boundY);
        }
            
    }
    
    /**
    * Permet de bouger les enemis de type1
    * @param boundX limite inferieure de la fenetre
    * @param boundY limite inferieure de la fenetre
    */
    private void bouger1(int boundX, int boundY){
        
        setLocation(getX()+deltaX , getY()+deltaY);
        
    }
    
    /**
    * Permet de bouger les enemis de type2
    * @param boundX limite inferieure de la fenetre
    * @param boundY limite inferieure de la fenetre
    */
    private void bouger2(int boundX, int boundY){
        setLocation(getX()+deltaX , getY()+deltaY);
        
        if ( getX()+ getWidth() >= boundX) {
            deltaX = -deltaX;
        }
        if (getY() <= minYBound || getY()+getHeight()>= maxYBound) {
            deltaY = -deltaY;
        }
        
        
        
    }
    
    /**
    * Permet de bouger les enemis de type3
    * @param boundX limite inferieure de la fenetre
    * @param boundY limite inferieure de la fenetre
    */
    public void bouger3(int boundX, int boundY){
        setLocation(getX()+deltaX , getY()+deltaY);
        if ( getX()+getWidth() >= boundX) {
            deltaX = -deltaX;
        }
        
        if (getY() <= 0 || getY()+getHeight()>= boundY) {
            deltaY = -deltaY;
        }
    }
    

    /**
     * Definit un bouléen si le vaisseau est touché
     * @param tiré 
     */
    public void setisShot(boolean tiré){
        this.isShot = tiré;
    }
    
    /**
     * Retourne si le vausseau est touché
     * @return 
     */
    public boolean getisShot(){
        return this.isShot;
    }
}
