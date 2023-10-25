package game.object;

import java.util.Arrays;
import java.util.List;

public class CellState {

    private int state;
    private int contextSum;
    private int[] contextDetails = new int[8];

    public CellState(int initialState) {
	this.state = initialState;
    }

    public void computeContext(List<Cell> connectedCells) {

	for (int i = 0; i < connectedCells.size(); i++) {
	    contextSum += connectedCells.get(i).getState();
	    contextDetails[i] = connectedCells.get(i).getState();
	}
    }

    public Integer getState() {
	return state;
    }

    public void setState(Integer state) {
	this.state = state;
    }

    public Integer getContext() {
	return contextSum;
    }

    @Override
    public String toString() {
	return "CellState [state=" + state + ", contextSum=" + contextSum + ", contextDetails="
		+ Arrays.toString(contextDetails) + "]";
    }

}
