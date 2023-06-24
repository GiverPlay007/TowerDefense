package me.giverplay.towerdefense.entities;

import me.giverplay.towerdefense.Game;

public class Spawner extends Entity {
  private static final int MAX_TIER_TIME = 1800;

  private int maxTime = 1;
  private int time = 0;

  private int tierTime = 0;
  private int tier = 1;

  public Spawner(double x, double y) {
    super(x, y, 32, 32, 0, SPRITE_SPAWNER);
    setDepth(2);
  }

  @Override
  public void tick() {
    time++;
    tierTime++;

    if(tierTime > MAX_TIER_TIME) {
      tierTime = 0;
      tier++;
    }

    if(time >= maxTime) {
      generate();
      time = 0;
      maxTime = random.nextInt(120 - 60) + 60;
    }
  }

  private void generate() {
    Enemy entity = new Enemy(getX(), getY(), random.nextInt(2) + 1);
    entity.setMaxLife(50 * tier);
    entity.setLife(entity.getMaxLife());

    Game.getGame().addEntity(entity);
  }

  public int getTier() {
    return tier;
  }
}
