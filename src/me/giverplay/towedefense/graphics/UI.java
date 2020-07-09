package me.giverplay.towedefense.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import me.giverplay.towedefense.Game;
import me.giverplay.towedefense.entities.Entity;

public class UI
{
	private ArrayList<Toast> toasts = new ArrayList<>();
	
	private Game game;
	private Toast toast = null;
	private Color currentColor = null;
	
	private boolean showingToast = false;
	
	private int toastFrames = 0;
	private int maxToastFrames = 0;
	private int toastFadeIn = 0;
	private int toastFadeOut = 0;
	private int time = 80;
	
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
		
		advanceToast(g);
	}
	
	public void advanceToast(Graphics g)
	{
		if(!showingToast)
		{
			if(toasts.size() == 0)
				return;
			
			toast = toasts.get(0);
			showingToast = true;
			toastFadeIn = toast.getFadeIn();
			toastFadeOut = toast.getFadeOut();
			maxToastFrames = toastFadeIn + toastFadeOut + time;
		}
		
		toastFrames++;
		
		if(toastFrames >= maxToastFrames)
		{
			toasts.remove(0);
			toast = null;
			showingToast = false;
			toastFrames = 0;
			
			return;
		}
		
		int alpha = 255;
		
		if(toastFrames <= toastFadeIn)
		{
			alpha = (int) (255 * toastFrames / toastFadeIn);
		}
		else if(toastFrames >= toastFadeIn + time)
		{
			alpha = (int) (255 * ( toastFadeOut - (toastFrames - (toastFadeIn + time))) / toastFadeOut);
		}
		
		currentColor = new Color(255, 255, 255, alpha);
		g.setColor(currentColor);
		
		g.setFont(FontUtils.getFont(32, Font.BOLD));
		int width = FontUtils.stringWidth(g, toast.getText());
		
		g.drawString(toast.getText(), (Game.WIDTH - width) / 2, Game.HEIGHT / 2);
	}
	
	public void addToast(Toast toast)
	{
		toasts.add(toast);
	}
}
