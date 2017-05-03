/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_tp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Yann
 */
public class Fenetre extends JFrame {
    
private final ArrayList<Integer> score = new ArrayList();

private Monde mond;

private JMenuBar mnuBar = new JMenuBar();
private JMenu mnuFichier = new JMenu("Fichier");

private JMenuItem mnuNvelPartie= new JMenuItem("Nouvelle Partie");
private JMenuItem mnuQuitter= new JMenuItem("Quitter");

private JMenu mnuWhat = new JMenu("?");
private JMenuItem mnuAide =  new JMenuItem("Aide ...");
private JMenuItem mnuTabPoint =  new JMenuItem("Tableau de Pointage...");
private JMenuItem mnuPropos =  new JMenuItem("À Propos ...");

private String tabScore="";

/**
 * Constructeur de la fenetre
 */
    public Fenetre() {
        
        
        setFocusable(true);
        setTitle("Space Invader");
        menus();
        setSize(800, 500);
        setLocation(400, 100);
        
        setBackground(Color.black);
        
        lectureClavier(); 
        mnuListeners();

        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        if (JOptionPane.showConfirmDialog(null, " Voulez-vous jouer?") == JOptionPane.OK_OPTION) {
           mond = new Monde(); 
           add(mond);
        } else 
        {JOptionPane.showMessageDialog(null, "Aurevoir!");
            System.exit(0);}
    }

    /**
     * Permet la lecture au clavier et stocke le resultat
     */
    private void lectureClavier() {

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent ke) {
                super.keyPressed(ke); //To change body of generated methods, choose Tools | Templates.
                mond.setKeyPressed(ke.getKeyCode());
            }

        });
    }
    
    /**
     * Initialise la structure des menus
     */
    private void menus(){
        setJMenuBar(mnuBar);  
        
        mnuBar.add(mnuFichier);
        mnuBar.add(mnuWhat);
        
        mnuFichier.add(mnuNvelPartie);
        mnuFichier.addSeparator();
        mnuFichier.add(mnuQuitter);
        
        mnuWhat.add(mnuAide);
        mnuWhat.add(mnuTabPoint);
        mnuWhat.addSeparator();
        mnuWhat.add(mnuPropos);
        
        
        
    }
    
    /**
     * Gère les actionListeners des menus
     */
    private void mnuListeners(){
        
        mnuNvelPartie.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
            score.add((Integer)mond.getScore());
            
            remove(mond);
            mond  = new Monde();
            
            add(mond);
            }
        });
        
        mnuQuitter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
            
        JOptionPane.showMessageDialog(null, "Merci d'avoir joué");
        System.exit(0); 
            }
        });
        
        mnuAide.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
            JOptionPane.showMessageDialog(null, " Règles du jeu : \n"
                    + "\n -Vous avez vies : 3"
                    + "\n -Le boss arrive après 40 secondes (modifiable)"
                    + "\n -Vous pouvez vous déplacer avec la souris"
                    + "\n -Touches de deplacement : "
                    + "\n           ▲ "
                    + "\n      ◄       ► "
                    + "\n           ▼ "
                    + "\n -Tir : Barre Espace ou Click Gauche"
                    + "\n -Bonus :  "
                    + "\n        Machin Bleu : Super Boule Mega Geante Bleu"
                    + "\n        Machin Jaune : Triple Tir De La Mort Qui Tue"
                    + "\n"
                    + "NB: Le score est sauvegardé apres qu'une nouvelle partie est commencée.");
            }
        });
        
        mnuTabPoint.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
            setTabScores();
            JOptionPane.showMessageDialog(null, "Scores : \n"+tabScore);
            
            }
        });
        
        mnuPropos.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
            JOptionPane.showMessageDialog(null, "Auteurs : \n"+
                                                 "Yann Cedric et Arman Adibi \n"
                                               + "Date de remise : 12 Decembre 2015.");}
        });
    }
    
    /**
     * Definit la string de scores
     */
    private void setTabScores(){
        for (Integer scor: score) {
            tabScore += "-"+(int)scor+"-"+" \n";
        }
    }
}
