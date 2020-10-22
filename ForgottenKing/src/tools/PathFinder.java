package tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import creatures.Creature;

public class PathFinder {
	private ArrayList<Point> open;
	private ArrayList<Point> closed;
	private HashMap<Point, Point> parents;
	private HashMap<Point,Integer> totalCost;

	public PathFinder() {
		this.open = new ArrayList<Point>();
		this.closed = new ArrayList<Point>();
		this.parents = new HashMap<Point, Point>();
		this.totalCost = new HashMap<Point, Integer>();
	}

	private int heuristicCost(Point from, Point to) {
		return Math.max(Math.abs(from.x - to.x), Math.abs(from.y - to.y));
		//return new Line(from.x, from.y, to.x, to.y).getPoints().size();
	}

	private int costToGetTo(Point from) {
		return parents.get(from) == null ? 0 : (1 + costToGetTo(parents.get(from)));
	}

	private int totalCost(Point from, Point to) {
		if (totalCost.containsKey(from))
			return totalCost.get(from);

		int cost = costToGetTo(from) + heuristicCost(from, to);
		totalCost.put(from, cost);
		return cost;
	}

	private void reParent(Point child, Point parent){
		parents.put(child, parent);
		totalCost.remove(child);
	}

	public ArrayList<Point> findPath(Creature creature, Point start, Point end, int maxTries) {
		open.clear();
		closed.clear();
		parents.clear();
		totalCost.clear();

		open.add(start);

		for (int tries = 0; tries < maxTries && open.size() > 0; tries++){
			Point closest = getClosestPoint(end);

			open.remove(closest);
			closed.add(closest);

			if (closest.equals(end))
				return createPath(start, closest);
			else
				checkNeighbors(creature, end, closest);
		}
		return null;
	}

	private Point getClosestPoint(Point end) {
		Point closest = open.get(0);
		for (Point other : open){
			if (totalCost(other, end) < totalCost(closest, end))
				closest = other;
		}
		return closest;
	}

	private void checkNeighbors(Creature creature, Point end, Point closest) {
		for (Point neighbor : closest.neighbors8()) {
			if (closed.contains(neighbor)
					|| !creature.canEnter(neighbor.x, neighbor.y, creature.z)
					&& !neighbor.equals(end))
				continue;

			if (open.contains(neighbor))
				reParentNeighborIfNecessary(closest, neighbor);
			else
				reParentNeighbor(closest, neighbor);
		}
	}

	private void reParentNeighbor(Point closest, Point neighbor) {
		reParent(neighbor, closest);
		open.add(neighbor);
	}

	private void reParentNeighborIfNecessary(Point closest, Point neighbor) {
		Point originalParent = parents.get(neighbor);
		double currentCost = costToGetTo(neighbor);
		reParent(neighbor, closest);
		double reparentCost = costToGetTo(neighbor);

		if (reparentCost < currentCost)
			open.remove(neighbor);
		else
			reParent(neighbor, originalParent);
	}

	private ArrayList<Point> createPath(Point start, Point end) {
		ArrayList<Point> path = new ArrayList<Point>();

		while (!end.equals(start)) {
			path.add(end);
			end = parents.get(end);
		}

		Collections.reverse(path);
		return path;
	}
}
//
//package tools;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import creatures.Creature;
//
///**
// *
// * Utilizing A* pathfinding for wandering packs on the surface
// *
// */
//
//
//public class PathFinder {
//	private Creature creature;
//	private Point start;
//	private Point target;
//	private ArrayList<Point> open;
//	private ArrayList<Point> closed;
//	private HashMap<Point, Point> parents;
//	private HashMap<Point, Integer> cost;
//
//	public PathFinder(Creature creature, Point target) {
//		this.target = target;
//		this.creature = creature;
//		open = new ArrayList<Point>();
//		closed = new ArrayList<Point>();
//		parents = new HashMap<Point,Point>();
//		cost = new HashMap<Point,Integer>();
//		start = new Point(creature.x, creature.y, creature.z);
//		open.add(start);
//		parents.put(start, null);
//		cost.put(start, 0);
//		main();
//	}
//	
//	/**
//	 * Main Method
//	 * While the open list is not empty:
//	 *  - Set the current node as the node with the lowest cost
//	 *  - Remove that node from open and add it to closed
//	 *  - Add all of its children to the open list if they are not already there and not already closed (by area)
//	 *  - If the child is already in the open list, compare the g value of its old parent and current, set current as the parent if its g value is less
//	 * 	- If the target area is in the list, break from the loop, otherwise continue until all available options are closed
//	 */
//	private void main() {
//		while (open.size() > 0) {
//			Point current = findNext();
//			List<Point> children = current.neighbors8();
//			
//			if (checkTarget()) {
//				break;
//			}
//			if (current.equals(target))
//				break;
//			
//			for (Point c : children) {
//				
//				if (closed.contains(c) 
//						|| (!creature.canEnter(c.x, c.y, c.z) && !c.equals(target)) 
//						|| (!creature.canSee(c.x, c.y, c.z)))
//					continue;
//				
//				if (!open.contains(c))
//					addNode(c, current);
//				else
//					changeParentIfNeeded(c, current);
//			}
//		}
//	}
//	
//	/**
//	 * A method that scans the open list for the one with the lowest cost, moves that node to the closed
//	 * list and returns it.
//	 */
//	private Point findNext() {
//		Point next = getMin();
//		closed.add(next);
//		open.remove(next);
//		return next;
//	}
//	
//	
//	/**
//	 * A method that returns the lowest node, in terms of g+h
//	 */
//	private Point getMin() {
//		Point min = open.get(0);
//		for (Point p : open) {
//			if (totalCost(p) < totalCost(min))
//				min = p;
//		}
//		return min;
//	}
//	
//	/**
//	 * Return the total cost of the point being considered
//	 */
//	private int totalCost(Point p) {
//		if (cost.containsKey(p))
//			return cost.get(p);
//		int g = cost.get(parents.get(p)) + 1;
//		int h = getHeuristicCost(p);
//		cost.put(p, g+h);
//		return g+h;
//	}
//	
//	/**
//	 * First calculate the difference of X and Y, and then calculate the hypotenuse of the new triangle
//	 */
//	private int getHeuristicCost(Point p) {
//		int xDif = Math.abs(p.x - target.x);
//		int yDif = Math.abs(p.y - target.y);
//		return xDif+yDif;
//	}
//	
//	/**
//	 * Changes the parent of point c if the cost of the new parent is less than the cost of the old parent
//	 */
//	private void changeParentIfNeeded(Point c, Point newParent) {
//		if (totalCost(newParent) < totalCost(parents.get(c)))
//			parents.put(c, newParent);
//	}
//	
//	/**
//	 * Sets the parent of a node and adds it to the open set
//	 */
//	private void addNode(Point c, Point parent) {
//		open.add(c);
//		parents.put(c,parent);
//	}
//	
//	/**
//	 * A method that checks every node in closed, if the target area is in one of these nodes, return true
//	 */
//	private boolean checkTarget() {
//		for (Point c : closed) {
//			if (c.equals(target))
//				return true;
//		}
//		return false;
//	}
//
//	/**
//	 * Returns an arraylist of points that the creature is to follow
//	 */
//	private ArrayList<Point> createPath(Point start, Point end) {
//		ArrayList<Point> path = new ArrayList<Point>();
//		while (end != null && !end.equals(start)) {
//			path.add(end);
//			end = parents.get(end);
//		}
//
//		Collections.reverse(path);
//		return path;
//	}
//	public List<Point> getPoints() {
//		return createPath(start, target);
//	}
//	
//}
