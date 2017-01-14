package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.game.src.classes.EntityA;
import com.game.src.classes.EntityB;

public class Enemy extends GameObject implements EntityB {

	
	
	private Textures tex;
	
	Random r = new Random();
	
	int speed = r.nextInt(8-3) + 3 ;
	private Game game;
	private Controller c;
	
	public Enemy(double x, double y, Textures tex,Controller c, Game game) {
		super(x,y);
		this.tex = tex;
		this.c = c;
		this.game = game;
		
	}
	public Enemy(double x, double y) {
		super(x,y);
	}
	
	public void tick(){
		y+=speed;
		
		if(y > Game.HEIGHT * Game.SCALE) {
			speed = r.nextInt(8-3) + 3 ;
			y = -10;
			x = r.nextInt(640);
			
		}
		
		for(int i =0; i < game.ea.size();i++) {
			EntityA tempEnt = game.ea.get(i);
			
			if(Physics.Collisions(this, tempEnt)) {
				c.removeEntity(this);
				c.removeEntity(tempEnt);
				game.setEnemyKill(game.getEnemyKill()+1);
				game.settotalEnemyKillCount(game.gettotalEnemyKillCount() + 1);
			}
		}
		
		
	}
	
	public void render(Graphics g) {
		g.drawImage(tex.enemy, (int) x, (int)y,null);
	
	}
	
	public void stationaryRender(Graphics g,int x, int y) {
		g.drawImage(tex.enemy,x,y,null);
	}
	
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y,32,32);
	}
	
	
	public double getY() {
	return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
		
	}
	
	
	
}
