package board;

public enum Movement {
	UP (1,0),
	DOWN(-1,0),
	LEFT(0,1),
	RIGHT(0,-1);
	
	private final int verticalChange;
	private final int horizontalChange;
	
	Movement(int verticalChange, int horizontalChange){
		this.verticalChange = verticalChange;
		this.horizontalChange = horizontalChange;
	}

	public int verticalChange() {
		return verticalChange;
	}

	public int horizontalChange() {
		return horizontalChange;
	}
	
	public Movement opposite(){
		Movement opposite = null;
		switch(this){
		case UP:
			opposite =  DOWN;
			break;
		case DOWN:
			opposite =  UP;
			break;
		case LEFT:
			opposite =  RIGHT;
			break;
		case RIGHT:
			opposite =  LEFT;
			break;
		}
		return opposite;
	}
}
