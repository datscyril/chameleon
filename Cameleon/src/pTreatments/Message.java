/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pTreatments;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author cyrilghali
 */
public class Message {

    public Message() {
    }
    
    public int confirmationMsg(String msg, String title) {
        JFrame jf = new JFrame();
        jf.setAlwaysOnTop(true);
        int response = JOptionPane.showConfirmDialog(jf, msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return response;
    }
    
    public void popUpMsg(String msg) {
        JDialog e= new JDialog();
        e.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(e, msg);
        
    }
}
