package me.giverplay.towedefense.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.giverplay.towedefense.Game;
import me.giverplay.towedefense.entities.Entity;

public class UI
{
	private Game game;
	
	public UI()
	{
		game = Game.getGame();
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.setFont(FontUtils.getFont(11, Font.PLAIN));
		g.drawString("FPS: " + Game.FPS, 2, 12);
	}
}
