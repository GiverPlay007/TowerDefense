package me.giverplay.towerdefense.graphics;

import me.giverplay.towerdefense.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static me.giverplay.towerdefense.world.World.TILE_SIZE;

public class UI {

  public static final BufferedImage SPRITE_MONEY;
  public static final BufferedImage SPRITE_HEART_FULL;
  public static final BufferedImage SPRITE_HEART_NON_FULL;
  public static final BufferedImage SPRITE_BAR;

  private final ArrayList<Toast> toasts = new ArrayList<>();

  private final Game game;
  private Toast toast = null;

  private boolean showingToast = false;

  private int toastFrames = 0;
  private int maxToastFrames = 0;
  private int toastFadeIn = 0;
  private int toastFadeOut = 0;

  public UI() {
    game = Game.getGame();
  }

  public void render(Graphics g) {
    g.setColor(Color.WHITE);
    g.setFont(FontUtils.getFont(11, Font.PLAIN));
    g.drawString("FPS: " + Game.FPS, 2, Game.HEIGHT - 5);

    int x;
    int y = Game.HEIGHT - 28;
    int maxLife = game.getMaxLife();
    int coe = 24;

    for(int i = 0; i < maxLife; i++) {
      x = Game.WIDTH - maxLife * 30 + (i * (coe + 5) + 5);
      g.drawImage(i < game.getLife() ? SPRITE_HEART_FULL : SPRITE_HEART_NON_FULL, x, y, coe, coe, null);
    }

    game.getShop().render(g);
    advanceToast(g);
  }

  public void advanceToast(Graphics g) {
    int time = 80;

    if(!showingToast) {
      if(toasts.size() == 0)
        return;

      toast = toasts.get(0);
      showingToast = true;
      toastFadeIn = toast.getFadeIn();
      toastFadeOut = toast.getFadeOut();
      maxToastFrames = toastFadeIn + toastFadeOut + time;
    }

    toastFrames++;

    if(toastFrames >= maxToastFrames) {
      toasts.remove(0);
      toast = null;
      showingToast = false;
      toastFrames = 0;

      return;
    }

    int alpha = 255;

    if(toastFrames <= toastFadeIn) {
      alpha = 255 * toastFrames / toastFadeIn;
    } else if(toastFrames >= toastFadeIn + time) {
      alpha = 255 * (toastFadeOut - (toastFrames - (toastFadeIn + time))) / toastFadeOut;
    }

    Color currentColor = new Color(255, 255, 255, alpha);
    g.setColor(currentColor);

    g.setFont(FontUtils.getFont(32, Font.BOLD));
    int width = FontUtils.stringWidth(g, toast.getText());

    g.drawString(toast.getText(), (Game.WIDTH - width) / 2, Game.HEIGHT / 2);
  }

  public void addToast(Toast toast) {
    toasts.add(toast);
  }

  static {
    Spritesheet sprites = Game.getGame().getSpritesheet();

    SPRITE_MONEY = sprites.getSprite(TILE_SIZE * 2, TILE_SIZE * 2, TILE_SIZE, TILE_SIZE);
    SPRITE_HEART_FULL = sprites.getSprite(TILE_SIZE, TILE_SIZE * 2, TILE_SIZE / 2, TILE_SIZE / 2);
    SPRITE_HEART_NON_FULL = sprites.getSprite(TILE_SIZE + TILE_SIZE / 2, TILE_SIZE * 2, TILE_SIZE / 2, TILE_SIZE / 2);
    SPRITE_BAR = sprites.getSprite(TILE_SIZE, 80, TILE_SIZE, 9);
  }
}
