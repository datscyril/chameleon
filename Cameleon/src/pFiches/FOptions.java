/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pFiches;

import pTreatments.Game;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pTreatments.Music;

/**
 *
 * @author Cyril
 */
public class FOptions extends javax.swing.JDialog {

    private static String pseudo_p1;
    private static String pseudo_p2;
    private int color_p1;
    private int color_p2;
    private boolean p1_isAI;
    private final boolean p2_isAI = false;
    private boolean difficulty_mode;
    private final int RED = 0;
    private final int BLUE = 1;
    private final int YELLOW = 2;
    private final int GREEN = 3;
    private boolean sighting_aid = false;
    private final Music m = new Music();
    private final String menu_select = "src/pSounds/menu-select.wav";

    public static String getPseudo_p1() {
        return pseudo_p1;
    }

    public static String getPseudo_p2() {
        return pseudo_p2;
    }

    /**
     * Creates new form JOptions
     * @param parent
     * @param modal
     */
    public FOptions(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
  
        resumeOptions();

    }

    public void saveOptions() {
        String nameOfSave = "src/pSaves/save_options";
        File f = new File(nameOfSave);

        //On écrit dans le fichier les atribut du jouer p1,p2,current player, puis on écris le tableau
        try ( FileWriter file = new FileWriter(f)) {
            file.write(pseudo_p1 + " " + color_p1 + " " + p1_isAI + System.lineSeparator());
            file.write(pseudo_p2 + " " + color_p2 + " " + p2_isAI + System.lineSeparator());
            file.write("" + difficulty_mode+ System.lineSeparator());
            file.write("" + sighting_aid+ System.lineSeparator());
            this.dispose();
           // JOptionPane.showMessageDialog(null, "It's saved, please restart game");
            this.getParent().setVisible(true);

        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void resumeOptions() { // On affiche les éléments utilisé la derniere fois
        File f = new File("src/pSaves/save_options");
        try {
            try ( FileReader entree = new FileReader(f);  BufferedReader br = new BufferedReader(entree)) {
                String separator = " ";
                //On récupère les 3 première lignes : Joueur 1, Joueur 2, Difficulty, Sighting Aid

                String sp1 = br.readLine();
                String sp2 = br.readLine();
                String sp3 = br.readLine();
                String sp4 = br.readLine();

                // On stocke les infos des joueur dans des tableau de srting
                String[] infos = sp1.split(separator);
                pseudoPlayer1.setText(infos[0]);
                colorPlayer1.setSelectedIndex(Integer.parseInt(infos[1]) - 1);
                if (Boolean.parseBoolean(infos[2]) == true) {
                    boxGameMode.setSelectedIndex(0);
                }
                infos = sp2.split(separator);
                pseudoPlayer2.setText(infos[0]);
                colorPlayer2.setSelectedIndex(Integer.parseInt(infos[1]) - 1);
                infos = sp3.split(separator);
                if (Boolean.parseBoolean(infos[0])) {
                    boxDifficulty.setSelectedIndex(1);
                } else {
                    boxDifficulty.setSelectedIndex(0);
                }
                infos = sp4.split(separator);
                if (Boolean.parseBoolean(infos[0])) {
                    boxSightingAid.setSelectedIndex(1);
                } else {
                    boxSightingAid.setSelectedIndex(0);
                }

            }

        } catch (Exception e) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPseudo = new javax.swing.JLabel();
        pseudoPlayer1 = new javax.swing.JTextField();
        jDifficulty = new javax.swing.JLabel();
        jColor = new javax.swing.JLabel();
        bSave = new javax.swing.JButton();
        jSightingAid = new javax.swing.JLabel();
        bHome = new javax.swing.JButton();
        jGameMode = new javax.swing.JLabel();
        boxSightingAid = new javax.swing.JComboBox<>();
        pseudoPlayer2 = new javax.swing.JTextField();
        boxDifficulty = new javax.swing.JComboBox<>();
        colorPlayer1 = new javax.swing.JComboBox<>();
        colorPlayer2 = new javax.swing.JComboBox<>();
        boxGameMode = new javax.swing.JComboBox<>();
        icon = new javax.swing.JLabel();
        bExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setForeground(new java.awt.Color(0, 0, 0));
        setIconImage(null);
        setMinimumSize(new java.awt.Dimension(1600, 1000));
        getContentPane().setLayout(null);

        jPseudo.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jPseudo.setForeground(new java.awt.Color(255, 255, 255));
        jPseudo.setText("Pseudo");
        getContentPane().add(jPseudo);
        jPseudo.setBounds(39, 66, 120, 33);

        pseudoPlayer1.setBackground(java.awt.Color.orange);
        pseudoPlayer1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        pseudoPlayer1.setForeground(new java.awt.Color(255, 255, 255));
        pseudoPlayer1.setText("Joshua");
        pseudoPlayer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pseudoPlayer1ActionPerformed(evt);
            }
        });
        getContentPane().add(pseudoPlayer1);
        pseudoPlayer1.setBounds(170, 60, 120, 39);

