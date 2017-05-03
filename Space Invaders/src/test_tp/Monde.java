/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_tp;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Yann
 */
public class Monde extends JPanel {

    private final ArrayList<Etoile> listEtoiles = new ArrayList();
    private final ArrayList<Enemi> listEnem = new ArrayList();
    private final ArrayList<Enemi> listEnemEnlever = new ArrayList();
    private final ArrayList<Etoile> listAEnlever = new ArrayList();
    private final ArrayList<Projectile> listAEnleverProj = new ArrayList();
    private final ArrayList<Coeur> listCoeurs = new ArrayList();
    private final ArrayList<Coeur> listCoeursAEnlever = new ArrayList();
    private final ArrayList<Color> listCoul = new ArrayList();
    private final ArrayList<Projectile> projectiles = new ArrayList();
    private final ArrayList<PowerUp> listPwr = new ArrayList();
    private final ArrayList<PowerUp> listPwrEnlever = new ArrayList();
    private int nbViesJoeur = 3;
    private boolean isClic = false, powerUP = false, isBoss = false,
            isPowerUp = false, commencer = false, pnlAjoutee = false;
    private Boss boss;

    private Vaisseau vsau = new Vaisseau();
    private int tabTemps[], cmpt = 0, temps = 0, score = 0, keyPressed, tempsBoss = 20, cmpt2 = 0, cmpt3 = 0, cmptBoss = 5;
    private JLabel lbltime = new JLabel("Temps écoulé : " + temps),
            lblScore = new JLabel("Score : " + score),
            lblCompte = new JLabel("Compte Boss : " + cmptBoss);
    private JPanel pnlTemps = new JPanel(new BorderLayout()),
            pnlScore = new JPanel(new BorderLayout()),
            pnlCompte = new JPanel(new BorderLayout());

    private boolean threadEtoiles = true, threadVaisseau = true;

