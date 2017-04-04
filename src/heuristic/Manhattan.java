package heuristic;

import algorithms.Node;
import board.Board;

public class Manhattan implements IHeuristic{
	
	@Override
	public int apply(Node node, Board goalState) {
		int hCost = 0;
		
		for (int i = 0; i < 9; i++) {
			hCost += ((Math.abs(goalState.getNumberLoc(i)[0]-node.getBoard().getNumberLoc(i)[0]) + 
					(Math.abs(goalState.getNumberLoc(i)[1]-node.getBoard().getNumberLoc(i)[1]))));
		}
		
		node.sethCost(hCost);
		return hCost;
	}
}
