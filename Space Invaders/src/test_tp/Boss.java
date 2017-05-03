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
public class Boss extends JComponent{
private boolean isUp = true, isHit = false;
private int deltaX, deltaY,cmpt = 0, cmpt2 = 0, santeeBoss = 8;

private Image bossUp = Toolkit.getDefaultToolkit().getImage("BOSS/bossup.png");
private Image bossUp_ = Toolkit.getDefaultToolkit().getImage("BOSS/bossup_.png");
private Image bossDown = Toolkit.getDefaultToolkit().getImage("BOSS/bossdown.png");
private Image bossDown_ = Toolkit.getDefaultToolkit().getImage("BOSS/bossdown_.png");

private BarreSanté barre = new BarreSanté(6);

    /**
     * Constructeur du boss
     * @param deltax deplacement en x
     * @param deltay deplacement en y
     */
    public Boss(int deltax, int deltay) {
        add(barre);
        barre.setLocation(0,0);
        this.deltaX = -2;
        deltaY = deltay;
        setSize(200 , 200);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        
        barre.setSanté(santeeBoss);
        
        if ( isUp == true) {
            
            if (isHit == true) {
               g.drawImage(bossUp_, 0, 0, this); 
               cmpt2++;
            if (cmpt2==50) {
               isHit = false;
               cmpt2 = 0;
            }
            }
            
            else {
                g.drawImage(bossUp, 0, 0, this);
            cmpt++;
            if (cmpt==50) {
               isUp = false;
               cmpt = 0;
            }
            }
    
        } else if (isUp == false) {
            if (isHit == true) {
                g.drawImage(bossDown_, 0, 0, this);
                cmpt2++;
            if (cmpt2==50) {
               isHit = true;
               cmpt2 = 0;
            }
            } else {
                g.drawImage(bossDown, 0, 0, this);
            cmpt++;
            if (cmpt==50) {
               isUp = true;
               cmpt = 0;
            }
            }
            
        }
    }  
    
    /**
     * Permet de bouger le boss
     * @param boundX limite de la fenetre en x
     * @param boundY limite de la fenetre en y
     */
    public void bouger(int boundX, int boundY){
        
        setLocation(getX()+deltaX , getY() + deltaY);
        
        if ( getX()+getWidth() <= 750) {
            deltaX = 0;
        }
        
        if (getY()+20 <= 0 || getY()+getHeight()- 60>= boundY) {
            deltaY = -deltaY;
        }
    }

    /**
     * Retourne le deplacement en x
     * @return deplacement en x
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * Met une variable à true si le boss recoit un tir
     * @param isHit booléen pur savoir si le boss est touché
     */
    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    /**
     * Calcule la santé du boss après un tir et la redefinit
     * @param hit 
     */
    public void setSanteeBoss2(int hit) {
        this.santeeBoss = santeeBoss - hit;
        barre.setCompte(-1);
    }

    /**
     * Definit la santé du boss
     * @param santeeBoss 
     */
    public void setSanteeBoss(int santeeBoss) {
        this.santeeBoss = santeeBoss;
    }

    /**
     * Retourne la santé du boss
     * @return retourne les points de santé du boss
     */
    public int getSanteeBoss() {
        return santeeBoss;
    }
    
    
    
    
    
    private void miniLag(boolean isUp, int tempsLag ){
        cmpt++;
            if (cmpt==tempsLag) {
               isUp = !isUp;
               cmpt = 0;
            }
    }
}