    private javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            temps = temps + 1;
            lbltime.setText("Temps écoulé : " + temps);

        }
    });

    private final Thread trdEtoiles = new Thread() {

        @Override
        public void run() {

            super.run(); //To change body of generated methods, choose Tools | Templates.
            listCoeurs.add(new Coeur());
            int i = 0;
            while (threadEtoiles) {

                if (isBoss == false) {
                    enleverEtoiles();

                    creationEtoilesAdroite();
                    bougerEtoiles();
                    invalidate();
                    repaint();
                } else {
                    fickLights();

                    invalidate();
                    repaint();


                }

                try {
                    sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Monde.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    private final Thread trdVaisseau = new Thread() {

        @Override
        public void run() {
            super.run(); //To change body of generated methods, choose Tools | Templates.
            add(vsau);
            listCoeurs.add(new Coeur());
            while (threadVaisseau) {
                lblScore.setText("Score : " + score);
                deplacerVaisseauTouches(8);
                bougerProjectiles();
                enleverProjectiles();
                if (isBoss == false) {
                    genererEnemis();
                }

                bougerEnemis();

                if (!listEnem.isEmpty()) {
                    tirEnemiRandom();
                }

                if (isPowerUp == true) {
                    cmpt2++;
                    if (cmpt2 == 150) {
                        vsau.setTypeTir(0);
                        isPowerUp = false;
                        cmpt2 = 0;
                    }
                }

                if (temps >= tempsBoss) {
                    bossMode();
                    if (pnlAjoutee == false) {
                        add(pnlCompte);
                        pnlAjoutee = true;
                    }
                }

                genererPowerUpRandom();
                if (listPwr.isEmpty() != true) {
                    bougerPowerUp();
                }

                if (listCoeurs.isEmpty() == true) {
                    joueurMort();
                }

                checkPowerUp();
                enleverPwr();

                enleverEnemisTués();
                enleverVie();
                enleverEnemisTués();

//                invalidate();
//                repaint();
                try {
                    sleep(14);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Monde.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    };

    private void fickLights() {
        if(getBackground().equals(Color.black)){
            sleeper(100);
            setBackground(Color.gray);
        }else {
            sleeper(100);
            setBackground(Color.black);
        }
    }

    public void sleeper(long timeSec){
        try {
            sleep(timeSec);
        } catch (InterruptedException e) {
            System.out.println("Timer error");
        }
    }


    public Monde() {
        score = 0;
        setSize(800, 500);
        boss = new Boss(2, 2);
        add(boss);
        boss.setLocation(800, 100);

        for (int i = 0; i < 10; i++) {
            listCoul.add(Color.white);
            listCoul.add(Color.gray);
        }

        timer.start();

        trdEtoiles.start();
        trdVaisseau.start();

        deplacerVaisseauSouris();
        vsau.setLocation(60, 250);
        creerCoeurs();

        vsau.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent me) {
                super.mouseDragged(me); //To change body of generated methods, choose Tools | Templates.
                isClic = true;
            }

            @Override
            public void mousePressed(MouseEvent me) {
                super.mousePressed(me); //To change body of generated methods, choose Tools | Templates.
                isClic = true;
            }
        });
        setBackground(Color.black);

        panelsTempsEtScore();

        setLayout(null);

    }

    /**
     * Initialise le vies
     */
    private void creerCoeurs() {
        listCoeurs.clear();
        int locationCoeur = 0;
        for (int i = 0; i < nbViesJoeur; i++) {
            Coeur coeur = new Coeur();
            listCoeurs.add(coeur);
            add(coeur);
            coeur.setLocation(locationCoeur, 10);
            locationCoeur = coeur.getLocation().x + coeur.getSize().width + 10;

        }
    }

    /**
     * Enlève les coeurs du la vue
     */
    private void enleverCoeurs() {
        for (Coeur cr : listCoeursAEnlever) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    remove(cr);
                }

            });

        }

        listCoeurs.removeAll(listCoeursAEnlever);
        listCoeursAEnlever.clear();
    }

    /**
     * Crée des etoiles a droite de l'écran aléatoirement
     */
    private void creationEtoilesAdroite() {
        Random rdm = new Random();

        int nbEtoiles = rdm.nextInt(4);
        int tailleEtoiles = rdm.nextInt(4);
        int y;
        for (int i = 0; i < nbEtoiles; i++) {
            tailleEtoiles = rdm.nextInt(4);
            Etoile str = new Etoile(tailleEtoiles, tailleEtoiles, listCoul.get(rdm.nextInt(10)));
            listEtoiles.add(str);
            y = rdm.nextInt(490);
            add(str);

            this.setComponentZOrder(str, 0);

            str.setLocation(790, y);
        }

//        for (int i = 0; i < 5; i++) {
//            Etoile str = new Etoile(3, 3, listCoul.get(rdm.nextInt(10)));
//            listEtoiles.add(str);
//            add(str);
//            str.setLocation(700, 400);
//        }
    }

    /**
     * Apelle la methode bouger chez les étoiles
     */
    private void bougerEtoiles() {
        for (Etoile etl : listEtoiles) {
            etl.bouger(getWidth(), getHeight());
            if (etl.getLocation().x < 0) {
                listAEnlever.add(etl);
            }
        }
    }

    /**
     * Enleve les étoiles qui sont sur les bords à droite
     */
    private void enleverEtoiles() {
//        for (Etoile bal : listEtoiles) {
//            for (Etoile bal2 : listEtoiles) {
//                if (bal.getBounds().intersects(bal2.getBounds())) {
//                    if (bal != bal2) {
//                        listAEnlever.add(bal);
//                        listAEnlever.add(bal2);
//                    }
//                }
//            }
//        }
        for (Etoile etl : listAEnlever) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    remove(etl);
                }

            });

        }

        listEtoiles.removeAll(listAEnlever);
        listAEnlever.clear();

    }

    /**
     * Apelle la methode bouger chez les projectiles
     */
    private void bougerProjectiles() {
        for (Projectile pr : projectiles) {
            pr.bouger(getWidth(), getHeight());
            if (pr.getLocation().x > getWidth()) {
                listAEnleverProj.add(pr);
            }
        }
    }

    /**
     * Enleve les projectiles
     */
    private void enleverProjectiles() {
        for (Projectile prj : listAEnleverProj) {

            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    remove(prj);
                }

            });

        }

        projectiles.removeAll(listAEnleverProj);
        listAEnleverProj.clear();
    }

    public void setKeyPressed(int source) {
        this.keyPressed = source;
    }

    /**
     * Gère les evenements et deplace le vaisseau
     * @param deplacement distance de deplacement avec touches 
     */
    private void deplacerVaisseauTouches(int deplacement) {
        if (keyPressed == KeyEvent.VK_UP) {
            vsau.setLocation(vsau.getLocation().x, vsau.getLocation().y - deplacement);
        } else if (keyPressed == KeyEvent.VK_DOWN) {
            vsau.setLocation(vsau.getLocation().x, vsau.getLocation().y + deplacement);
        } else if (keyPressed == KeyEvent.VK_LEFT) {
            vsau.setLocation(vsau.getLocation().x - deplacement, vsau.getLocation().y);
        } else if (keyPressed == KeyEvent.VK_RIGHT) {
            vsau.setLocation(vsau.getLocation().x + deplacement, vsau.getLocation().y);
        } else if (keyPressed == KeyEvent.VK_SPACE) {
            nouveauTirLocation(vsau.getLocation().x + vsau.getSize().width,
                    vsau.getLocation().y + vsau.getSize().height / 2, true, vsau);
        } 
//        else if (keyPressed == KeyEvent.VK_ESCAPE) {
//            System.exit(0);
//        } 
        else if (isClic == true) {
            nouveauTirLocation(vsau.getLocation().x + vsau.getSize().width,
                    vsau.getLocation().y + vsau.getSize().height / 2, true, vsau);
            isClic = false;
        }
        keyPressed = 0;

    }

    /**
     * Permet de deplacer le vaisseeau avec la souris
     */
    private void deplacerVaisseauSouris() {
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent me) {
                super.mouseMoved(me); //To change body of generated methods, choose Tools | Templates.
                vsau.setLocation(me.getPoint().x - vsau.getSize().width / 2, me.getPoint().y - vsau.getSize().height / 2);
            }
        });

    }

