package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import algorithms.AStar;
import algorithms.Node;
import board.Board;
import heuristic.Heuristics;

public class PuzzleGUI extends BaseGUI {
	
	private JTabbedPane display;
	private JPanel solutionPanel, solvedPanel, settingsPanel, puzzlePanel, puzzleBoardPanel, solutionBoardPanel, boardPanel, nodePanel, startBoardPanel, compilerPanel;
	private JLabel puzzleLabel, solvedLabel, solutionLabel, algoLabel, heuristicLabel, fCostLabel, gCostLabel, hCostLabel, nodesGenLabel, nodesTestLabel, movesLabel, compilerLabel, timeLabel;
	private JButton solve, save, first, prev, clearStepBoard, next, last, clearInitBoard, clearGoalBoard, compile;
	private JComboBox<String> algorithms, heuristics;
	
	private JTextField puzzOne,puzzTwo,puzzThree,puzzFour,puzzFive,puzzSix,puzzSeven,puzzEight,puzzNine;
	private JTextField solvedOne,solvedTwo,solvedThree,solvedFour,solvedFive,solvedSix,solvedSeven,solvedEight,solvedNine;
	private JTextField solutionOne,solutionTwo,solutionThree,solutionFour,solutionFive,solutionSix,solutionSeven,solutionEight,solutionNine;
	private JTextField fCost,gCost,hCost,nodesGen,nodesTest,moves,compiler,time;
	
	private HashMap<JComponent,GridBagConstraints> constraints = new HashMap<JComponent,GridBagConstraints>();
	private JTextField[][] puzzIn = {{puzzOne,puzzTwo,puzzThree},{puzzFour,puzzFive,puzzSix},{puzzSeven,puzzEight,puzzNine}};
	private JTextField[][] solvedValues = {{solvedOne,solvedTwo,solvedThree},{solvedFour,solvedFive,solvedSix},{solvedSeven,solvedEight,solvedNine}};
	private JTextField[][] solutionValues = {{solutionOne,solutionTwo,solutionThree},{solutionFour,solutionFive,solutionSix},{solutionSeven,solutionEight,solutionNine}};
	
	private String[] algoNames = {"A Star"};
	private String[] heuristicNames = {"Manhattan Distance","Nilsson Sequence Score","Out Of Place","Linear Conflict","Misplaced Tiles"};
	private Node[] nodeList;
	
	private HashMap<String,Heuristics> heuristicMap = new HashMap<String,Heuristics>();
	
	private static int[][] solutionBoard = new int[3][3];
	private static Board[] path;
		
	private boolean useDefault = true;
	private boolean printOut = false;
	private int pathLoc = 0;

	public PuzzleGUI(String title) {
		super(title);
		init();
		addComponents();
		super.add(display);
		super.refresh();
	}
	
