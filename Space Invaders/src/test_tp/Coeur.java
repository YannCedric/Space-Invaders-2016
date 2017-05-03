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
public class Coeur extends JComponent{
     
private Image heart = Toolkit.getDefaultToolkit().getImage("Animations/rsz_heart.png");

/**
 * Constructeur d'un coeur
 */
    public Coeur() {
        setSize(40,36);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        g.drawImage(heart, 0, 0, this);
    }

}
