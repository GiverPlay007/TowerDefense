package me.giverplay.towerdefense.entities;

import me.giverplay.towerdefense.Game;

import java.awt.Color;
import java.awt.Graphics;

public class Tower extends Entity {
  private final Game game;

  private int xa = 0;
  private int ya = 0;
  private int lastAttack = 0;

  private boolean attack = false;

  private Enemy target;

  public Tower(int x, int y, int width, int height) {
    super(x, y, width, height, 1, SPRITE_TORRE);
    game = Game.getGame();

    setDepth(2);
  }

  @Override
  public void tick() {
    attack = false;

    if(target != null && target.removed) target = null;
    if(game.getTotalTime() - lastAttack < 30) return;

    if(target == null) {
      for(int i = 0; i < game.getEntities().size(); i++) {
        Entity e = game.getEntities().get(i);

        if(!(e instanceof Enemy))
          continue;

        if(pointDistance(getX() + 16, getY() + 16, e.getX() + 16, e.getY() + 16) < 120) {
          target = (Enemy) e;
        }
      }
    }

    attack = target != null;

    if(attack) {
      lastAttack = game.getTotalTime();
      xa = target.getX() + 16;
      ya = target.getY() + 16;
      target.modifyLife(-10);
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
