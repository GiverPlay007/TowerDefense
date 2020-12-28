package me.giverplay.towedefense.entities;

import me.giverplay.towedefense.Game;

public class Spawner extends Entity
{
	private int maxTime = 1;
	private int time = 0;
	
	public Spawner(double x, double y)
	{
		super(x, y, 32, 32, 0, SPRITE_SPAWNER);
		
		setDepth(2);
	}
	
	@Override
	public void tick()
	{
		time++;
		
		if(time >= maxTime)
		{
			time = 0;
			maxTime = random.nextInt(120 - 60) + 60;
			Game.getGame().addEntity(new Enemy(getX(), getY(), random.nextInt(2) + 1));
		}
	}
}
