package me.giverplay.towedefense;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import me.giverplay.towedefense.entities.Entity;
import me.giverplay.towedefense.events.Listeners;
import me.giverplay.towedefense.graphics.FontUtils;
import me.giverplay.towedefense.graphics.Spritesheet;
import me.giverplay.towedefense.graphics.UI;
import me.giverplay.towedefense.sound.Sound;
import me.giverplay.towedefense.world.World;

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 640;
	
	public static int FPS = 0;
	
	private List<Entity> entities;
	
	private static Game game;
	
	private Spritesheet sprite;
	private World world;
	private UI ui;
	
	private Thread thread;
	private JFrame frame;
	
	private boolean isRunning = false;
	private boolean showGameOver = true;
	private boolean morreu = false;
	private boolean nextLevel = false;
	
	private int gameOverFrames = 0;
	private int maxGameOverFrames = 30;
	
	public static Game getGame()
	{
		return game;
	}
	
	// Métodos Startup | TODO
	public Game()
	{
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		setupFrame();
		setupAssets();
	}
	
	public static void main(String[] args)
	{
		Game game = new Game();
		game.start();
	}
	
	private void setupFrame()
	{
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
	
	private void setupAssets()
	{
		game = this;
		
		entities = new ArrayList<>();
		
		sprite = new Spritesheet("/Spritesheet.png");
		world = new World("/World.png");
		
		ui = new UI();
		
		nextLevel = false;
		morreu = false;
	}
	
	// Metodos de Controle do Fluxo | TODO
	
	public synchronized void start()
	{
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		isRunning = false;
		
		try
		{
			thread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized void restart()
	{
		setupAssets();
	}
	
	public static void handleRestart()
	{
		game.restart();
	}
	
	// Core | TODO
	
	@Override
	public void run()
	{
		requestFocus();
		
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		
		double ticks = 60.0D;
		double ns = 1000000000 / ticks;
		double delta = 0.0D;
		
		int fps = 0;
		
		while(isRunning)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1)
			{
				tick();
				render();
				
				delta--;
				fps++;
			}
			
			if(System.currentTimeMillis() - timer >= 1000)
			{
				FPS = fps;
				fps = 0;
				timer += 1000;
			}
		}
		
		stop();
	}
	
	public synchronized void tick()
	{
		if(!morreu && !nextLevel)
		{
			for(int i = 0; i < entities.size(); i++) entities.get(i).tick();
		}
	}
	
	public synchronized void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(new Color(110, 200, 255));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		/** Renderiaza��o do Jogo **/
		
		world.render(g);
		
		Collections.sort(entities, Entity.sortDepth);
		
		for(int i = 0; i < entities.size(); i++) entities.get(i).render(g);
		
		ui.render(g);
		
		/******/
		
		if(morreu || nextLevel)
		{
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH, HEIGHT);
			
			String txt = morreu ? "Game Over" : "Você Venceu!";
			g.setColor(Color.WHITE);
			g.setFont(FontUtils.getFont(32, Font.BOLD));
			g.drawString(txt, (WIDTH - g.getFontMetrics(g.getFont()).stringWidth(txt)) / 2, HEIGHT / 2);
			
			gameOverFrames++;
			
			if(gameOverFrames > maxGameOverFrames)
			{
				gameOverFrames = 0;
				showGameOver = !showGameOver;
			}
			
			if(showGameOver)
			{
				g.setFont(FontUtils.getFont(24, Font.BOLD));
				g.drawString("> Aperte ENTER para reiniciar <", (WIDTH - g.getFontMetrics(g.getFont()).stringWidth("> Aperte ENTER para reiniciar <")) / 2, HEIGHT / 2 + 28);
			}
		}
		
		bs.show();
	}
	
	// Getters e Setters | TODO
	
	public Spritesheet getSpritesheet()
	{
		return this.sprite;
	}
	
	public World getWorld()
	{
		return this.world;
	}
	
	public List<Entity> getEntities()
	{
		return this.entities;
	}
	
	public boolean morreu()
	{
		return this.morreu;
	}
	
	public boolean venceu()
	{
		return this.nextLevel;
	}

	public void matar()
	{
		this.morreu = true;
		Sound.lose.play();
	}
	
	public void removeEntity(Entity e)
	{
		entities.remove(e);
	}
	
	public void addEntity(Entity e)
	{
		entities.add(e);
	}
}
