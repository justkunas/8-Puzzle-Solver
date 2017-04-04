package algorithms;

import java.awt.Toolkit;
import java.util.ArrayList;

import board.Board;
import board.Movement;
import heuristic.IHeuristic;
import heuristic.Nilsson;
import main.Timer;

public class AStar {
	
	private final Board goal;
	private final Node startState;
	private final IHeuristic heuristic;
	private Node currentState;
	
	private final Timer timer = new Timer();

	private ArrayList<Node> openList = new ArrayList<Node>();
	private ArrayList<Node> closedList = new ArrayList<Node>();
	
	private int iterationCount;

	public AStar(Board goal, Node startState) {
		this.goal = goal;
		this.startState = startState;
		this.heuristic = null;
		this.setCurrentState(startState);
		
		boolean solvable = this.isSolvable(startState.getBoard());
		System.out.println("isSolvable? "+solvable);
	}

	public AStar(Board goal, Node startState, IHeuristic heuristic) {
		if(heuristic instanceof Nilsson){
			this.goal = new Board(new int[][] {{1,2,3},{8,0,4},{7,6,5}});
		}else{
			this.goal = goal;
		}
		heuristic.apply(startState, getGoal());
		this.startState = startState;
		this.heuristic = heuristic;
		this.setCurrentState(startState);
		
		boolean solvable = this.isSolvable(startState.getBoard());
		System.out.println("isSolvable? "+solvable);
	}
	
	public Board getGoal() {
		return goal;
	}

	public Node getStartState() {
		return startState;
	}

	public Node getCurrentState() {
		return currentState;
	}

	public void setCurrentState(Node currentState) {
		this.currentState = currentState;
	}

	public ArrayList<Node> getOpenList() {
		return openList;
	}

	public ArrayList<Node> getClosedList() {
		return closedList;
	}
	
	public void alterLists(){
		Movement[] moves = getCurrentState().getBoard().availableMoves(0);
		for (Movement movement : moves) {
			boolean available = true;
			int[][] values = new int[3][3];
			for (int i = 0; i < values.length; i++) {
				for (int j = 0; j < values[i].length; j++) {
					values[i][j] = getCurrentState().getBoard().getBoard()[i][j];
				}
			}
			Board newBoard = new Board(values);
			newBoard.move(movement);
			
			for (Node node : closedList) {
				if(node.getBoard().equals(newBoard)){
					available = false;
				}
			}
			
			for (Node node : openList) {
				if(node.getBoard().equals(newBoard)){
					available = false;
				}
			}
			
			Node nd = new Node(newBoard,getCurrentState(),getCurrentState().getgCost() + 1);
			
			if(heuristic != null){
				heuristic.apply(nd, getGoal());
			}
			
			if(available){
				openList.add(nd);
			}
		}
		openList.remove(getCurrentState());
		closedList.add(getCurrentState());
	}
	
	public void changeCurrentNode(){
		setCurrentState(openList.get(0));
		for (Node node : openList) {
			if(getCurrentState().getfCost() > node.getfCost()){
				setCurrentState(node);
			}
		}
	}
	
	public void iterate(int length){
		for (int i = 0; i < length; i++) {
			alterLists();
			changeCurrentNode();
		}
	}
	
	public void solve(boolean print){
		timer.start();
		while(!getCurrentState().getBoard().equals(getGoal())){
			iterationCount++;
			this.iterate(1);
			if(print){
				getCurrentState().printNode();
				System.out.println("Iteration Cost: " + iterationCount);
			}
		};
		timer.stop();
		Toolkit.getDefaultToolkit().beep();
		System.out.println("Elappsed Time: " + timer.getElapsedTime() + "ms");
		if(print){
			System.out.println("No. of nodes: " + (closedList.size() + openList.size()));
		}
	}
	
	public Node[] backtrack(){
		ArrayList<Node> path = new ArrayList<Node>();
		Node parent = getCurrentState();
		do{
			path.add(parent);
			parent = parent.getParent();
		}while(parent != null);
		
		return path.toArray(new Node[0]);
	}
	
	public boolean isSolvable(Board board){
		int workingInv = 0;
		int[][] workingBoard = new int[this.getGoal().getBoard().length][this.getGoal().getBoard()[1].length];
		
		for (int i = 0; i < workingBoard.length; i++) {
			for (int j = 0; j < workingBoard[i].length; j++) {
				workingBoard[i][j] = this.getGoal().getBoard()[i][j];
			}
		}
		
		Board workingGoalBoard = new Board(workingBoard);
		workingGoalBoard.move(Movement.UP);
		
		ArrayList<Integer> goalBoardConcat = new ArrayList<Integer>();
		for (int[] arr : workingGoalBoard.getBoard()) {
			for (int i : arr) {
				goalBoardConcat.add(i);
			}
		}

		for (int i = 0; i < goalBoardConcat.size(); i++) {
			
			for (int j = i; j < goalBoardConcat.size(); j++) {
				if(!(goalBoardConcat.get(j) == 0 || goalBoardConcat.get(i) == 0)&& goalBoardConcat.get(i) > goalBoardConcat.get(j)){
					workingInv++;
					System.out.println("Comparing " + goalBoardConcat.get(i)
					+ " to " + goalBoardConcat.get(j) + " inversion: " + true);
				}
			}
		}
		
		int inversions = 0;
		ArrayList<Integer> concatination = new ArrayList<Integer>();
		for (int[] arr : board.getBoard()) {
			for (int i : arr) {
				concatination.add(i);
			}
		}
		
		for (int i = 0; i < concatination.size(); i++) {
			
			for (int j = i; j < concatination.size(); j++) {
				if(!(concatination.get(j) == 0 || concatination.get(i) == 0)&& concatination.get(i) > concatination.get(j)){
					inversions++;
					System.out.println("Comparing " + concatination.get(i)
					+ " to " + concatination.get(j) + " inversion: " + true);
				}
			}
		}
		
		System.out.println("No. of inversions: "+inversions);
		return ((inversions%2)==(workingInv%2));
	}

	public IHeuristic getHeuristic() {
		return heuristic;
	}
	
	public Timer getTimer() {
		return timer;
	}

}
