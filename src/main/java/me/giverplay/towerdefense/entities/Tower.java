package me.giverplay.towerdefense.entities;

import me.giverplay.towerdefense.Game;

import java.awt.Color;
import java.awt.Graphics;

public class Tower extends Entity {
  private final Game game;

  private int xa = 0;
  private int ya = 0;

  private boolean attack = false;

  public Tower(int x, int y, int width, int height) {
    super(x, y, width, height, 1, SPRITE_TORRE);
    game = Game.getGame();

    setDepth(2);
  }

  @Override
  public void tick() {
    Enemy enemy = null;

    for(int i = 0; i < game.getEntities().size(); i++) {
      Entity e = game.getEntities().get(i);

      if(!(e instanceof Enemy))
        continue;

      if(pointDistance(getX() + 16, getY() + 16, e.getX() + 16, e.getY() + 16) < 120) {
        enemy = (Enemy) e;
      }
    }

    attack = enemy != null;

    if(attack) {
      xa = enemy.getX() + 16;
      ya = enemy.getY() + 16;
      enemy.modifyLife(-1);
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
