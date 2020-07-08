package me.giverplay.towedefense.entities;

import me.giverplay.towedefense.Game;
import me.giverplay.towedefense.sound.Sound;

public class Enemy extends Entity
{
	private Game game;
	
	public Enemy(double x, double y, int width, int height, double speed)
	{
		super(x, y, width, height, speed, null);
		
		setDepth(1);
		
		game = Game.getGame();
	}
	
	@Override
	public void tick()
	{
		
	}
	
	@Override
	public void destroy()
	{
		super.destroy();
		Sound.hit2.play();
	}
}
