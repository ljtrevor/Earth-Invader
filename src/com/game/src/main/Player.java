//entity A and entity B do not collide with each other

package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.src.classes.EntityA;
import com.game.src.classes.EntityB;

public class Player extends GameObject implements EntityA {
	
	// x and y coordinate of player
	
	private double velx = 0;
	private double vely = 0;
	private Textures tex;
	
	Game game;
	Controller controller;
	

	public Player(double x, double y, Textures tex, Game game, Controller controller) {
		super(x,y);
		this.tex = tex;
		this.game = game;
		this.controller = controller;
	}
	
	public void tick() {
		x+= velx;
		y+= vely;
		
		
		if(x <= 0) 
			x = 0;
		if (x >= 640 - 19)
			x = 640 - 19;
		if (y <= 0)
			y = 0;
		if (y >= 480 -32)
			y = 480 - 32;
		for(int i =0; i < game.eb.size();i++) {
			
			EntityB tempEnt = game.eb.get(i);
			
			if(Physics.Collisions(this, tempEnt)) {
				controller.removeEntity(tempEnt);
				Game.health -= 10;
				game.setEnemyKill(game.getEnemyKill() + 1);
				game.settotalEnemyKillCount(game.gettotalEnemyKillCount() + 1);
			}
			
		}
		
		
	}
	
	public void render(Graphics g) {
		g.drawImage(tex.player,(int)x,(int)y,null);
	}
	
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y,32,32);
	}
	
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void setx(double x) {
		this.x = x;
	}
	
	public void sety(double y) {
		this.y = y;
	}
	
	public void setVelx(double velx) {
		this.velx = velx;
	}
	
	public void setVely(double vely) {
		this.vely = vely;
	}
	
	
	
	
}
