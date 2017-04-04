package heuristic;

import algorithms.Node;
import board.Board;

public class MisplacedTiles implements IHeuristic{

	@Override
	public int apply(Node node, Board goalState) {
		
		int hCost = 0;
		
		for (int[] row : node.getBoard().getBoard()) {
			for (int tile : row) {
				if(((node.getBoard().getNumberLoc(tile)[0] != goalState.getNumberLoc(tile)[0])
						||(node.getBoard().getNumberLoc(tile)[1] != goalState.getNumberLoc(tile)[1]))
						&& tile != 0){
					hCost++;
				}
			}
		}
		
		node.sethCost(hCost);
		return hCost;
	}

}
