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
public class Vaisseau extends JComponent{
private Image vso = Toolkit.getDefaultToolkit().getImage("0.png");
private Image vso2 = Toolkit.getDefaultToolkit().getImage("vsoProfil.png");
private Image vso3 = Toolkit.getDefaultToolkit().getImage("rsz_blueships1.png");

private Image mort = Toolkit.getDefaultToolkit().getImage("rsz_blueships1.png");
private int typeTir = 0;
private boolean invinsible = false;

/**
 * Constructeur du vaisseau
 */
public Vaisseau() {
        setSize(89, 29);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        g.drawImage(vso2, 0, 0, this);
    }

    /**
     * Definit le type de tir dont est equipe le vaisseau
     * @param typeTir type de tir
     */
    public void setTypeTir(int typeTir) {
        this.typeTir = typeTir;
    }

    /**
     * Retourne le type de tir equipe
     * @return type de tir
     */
    public int getTypeTir() {
        return typeTir;
    }
    
    
    
    
}
