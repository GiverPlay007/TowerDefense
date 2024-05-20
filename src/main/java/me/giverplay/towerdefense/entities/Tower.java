package me.giverplay.towerdefense.entities;

import me.giverplay.towerdefense.world.World;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Tower extends Entity {

  protected int xa = 0;
  protected int ya = 0;
  protected int lastAttack = 0;
  protected int damage = 10;
  protected int reach = 120;

  protected boolean attack = false;
  protected Enemy target;

  public Tower(BufferedImage sprite, int x, int y) {
    super(x, y, World.TILE_SIZE, World.TILE_SIZE, 1, sprite);

    setDepth(2);
  }

  @Override
  public void tick() {
    attack = false;

    if(target != null) {
      if(target.removed) {
        target = null;
        return;
      }

      if(pointDistance(getX() + 16, getY() + 16, target.getX() + 16, target.getY() + 16) > 120) {
        target = null;
      }
    }

    if(game.getTotalTime() - lastAttack < 30) return;

    if(target == null) {
      for(int i = 0; i < game.getEntities().size(); i++) {
        Entity e = game.getEntities().get(i);

        if(!(e instanceof Enemy))
          continue;

        if(pointDistance(getX() + 16, getY() + 16, e.getX() + 16, e.getY() + 16) <= reach) {
          target = (Enemy) e;
        }
      }
    }

    attack = target != null;

    if(attack) {
      lastAttack = game.getTotalTime();
      xa = target.getX() + 16;
      ya = target.getY() + 16;
      target.modifyLife(-damage);
    }
  }

  @Override
  public void render(Graphics g) {
    super.render(g);

    if(attack) {
      g.setColor(Color.RED);
      g.drawLine(getX() + 16, getY() + 16, xa, ya);
    }
  }
}
