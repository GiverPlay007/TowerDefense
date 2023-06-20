package me.giverplay.towerdefense.sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Objects;

public class Sound {
  public static GameClip coin = load("/coin.wav");
  public static GameClip hit = load("/hit.wav");
  public static GameClip lose = load("/lose.wav");
  public static GameClip hit2 = load("/hit2.wav");
  public static GameClip jump = load("/jump.wav");
  public static GameClip life = load("/life.wav");
  public static GameClip up = load("/up.wav");

  public static class GameClip {
    public Clip clip;

    public GameClip(byte[] buffer) throws IllegalArgumentException, LineUnavailableException, IOException, UnsupportedAudioFileException {
      if(buffer == null) {
        throw new IllegalArgumentException("Buffer n�o pode ser nulo");
      }

      clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
    }

    public void play() {
      clip.stop();
      clip.setFramePosition(0);
      clip.start();
    }

    public void loop() {
      clip.loop(1000);
    }
  }

  private static GameClip load(String name) {
    try(
      DataInputStream dis = new DataInputStream(Objects.requireNonNull(Sound.class.getResourceAsStream(name)));
      ByteArrayOutputStream baos = new ByteArrayOutputStream()
    ) {
      byte[] buffer = new byte[1024];

      int read;

      while((read = dis.read(buffer)) >= 0) {
        baos.write(buffer, 0, read);
      }

      byte[] data = baos.toByteArray();

      try {
        return new GameClip(data);
      } catch(Exception e) {
        System.out.println("Falha ao inicializar áudio: " + name);
      }
    } catch(IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
