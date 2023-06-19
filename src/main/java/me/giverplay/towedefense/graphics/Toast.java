package me.giverplay.towedefense.graphics;

public class Toast {
  private final String text;

  private final int fadeIn;
  private final int fadeOut;

  public Toast(String text, int fadeIn, int fadeOut) {
    this.text = text;
    this.fadeIn = fadeIn;
    this.fadeOut = fadeOut;
  }

  public String getText() {
    return text;
  }

  public int getFadeIn() {
    return fadeIn;
  }

  public int getFadeOut() {
    return this.fadeOut;
  }
}
