package me.giverplay.towerdefense.utils;

public final class TimeUtils {
  private TimeUtils() { }

  public static void sleep(long ms) {
    try {
      Thread.sleep(ms);
    } catch(InterruptedException ignored) { }
  }
}
