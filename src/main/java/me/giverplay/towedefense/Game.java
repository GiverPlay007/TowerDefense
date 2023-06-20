package me.giverplay.towedefense;

import me.giverplay.towedefense.entities.Entity;
import me.giverplay.towedefense.events.Listeners;
import me.giverplay.towedefense.events.TowerController;
import me.giverplay.towedefense.graphics.FontUtils;
import me.giverplay.towedefense.graphics.Spritesheet;
import me.giverplay.towedefense.graphics.UI;
import me.giverplay.towedefense.sound.Sound;
import me.giverplay.towedefense.world.World;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class Game extends Canvas implements Runnable {
  private static final long serialVersionUID = 1L;
  private static final int MAX_GAME_OVER_FRAMES = 30;

  public static final int WIDTH = 1024;
  public static final int HEIGHT = 640;

  public static int FPS = 0;

  private List<Entity> entities;
  private List<Entity> toRemoveEntities;

  private static Game game;

  private TowerController controller;
  private Spritesheet sprite;
  private World world;
  private UI ui;

  private Thread thread;
  private JFrame frame;

  private boolean isRunning = false;
  private boolean showGameOver = true;
  private boolean isDead = false;

  private int gameOverFrames = 0;
  private int life;
  private int maxLife;
  private int money;

  public static Game getGame() {
    return game;
  }

  public Game() {
    setPreferredSize(new Dimension(WIDTH, HEIGHT));

    setupFrame();
    setupAssets();
  }

  public static void main(String[] args) {
    Game game = new Game();
    game.start();
  }

  private void setupFrame() {
    frame = new JFrame("Game 05 - Tower Defense");
    frame.add(this);
    frame.setResizable(false);
    frame.setUndecorated(false);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    new Listeners(this);
  }

  private void setupAssets() {
    game = this;

    life = 10;
    maxLife = 10;
    money = 300;

    entities = new ArrayList<>();
    toRemoveEntities = new ArrayList<>();

    controller = new TowerController(this);
    sprite = new Spritesheet("/Spritesheet.png");
    world = new World("/World.png");

    ui = new UI();

    isDead = false;
  }

  public synchronized void start() {
    isRunning = true;
    thread = new Thread(this);
    thread.start();
  }

  public synchronized void stop() {
    isRunning = false;

    try {
      thread.join();
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
  }

  public synchronized void restart() {
    setupAssets();
  }

  public static void handleRestart() {
    game.restart();
  }

  @Override
  public void run() {
    requestFocus();

    long lastTime = System.nanoTime();
    long timer = System.currentTimeMillis();

    double ticks = 60.0D;
    double ns = 1000000000 / ticks;
    double delta = 0.0D;

    int fps = 0;

    while(isRunning) {
      long now = System.nanoTime();
      delta += (now - lastTime) / ns;
      lastTime = now;

      if(delta >= 1) {
        tick();
        render();

        delta--;
        fps++;
      }

      if(System.currentTimeMillis() - timer >= 1000) {
        FPS = fps;
        fps = 0;
        timer += 1000;
      }

      try {
        Thread.sleep(2);
      } catch(Exception ignored) { }
    }

    stop();
  }

  public void tick() {
    if(life <= 0) {
      handleGameOver();
    }

    if(!isDead) {
      controller.tick();

      for(int i = 0; i < entities.size(); i++) entities.get(i).tick();

      entities.removeAll(toRemoveEntities);
      toRemoveEntities.clear();
    }
  }

  public void render() {
    BufferStrategy bs = this.getBufferStrategy();

    if(bs == null) {
      this.createBufferStrategy(3);
      return;
    }

    Graphics g = bs.getDrawGraphics();

    g.setColor(new Color(110, 200, 255));
    g.fillRect(0, 0, WIDTH, HEIGHT);

    world.render(g);

    entities.sort(Entity.sortDepth);

    for(Entity entity : entities) entity.render(g);

    ui.render(g);

    if(isDead) {
      Graphics2D g2 = (Graphics2D) g;

      g2.setColor(new Color(0, 0, 0, 100));
      g2.fillRect(0, 0, WIDTH, HEIGHT);

      String txt = "GameOver";
      g.setColor(Color.WHITE);
      g.setFont(FontUtils.getFont(32, Font.BOLD));
      g.drawString(txt, (WIDTH - g.getFontMetrics(g.getFont()).stringWidth(txt)) / 2, HEIGHT / 2);

      gameOverFrames++;

      if(gameOverFrames > MAX_GAME_OVER_FRAMES) {
        gameOverFrames = 0;
        showGameOver = !showGameOver;
      }

      if(showGameOver) {
        g.setFont(FontUtils.getFont(24, Font.BOLD));
        g.drawString("> Aperte ENTER para reiniciar <", (WIDTH - g.getFontMetrics(g.getFont()).stringWidth("> Aperte ENTER para reiniciar <")) / 2, HEIGHT / 2 + 28);
      }
    }

    bs.show();
  }

  public Spritesheet getSpritesheet() {
    return this.sprite;
  }

  public World getWorld() {
    return this.world;
  }

  public List<Entity> getEntities() {
    return this.entities;
  }

  public void handleGameOver() {
    this.isDead = true;
    Sound.lose.play();
  }

  public void removeEntity(Entity e) {
    toRemoveEntities.add(e);
  }

  public void addEntity(Entity e) {
    entities.add(e);
  }

  public int getLife() {
    return this.life;
  }

  public int getMaxLife() {
    return this.maxLife;
  }

  public void removeLife() {
    life--;
  }

  public void onEnemyDestroy() {
    modifyMoney(100);
    life++;

    if(life > maxLife) life = maxLife;
  }

  public void modifyMoney(int amount) {
    money += amount;
  }

  public int getMoney() {
    return this.money;
  }

  public TowerController getController() {
    return this.controller;
  }

  public UI getUI() {
    return this.ui;
  }
}
