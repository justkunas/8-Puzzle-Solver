package heuristic;

import algorithms.Node;
import board.Board;

public class OutOfPlace implements IHeuristic{

	@Override
	public int apply(Node node, Board goalState) {
		int hCost = 0;
		Board currBoard = node.getBoard();
		
		for (int i = 0; i < 9; i++) {
			if(i!=0){
				if(currBoard.getNumberLoc(i)[0] != goalState.getNumberLoc(i)[0]){
					hCost++;
				}
				if(currBoard.getNumberLoc(i)[1] != goalState.getNumberLoc(i)[1]){
					hCost++;
				}
			}
		}
		
		node.sethCost(hCost);
		return hCost;
	}

}
