package algorithms;

import board.Board;
import board.Movement;

public class Node {
	
	private Board board;
	private final Node parent;
	private int fCost;
	private int gCost;
	private int hCost;
	
	public Node(Board board, Node parent, int gCost) {
		this.setBoard(board);
		this.setgCost(gCost);
		this.parent = parent;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Node getParent() {
		return parent;
	}

	public int getfCost() {
		this.setfCost(this.getgCost() + this.gethCost());
		return fCost;
	}

	public void setfCost(int fCost) {
		this.fCost = fCost;
	}

	public int getgCost() {
		return gCost;
	}

	public void setgCost(int gCost) {
		this.gCost = gCost;
	}

	public int gethCost() {
		return hCost;
	}

	public void sethCost(int hCost) {
		this.hCost = hCost;
	}
	
	public void printNode(){
		getBoard().printBoard();
		System.out.println("H cost: " + gethCost() + ", G cost: " + getgCost() + ", F cost: " + getfCost());
		
	}
	
	public Movement calculateMovement(){
		if(parent != null){
			Movement move = null;
			Board parentBoard = getParent().getBoard();
			int[] direction = {(parentBoard.getNumberLoc(0)[0] - this.getBoard().getNumberLoc(0)[0]),
					(parentBoard.getNumberLoc(0)[1] - this.getBoard().getNumberLoc(0)[1])};
			
			switch(direction[0]){
			case -1:
				move = Movement.DOWN;
				break;
			case 1:
				move = Movement.UP;
				break;
			case 0:
				if(direction[1] == -1){
					move = Movement.RIGHT;
				}else{
					move = Movement.LEFT;
				}
				break;
			}
			
			return move;
		}else{
			return null;
		}
	}
}
