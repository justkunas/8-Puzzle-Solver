package heuristic;

import algorithms.Node;
import board.Board;

public class Nilsson implements IHeuristic{

	@Override
	public int apply(Node node, Board goalState) {
		Heuristics.MANHATTAN_DISTANCE.heuristic().apply(node, goalState);
		
		int hCost = node.gethCost();
		int score = 0;
		
		int[][] fixedGoal = {{1,2,3},{8,0,4},{7,6,5}};
		int[][] currLoc = node.getBoard().getBoard();
		goalState = new Board(fixedGoal);
		
		score += (currLoc[1][1] != 0)?  1:0;
		
		for (int i = 0; i < currLoc.length; i++) {
			for (int j = 0; j < currLoc[i].length; j++) {
				
				if(i != 1 && j != 1){
					switch(i){
					case 0:
						if(j != 2){
							score += ((currLoc[i][j] + 1) != currLoc[i][j+1])? 2:0;
						}else{
							score += ((currLoc[i][j] + 1) != currLoc[i+1][j])? 2:0;
						}
						break;
					case 1:
						if(j == 0){
							score += (currLoc[0][0] != 1)? 2:0;	
						}else if(j == 2){
							score += ((currLoc[i][j] + 1) != currLoc[i+1][j])? 2:0;
						}
						break;
					case 2:
						if(j != 0){
							score += ((currLoc[i][j] + 1) != currLoc[i][j-1])? 2:0;
						}else{
							score += ((currLoc[i][j] + 1) != currLoc[i-1][j])? 2:0;
						}
						break;
					}
				}
			}
		}
		
		score *= 3;
		hCost += score;
		node.sethCost(hCost);
		return hCost;
	}

}
