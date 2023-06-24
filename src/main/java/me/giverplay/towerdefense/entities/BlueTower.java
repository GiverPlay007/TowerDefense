package me.giverplay.towerdefense.entities;

public class BlueTower extends Tower {
  public BlueTower(int x, int y) {
    super(SPRITE_TOWER[0], x, y);
    damage = 8;
  }
}
