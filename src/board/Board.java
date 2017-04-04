package board;

import java.util.ArrayList;

public class Board {
	private int[][] board;
	
	public Board(int[][] board){
		this.setBoard(board);
	}
	
	public int[] getNumberLoc(int number){
		int[] loc = new int[2];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if(this.getBoard()[i][j] == number){
					loc[0] = i;
					loc[1] = j;
				}
			}
		}
		return loc;
	}
	
	public void switchValues(int val1, int val2){
		int[] val1Loc = getNumberLoc(val1);
		int[] val2Loc = getNumberLoc(val2);
		
		board[val1Loc[0]][val1Loc[1]] = val2;
		board[val2Loc[0]][val2Loc[1]] = val1;
	}
	
	public void printBoard(){
		System.out.println();
		for (int[] is : board) {
			for (int i : is) {
				System.out.print("[" + i + "]");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void move(Movement move){
		if(this.validMove(move, 0)){
			int[] zeroLoc = getNumberLoc(0);
			int switchVal = board[zeroLoc[0] - move.verticalChange()][zeroLoc[1] - move.horizontalChange()];
			
			this.switchValues(0, switchVal);
		}
	}
	
	public boolean equals(Board board){
		boolean equal = true;
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				equal &= (this.board[i][j] == board.getBoard()[i][j]);
			}
		}
		return equal;
	}
	
	public boolean validMove(Movement move, int value){
		boolean contains = false;
		Movement[] moves = this.availableMoves(value);
		
		for (Movement movement : moves) {
			if(movement == move){
				contains = true;
			}
		}
		
		return contains;
	}
	
	public Movement[] availableMoves(int value){
		int[] position = this.getNumberLoc(value);
		ArrayList<Movement> availableMoves = new ArrayList<Movement>();
		
		if(position[0] > 0){
			availableMoves.add(Movement.UP);
		}
		if(position[0] < 2){
			availableMoves.add(Movement.DOWN);
		}
		
		if(position[1] > 0){
			availableMoves.add(Movement.LEFT);
		}
		
		if(position[1] < 2){
			availableMoves.add(Movement.RIGHT);
		}
		return availableMoves.toArray(new Movement[0]);
	}
	
	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}
}
