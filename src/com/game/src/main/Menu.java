package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Menu {
	
	public Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 120, 150, 100,50);
	
	public Rectangle ExitButton = new Rectangle(Game.WIDTH / 2 + 120, 350, 100,50);
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Font font1 = new Font("arial",Font.BOLD,50);
		g.setFont(font1);
		g.setColor(Color.white);
		g.drawString("Earth Invader", Game.WIDTH / 2,100);
		
		Font font2 = new Font("arial",Font.BOLD,30);
		g.setFont(font2);
		g.drawString("Play", playButton.x+20, playButton.y+35);
		
		g.drawString("Exit", ExitButton.x+20, ExitButton.y+35);
		g2d.draw(playButton);

		g2d.draw(ExitButton);
		
	}
}