	private void init(){
		display =  new JTabbedPane();
		
		solutionPanel = new JPanel();
		solutionBoardPanel = new JPanel();
		puzzlePanel = new JPanel();
		puzzleBoardPanel = new JPanel();
		solvedPanel = new JPanel();
		settingsPanel = new JPanel();
		boardPanel = new JPanel();
		nodePanel = new JPanel();
		startBoardPanel = new JPanel();
		compilerPanel = new JPanel();
		
		puzzleLabel = new JLabel("Initial board");
		solvedLabel = new JLabel("Path board:");
		algoLabel = new JLabel("Algoithm:");
		heuristicLabel = new JLabel("Heuristic:");
		solutionLabel = new JLabel("Solution:");
		fCostLabel = new JLabel("F Cost:");
		gCostLabel = new JLabel("G Cost:");
		hCostLabel = new JLabel("H Cost:");
		nodesGenLabel = new JLabel("Nodes Generated:");
		nodesTestLabel = new JLabel("Nodes Tested:");
		movesLabel = new JLabel("Moves:");
		compilerLabel = new JLabel("Compiler: ");
		timeLabel = new JLabel("Time Taken: ");
		
		solve = new JButton("Solve");
		save = new JButton("Save");
		first = new JButton("|<");
		prev = new JButton("<");
		clearStepBoard = new JButton("O");
		next = new JButton(">");
		last = new JButton(">|");
		
		clearInitBoard = new JButton("Clear");
		clearGoalBoard = new JButton("Clear");
		compile = new JButton("Compile");
		
		fCost = new JTextField(2);
		gCost = new JTextField(2);
		hCost = new JTextField(2);
		nodesGen = new JTextField(5);
		nodesTest = new JTextField(5);
		moves = new JTextField(2);
		compiler = new JTextField(17);
		time = new JTextField(5);
		
		algorithms = new JComboBox<String>(algoNames);
		heuristics = new JComboBox<String>(heuristicNames);
		
		solutionPanel.setLayout(new GridBagLayout());
		solutionBoardPanel.setLayout(new GridBagLayout());
		puzzlePanel.setLayout(new GridBagLayout());
		puzzleBoardPanel.setLayout(new GridBagLayout());
		solvedPanel.setLayout(new GridBagLayout());
		settingsPanel.setLayout(new GridBagLayout());
		boardPanel.setLayout(new GridBagLayout());
		nodePanel.setLayout(new GridBagLayout());
		startBoardPanel.setLayout(new GridBagLayout());
		compilerPanel.setLayout(new GridBagLayout());

		solutionPanel.setName("Solution");
		solutionBoardPanel.setName("Solution Board Panel");
		puzzlePanel.setName("Puzzle Panel");
		puzzleBoardPanel.setName("Initial Board Panel");
		solvedPanel.setName("Solved Panel");
		settingsPanel.setName("Settings Panel");
		boardPanel.setName("Board Panel");
		nodePanel.setName("Node Panel");
		startBoardPanel.setName("Starting Board Panel");
		compilerPanel.setName("Compiler Panel");
		
		puzzleLabel.setName("Puzzle Label");
		solvedLabel.setName("Solved Label");
		algoLabel.setName("Algorithms Label");
		heuristicLabel.setName("Heuristics Label");
		solutionLabel.setName("Solution Label");
		fCostLabel.setName("F Cost Label");
		gCostLabel.setName("G Cost Label");
		hCostLabel.setName("H Cost Label");
		nodesGenLabel.setName("Nodes Generated Label");
		nodesTestLabel.setName("Nodes Tested Label");
		compilerLabel.setName("Compiler Label");
		timeLabel.setName("Time Label");

		save.setName("Save");
		first.setName("First");
		prev.setName("Previous");
		clearStepBoard.setName("Clear Step Board");
		next.setName("Next");
		last.setName("Last");
		clearInitBoard.setName("Clear Initial Board");
		clearGoalBoard.setName("Clear Goal Board");
		compile.setName("Compile");
		
		solve.setName("Solve");
		algorithms.setName("Algorithms");
		heuristics.setName("Heuristics");
		
		fCost.setName("F Cost");
		gCost.setName("G Cost");
		hCost.setName("H Cost");
		nodesGen.setName("Nodes Generated");
		nodesTest.setName("Nodes Tested");
		compiler.setName("Compiler");
		time.setName("Time Taken");
		
		movesLabel.setName("Moves Label");
		moves.setName("Moves");
		
		heuristicMap.put(heuristicNames[0], Heuristics.MANHATTAN_DISTANCE);
		heuristicMap.put(heuristicNames[1], Heuristics.NILSSON_SEQUENCE_SCORE);
		heuristicMap.put(heuristicNames[2], Heuristics.OUT_OF_PLACE);
		heuristicMap.put(heuristicNames[3], Heuristics.LINEAR_CONFLICT);
		heuristicMap.put(heuristicNames[4], Heuristics.MISPLACED_TILES);
	}
	
