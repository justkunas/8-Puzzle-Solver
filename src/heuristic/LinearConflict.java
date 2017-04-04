package heuristic;

import algorithms.Node;
import board.Board;

public class LinearConflict implements IHeuristic{

	@Override
	public int apply(Node node, Board goalState) {
		int hCost = 0;
		Board currBoard = node.getBoard();
		
		Heuristics.MANHATTAN_DISTANCE.heuristic().apply(node, goalState);
		hCost = node.gethCost();
		
		for (int i = 0; i < currBoard.getBoard().length; i++) {
			for (int j = 0; j < currBoard.getBoard()[i].length; j++) {
				int checking = currBoard.getBoard()[i][j];
				if(goalState.getNumberLoc(checking)[0] == i){
					switch(j){
					case 0:
						if(goalState.getNumberLoc(currBoard.getBoard()[i][1])[0] == j){
							hCost += 2;
						}
					case 1:
						if(goalState.getNumberLoc(currBoard.getBoard()[i][2])[0] <= j){
							hCost += 2;
						}
						break;
					}
				}
			}
		}
		
		node.sethCost(hCost);
		return hCost;
	}

}
