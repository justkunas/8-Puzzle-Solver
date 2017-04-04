package heuristic;

import algorithms.Node;
import board.Board;

public interface IHeuristic {
	
	public int apply(Node node, Board goalState);
	
}
