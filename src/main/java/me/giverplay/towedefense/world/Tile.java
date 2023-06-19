package me.giverplay.towedefense.world;

import me.giverplay.towedefense.Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static me.giverplay.towedefense.world.World.TILE_SIZE;

public class Tile {
  private static final Game game = Game.getGame();

  public static BufferedImage TILE_GRASS = game.getSpritesheet().getSprite(0, 0, TILE_SIZE, TILE_SIZE);
  public static BufferedImage TILE_PATH = game.getSpritesheet().getSprite(TILE_SIZE, 0, TILE_SIZE, TILE_SIZE);

  private final BufferedImage sprite;

  private final boolean isRigid;

  private final int x;
  private final int y;

  public Tile(int x, int y, boolean isRigid, BufferedImage sprite) {
    this.x = x;
    this.y = y;
    this.sprite = sprite;
    this.isRigid = isRigid;
  }

  public void render(Graphics g) {
    g.drawImage(sprite, x, y, null);
  }

  public boolean isRigid() {
    return this.isRigid;
  }
}
