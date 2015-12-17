package com.example.devoirfinal;

import java.util.*;

public abstract class AStar<T>
{

	@SuppressWarnings("rawtypes")
	private class Path implements Comparable{
		public T point;
		public Double f;
		public Double g;
		public Path parent;
		
		public Path(){
			parent = null;
			point = null;
			g = f = 0.0;
		}
		
		public Path(Path p){
			this();
			parent = p;
			g = p.g;
			f = p.f;
		}
		
		@SuppressWarnings("unchecked")
		public int compareTo(Object o){
			Path p = (Path)o;
			return (int)(f - p.f);
		}
		

		public T getPoint(){
			return point;
		}
		

		public void setPoint(T p){
			point = p;
		}
	}
	
	protected abstract boolean isGoal(T node);
	protected abstract Double g(T from, T to);
	protected abstract Double h(T from, T to);
	protected abstract List<T> generateSuccessors(T node);
	
	
	private PriorityQueue<Path> paths;
	private HashMap<T, Double> mindists;
	private Double lastCost;
	private int expandedCounter;
	
	public int getExpandedCounter(){
		return expandedCounter;
	}

	public AStar(){
		paths = new PriorityQueue<Path>();
		mindists = new HashMap<T, Double>();
		expandedCounter = 0;
		lastCost = 0.0;
	}
	
	protected Double f(Path p, T from, T to){
		Double g =  g(from, to) + ((p.parent != null) ? p.parent.g : 0.0);
		Double h = h(from, to);
		
		p.g = g;
		p.f = g + h;
		
		return p.f;
	}
	
	private void expand(Path path){
		T p = path.getPoint();
		Double min = mindists.get(path.getPoint());
		
		/*
		 * If a better path passing for this point already exists then
		 * don't expand it.
		 */
		if(min == null || min.doubleValue() > path.f.doubleValue())
			mindists.put(path.getPoint(), path.f);
		else
			return;
		
		List<T> successors = generateSuccessors(p);
		
		for(T t : successors){
			Path newPath = new Path(path);
			newPath.setPoint(t);
			f(newPath, path.getPoint(), t);
			paths.offer(newPath);
		}
		
		expandedCounter++;
	}
	
	public Double getCost(){
		return lastCost;
	}
	
	public List<T> compute(T start){
		try{
			Path root = new Path();
			root.setPoint(start);
			
			/* Needed if the initial point has a cost.  */
			f(root, start, start);
			
			expand(root);
			
			for(;;){
				Path p = paths.poll();
				
				if(p == null){
					lastCost = Double.MAX_VALUE;
					return null;
				}
					
				T last = p.getPoint();
				
				lastCost = p.g;
				
				if(isGoal(last)){
					LinkedList<T> retPath = new LinkedList<T>();
					
					for(Path i = p; i != null; i = i.parent){
						retPath.addFirst(i.getPoint());
					}
					
					return retPath;
				}
				expand(p);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
}