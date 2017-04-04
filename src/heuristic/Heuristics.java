package heuristic;

public enum Heuristics {
	
	MANHATTAN_DISTANCE(new Manhattan()),
	NILSSON_SEQUENCE_SCORE(new Nilsson()),
	OUT_OF_PLACE(new OutOfPlace()),
	LINEAR_CONFLICT(new LinearConflict()),
	MISPLACED_TILES(new MisplacedTiles());
	
	private final IHeuristic heuristicVal;
	
	Heuristics(IHeuristic heuristic){
		this.heuristicVal = heuristic;
	}
	
	public IHeuristic heuristic(){
		return heuristicVal;
	}
}
