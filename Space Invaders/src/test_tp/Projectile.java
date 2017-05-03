/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_tp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.JComponent;

/**
 *
 * @author Yann
 */
public class Projectile extends JComponent{
private Color coul;
private int deltax , deltay;
private boolean isMien, dopeBullet;
private Image bullet = Toolkit.getDefaultToolkit().getImage("Animations/bullet (1).png");
    
    /**
     * Constructeur de projectile
     * @param sizeX taille en x
     * @param sizeY taille en y
     * @param deltaX deplacement en x
     * @param deltaY deplacement en y
     * @param clr couleur du projectile
     * @param mine booleen pour definir si le projectile est au joueur ou non
     */
    public Projectile(int sizeX ,int sizeY, int deltaX,  int deltaY , Color clr , boolean mine) {
        setSize(sizeX, sizeY);
        Random rdm = new Random();
        deltax = deltaX ;
        deltay = deltaY ;
        if ( mine == false) {
          deltax = -deltax; 
        }
        coul = clr;
        isMien = mine;
    } 

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g); //To change body of generated methods, choose Tools | Templates.
        if (dopeBullet == false){
        g.setColor(coul);
        g.fillOval(0,0,getWidth()-1, getHeight()-1);}
         if (dopeBullet == true){
            g.drawImage(bullet, 0, 0, this);
        }
    }
    
    /**
     * Methode pour deplacer le projectile
     * @param boundX limite de la fenetre en x
     * @param boundY limite de la fenetre en x
     */
    public void bouger(int boundX, int boundY){
        
        setLocation(getX()+deltax , getY()+deltay);
        
//        if (getX() <= 0 || getX()+getWidth() >= boundX) {
//            deltax = -deltax;
//        }
//        
//        if (getY() <= 0 || getY()+getHeight()>= boundY) {
//            deltax = -deltax;
//        }
    } 
    
    /**
     * Rend le projectile cool
     */
    public void setCoolBullet(){
        this.dopeBullet = true;
    }
    
    /**
     * Retourne true si le projectile aparient au joueur
     * @return 
     */
    public boolean getIsMine(){
        return isMien;
    }
}
