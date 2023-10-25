package game.object;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.geometry.Position3d;

public class Cell {

    private static int activeStateBufferIndex = 0;
    private static int nextStateBufferIndex = 1;

    private CellState[] statesBuffer = new CellState[2];

    private Position3d position;

    private Grid grid;

    private List<Cell> connectedCells;

    private static final Logger LOGGER = LogManager.getLogger(Cell.class);

    public Cell(Grid grid, int initialState, Position3d position) {

	statesBuffer[activeStateBufferIndex] = new CellState(initialState);
	this.grid = grid;
	this.position = position;
    }

    // return the active state of the cell
    public int getState() {
	return statesBuffer[activeStateBufferIndex].getState();
    }

    // set the next state of the state
    public void setNextState(int state) {
	statesBuffer[nextStateBufferIndex] = new CellState(state);
    }

    // compute and return the current context of this cell
    public int genContext() {

	if (connectedCells == null)
	    connectedCells = grid.getConnectedCells(this);

	statesBuffer[activeStateBufferIndex].computeContext(connectedCells);

	return statesBuffer[activeStateBufferIndex].getContext();

    }

    public Position3d getPosition() {
	return position;
    }

    public static void flipCurrentActiveStateBufferIndex() {
	activeStateBufferIndex = activeStateBufferIndex == 0 ? 1 : 0;
	nextStateBufferIndex = nextStateBufferIndex == 0 ? 1 : 0;
    }

    @Override
    public String toString() {
	return "Cell [statesBuffer=" + Arrays.toString(statesBuffer) + ", position=" + position + ", grid=" + grid
		+ ", connectedCells=" + connectedCells + "]";
    }

}
