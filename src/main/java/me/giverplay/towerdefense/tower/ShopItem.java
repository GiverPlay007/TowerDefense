package me.giverplay.towerdefense.tower;

import me.giverplay.towerdefense.entities.Tower;

import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;

public class ShopItem {
  private final Class<? extends Tower> itemClass;
  private final BufferedImage icon;
  private final int cost;

  public ShopItem(Class<? extends Tower> itemClass, BufferedImage icon, int cost) {
    this.itemClass = itemClass;
    this.icon = icon;
    this.cost = cost;
  }

  public Tower create(int x, int y) {
    try {
      Constructor<?> constructor = itemClass.getConstructor(int.class, int.class);
      Object tower = constructor.newInstance(x, y);
      return (Tower) tower;
    } catch(Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public BufferedImage getIcon() {
    return icon;
  }

  public int getCost() {
    return cost;
  }
}
