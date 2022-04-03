/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pTreatments;

import javax.swing.JButton;

/**
 *
 * @author cghali
 */
public class FButton extends JButton{
    private int i;
    private int j;
//    private boolean chosen;
    
    public FButton(int i, int j) {
        this.i = i;
        this.j = j;
//        chosen=false;
        this.setText("");
    }
   
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

//    public boolean isChosen() {
//        return chosen;
//    }
//
//    public void setChosen(boolean chosen) {
//        this.chosen = chosen;
//    }

    public void setJ(int j) {
        this.j = j;
    }

}
