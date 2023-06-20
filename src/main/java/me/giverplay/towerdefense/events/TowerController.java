package me.giverplay.towerdefense.events;

import me.giverplay.towerdefense.Game;
import me.giverplay.towerdefense.entities.Entity;
import me.giverplay.towerdefense.entities.Tower;
import me.giverplay.towerdefense.graphics.Toast;
import me.giverplay.towerdefense.world.World;

import static me.giverplay.towerdefense.world.World.TILE_SIZE;

public class TowerController {
  private final Game game;

  private boolean hasLastClick = false;
  private int lastX = 0;
  private int lastY = 0;

  public TowerController(Game game) {
    this.game = game;
  }

  public void tick() {
    if(hasLastClick) {
      hasLastClick = false;

      if(game.getMoney() < 100) {
        game.getUI().addToast(new Toast("Dinheiro insuficiente!", 15, 15));
        return;
      }

      int xx = (lastX / TILE_SIZE) * TILE_SIZE;
      int yy = (lastY / TILE_SIZE) * TILE_SIZE;

      if(World.canMove(xx, yy)) {
        game.getUI().addToast(new Toast("Você não pode colocar torres no caminho!", 15, 15));
        return;
      }

      Tower tw = new Tower(xx, yy, TILE_SIZE, TILE_SIZE);

      for(int i = 0; i < game.getEntities().size(); i++) {
        Entity e = game.getEntities().get(i);

        if(!(e instanceof Tower))
          continue;

        if(e.isColliding(tw)) {
          game.getUI().addToast(new Toast("Já existe uma torre neste local!", 15, 15));
          return;
        }
      }

      game.addEntity(tw);
      game.modifyMoney(-100);
    }
  }

  public void handleClick(int x, int y) {
    lastX = x;
    lastY = y;
    hasLastClick = true;
  }
}
