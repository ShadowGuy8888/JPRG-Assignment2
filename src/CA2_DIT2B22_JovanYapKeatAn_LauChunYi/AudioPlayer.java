/*
    Name: Jovan Yap Keat An
    Adm No: p2429407
    Class: DIT/FT/2B/22
*/
package CA2_DIT2B22_JovanYapKeatAn_LauChunYi;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Jovan
 * Handles audio playback for system sounds
 */
public class AudioPlayer {
    
    /**
     * Plays a sound effect from the given file path
     * @param filePath Path to the sound file (WAV format recommended)
     */
    
    public static void playSound(String filePath) {
        new Thread(() -> {
            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filePath));
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
        }).start();
    }
}