//    private boolean isSurLimites() {
//        if (Math.abs(vsau.getLocation().x - 0) > 2 && 2 > Math.abs(vsau.getLocation().x - 500)
//                && Math.abs(vsau.getLocation().y - 0) > 2 && 2 > Math.abs(vsau.getLocation().y - 500)) {
//            return false;
//        } else {
//            return true;
//        }
//    }
    private void bougerEnemis() {
        for (Enemi mni : listEnem) {
            mni.bouger(getWidth() + 200, getHeight() + 10);
            if (mni.getLocation().x < 0) {
                listEnemEnlever.add(mni);
            }
        }
    }

    /**
     * Enlève les enemis qui se font tuer.
     */
    private void enleverEnemisTués() {

        for (Projectile proj : projectiles) {
            for (Enemi nmi : listEnem) {
//                if (nmi.getisShot() == true) {
//                    
//                }
                if (proj.getIsMine() == true) {
                    if (proj.getBounds().intersects(nmi.getBounds())) {
                        if (nmi.getisShot() == true) {
                            listAEnleverProj.add(proj);
                            listEnemEnlever.add(nmi);
                            score = score + 10;
                       //nmi.setisShot(false);

                        }
                        nmi.setisShot(true);

                    }
                }

            }
        }
        for (Enemi nmi : listEnemEnlever) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    remove(nmi);
                }

            });

        }

        listEnem.removeAll(listEnemEnlever);
        listEnemEnlever.clear();
    }

    /**
     * Génère des enemis qui se deplacent en ligne droite
     */
    private void enemisLigneDroite() {

        Random rdm = new Random();
        int locationEnemiY = rdm.nextInt(450 - 20) + 20;
        int nbEnemis = rdm.nextInt(4 - 2) + 2;

        nbEnemis = 4;

        int typeEnemis = 1;
        int vitesseEnem = -2;

        for (int i = 0; i < nbEnemis; i++) {
            Enemi nmi = new Enemi(typeEnemis, vitesseEnem, 0, 0);
            listEnem.add(nmi);
            add(nmi);
            nmi.setLocation(980, locationEnemiY);
            locationEnemiY = nmi.getLocation().y + nmi.getSize().height + 5;
        }

    }

    /**
     * Génère des enemis qui se deplacent de haut en bas
     */
    private void enemisHautBas() {
        Random rdm = new Random();
        int locationEnemiX = rdm.nextInt(1050 - 980) + 980;
        locationEnemiX = 800;
        int locationEnemiY = rdm.nextInt(420 - 20) + 20;
        final int Ypremier = locationEnemiY;

        int nbEnemis = rdm.nextInt(5);
        nbEnemis = 3;

        int typeEnemis = 2;

        int vitesseEnem = -2;
        int vitesseEnemY = 4;
        for (int i = 0; i < nbEnemis; i++) {

            Enemi nmi = new Enemi(typeEnemis, vitesseEnem, vitesseEnemY, Ypremier);
            listEnem.add(nmi);
            add(nmi);
            nmi.setLocation(locationEnemiX, locationEnemiY);
            locationEnemiX = nmi.getLocation().x + nmi.getSize().width;
            locationEnemiY = locationEnemiY - 40;
        }

    }

    /**
     * Génère des enemis qui rebondissent sur les bordures(haut,bas)
     */
    private void enemisRebondissant() {
        Random rdm = new Random();
        int locationEnemiX = 850;
        int locationEnemiY = rdm.nextInt(420 - 50) + 50;
        //int nbEnemis = rdm.nextInt(5);
        int nbEnemis = 3;
        int typeEnemis = 0;

        int vitesseEnem = -2;
        int vitesseEnemY = 4;
        boolean bool = true;

        for (int i = 0; i < nbEnemis; i++) {

            Enemi nmi = new Enemi(typeEnemis, vitesseEnem, vitesseEnemY, 0);
            listEnem.add(nmi);
            add(nmi);
            nmi.setLocation(locationEnemiX, locationEnemiY);
            locationEnemiX = nmi.getLocation().x + nmi.getSize().width;

            if (bool == true) {
                locationEnemiY = locationEnemiY - 15;
                bool = !bool;
            } else if (bool == false) {
                locationEnemiY = locationEnemiY + 15;
                bool = !bool;
            }

        }
    }

    /**
     * Genere aléatoirement des enemis 
     */
    private void genererEnemis() {
        double d = Math.random();
        if (d < 0.508) {
        } // 50% chance of being here
        else if (d < 0.511) {
            enemisRebondissant();
        } // 2% chance of being here
        else if (d < 0.515) {
            enemisHautBas();
        } // 1% chance of being here
        else if (d < 0.520) {
            enemisLigneDroite();
        } else if (d < 0.524) {
        } else // 5% chance of being here
        {
        }
    }

    /**
     * Crée un projectile à un endroit specifique
     * @param x location du tir en x
     * @param y location du tir en y
     * @param mine booléen pour savoir si le pojectile est au joueur ou non
     * @param obj Objet qui emet le tir
     */
    private void nouveauTirLocation(int x, int y, boolean mine, Object obj) {

        if (obj instanceof Vaisseau) {
            switch (((Vaisseau) obj).getTypeTir()) {
                case 0:
                    Projectile proj;
                    proj = new Projectile(30, 13, 15, 0, Color.red, true);
                    proj.setCoolBullet();

                    proj.setLocation(x, y);
                    projectiles.add(proj);
                    add(proj);
                    break;

                case 2:
                    Projectile proj1;
                    Projectile proj2;
                    Projectile proj3;
                    proj1 = new Projectile(30, 13, 10, 3, Color.red, true);
                    proj2 = new Projectile(30, 13, 10, 0, Color.red, true);
                    proj3 = new Projectile(30, 13, 10, -3, Color.red, true);
                    proj1.setCoolBullet();
                    proj2.setCoolBullet();
                    proj3.setCoolBullet();

                    proj1.setLocation(x, y);
                    proj2.setLocation(x, y);
                    proj3.setLocation(x, y);
                    projectiles.add(proj1);
                    projectiles.add(proj2);
                    projectiles.add(proj3);
                    add(proj1);
                    add(proj2);
                    add(proj3);

                    break;
                case 1:
                    Projectile proj4;
                    proj = new Projectile(60, 60, 5, 0, Color.BLUE, true);

                    proj.setLocation(x - 2, y - 30);
                    projectiles.add(proj);
                    add(proj);
                    break;
            }
        } else {
            Projectile proj;
            if (mine == true) {
                proj = new Projectile(30, 13, 15, 0, Color.red, true);
                proj.setCoolBullet();
            } else {
                proj = new Projectile(10, 10, 4, 0, Color.RED, false);
            }

            proj.setLocation(x, y);
            projectiles.add(proj);
            add(proj);
        }

    }

    /**
     * Fait tirer un enemi a un moment aléatoire
     */
    private void tirEnemiRandom() {
        Random rdm = new Random();
        int indexRdm = rdm.nextInt(listEnem.size() - 0) + 0;

        Enemi nmiRdm = listEnem.get(indexRdm);

        double d = Math.random();
        if (d < 0.5) {
        } // 50% chance of being here
        else if (d < 0.51) { //enemisHautBas();
            nouveauTirLocation(nmiRdm.getLocation().x - nmiRdm.getSize().width,
                    nmiRdm.getLocation().y + nmiRdm.getSize().height / 2, false, nmiRdm);
        } // 2% chance of being here
        else if (d < 0.55) { //enemisLigneDroite();
        } // 1% chance of being here
        else if (d < 0.56) { //enemisRebondissant();
        }

    }

