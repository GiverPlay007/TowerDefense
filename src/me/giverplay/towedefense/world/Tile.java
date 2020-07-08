package me.giverplay.towedefense.world;

import static me.giverplay.towedefense.world.World.TILE_SIZE;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.giverplay.towedefense.Game;

public class Tile
{
	private static Game game = Game.getGame();
	
	public static BufferedImage TILE_GRASS = game.getSpritesheet().getSprite(TILE_SIZE * 0, 0, TILE_SIZE, TILE_SIZE);
	public static BufferedImage TILE_PATH = game.getSpritesheet().getSprite(TILE_SIZE * 1, 0, TILE_SIZE, TILE_SIZE);
	
	private BufferedImage sprite;
	
	private boolean isRigid;
	
	private int x, y;
	
	public Tile(int x, int y, boolean isRigid, BufferedImage sprite)
	{
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.isRigid = isRigid;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(sprite, x, y, null);
	}
	
	public boolean isRigid()
	{
		return this.isRigid;
	}
}