        jDifficulty.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jDifficulty.setForeground(new java.awt.Color(255, 255, 255));
        jDifficulty.setText("Difficulty");
        getContentPane().add(jDifficulty);
        jDifficulty.setBounds(40, 159, 130, 33);

        jColor.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jColor.setForeground(new java.awt.Color(255, 255, 255));
        jColor.setText("Color");
        getContentPane().add(jColor);
        jColor.setBounds(40, 232, 80, 33);

        bSave.setBackground(java.awt.Color.orange);
        bSave.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        bSave.setForeground(new java.awt.Color(255, 255, 255));
        bSave.setText("Save");
        bSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSaveActionPerformed(evt);
            }
        });
        getContentPane().add(bSave);
        bSave.setBounds(30, 660, 150, 41);

        jSightingAid.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jSightingAid.setForeground(new java.awt.Color(255, 255, 255));
        jSightingAid.setText("Sighting aid");
        jSightingAid.setToolTipText("");
        getContentPane().add(jSightingAid);
        jSightingAid.setBounds(30, 400, 150, 33);

        bHome.setBackground(java.awt.Color.orange);
        bHome.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        bHome.setForeground(new java.awt.Color(255, 255, 255));
        bHome.setText("Return Home");
        bHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHomeActionPerformed(evt);
            }
        });
        getContentPane().add(bHome);
        bHome.setBounds(30, 720, 250, 41);

        jGameMode.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jGameMode.setForeground(new java.awt.Color(255, 255, 255));
        jGameMode.setText("Game Mode");
        getContentPane().add(jGameMode);
        jGameMode.setBounds(30, 305, 150, 33);

        boxSightingAid.setBackground(java.awt.Color.orange);
        boxSightingAid.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        boxSightingAid.setForeground(new java.awt.Color(255, 255, 255));
        boxSightingAid.setMaximumRowCount(2);
        boxSightingAid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "False", "True" }));
        boxSightingAid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxSightingAidActionPerformed(evt);
            }
        });
        getContentPane().add(boxSightingAid);
        boxSightingAid.setBounds(250, 400, 98, 39);

        pseudoPlayer2.setBackground(java.awt.Color.orange);
        pseudoPlayer2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        pseudoPlayer2.setForeground(new java.awt.Color(255, 255, 255));
        pseudoPlayer2.setText("Leon");
        getContentPane().add(pseudoPlayer2);
        pseudoPlayer2.setBounds(310, 60, 140, 39);

        boxDifficulty.setBackground(java.awt.Color.orange);
        boxDifficulty.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        boxDifficulty.setForeground(new java.awt.Color(255, 255, 255));
        boxDifficulty.setMaximumRowCount(2);
        boxDifficulty.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Easy", "Normal" }));
        boxDifficulty.setSelectedIndex(1);
        boxDifficulty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxDifficultyActionPerformed(evt);
            }
        });
        getContentPane().add(boxDifficulty);
        boxDifficulty.setBounds(250, 150, 129, 39);

        colorPlayer1.setBackground(java.awt.Color.orange);
        colorPlayer1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        colorPlayer1.setForeground(new java.awt.Color(255, 255, 255));
        colorPlayer1.setMaximumRowCount(4);
        colorPlayer1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Red", "Blue", "Yellow", "Green" }));
        colorPlayer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorPlayer1ActionPerformed(evt);
            }
        });
        getContentPane().add(colorPlayer1);
        colorPlayer1.setBounds(240, 230, 117, 39);

        colorPlayer2.setBackground(java.awt.Color.orange);
        colorPlayer2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        colorPlayer2.setForeground(new java.awt.Color(255, 255, 255));
        colorPlayer2.setMaximumRowCount(4);
        colorPlayer2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Red", "Blue", "Yellow", "Green" }));
        colorPlayer2.setSelectedIndex(1);
        colorPlayer2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorPlayer2ActionPerformed(evt);
            }
        });
        getContentPane().add(colorPlayer2);
        colorPlayer2.setBounds(370, 230, 117, 39);

        boxGameMode.setBackground(java.awt.Color.orange);
        boxGameMode.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        boxGameMode.setForeground(new java.awt.Color(255, 255, 255));
        boxGameMode.setMaximumRowCount(2);
        boxGameMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Human vs AI", "Human vs Human" }));
        boxGameMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxGameModeActionPerformed(evt);
            }
        });
        getContentPane().add(boxGameMode);
        boxGameMode.setBounds(260, 300, 241, 39);

        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pImages/leon.jpeg"))); // NOI18N
        getContentPane().add(icon);
        icon.setBounds(-20, 0, 1790, 990);

        bExit.setBackground(java.awt.Color.orange);
        bExit.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        bExit.setForeground(new java.awt.Color(255, 255, 255));
        bExit.setText("Exit");
        bExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExitActionPerformed(evt);
            }
        });
        getContentPane().add(bExit);
        bExit.setBounds(30, 790, 250, 41);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pseudoPlayer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pseudoPlayer1ActionPerformed
        // TODO add your handling code here:
        m.playFXSound(menu_select);

    }//GEN-LAST:event_pseudoPlayer1ActionPerformed

    private void boxDifficultyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxDifficultyActionPerformed
        // TODO add your handling code here:
        m.playFXSound(menu_select);


    }//GEN-LAST:event_boxDifficultyActionPerformed

    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
        // TODO add your handling code here:
        //On enregistre les informations dans save_options
        difficulty_mode = boxDifficulty.getSelectedIndex() == 1;
        p1_isAI = boxGameMode.getSelectedIndex() == 0;
        pseudo_p1 = pseudoPlayer1.getText();
        pseudo_p2 = pseudoPlayer2.getText();
        switch (colorPlayer1.getSelectedIndex()) {
            case RED:
                color_p1 = RED + 1; // du au décalagfe des indice de combobox
                break;
            case BLUE:
                color_p1 = BLUE + 1;
                break;
            case YELLOW:
                color_p1 = YELLOW + 1;
                break;
            case GREEN:
                color_p1 = GREEN + 1;
                break;
        }
        switch (colorPlayer2.getSelectedIndex()) {
            case RED:
                color_p2 = RED + 1; // du au décalagfe des indice de combobox
                break;
            case BLUE:
                color_p2 = BLUE + 1;
                break;
            case YELLOW:
                color_p2 = YELLOW + 1;
                break;
            case GREEN:
                color_p2 = GREEN + 1;
                break;
        }
        sighting_aid = boxSightingAid.getSelectedIndex() != 0;
        m.playFXSound(menu_select);
        saveOptions();


    }//GEN-LAST:event_bSaveActionPerformed

    private void colorPlayer2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorPlayer2ActionPerformed
        // TODO add your handling code here:
        m.playFXSound(menu_select);
    }//GEN-LAST:event_colorPlayer2ActionPerformed

    private void colorPlayer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorPlayer1ActionPerformed
        // TODO add your handling code here:
        m.playFXSound(menu_select);
    }//GEN-LAST:event_colorPlayer1ActionPerformed

    private void bHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHomeActionPerformed
        // TODO add your handling code here:
        m.playFXSound(menu_select);
        this.setVisible(false);
        this.getParent().setVisible(true);
    }//GEN-LAST:event_bHomeActionPerformed

    private void bExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExitActionPerformed
        // TODO add your handling code here:
        m.playFXSound(menu_select);
        System.exit(0);
    }//GEN-LAST:event_bExitActionPerformed

    private void boxGameModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxGameModeActionPerformed
        // TODO add your handling code here:
        m.playFXSound(menu_select);
    }//GEN-LAST:event_boxGameModeActionPerformed

    private void boxSightingAidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxSightingAidActionPerformed
        // TODO add your handling code here:
        m.playFXSound(menu_select);
    }//GEN-LAST:event_boxSightingAidActionPerformed

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
            java.util.logging.Logger.getLogger(FOptions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FOptions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FOptions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FOptions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                FOptions dialog = new FOptions(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bExit;
    private javax.swing.JButton bHome;
    private javax.swing.JButton bSave;
    private javax.swing.JComboBox<String> boxDifficulty;
    private javax.swing.JComboBox<String> boxGameMode;
    private javax.swing.JComboBox<String> boxSightingAid;
    private javax.swing.JComboBox<String> colorPlayer1;
    private javax.swing.JComboBox<String> colorPlayer2;
    private javax.swing.JLabel icon;
    private javax.swing.JLabel jColor;
    private javax.swing.JLabel jDifficulty;
    private javax.swing.JLabel jGameMode;
    private javax.swing.JLabel jPseudo;
    private javax.swing.JLabel jSightingAid;
    private javax.swing.JTextField pseudoPlayer1;
    private javax.swing.JTextField pseudoPlayer2;
    // End of variables declaration//GEN-END:variables
}