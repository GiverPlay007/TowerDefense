package me.giverplay.towerdefense.entities;

public class PinkTower extends Tower {
  public PinkTower(int x, int y) {
    super(SPRITE_TOWER[1], x, y);
    damage = 15;
  }
}
