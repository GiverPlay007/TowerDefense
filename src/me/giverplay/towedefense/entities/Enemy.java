package me.giverplay.towedefense.entities;

import java.awt.Graphics;

import me.giverplay.towedefense.Game;
import me.giverplay.towedefense.sound.Sound;
import me.giverplay.towedefense.world.World;

public class Enemy extends Entity
{
	private Game game;
	
	private int coeff;
	private int anim;
	private int maxAnim = 4;
	private int animF = 0;
	private int maxAnimF = 10;
	
	public Enemy(double x, double y, double speed)
	{
		super(x, y, 32, 32, speed, null);
		
		setDepth(1);
		game = Game.getGame();
		
		coeff = random.nextInt(9);
		coeff = coeff == 1 ? 0 : coeff == 2 ? 0 : coeff == 4 ? 3 : coeff == 5 ? 3 : coeff == 7 ? 6 : coeff == 8 ? 6 : coeff;	
		
		anim = coeff;
		maxAnim = coeff + 3;
		
		path = World.getPath();
	}
	
	@Override
	public void tick()
	{
		if(path.size() > 0)
			followPath(path);
		else
			x += speed;
		
		if(x > Game.WIDTH)
		{
			destroy();
			game.removeLife();
		}
	}
	
	@Override
	public void render(Graphics g)
	{
		animF++;
		
		if(animF >= maxAnimF)
		{
			anim++;
			animF = 0;
			
			if(anim >= maxAnim)
			{
				anim = coeff;
			}
		}
		
		g.drawImage(SPRITE_ENEMY[anim], getX(), getY(), null);
	}
	
	@Override
	public void destroy()
	{
		super.destroy();
		Sound.hit2.play();
	}
}
