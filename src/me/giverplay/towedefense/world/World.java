package me.giverplay.towedefense.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import me.giverplay.towedefense.Game;
import me.giverplay.towedefense.algorithms.AStar;
import me.giverplay.towedefense.algorithms.Node;
import me.giverplay.towedefense.algorithms.Vector2i;
import me.giverplay.towedefense.entities.Spawner;
import me.giverplay.towedefense.graphics.Cores;

public class World
{
	public static final int TILE_SIZE = 32;
	
	private static Tile[] tiles;
	private static List<Node> path;
	
	private static Vector2i inicio;
	private static Vector2i fim;
	
	private Game game;
	
	private int width;
	private int height;
	
	public World(String dir)
	{
		game = Game.getGame();
		
		try
		{
			initializeWorld(dir);
			path = AStar.findPath(this, inicio, fim);
		} 
		catch (IOException | ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Falha na inicialização do mundo :(\nErro: " + e.getLocalizedMessage() + "\nCausa: " + e.getCause()
				+ "\nStacktrace: \n");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void initializeWorld(String path) throws IOException, ArrayIndexOutOfBoundsException
	{
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			
			width = map.getWidth();
			height = map.getHeight();
			
			int lenght = width * height;
			int[] pixels = new int[lenght];
			
			tiles = new Tile[lenght];
			
			map.getRGB(0, 0, width, height, pixels, 0, width);
			
			for(int xx = 0; xx < width; xx++)
			{
				for(int yy = 0; yy < height; yy++)
				{
					int index = xx + (yy * width);
					
					tiles[index] = new GrassTile(xx * TILE_SIZE, yy * TILE_SIZE);
					
					switch (pixels[index])
					{	
						case Cores.TILE_PATH:
							tiles[index] = new PathTile(xx * TILE_SIZE, yy * TILE_SIZE);
							break;
							
						case Cores.LOC_SPAWNER:
							tiles[index] = new PathTile(xx * TILE_SIZE, yy * TILE_SIZE);
							game.addEntity(new Spawner(xx * TILE_SIZE, yy * TILE_SIZE));
							inicio = new Vector2i(xx, yy);
							break;
							
						case Cores.LOC_END:
							tiles[index] = new PathTile(xx * TILE_SIZE, yy * TILE_SIZE);
							fim = new Vector2i(xx, yy);
							break;
							
						default:
							break;
					}
				}
			}
	}
	
	public void render(Graphics g)
	{
		int xs = 0;
		int ys = 0;
		int xf = Game.WIDTH;
		int yf = Game.HEIGHT;
		
		
		for(int xx = xs; xx <= xf; xx++)
		{
			for(int yy = ys; yy <= yf; yy++)
			{
				
				if(xx < xs || yy < ys || xx >= width || yy >= height)
					continue;
				
				Tile tile = tiles[xx + yy * width];
				
				if(tile != null)
					tile.render(g);
			}
		}
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public Tile[] getTiles()
	{
		return tiles;
	}
	
	public static List<Node> getPath()
	{
		List<Node> nodeL = new ArrayList<>();
		
		for(int i = 0; i < path.size(); i++)
			nodeL.add(path.get(i));
		
		return nodeL;
	}
	
	public static boolean canMove(int xn, int yn)
	{
		int x1 = xn / TILE_SIZE;
		int y1 = yn / TILE_SIZE;
		
		int x2 = (xn + TILE_SIZE -1) / TILE_SIZE;
		int y2 = yn / TILE_SIZE;
		
		int x3 = xn / TILE_SIZE;
		int y3 = (yn + TILE_SIZE -1) / TILE_SIZE;
		
		int x4 = (xn + TILE_SIZE -1) / TILE_SIZE;
		int y4 = (yn + TILE_SIZE -1) / TILE_SIZE;
		
		World world = Game.getGame().getWorld();
		
		int index1 = x1 + (y1 * world.getWidth());
		int index2 = x2 + (y2 * world.getWidth());
		int index3 = x3 + (y3 * world.getWidth());
		int index4 = x4 + (y4 * world.getWidth());
		
		return !(tiles[index1].isRigid()
				|| tiles[index2].isRigid()
				|| tiles[index3].isRigid()
				|| tiles[index4].isRigid());
	}
}
