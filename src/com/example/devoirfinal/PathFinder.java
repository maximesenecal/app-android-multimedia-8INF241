package com.example.devoirfinal;

import android.annotation.SuppressLint;
import java.util.*;

public class PathFinder extends AStar<PathFinder.Node>
{
	private int[][] map;
	int xTo=0;
	int yTo=0;
	
	public static class Node{
		public int x;
		public int y;
		Node(int x, int y){
			this.x = x; 
			this.y = y;
		}
		public String toString(){
			return "(" + x + ", " + y + ") ";
		} 
	}
	
	public PathFinder(int[][] map){
		this.map = map;
	}
	
	public void setXY(int x, int y)
	{
		xTo=x;
		yTo=y;
	}
	
	protected boolean isGoal(Node node){
		return (node.x == xTo) && (node.y == yTo);            //POINT D'ARRIVEE /!\
	}
	
	protected Double g(Node from, Node to){
		
		if(from.x == to.x && from.y == to.y)
			return 0.0;
		
		if(map[to.y][to.x] == 1)
			return 1.0;
		
		return Double.MAX_VALUE;
	}
	
	@SuppressLint("UseValueOf")
	protected Double h(Node from, Node to){
		/* Calcule la distance de manhattan  */
		return new Double(Math.abs(map[0].length - 1 - to.x) + Math.abs(map.length - 1 - to.y));
	}
	
	protected List<Node> generateSuccessors(Node node){
		List<Node> ret = new LinkedList<Node>();
		int x = node.x;
		int y = node.y;
		if(y < map.length - 1 && map[y+1][x] == 1)
			ret.add(new Node(x, y+1));
		
		if(x < map[0].length - 1 && map[y][x+1] == 1)
			ret.add(new Node(x+1, y));
		
		return ret;
	}                               
}