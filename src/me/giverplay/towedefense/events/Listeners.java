package me.giverplay.towedefense.events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import me.giverplay.towedefense.Game;

public class Listeners implements MouseListener
{
	private Game game;
	
	public Listeners(Game game)
	{
		this.game = game;
		this.game.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}
}
