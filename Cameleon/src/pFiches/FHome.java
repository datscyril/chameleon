/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pFiches;

import javax.swing.*;
import java.awt.*;
import pTreatments.Music;

/**
 *
 * @author cghali
 */
public class FHome extends javax.swing.JFrame {

    private final FGame game;
    private final FResumeGame resumeGame;
    private final FOptions options;
    private final Music m = new Music();
    private final String menu_select = "src/pSounds/menu-select.wav";
    private final String letsgo = "src/pSounds/let-s-go.wav";

    public FGame getGame() {
        return game;
    }

    String pseudo1 = "Joshua";

    /**
     * Creates new form FAccueil
     */
    public FHome() {
        initComponents();
        setDeco();
        game = new FGame(this, false);
        resumeGame = new FResumeGame(this, false);
        options = new FOptions(this, false);
        game.setLocation(0, 0);
        game.setResizable(true);
        options.setBackground(Color.BLACK);
        resumeGame.setBackground(Color.BLACK);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bResumeGame = new javax.swing.JButton();
        bNewGame = new javax.swing.JButton();
        lTitle = new javax.swing.JLabel();
        bOptions = new javax.swing.JButton();
        bExit = new javax.swing.JButton();
        jBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        getContentPane().setLayout(null);

        bResumeGame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pImages/load.png"))); // NOI18N
        bResumeGame.setBorderPainted(false);
        bResumeGame.setContentAreaFilled(false);
        bResumeGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResumeGameActionPerformed(evt);
            }
        });
        getContentPane().add(bResumeGame);
        bResumeGame.setBounds(80, 360, 300, 100);

        bNewGame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pImages/iu.png"))); // NOI18N
        bNewGame.setBorderPainted(false);
        bNewGame.setContentAreaFilled(false);
        bNewGame.setFocusPainted(false);
        bNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNewGameActionPerformed(evt);
            }
        });
        getContentPane().add(bNewGame);
        bNewGame.setBounds(80, 240, 300, 120);

        lTitle.setFont(new java.awt.Font("Tempus Sans ITC", 1, 72)); // NOI18N
        lTitle.setForeground(new java.awt.Color(255, 255, 255));
        lTitle.setText("Cameleon");
        lTitle.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(lTitle);
        lTitle.setBounds(700, 40, 420, 130);

        bOptions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pImages/options_1.png"))); // NOI18N
        bOptions.setBorderPainted(false);
        bOptions.setContentAreaFilled(false);
        bOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOptionsActionPerformed(evt);
            }
        });
        getContentPane().add(bOptions);
        bOptions.setBounds(90, 470, 270, 100);

        bExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pImages/exit.png"))); // NOI18N
        bExit.setBorderPainted(false);
        bExit.setContentAreaFilled(false);
        bExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExitActionPerformed(evt);
            }
        });
        getContentPane().add(bExit);
        bExit.setBounds(70, 650, 320, 150);

        jBackground.setBackground(new java.awt.Color(204, 204, 204));
        jBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pImages/animal-blur-chameleon-567540-1.jpg"))); // NOI18N
        jBackground.setOpaque(true);
        getContentPane().add(jBackground);
        jBackground.setBounds(10, 10, 1880, 1270);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNewGameActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        
        game.setVisible(true);
        game.initGame();
        game.gridRandomColor();
        game.setResizable(true);
        m.playFXSound(letsgo);

    }//GEN-LAST:event_bNewGameActionPerformed

    private void setDeco() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jBackground.setSize(screenSize);
        scaleImage();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        setSize(width, height);
        setMinimumSize(screenSize);
        setPreferredSize(screenSize);
        setMaximumSize(screenSize);
        int R = 17;
        int G = 100;
        int B = 114;
        Color color = new Color(R, G, B);
        this.getContentPane().setBackground(color);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setAlwaysOnTop(true);
        setVisible(true);
    }

    private void bOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOptionsActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        m.playFXSound(menu_select);
        options.setVisible(true);

    }//GEN-LAST:event_bOptionsActionPerformed

    private void bExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExitActionPerformed
        // TODO add your handling code here:

        System.exit(0);
    }//GEN-LAST:event_bExitActionPerformed

    private void bResumeGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResumeGameActionPerformed
        // TODO add your handling code here:
        m.playFXSound(menu_select);
        this.setVisible(false);
        resumeGame.setVisible(true);
    }//GEN-LAST:event_bResumeGameActionPerformed

    private void scaleImage() {
        ImageIcon icon = new ImageIcon("src/pImages/animal-blur-chameleon-567540-1.jpg");
        Image img = icon.getImage();
        Image imgScale = img.getScaledInstance(jBackground.getWidth(), jBackground.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        jBackground.setIcon(scaledIcon);

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
            java.util.logging.Logger.getLogger(FHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FHome().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bExit;
    private javax.swing.JButton bNewGame;
    private javax.swing.JButton bOptions;
    private javax.swing.JButton bResumeGame;
    private javax.swing.JLabel jBackground;
    private javax.swing.JLabel lTitle;
    // End of variables declaration//GEN-END:variables
}
