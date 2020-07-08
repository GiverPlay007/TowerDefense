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
		g.drawString("FPS: " + Game.FPS, 2, Game.HEIGHT - 5);
		
		int coe = 24;
		
		g.drawImage(Entity.SPRITE_BAR, Game.WIDTH - 80 - coe, coe + 1, 100, 30, null);
		g.drawImage(Entity.SPRITE_MONEY, Game.WIDTH - coe - 75, coe, null);
		
		g.setFont(FontUtils.getFont(14, Font.PLAIN));
		g.drawString("$" + game.getMoney(), Game.WIDTH -65, 46);
		
		for(int i = 0; i < game.getMaxLife(); i++)
		{
			g.drawImage(i < game.getLife() ? Entity.SPRITE_HEART_FULL : Entity.SPRITE_HEART_NON_FULL, i * (coe + 5) + 5, 0, coe, coe, null);
		}
	}
}
