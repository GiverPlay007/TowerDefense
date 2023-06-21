package me.giverplay.towerdefense.events;

import me.giverplay.towerdefense.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Listeners implements MouseListener, MouseMotionListener {
  private final Game game;

  public Listeners(Game game) {
    this.game = game;
    this.game.addMouseListener(this);
    this.game.addMouseMotionListener(this);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    game.getController().handleClick(e.getX(), e.getY());
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseDragged(MouseEvent e) {
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    game.getController().handleMouseMove(e.getX(), e.getY());
  }
}
