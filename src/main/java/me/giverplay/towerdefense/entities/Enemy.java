package me.giverplay.towerdefense.entities;

import me.giverplay.towerdefense.Game;
import me.giverplay.towerdefense.sound.Sound;
import me.giverplay.towerdefense.world.World;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy extends Entity {
  private static final int MAX_ANIM_FRAMES = 10;

  private final Game game;
  private final int maxAnim;

  private int base;
  private int anim;
  private int animFrames = 0;

  public Enemy(double x, double y, double speed) {
    super(x, y, 32, 32, speed, null);

    setDepth(1);
    game = Game.getGame();

    base = random.nextInt(9);
    base = base == 1 ? 0 : base == 2 ? 0 : base == 4 ? 3 : base == 5 ? 3 : base == 7 ? 6 : base == 8 ? 6 : base;

    anim = base;
    maxAnim = base + 3;

    path = World.getPath();
  }

  @Override
  public void tick() {
    if(path.size() > 0)
      followPath(path);
    else
      x += speed;

    if(x > Game.WIDTH) {
      destroy();
      game.removeLife();
    }

    if(getLife() <= 0) {
      destroy();
      game.onEnemyDestroy();
    }
  }

  @Override
  public void render(Graphics g) {
    animFrames++;

    if(animFrames >= MAX_ANIM_FRAMES) {
      anim++;
      animFrames = 0;

      if(anim >= maxAnim) {
        anim = base;
      }
    }

    g.drawImage(SPRITE_ENEMY[anim], getX(), getY(), null);

    g.setColor(Color.RED);
    g.fillRect(getX(), getY() - 10, calcLife(getMaxLife()), 8);

    g.setColor(Color.GREEN);
    g.fillRect(getX(), getY() - 10, calcLife(getLife()), 8);
  }

  @Override
  public void destroy() {
    super.destroy();
    Sound.hit2.play();
  }

  private int calcLife(double life) {
    return (int) (32 * life / getMaxLife());
  }
}