	private void addComponents(){
		display.addTab("8-Puzzle", puzzlePanel);
		display.addTab("Solution", solutionPanel);
		
		addComponent(1, 4, compilerPanel, puzzlePanel);
		addComponent(0, 2, puzzleLabel, puzzlePanel);
		addComponent(0, 3, puzzleBoardPanel, puzzlePanel);
		addComponent(2, 3, solvedPanel, puzzlePanel);
		addComponent(1, 2, 1, 2, settingsPanel, puzzlePanel);
		addComponent(2, 4, boardPanel, puzzlePanel);
		addComponent(3, 2, 1, 2, nodePanel, puzzlePanel);
		addComponent(2, 2, solvedLabel, puzzlePanel);
		addComponent(0, 4, startBoardPanel, puzzlePanel);
		
		addComponent(0, 0, solutionLabel, solutionPanel);
		addComponent(0, 1, solutionBoardPanel, solutionPanel);
		
		addComponent(0, 0, compilerLabel, compilerPanel);
		addComponent(1, 0, compiler, compilerPanel);
		addComponent(0, 1, 2, 1,compile, compilerPanel);
		
		//*
		int count = 0;
		for (int i = 0; i < puzzIn.length; i++) {
			for (int j = 0; j < puzzIn[i].length; j++) {
				puzzIn[i][j] = new JTextField(2);
				solvedValues[i][j] = new JTextField(2);
				solutionValues[i][j] = new JTextField(2);
				
				count++;
				
				puzzIn[i][j].setName("puzzle square "+count);
				solvedValues[i][j].setName("solved square "+count);
				solutionValues[i][j].setName("solution square "+count);
				
				puzzIn[i][j].setToolTipText("Initial board " + (i+1) + ", " + (j+1));
				solvedValues[i][j].setToolTipText("Solved board " + (i+1) + ", " + (j+1));
				solutionValues[i][j].setToolTipText("Solution board " + (i+1) + ", " + (j+1));
				
				puzzIn[i][j].setHorizontalAlignment(JTextField.CENTER);
				solvedValues[i][j].setHorizontalAlignment(JTextField.CENTER);
				solutionValues[i][j].setHorizontalAlignment(JTextField.CENTER);
				
				solvedValues[i][j].setEnabled(false);
				solvedValues[i][j].setDisabledTextColor(Color.GRAY);
				solvedValues[i][j].setBackground(Color.WHITE);
				
				puzzIn[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
				solvedValues[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
				solutionValues[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
				
				
				if(j == 0){
					addComponent(i, j, 1, 1, GridBagConstraints.WEST ,puzzIn[i][j], puzzleBoardPanel);
					addComponent(i, j, 1, 1, GridBagConstraints.WEST ,solvedValues[i][j], solvedPanel);
					addComponent(i, j, 1, 1, GridBagConstraints.WEST ,solutionValues[i][j], solutionBoardPanel);
				}else if(j==2){
					addComponent(i, j, 1, 1, GridBagConstraints.EAST ,puzzIn[i][j], puzzleBoardPanel);
					addComponent(i, j, 1, 1, GridBagConstraints.EAST ,solvedValues[i][j], solvedPanel);
					addComponent(i, j, 1, 1, GridBagConstraints.EAST ,solutionValues[i][j], solutionBoardPanel);
				}else{
					addComponent(i, j, 1, 1, GridBagConstraints.CENTER ,puzzIn[i][j], puzzleBoardPanel);
					addComponent(i, j, 1, 1, GridBagConstraints.CENTER ,solvedValues[i][j], solvedPanel);
					addComponent(i, j, 1, 1, GridBagConstraints.CENTER ,solutionValues[i][j], solutionBoardPanel);
				}
				
			}
		}
				
		addComponent(0, 0, algoLabel, settingsPanel);
		addComponent(0, 1, algorithms, settingsPanel);
		addComponent(0, 2, heuristicLabel, settingsPanel);
		addComponent(0, 3, heuristics, settingsPanel);
		
		addComponent(0, 1, first, boardPanel);
		addComponent(0, 0, prev, boardPanel);
		addComponent(1, 1, clearStepBoard, boardPanel);
		addComponent(2, 0, next, boardPanel);
		addComponent(2, 1, last, boardPanel);
		
		addComponent(0, 0, solve, startBoardPanel);
		addComponent(0, 1, clearInitBoard, startBoardPanel);
		addComponent(0, 3, save, solutionPanel);
		addComponent(0, 4, clearGoalBoard, solutionPanel);
		
		addComponent(0, 0, gCostLabel, nodePanel);
		addComponent(1, 0, gCost, nodePanel);
		addComponent(0, 1, hCostLabel, nodePanel);
		addComponent(1, 1, hCost, nodePanel);
		addComponent(0, 2, fCostLabel, nodePanel);
		addComponent(1, 2, fCost, nodePanel);
		addComponent(0, 3, movesLabel, nodePanel);
		addComponent(1, 3, moves, nodePanel);
		addComponent(0, 4, nodesGenLabel, nodePanel);
		addComponent(1, 4, nodesGen, nodePanel);
		addComponent(0, 5, nodesTestLabel, nodePanel);
		addComponent(1, 5, nodesTest, nodePanel);
		addComponent(0, 6, timeLabel, nodePanel);
		addComponent(1, 6, time, nodePanel);
		
		fCost.setEnabled(false);
		fCost.setDisabledTextColor(Color.GRAY);
		fCost.setBackground(Color.WHITE);
		fCost.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		gCost.setEnabled(false);
		gCost.setDisabledTextColor(Color.GRAY);
		gCost.setBackground(Color.WHITE);
		gCost.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		hCost.setEnabled(false);
		hCost.setDisabledTextColor(Color.GRAY);
		hCost.setBackground(Color.WHITE);
		hCost.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		nodesGen.setEnabled(false);
		nodesGen.setDisabledTextColor(Color.GRAY);
		nodesGen.setBackground(Color.WHITE);
		nodesGen.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		nodesTest.setEnabled(false);
		nodesTest.setDisabledTextColor(Color.GRAY);
		nodesTest.setBackground(Color.WHITE);
		nodesTest.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		moves.setEnabled(false);
		moves.setDisabledTextColor(Color.GRAY);
		moves.setBackground(Color.WHITE);
		moves.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		time.setEnabled(false);
		time.setDisabledTextColor(Color.GRAY);
		time.setBackground(Color.WHITE);
		time.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		fCost.setHorizontalAlignment(JTextField.CENTER);
		gCost.setHorizontalAlignment(JTextField.CENTER);
		hCost.setHorizontalAlignment(JTextField.CENTER);
		nodesGen.setHorizontalAlignment(JTextField.CENTER);
		nodesTest.setHorizontalAlignment(JTextField.CENTER);
		moves.setHorizontalAlignment(JTextField.CENTER);
		time.setHorizontalAlignment(JTextField.CENTER);
		
		System.out.println();
		//*/
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean success = checkBoard(solutionBoardPanel);
				
				Component[] components = solutionBoardPanel.getComponents();
				int[][] solution = new int[3][3];
				int count1 = 0;
				int count2 = 0;
				
				if(success){
					useDefault = false;
					for (Component component : components) {
						if(component instanceof JTextField){
							if(count1 == 3){
								count1 = 0;
								count2++;
							}
							solution[count1][count2] = Integer.parseInt(((JTextField) component).getText());
							count1++;
						}
					}
					
					solutionBoard = solution;
					JOptionPane.showMessageDialog(null, "Board saved successfully");
				}else{
					JOptionPane.showMessageDialog(null, "Please enter the numbers 0-8","Integer range mis-match",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		solve.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println("Solving");
				
				boolean success = checkBoard(puzzleBoardPanel);
				
				Component[] components = puzzleBoardPanel.getComponents();
				int[][] puzzleBoardIn = new int[3][3];
				int count1 = 0;
				int count2 = 0;
				
				if(success){
					for (Component component : components){
						if(component instanceof JTextField){
							if(count1 == 3){
								count1 = 0;
								count2++;
							}
							puzzleBoardIn[count1][count2] = Integer.parseInt(((JTextField) component).getText());
							count1++;
						}
					}

					Board goalBoard;
					
					if(useDefault){
						int[][] defaultValue = {{1,2,3},{8,0,4},{7,6,5}};
						//int[][] defaultValue = {{1,2,3},{4,5,6},{7,8,0}};
						goalBoard = new Board(defaultValue);
					}else{
						goalBoard = new Board(solutionBoard);
					}

					Board startBoard = new Board(puzzleBoardIn);
					AStar algo = null;
					
					switch (algorithms.getSelectedItem().toString()){
					case "A Star":
						
						Node originNode = new Node(startBoard, null, 0);
						algo = new AStar(goalBoard, originNode, heuristicMap.get(heuristics.getSelectedItem()).heuristic());
						
					}
					
					if(algo.isSolvable(startBoard)){
						algo.solve(printOut);
						nodeList = algo.backtrack();
						ArrayList<Board> boardList = new ArrayList<Board>(); 
						
						for (Node node : nodeList) {
							boardList.add(node.getBoard());
						}
						
						path = boardList.toArray(new Board[0]);
						int noOfNodes = (algo.getClosedList().size())+algo.getOpenList().size();
	
						gCost.setText(""+nodeList[pathLoc].getgCost());
						hCost.setText(""+nodeList[pathLoc].gethCost());
						fCost.setText(""+nodeList[pathLoc].getfCost());
						moves.setText(""+(nodeList.length-1));
						nodesTest.setText(""+algo.getClosedList().size());
						nodesGen.setText(""+noOfNodes);
						time.setText(""+algo.getTimer().getElapsedTime());
						
						setSolvedGrid(path[pathLoc].getBoard());
						
						/*
						ArrayList<Node> ndes = algo.getOpenList();
						HashMap<Board,Integer> occurence = new HashMap<Board,Integer>();
						for (int i = 0; i < ndes.size(); i++) {
							int count = 0;
							for (int j = 0; j < ndes.size(); j++) {
								if(ndes.get(j).getBoard().equals(ndes.get(i).getBoard())){
									count++;
								}
							}
							occurence.put(ndes.get(i).getBoard(), count);
						}
						Board[] allNodes = occurence.keySet().toArray(new Board[0]);
						int count3 = 0;
						int count4 = 0;
						for (int i = 0; i < allNodes.length; i++) {
							
							if(occurence.get(allNodes[i]) > 1){
								System.out.println("Occurs: " + occurence.get(allNodes[i]) + " times");
								allNodes[i].printBoard();
								count4 += occurence.get(allNodes[i]);
								count4--;
								count3++;
								System.out.println();
							}
						}
						System.out.println();
						System.out.println(count3 + " boards appear more then once resulting in " + count4 + " duplicate's");
						*/
					}else{
						JOptionPane.showMessageDialog(null, "This board is un-solvable","Board Error",JOptionPane.ERROR_MESSAGE);
					}
					
				}else{
					JOptionPane.showMessageDialog(null, "Please enter the numbers 0-8","Integer range mis-match",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		first.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pathLoc = path.length-1;
				setSolvedGrid(path[pathLoc].getBoard());
				
				gCost.setText(""+nodeList[pathLoc].getgCost());
				hCost.setText(""+nodeList[pathLoc].gethCost());
				fCost.setText(""+nodeList[pathLoc].getfCost());
			}
		});

		last.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pathLoc = 0;
				setSolvedGrid(path[pathLoc].getBoard());
				
				gCost.setText(""+nodeList[pathLoc].getgCost());
				hCost.setText(""+nodeList[pathLoc].gethCost());
				fCost.setText(""+nodeList[pathLoc].getfCost());
			}
		});

		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pathLoc != 0){
					pathLoc--;
				}
				setSolvedGrid(path[pathLoc].getBoard());
				
				gCost.setText(""+nodeList[pathLoc].getgCost());
				hCost.setText(""+nodeList[pathLoc].gethCost());
				fCost.setText(""+nodeList[pathLoc].getfCost());
			}
		});

		prev.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pathLoc != path.length-1){
					pathLoc++;
				}
				setSolvedGrid(path[pathLoc].getBoard());
				
				gCost.setText(""+nodeList[pathLoc].getgCost());
				hCost.setText(""+nodeList[pathLoc].gethCost());
				fCost.setText(""+nodeList[pathLoc].getfCost());
			}
		});
		
		compile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				char[] chars = compiler.getText().toCharArray();
				int row = 0;
				int col = 0;
				for (char chr : chars) {
					System.out.println(chr);
					if(chr != ','){
						puzzIn[row][col].setText(""+chr);
						if(row == 2){
							row = 0;
							col++;
						}else{
							row++;
						}
					}
				}
				
			}
		});
		
		heuristics.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(heuristics.getSelectedItem() == heuristicNames[1]){
					for (JTextField[] textFieldArr : solutionValues) {
						for (JTextField textField : textFieldArr) {
							textField.setEnabled(false);
							switch(textField.getName()){
							case "solution square 1":
								textField.setText("1");
								break;
							case "solution square 4":
								textField.setText("2");
								break;
							case "solution square 7":
								textField.setText("3");
								break;
							case "solution square 2":
								textField.setText("8");
								break;
							case "solution square 5":
								textField.setText("0");
								break;
							case "solution square 8":
								textField.setText("4");
								break;
							case "solution square 3":
								textField.setText("7");
								break;
							case "solution square 6":
								textField.setText("6");
								break;
							case "solution square 9":
								textField.setText("5");
								break;
							}
						}
					}
				}else{
					for (JTextField[] textFieldArr : solutionValues) {
						for (JTextField textField : textFieldArr) {
							textField.setEnabled(true);
						}
					}
				}
				
				
			}
		});
		
		clearInitBoard.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (JTextField[] textFields : puzzIn) {
					for (JTextField textField : textFields) {
						textField.setText(null);
					}
				}
				
			}
		});

		clearStepBoard.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (JTextField[] textFields : solvedValues) {
					for (JTextField textField : textFields) {
						textField.setText(null);
					}
				}
				
				fCost.setText(null);
				gCost.setText(null);
				hCost.setText(null);
				nodesGen.setText(null);
				nodesTest.setText(null);
				moves.setText(null);
				time.setText(null);
				
			}
		});
		
		clearGoalBoard.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(heuristics.getSelectedItem() == heuristicNames[1]){
					JOptionPane.showMessageDialog(null, "This board is fixed for the currently selected heuristic","Fixed Solution",JOptionPane.ERROR_MESSAGE);
				}else{
					for (JTextField[] textFields : solutionValues) {
						for (JTextField textField : textFields) {
							textField.setText(null);
						}
					}
				}
			}
		});
		
		
	}
	
	private void setSolvedGrid(int[][] values){
		for (int i = 0; i < solvedValues.length; i++) {
			for (int j = 0; j < solvedValues[i].length; j++) {
				solvedValues[j][i].setText(""+values[i][j]);
			}
		}
	}
	
	private boolean checkBoard(JPanel panel){

		int count1 = 0;
		int count2 = 0;
		JTextField[][] currCheckingBoard = new JTextField[3][3];
		Component[] components = panel.getComponents();
		
		for (Component component : components) {
			if(component instanceof JTextField){
				
				if(count1 == 3){
					count1 = 0;
					count2++;
				}
				currCheckingBoard[count1][count2] = (JTextField) component;
				count1++;
			}
		}
		
		boolean err = false;
		boolean[] oneToNine = new boolean[9];
		boolean success = true;
		
		int[][] solution = new int[3][3];
		int[] toCheck = new int[9];
		int count = 0;
		
		for (int i = 0; i < currCheckingBoard.length; i++) {
			for (int j = 0; j < currCheckingBoard[i].length; j++) {
				if(currCheckingBoard[i][j].getText().length() != 1){
					JOptionPane.showMessageDialog(null, "Please enter one character per square","Character size mis-match",JOptionPane.ERROR_MESSAGE);
					err = true;
					System.out.println(err);
					return false;
				}
				
				try{
					solution[i][j] = Integer.parseInt(currCheckingBoard[i][j].getText());
					toCheck[count] = solution[i][j];
					count++;
				}catch(Exception exc){
					JOptionPane.showMessageDialog(null, "Please only enter numbers","Character type mis-match",JOptionPane.ERROR_MESSAGE);
					err = true;
					System.out.println(err);
					return false;
				}
			}
			System.out.println(err);
			if(err){
				return false;
			}
		}
		
		err = false;
		for (int i = 0; i < 9; i++) {
			oneToNine[i] = false;
			for (int j : toCheck) {
				if(i == j){
					oneToNine[i] = true;
					break;
				}
			}
		}
		
		for (int i = 0; i < oneToNine.length; i++) {
			success &= oneToNine[i];
		}
		
		System.out.println("Success: " + success);
		return success;
	}

	private void addComponent(int x, int y, JComponent component, JPanel panel){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x;
		gbc.gridy = y;
		panel.add(component, gbc);
		constraints.put(component, gbc);
		
		//System.out.println("Adding \"" + component.getName() + "\" to \"" + panel.getName() + "\" at " + x + ", " + y);
	}
	
	private void addComponent(int x, int y, GridBagConstraints gbc, JComponent component, JPanel panel){
		gbc.insets = new Insets(2, 3, 2, 2);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x;
		gbc.gridy = y;
		panel.add(component, gbc);
		constraints.put(component, gbc);

		//System.out.println("Adding \"" + component.getName() + "\" to \"" + panel.getName() + "\" at " + x + ", " + y);
	}
	
	//*
	private void addComponent(int x, int y, int width, int height, JComponent component, JPanel panel){
		GridBagConstraints gbc  = new GridBagConstraints();
		gbc.gridwidth = width;
		gbc.gridheight = height;
		addComponent(x,y,gbc,component,panel);
	}
	//*/
	
	/*
	private void addComponent(int x, int y, Insets inset, JComponent component, JPanel panel){
		GridBagConstraints gbc  = new GridBagConstraints();
		gbc.insets = inset;
		addComponent(x,y,gbc,component,panel);
	}
	//*/
	
	private void addComponent(int x, int y, int width, int height, GridBagConstraints gbc, JComponent component, JPanel panel){
		gbc.gridwidth = width;
		gbc.gridheight = height;
		addComponent(x,y,gbc,component,panel);
	}
	
	private void addComponent(int x, int y, int width, int height, int anchor, JComponent component, JPanel panel){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = anchor;
		addComponent(x,y,width,height,gbc,component,panel);
	}
	
}
