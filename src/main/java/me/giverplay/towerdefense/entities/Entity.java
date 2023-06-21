package me.giverplay.towerdefense.entities;

import me.giverplay.towerdefense.Game;
import me.giverplay.towerdefense.algorithms.Node;
import me.giverplay.towerdefense.algorithms.Vector2i;
import me.giverplay.towerdefense.graphics.Spritesheet;
import me.giverplay.towerdefense.world.World;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static me.giverplay.towerdefense.world.World.TILE_SIZE;

public class Entity {
  public static final BufferedImage[] SPRITE_ENEMY;

  public static final BufferedImage SPRITE_SPAWNER;
  public static final BufferedImage SPRITE_MONEY;
  public static final BufferedImage SPRITE_HEART_FULL;
  public static final BufferedImage SPRITE_HEART_NON_FULL;
  public static final BufferedImage SPRITE_BAR;
  public static final BufferedImage SPRITE_TORRE;

  static {
    Spritesheet sprites = Game.getGame().getSpritesheet();

    SPRITE_ENEMY = new BufferedImage[9];

    for(int i = 0; i < 4; i++) {
      SPRITE_ENEMY[i] = sprites.getSprite(i * TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);
      SPRITE_ENEMY[i + 3] = sprites.getSprite((i + 3) * TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);
      SPRITE_ENEMY[i + 5] = sprites.getSprite((i + 5) * TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    SPRITE_SPAWNER = sprites.getSprite(0, TILE_SIZE * 2, TILE_SIZE, TILE_SIZE);
    SPRITE_MONEY = sprites.getSprite(TILE_SIZE * 2, TILE_SIZE * 2, TILE_SIZE, TILE_SIZE);
    SPRITE_HEART_FULL = sprites.getSprite(TILE_SIZE, TILE_SIZE * 2, TILE_SIZE / 2, TILE_SIZE / 2);
    SPRITE_HEART_NON_FULL = sprites.getSprite(TILE_SIZE + TILE_SIZE / 2, TILE_SIZE * 2, TILE_SIZE / 2, TILE_SIZE / 2);
    SPRITE_BAR = sprites.getSprite(TILE_SIZE, 80, TILE_SIZE, 9);
    SPRITE_TORRE = sprites.getSprite(TILE_SIZE * 3, TILE_SIZE * 2, TILE_SIZE, TILE_SIZE);
  }

  protected static final Game game = Game.getGame();
  protected static final Random random = new Random();

  protected List<Node> path;

  protected double x;
  protected double y;
  protected double speed;

  protected boolean removed;

  private final BufferedImage sprite;

  private final int width;
  private final int height;

  private int maxLife = 100;
  private int life = 100;
  private int depth;


  public Entity(double x, double y, int width, int height, double speed, BufferedImage sprite) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.speed = speed;
    this.depth = 0;

    this.sprite = sprite;
  }

  public void tick() {
  }

  public void render(Graphics g) {
    g.drawImage(sprite, getX(), getY(), width, height, null);
  }

  public void destroy() {
    removed = true;
    game.removeEntity(this);
  }

  public void setDepth(int toSet) {
    this.depth = toSet;
  }

  public void followPath(List<Node> path) {
    if(path != null) {
      if(path.size() > 0) {
        Vector2i target = path.get(path.size() - 1).getTile();

        if(x < target.x * World.TILE_SIZE) {
          x += speed;
        } else if(x > target.x * World.TILE_SIZE) {
          x -= speed;
        }

        if(y < target.y * World.TILE_SIZE) {
          y += speed;
        } else if(y > target.y * World.TILE_SIZE) {
          y -= speed;
        }

        if(x == target.x * World.TILE_SIZE && y == target.y * World.TILE_SIZE)
          path.remove(path.size() - 1);
      }
    }
  }

  public double pointDistance(int x1, int y1, int x2, int y2) {
    return Math.sqrt((x2 - x1) * (x2 - x1) + ((y2 - y1) * (y2 - y1)));
  }

  public boolean isColliding(Entity entity) {
    Rectangle thisRect = new Rectangle(getX(), getY(), getWidth(), getHeight());
    Rectangle entityRect = new Rectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());

    return thisRect.intersects(entityRect);
  }

  public static Comparator<Entity> sortDepth = Comparator.comparingInt(Entity::getDepth);

  public void modifyLife(int toModify) {
    life += toModify;

    if(life < 0)
      life = 0;
    if(life > maxLife)
      life = maxLife;
  }

  public void setMaxLife(int maxLife) {
    this.maxLife = maxLife;
  }

  public void setLife(int life) {
    this.life = life;
  }

  public int getMaxLife() {
    return this.maxLife;
  }

  public int getLife() {
    return life;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getX() {
    return (int) this.x;
  }

  public int getY() {
    return (int) this.y;
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public int getDepth() {
    return this.depth;
  }

  public BufferedImage getSprite() {
    return this.sprite;
  }

  public boolean isRemoved() {
    return removed;
  }
}
