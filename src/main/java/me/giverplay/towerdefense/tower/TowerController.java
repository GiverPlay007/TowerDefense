package me.giverplay.towerdefense.tower;

import me.giverplay.towerdefense.Game;
import me.giverplay.towerdefense.entities.Entity;
import me.giverplay.towerdefense.entities.Tower;
import me.giverplay.towerdefense.graphics.Toast;
import me.giverplay.towerdefense.world.World;

import static me.giverplay.towerdefense.world.World.TILE_SIZE;

public class TowerController {
  private final Game game;
  private final Shop shop;

  private Tower selectedTower;

  private boolean isInPath;
  private boolean isInTower;
  private boolean hasLastClick = false;
  private int lastX = 0;
  private int lastY = 0;

  public TowerController(Game game) {
    this.game = game;
    this.shop = game.getShop();
  }

  public void tick() {
    int x = lastX / TILE_SIZE * TILE_SIZE;
    int y = lastY / TILE_SIZE * TILE_SIZE;

    if(selectedTower != null) {
      selectedTower.setX(x);
      selectedTower.setY(y);

      isInPath = World.isPath(x, y);
      isInTower = false;

      for(int i = 0; i < game.getEntities().size(); i++) {
        Entity e = game.getEntities().get(i);

        if(!(e instanceof Tower)) continue;
        if(e.isColliding(selectedTower)) isInTower = true;
      }
    }

    if(hasLastClick) {
      hasLastClick = false;

      if(selectedTower != null) {
        addTower();
        return;
      }

      Tower tower = shop.getTower(lastX, lastY);

      if(tower != null) {
        selectedTower = tower;
      }
    }
  }

  private void addTower() {
    if(isInPath) {
      game.getUI().addToast(new Toast("Você não pode colocar torres no caminho!", 15, 15));
      return;
    }

    if(isInTower) {
      game.getUI().addToast(new Toast("Já existe uma torre neste local!", 15, 15));
      return;
    }

    if(shop.purchase()) {
      game.addEntity(selectedTower);
      selectedTower = null;
      isInPath = false;
      isInTower = false;
    }
  }

  public void handleClick(int x, int y) {
    lastX = x;
    lastY = y;
    hasLastClick = true;
  }

  public void handleMouseMove(int x, int y) {
    lastX = x;
    lastY = y;
  }

  public Tower getSelectedTower() {
    return selectedTower;
  }

  public boolean isInvalidPosition() {
    return isInPath || isInTower;
  }
}
