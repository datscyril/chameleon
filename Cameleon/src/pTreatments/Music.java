/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pTreatments;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import pTreatments.Message;

/**
 *
 * @author Cyril
 */
public class Music {

    private boolean musicIsPlaying = false;

    private Clip clipp;
    private long clipTimePosition;

    public void playMusic(String musicLocation) {
        try {
            File musicPath = new File(musicLocation);
            if (musicPath.exists()) {
                clipTimePosition = 0;

                //Si music en cours on la stoppe
                if (musicIsPlaying) {
                    clipp.stop();
                }
                //On jour la nouvelle musique
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                this.clipp = clip;
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                musicIsPlaying = true;

            } else {

                Message e = new Message();
                e.popUpMsg("Error");
            }
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
        }
    }

    public void pauseMusic() {
        clipTimePosition = clipp.getMicrosecondPosition();
        clipp.stop();
        musicIsPlaying = false;

    }

    public void resumeMusic() {
        if (musicIsPlaying == false) {
            clipp.setMicrosecondPosition(clipTimePosition);
            clipp.start();
            musicIsPlaying = true;
        }
    }

    public void playFXSound(String musicLocation) {
        try {
            File musicPath = new File(musicLocation);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();

            } else {
                Message e = new Message();
                e.popUpMsg("Can't find file");
            }
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
        }
    }

}
