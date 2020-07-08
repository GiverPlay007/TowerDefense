package me.giverplay.towedefense.entities;

import java.awt.Graphics;

import me.giverplay.towedefense.Game;

public class Player extends Entity
{
	private boolean up, down, left, right;
	
	private int maxVida = 5;
	private int vida = 5;
	
	private Game game;
	
	public Player(int x, int y, int width, int height)
	{
		super(x, y, width, height, 1, null);
		game = Game.getGame();
		
		setDepth(2);
	}
	
	@Override
	public void tick()
	{
		
	}
	
	@Override
	public void render(Graphics g)
	{
		super.render(g);
	}
	
	public boolean walkingRight()
	{
		return this.right;
	}
	
	public boolean walkingLeft()
	{
		return this.left;
	}
	
	public boolean walkingDown()
	{
		return this.down;
	}
	
	public boolean walkingUp()
	{
		return this.up;
	}
	
	public void setWalkingRight(boolean walking)
	{
		this.right = walking;
	}
	
	public void setWalkingLeft(boolean walking)
	{
		this.left = walking;
	}
	
	public int getLife()
	{
		return vida;
	}
	
	public void modifyLife(int toModify)
	{
		vida += toModify;
		
		if (vida < 0)
			vida = 0;
		if (vida > maxVida)
			vida = maxVida;
	}
	
	public int getMaxLife()
	{
		return this.maxVida;
	}
}
