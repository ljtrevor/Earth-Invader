package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.src.classes.EntityA;



public class Bullet extends GameObject implements EntityA {
	

	private Textures tex;
	private Game game;
	
	public Bullet(double x, double y, Textures tex, Game game) {
		super(x,y);
		this.tex = tex;
		this.game = game;
		
	}
	
	public void tick() {
		y-= 10;
		
	}
	
	public void render(Graphics g) {
		g.drawImage(tex.missile,(int)x,(int)y,null);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y,32,32);
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getX() {
		return this.x;
	}

}
