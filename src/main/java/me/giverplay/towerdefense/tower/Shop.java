package me.giverplay.towerdefense.tower;

import me.giverplay.towerdefense.Game;
import me.giverplay.towerdefense.entities.BlueTower;
import me.giverplay.towerdefense.entities.Entity;
import me.giverplay.towerdefense.entities.PinkTower;
import me.giverplay.towerdefense.entities.Tower;
import me.giverplay.towerdefense.graphics.FontUtils;
import me.giverplay.towerdefense.graphics.Toast;
import me.giverplay.towerdefense.graphics.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Shop {

  public static final int TOTAL_TOWERS = 2;

  private static final int SQUARE_WIDTH = 64;
  private static final int SQUARE_HEIGHT = 76;
  private static final int BAR_WIDTH = SQUARE_WIDTH * TOTAL_TOWERS;
  private static final int BAR_HEIGHT = SQUARE_HEIGHT;
  private static final int BAR_XS = Game.WIDTH / 2 - BAR_WIDTH / 2;
  private static final int BAR_XF = BAR_XS + BAR_WIDTH;
  private static final int BAR_YS = 6;
  private static final int BAR_YF = BAR_YS + BAR_HEIGHT;

  private final List<ShopItem> items;
  private final Game game;

  private ShopItem currentItem;

  public Shop(Game game) {
    this.game = game;
    this.items = new ArrayList<>();

    registerItem(BlueTower.class, Entity.SPRITE_TOWER[0], 100);
    registerItem(PinkTower.class, Entity.SPRITE_TOWER[1], 850);
  }

  public void render(Graphics g) {
    g.setColor(new Color(0, 0, 0, 120));
    g.fillRect(BAR_XS, BAR_YS, BAR_WIDTH, BAR_HEIGHT);
    g.setColor(Color.BLACK);
    g.drawRect(BAR_XS, BAR_YS, BAR_WIDTH, BAR_HEIGHT);
    g.setFont(FontUtils.getFont(16, Font.PLAIN));

    String txt;
    int xs;

    for(int i = 0; i < items.size(); i++) {
      ShopItem item = items.get(i);
      g.setColor(Color.BLACK);
      g.drawRect(BAR_XS, BAR_YS, SQUARE_WIDTH, SQUARE_HEIGHT);
      g.setColor(Color.WHITE);

      txt = "$" + item.getCost();
      xs = BAR_XS + SQUARE_WIDTH * i;

      g.drawString(txt, xs + (SQUARE_WIDTH - FontUtils.stringWidth(g, txt)) / 2, BAR_YF -1);
      g.drawImage(item.getIcon(), xs + 6, BAR_YS + 6, 48, 48, null);
    }

    Tower tower = game.getController().getSelectedTower();

    if(tower != null) {
      Color color = game.getController().isInvalidPosition()
        ? new Color(255, 53, 53, 200)
        : new Color(255, 255, 255, 100);

      g.setColor(color);
      g.fillRect(tower.getX(), tower.getY(), tower.getWidth(), tower.getHeight());
      tower.render(g);
    }

    g.drawImage(UI.SPRITE_BAR, Game.WIDTH - 104, 17, 100, 30, null);
    g.drawImage(UI.SPRITE_MONEY, Game.WIDTH - 100, 16, null);

    g.setColor(Color.WHITE);
    g.drawString("$" + game.getMoney(), Game.WIDTH - 65, 38);
  }

  private void registerItem(Class<? extends Tower> clazz, BufferedImage icon, int cost) {
    items.add(new ShopItem(clazz, icon, cost));
  }

  public Tower getTower(int x, int y) {
    if(x >= BAR_XS && x <= BAR_XF && y >= BAR_YS && y <= BAR_YF) {
      ShopItem item = items.get((x - BAR_XS) / SQUARE_WIDTH);
      currentItem = item;
      return item.create();
    }

    return null;
  }

  public boolean purchase() {
    if(currentItem == null) return false;

    if(game.getMoney() < currentItem.getCost()) {
      game.getUI().addToast(new Toast("Dinheiro insuficiente!", 15, 15));
      return false;
    }

    game.modifyMoney(-currentItem.getCost());
    return true;
  }
}
