package com.game.src.main;

import java.util.LinkedList;

import com.game.src.classes.EntityA;
import com.game.src.classes.EntityB;
import com.game.src.main.GameObject;


public class Physics {

	public static boolean Collisions(EntityA enta, EntityB entb) {
		
			
			if(enta.getBounds().intersects(entb.getBounds())) {
				return true;
			}
		
		return false;
	}
	
	public static boolean Collisions(EntityB entb, EntityA enta) {
		
			
			if(entb.getBounds().intersects(enta.getBounds())) {
				return true;
			}
		
		return false;
	}
	
}