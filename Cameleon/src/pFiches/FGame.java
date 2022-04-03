/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pFiches;

import pTreatments.FButton;
import pTreatments.Game;
import pTreatments.Player;
import pTreatments.Position;
import pTreatments.Message;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pTreatments.Music;

/**
 *
 * @author cghali
 */
public final class FGame extends javax.swing.JDialog {

    //VARIABLES DE MUSIC
    private final Music m = new Music();
    private final String menu_select = "src/pSounds/menu-select.wav";
    private final String laugh = "src/pSounds/laugh.wav";
    private final String congrats = "src/pSounds/win.wav";
    private final String arcade = "src/pSounds/goarcade.wav";
    private final String gameover = "src/pSounds/gameover.wav";
    private final String matrix = "src/pSounds/propellerheads-spybreak.wav";
    private final String mission = "src/pSounds/mission-impossible-theme.wav";
    private final String roddyrich = "src/pSounds/roddy-ricch-the-box-official-audio.wav";
    private String selectedMusic;
    private String musicPlayed;
    private long musicPos; // position temporel de la musique
    //VARIABLE GRILLE BOUTON
    private Position premierButton = null;
    private Position secondButton = null;
    private final FButton tabBout[][] = new FButton[9][9];
    //VARIABLE DE JEU
    private Player p1;
    private Player p2;
    private final Message message = new Message();
    private Game game;
    private boolean isThereAnAI = true;
    private boolean difficulty = false;
    private boolean sighting_aid = true;
    private boolean resumedGame = false; // partie chargée ou non

    public FGame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //m.setVolume(0);
        //DECORATION
        gridRandomColor();  
        
        // this.setLocationRelativeTo(null); // Pour centrer
       // this.setLocation(0, 0);

