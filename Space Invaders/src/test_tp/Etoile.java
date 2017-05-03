/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_tp;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JComponent;

/**
 *
 * @author Yann
 */
public class Etoile extends JComponent {
private int deltaX, deltaY;
private Color clr;
/**
 * Constructeur d'étoiles
 * @param sizeX taille en x
 * @param sizeY taille en y
 * @param clr COuleur de projectile
 */
    public Etoile(int sizeX, int sizeY, Color clr) {
        setSize(sizeX, sizeY);
        Random rdm = new Random();
        deltaX = rdm.nextInt(2)-3;
        deltaY = rdm.nextInt(1);
        this.clr = clr;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g); //To change body of generated methods, choose Tools | Templates.
        g.setColor(clr);
        g.fillOval(0,0,getWidth(), getHeight());
    }
    
    /**
     * Permet de déplacer l'étoile de gauche à droite
     * @param boundX
     * @param boundY 
     */
    public void bouger(int boundX, int boundY){
        
        setLocation(getX()+deltaX , getY()+deltaY);
        
//        if (getX() <= 0 || getX()+getWidth() >= boundX) {
//            deltaX = -deltaX;
//        }
//        
//        if (getY() <= 0 || getY()+getHeight()>= boundY) {
//            deltaY = -deltaY;
//        }
    }
    
    /**
     * Pour obtenir le deltax
     * @return 
     */
    public int getDeltaX(){
        return deltaX;
    }
    /**
     * Pour definir le deltaX
     * @param delta 
     */
    public void setDeltaX (int delta){
        this.deltaX= delta;
    }
}