//    private enum typeTir {
//    Base, super1, super2, super3
//}
    /**
     * Enleve les vies perdues de la vue
     */
    private void enleverVie() {
        enleverCoeurs();

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

            }

        });

        for (Enemi enem : listEnem) {
            for (Projectile prj : projectiles) {

                if (prj.getIsMine() == false) {
                    if (vsau.getBounds().intersects(prj.getBounds())) {
                        if (listCoeurs.size() > 0) {
                            score = score - 10;
                            listCoeursAEnlever.add(listCoeurs.get(listCoeurs.size() - 1));
                            listAEnleverProj.add(prj);

                        }

                    }

                }
                if (vsau.getBounds().intersects(enem.getBounds())) {
                    if (listCoeurs.size() > 0) {
                        listCoeursAEnlever.add(listCoeurs.get(listCoeurs.size() - 1));
                        enem.setisShot(true);
                        listEnemEnlever.add(enem);
                        score = score - 5;
                    }

                    return;

                }

            }
        }

    }

    /**
     * Génere aléatoirement des bonis à droite de l'écran 
     */
    private void genererPowerUpRandom() {

        double d = Math.random();
        if (d < 0.5) {
        } // 50% chance of being here
        else if (d < 0.505) { //enemisHautBas();
            genererUnPowerUp();
        } // 1% chance of being here
        else if (d < 0.51) { //enemisLigneDroite();
        } // 3% chance of being here
        else if (d < 0.56) { //enemisRebondissant();
        }

    }

    /**
     * Cree une instance de Boni et l'ajoute à la liste de powerUps.
     */
    private void genererUnPowerUp() {
        Random rdm = new Random();

        PowerUp pwr = new PowerUp(rdm.nextInt(2), -2, 0);
        listPwr.add(pwr);
        add(pwr);
        pwr.setLocation(getWidth() + 20, rdm.nextInt(getHeight() - 20) + 20);
    }

    /**
     * Apelle la methode bouger chez les bonis
     */
    private void bougerPowerUp() {
        for (PowerUp pwr : listPwr) {
            pwr.bouger();
        }
    }

    /**
     * Verifie si les powerUp sont en contact avec le vaisseau
     */
    private void checkPowerUp() {

        for (PowerUp pwr : listPwr) {
            if (vsau.getBounds().intersects(pwr.getBounds())) {
                vsau.setTypeTir(pwr.getTypePowerUP());
                isPowerUp = true;
                listPwrEnlever.add(pwr);
            }
        }

    }

    /**
     * Enleve les powerups de la vue.
     */
    private void enleverPwr() {

        for (PowerUp pwr : listPwrEnlever) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    remove(pwr);
                }

            });

        }

        listPwr.removeAll(listPwrEnlever);
        listPwrEnlever.clear();
    }

    /**
     * Déplace le boss, enlève ses vies quand il se fait tirer dessus. 
     */
    private void bossMode() {

        isBoss = true;
        boss.bouger(800, 400);
        if (boss.getDeltaX() == 0) {
            if (cmpt > 30) {
                nouveauTirLocation(boss.getLocation().x - 20, boss.getLocation().y + boss.getHeight() / 2 - 50, false, boss);
                nouveauTirLocation(boss.getLocation().x, boss.getLocation().y + boss.getHeight() / 2 + 150, false, boss);
                cmpt = 0;
            } else {
                cmpt++;
            }
        }
        checkHitBoss();
    }

    /**
     * Vérifie si le boss se fait tirer dessus et lui em;àve des vies
     */
    private void checkHitBoss() {

        cmpt3++;
        if (cmpt3 == 20) {
            boss.setIsHit(false);
            cmpt3 = 0;
        }

        for (Projectile prj : projectiles) {
            if (vsau.getBounds().intersects(boss.getBounds())
                    || (prj.getBounds().intersects(boss.getBounds()) && prj.getIsMine() == true)) {
                enleverCoeurs();
                boss.setIsHit(true);
                boss.setSanteeBoss2(1);

                // System.out.println("santé : "+boss.getSanteeBoss());
                if (cmptBoss == 0) {
                    bossMort();
                }
                if (boss.getSanteeBoss() <= 0) {
                    cmptBoss--;
                    lblCompte.setText("Compte Boss : " + cmptBoss);
                    boss.setSanteeBoss(8);
                }
                listAEnleverProj.add(prj);
                score = score + 10;
            }
        }

    }

    /**
     * Rajoute à la vue un panel pour le temps et le score
     */
    private void panelsTempsEtScore() {
        pnlTemps.setSize(110, 20);
        pnlTemps.add(lbltime, BorderLayout.CENTER);
        pnlTemps.setLocation(10, getHeight() - 90);
        add(pnlTemps);

        pnlScore.setSize(90, 20);
        pnlScore.add(lblScore, BorderLayout.CENTER);
        pnlScore.setLocation(getWidth() - 450, getHeight() - 90);
        add(pnlScore);

        pnlCompte.setSize(103, 20);
        pnlCompte.add(lblCompte, BorderLayout.CENTER);
        pnlCompte.setLocation(getWidth() - 150, getHeight() - 90);

    }

    /**
     * Enlève le vaisseau de la vue, et  affiche le message de fin.
     */
    private void joueurMort() {
        threadEtoiles = false;
        threadVaisseau = false;
        remove(vsau);
        JOptionPane.showMessageDialog(null, "Vous etes Mort!");
        JOptionPane.showMessageDialog(null, "Votre Score : " + score);
        
    }

    /**
     * Affiche les messages finaux et arrete le tread de vaisseau. La methode enlève aussi le boss
     */
    private void bossMort() {
        JOptionPane.showMessageDialog(null, "BRAVO! Le boss est mort!");
        JOptionPane.showMessageDialog(null, "Votre Score : " + score);
        timer.stop();
        temps = 0;
        threadVaisseau = false;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (Projectile prj : projectiles) {
                    remove(prj);
                }
                for (PowerUp pwr : listPwr) {
                    remove(pwr);

                }
                remove(boss);
            }
        });

        isBoss = false;

        pnlTemps.setSize(215, 20);
        lbltime.setText(" Vous avez battu Sir Space Nugget !! ");
        pnlTemps.setLocation(300, 200);
        
        pnlCompte.setSize(180, 20);
        lblCompte.setText(" Fin de la partie. Allez au menu!");
        pnlCompte.setLocation(320 , 250);
    }

    /**
     * Permet au parent d'acceder au score
     * @return score du joueur
     */
    public int getScore() {
        return score;
    }
    
    
}

//SwingUtilities.invokeLater(new Runnable() {
//                @Override
//                public void run(){}
//            });