        //On définit le layout à utiliser
        jGrid.setLayout(new GridLayout(9, 9));
        //On ajoute le bouton au panel du JDialog
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                FButton button = new FButton(i, j);
                tabBout[i][j] = button;
                jGrid.add(button);
                if ((i <= 2 || i >= 6) || (j <= 2 || j >= 6)) { // Si en dehors des boutons interdit (au milieu)
                    button.addActionListener((java.awt.event.ActionEvent evt) -> {
                        boutActionPerformed(evt); // 
                    });
                }
            }
        }
        initGame();

    }

    public void initGame() {

        try {
            if (resumedGame == false) {
                retrieveOptions();
            }
            // On récupère les informations de la fiche options
            // resumedGame=false;
        } catch (IOException ex) {
            Logger.getLogger(FGame.class.getName()).log(Level.SEVERE, null, ex);
            //Sinon création d'un jeu par défaut;
            p1 = new Player("Cyril", 1, true);
            p2 = new Player("Joshua", 2, false);
            game = new Game(p1, p2);
        }

        //On définit les textes des JLabel
        setText();
        //SI un IA est présent, le joueur humain commence
        this.isThereAnAI = game.isThereAnAI();

        if (isThereAnAI) {
            if ((p1.isAI()) && game.isCurrentPlayer(p1)) {
                game.moveIA();
                game.setCurrentPlayer(p2);
            } else if ((p2.isAI()) && game.isCurrentPlayer(p2)) {
                game.moveIA();
                game.setCurrentPlayer(p2);
            }
        }
        game.afficherGrille(tabBout);
        game.setDifficulty(difficulty);
        jAnnounceCurrentPlayer.setText("The first player is :");
        jCurrentPlayer.setText("" + game.getCurrentPlayer().getPseudo() + "(" + game.getPlayerColor() + ")");

    }

    private void adaptScreenSize() {
        //On récupère la taille de l'ecran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        setSize(width, height);
        setPreferredSize(screenSize);
        setMaximumSize(screenSize);
        jGrid.setSize((int) (0.5 * width), (int) (0.5 * height));
        jGrid.setPreferredSize(screenSize);
        //On la place en haut a gauche
        this.setLocation(0, 0);
        jGrid.revalidate();
        jGrid.repaint();
    }

    public void gridRandomColor() {
        int R = (int) (Math.random() * 256);
        int G = (int) (Math.random() * 256);
        int B = (int) (Math.random() * 256);
        Color randomColor = new Color(R, G, B);
        this.getContentPane().setBackground(Color.ORANGE);
        jGrid.setBackground(randomColor);
    }

    private void retrieveOptions() throws FileNotFoundException, IOException {
        File f = new File("src/pSaves/save_options");
        try (
                FileReader entree = new FileReader(f); BufferedReader br = new BufferedReader(entree)) {

            String separator = " ";

            //On récupère les 3 première lignes : Joueur 1, Joueur 2, Difficulté
            String sp1 = br.readLine();
            String sp2 = br.readLine();
            String sp3 = br.readLine();
            String sp4 = br.readLine();

            // On stocke les infos des joueur dans des tableau de srting
            String infos[] = sp1.split(separator);
            this.p1 = new Player(infos[0], Integer.parseInt(infos[1]), Boolean.parseBoolean(infos[2]));
            //On créé le joueur

            infos = sp2.split(separator);
            //Idem
            this.p2 = new Player(infos[0], Integer.parseInt(infos[1]), Boolean.parseBoolean(infos[2]));
            infos = sp3.split(separator);
            difficulty = Boolean.parseBoolean(infos[0]);
            infos = sp4.split(separator);
            sighting_aid = Boolean.parseBoolean(infos[0]);
            game = new Game(p1, p2);
            isThereAnAI = game.isThereAnAI();
            br.close();
            entree.close();

        } catch (Exception e) {

        }

    }

    private void boutActionPerformed(java.awt.event.ActionEvent evt) {
        FButton bout = (FButton) evt.getSource();

        //appel de méthode de traitement
        if (premierButton == null) {
            premierButton = new Position(bout.getI(), bout.getJ());
            //On évite les cas interdit
            if (game.getColor(premierButton) == 0 || game.getColor(premierButton) != game.getCurrentPlayer().getColor()) {
                premierButton = null; // Pour éviter de cliquer sur des cases vides inutilement

            } else if (premierButton != null && sighting_aid) {
                game.displayPossibleMove(tabBout, premierButton);
            }

        } else if (secondButton == null) {

            secondButton = new Position(bout.getI(), bout.getJ());
            if (game.isMovePossible(premierButton, secondButton)) {

                game.move(premierButton, secondButton);
                game.afficherGrille(tabBout);
                game.assignPlayer();
            }
            premierButton = null;
            secondButton = null;

            if (isThereAnAI && game.getCurrentPlayer().isAI()) {
                game.moveIA();
                game.assignPlayer();
                game.afficherGrille(tabBout);
            }

            setText();

        }
    }

    public void fResumeGame(File f) {

        try {
            game.resumeGame(f);
            resumedGame = true;
            jAnnounceCurrentPlayer.setText("The current player is :");
            jCurrentPlayer.setText("" + game.getCurrentPlayer().getPseudo());
            game.afficherGrille(tabBout);
        } catch (IOException ex) {
            Logger.getLogger(FGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Player getP1() {
        return p1;
    }

    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public Player getP2() {
        return p2;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }

    public void setResumedGame(boolean resumedGame) {
        this.resumedGame = resumedGame;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jGrid = new javax.swing.JPanel();
        jCurrentPlayer = new javax.swing.JLabel();
        bInterrupt = new javax.swing.JButton();
        bLeave = new javax.swing.JButton();
        jWinner = new javax.swing.JLabel();
        jAnnounceWinner = new javax.swing.JLabel();
        jAnnounceCurrentPlayer = new javax.swing.JLabel();
        bNewGame = new javax.swing.JButton();
        jScore2 = new javax.swing.JLabel();
        jAnnounceScore = new javax.swing.JLabel();
        jScore = new javax.swing.JLabel();
        bHome = new javax.swing.JButton();
        jMusic = new javax.swing.JLabel();
        bResume = new javax.swing.JButton();
        bPause = new javax.swing.JButton();
        bPlay = new javax.swing.JButton();
        jComboBoxMusic = new javax.swing.JComboBox<>();
        bScreenSize = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(java.awt.Color.darkGray);
        setIconImage(null);
        setIconImages(null);
        setMinimumSize(new java.awt.Dimension(1135, 640));
        setResizable(false);
        setSize(new java.awt.Dimension(1135, 640));

        jGrid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jGrid.setMaximumSize(new java.awt.Dimension(5000, 5000));
        jGrid.setMinimumSize(new java.awt.Dimension(200, 200));
        jGrid.setPreferredSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout jGridLayout = new javax.swing.GroupLayout(jGrid);
        jGrid.setLayout(jGridLayout);
        jGridLayout.setHorizontalGroup(
            jGridLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 507, Short.MAX_VALUE)
        );
        jGridLayout.setVerticalGroup(
            jGridLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );

        jCurrentPlayer.setBackground(new java.awt.Color(204, 204, 255));
        jCurrentPlayer.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jCurrentPlayer.setText("CurrentP");
        jCurrentPlayer.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        bInterrupt.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        bInterrupt.setText("Save Game");
        bInterrupt.setFocusable(false);
        bInterrupt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bInterrupt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bInterrupt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInterruptActionPerformed(evt);
            }
        });

        bLeave.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        bLeave.setText("Rage Quit");
        bLeave.setFocusable(false);
        bLeave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bLeave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bLeave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLeaveActionPerformed(evt);
            }
        });

        jWinner.setBackground(new java.awt.Color(204, 204, 255));
        jWinner.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jWinner.setText("Winner");

        jAnnounceWinner.setBackground(new java.awt.Color(204, 204, 255));
        jAnnounceWinner.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jAnnounceWinner.setText("The Winner is :");

        jAnnounceCurrentPlayer.setBackground(new java.awt.Color(204, 204, 255));
        jAnnounceCurrentPlayer.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jAnnounceCurrentPlayer.setText("The current player is");

        bNewGame.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        bNewGame.setText("New Game");
        bNewGame.setFocusable(false);
        bNewGame.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bNewGame.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNewGameActionPerformed(evt);
            }
        });

        jScore2.setBackground(new java.awt.Color(204, 204, 255));
        jScore2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jScore2.setText("P2 Score2");

        jAnnounceScore.setBackground(new java.awt.Color(204, 204, 255));
        jAnnounceScore.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jAnnounceScore.setText("Score : ");

        jScore.setBackground(new java.awt.Color(204, 204, 255));
        jScore.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jScore.setText("P1 Score 1");

        bHome.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        bHome.setText("Return Home");
        bHome.setFocusable(false);
        bHome.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bHome.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHomeActionPerformed(evt);
            }
        });

        jMusic.setBackground(new java.awt.Color(204, 204, 255));
        jMusic.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jMusic.setText("Music:");

        bResume.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        bResume.setText("Resume");
        bResume.setFocusable(false);
        bResume.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bResume.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResumeActionPerformed(evt);
            }
        });

        bPause.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        bPause.setText("Pause");
        bPause.setFocusable(false);
        bPause.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bPause.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPauseActionPerformed(evt);
            }
        });

        bPlay.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        bPlay.setText("Play");
        bPlay.setFocusable(false);
        bPlay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bPlay.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPlayActionPerformed(evt);
            }
        });

        jComboBoxMusic.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jComboBoxMusic.setMaximumRowCount(3);
        jComboBoxMusic.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Matrix", "Mission Impossible", "The Box" }));
        jComboBoxMusic.setSelectedIndex(1);
        jComboBoxMusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMusicActionPerformed(evt);
            }
        });

        bScreenSize.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        bScreenSize.setText("Adapt to Screen Size");
        bScreenSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bScreenSizeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jAnnounceScore))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bHome)
                                    .addComponent(bLeave)
                                    .addComponent(bInterrupt)
                                    .addComponent(bNewGame)
                                    .addComponent(jScore)
                                    .addComponent(jScore2)
                                    .addComponent(jWinner)
                                    .addComponent(jAnnounceWinner))
                                .addGap(134, 134, 134)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxMusic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bPlay)
                                    .addComponent(bPause)
                                    .addComponent(bResume)
                                    .addComponent(jMusic)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jCurrentPlayer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bScreenSize, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jAnnounceCurrentPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jGrid, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                .addGap(49, 49, 49))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bHome, bInterrupt, bLeave, bNewGame});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jGrid, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                        .addGap(90, 90, 90))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jAnnounceCurrentPlayer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCurrentPlayer)
                        .addGap(24, 24, 24)
                        .addComponent(jAnnounceScore)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScore)
                        .addGap(5, 5, 5)
                        .addComponent(jScore2)
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jAnnounceWinner)
                            .addComponent(jMusic))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jWinner)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxMusic, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bScreenSize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bNewGame, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bPlay, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bInterrupt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bLeave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bHome))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(bPause)
                                .addGap(18, 18, 18)
                                .addComponent(bResume)))
                        .addContainerGap(32, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bInterruptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInterruptActionPerformed

        // TODO add your handling code here:
        m.playFXSound(arcade);
        int response = message.confirmationMsg("Would you like to save your game ?", "Save");
        if (response == 0) {
            game.interruptGame();
            response = message.confirmationMsg("Exit the program ?", "Exit");
            if (response == 0) {
                dispose();
                System.exit(0);
            } else {
                dispose();
                this.getParent().setVisible(true);
            }

        } else {
            message.popUpMsg("Okay");
        }
    }//GEN-LAST:event_bInterruptActionPerformed

    private void bLeaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLeaveActionPerformed
        // TODO add your handling code here:
        m.playFXSound(laugh);
        message.popUpMsg("Looser");
        System.exit(0);

    }//GEN-LAST:event_bLeaveActionPerformed

    private void bNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNewGameActionPerformed
        // TODO add your handling code here:

        game.newGrid();
        gridRandomColor();
        try {
            retrieveOptions();
        } catch (IOException ex) {
            Logger.getLogger(FGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        initGame();
        jAnnounceCurrentPlayer.setText("The current player is :");
        jCurrentPlayer.setText("" + game.getCurrentPlayer().getPseudo());
        m.playFXSound(menu_select);

        game.afficherGrille(tabBout);

    }//GEN-LAST:event_bNewGameActionPerformed

    private void bHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHomeActionPerformed
        // TODO add your handling code here:
        m.playFXSound(menu_select);
        this.dispose();
        this.getParent().setVisible(true);

    }//GEN-LAST:event_bHomeActionPerformed

    private void bResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResumeActionPerformed
        // TODO add your handling code here:
        m.resumeMusic();

    }//GEN-LAST:event_bResumeActionPerformed

    private void bPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPauseActionPerformed
        // TODO add your handling code here:
        m.pauseMusic();

    }//GEN-LAST:event_bPauseActionPerformed

    private void bPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPlayActionPerformed
        // TODO add your handling code here:
        int choice = jComboBoxMusic.getSelectedIndex();
        switch (choice) {
            case 0:
                selectedMusic = matrix;
                break;
            case 1:
                selectedMusic = mission;
                break;
            case 2:
                selectedMusic = roddyrich;
                break;
        }
        if (!(musicPlayed == selectedMusic)) {
            //m.pauseMusic();
            musicPlayed = selectedMusic;
            m.playMusic(musicPlayed);
        }
    }//GEN-LAST:event_bPlayActionPerformed

    private void jComboBoxMusicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMusicActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxMusicActionPerformed

    private void bScreenSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bScreenSizeActionPerformed
        // TODO add your handling code here:
        adaptScreenSize();
        m.playFXSound(menu_select);
    }//GEN-LAST:event_bScreenSizeActionPerformed

    private void setText() {

        //Set text current p
        if (game.isThereAnAI() == true) {
            jCurrentPlayer.setText("");
            jAnnounceCurrentPlayer.setText("");
        } else {
            jAnnounceCurrentPlayer.setText("The current player is :");
            jCurrentPlayer.setText("" + game.getCurrentPlayer().getPseudo() + "(" + game.getPlayerColor() + ")");
        }
        jAnnounceScore.setText("Score :");
        jScore.setText(p1.getPseudo() + ":         " + game.countPawns(p1));
        jScore2.setText(p2.getPseudo() + ":         " + game.countPawns(p2));

        //Set text isEnd
        if (game.isEnd()) {
            jAnnounceWinner.setText("The winner is : ");
            jWinner.setText("" + game.pickTheWinner().getPseudo());
            message.popUpMsg("" + getP1().getPseudo() + ": " + game.countPawns(p1) + "   " + getP2().getPseudo() + ": " + game.countPawns(p2));
            if (game.pickTheWinner().isAI() == false) {
                m.playFXSound(congrats);
            } else {
                m.playFXSound(gameover);
            }
        } else {
            jAnnounceWinner.setText("");
            jWinner.setText("");
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>


        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            FGame dialog = new FGame(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bHome;
    private javax.swing.JButton bInterrupt;
    private javax.swing.JButton bLeave;
    private javax.swing.JButton bNewGame;
    private javax.swing.JButton bPause;
    private javax.swing.JButton bPlay;
    private javax.swing.JButton bResume;
    private javax.swing.JButton bScreenSize;
    private javax.swing.JLabel jAnnounceCurrentPlayer;
    private javax.swing.JLabel jAnnounceScore;
    private javax.swing.JLabel jAnnounceWinner;
    private javax.swing.JComboBox<String> jComboBoxMusic;
    private javax.swing.JLabel jCurrentPlayer;
    private javax.swing.JPanel jGrid;
    private javax.swing.JLabel jMusic;
    private javax.swing.JLabel jScore;
    private javax.swing.JLabel jScore2;
    private javax.swing.JLabel jWinner;
    // End of variables declaration//GEN-END:variables
}
