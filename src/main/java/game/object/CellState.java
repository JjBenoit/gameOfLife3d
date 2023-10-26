package game.object;

import java.util.List;

public class CellState
{

    private int state;

    private int contextSum;

    public CellState(int initialState)
    {
        this.state = initialState;
    }

    public CellState(CellState cell)
    {
        this.state = cell.state;
        this.contextSum = cell.contextSum;
    }

    public void computeContext(List<Cell> connectedCells)
    {
        contextSum = connectedCells.stream().map(x -> x.getState()).reduce(0, Integer::sum);
    }

    public void updateContext(int delta)
    {
        contextSum += delta;
    }

    public Integer getState()
    {
        return state;
    }

    public void setState(Integer state)
    {
        this.state = state;
    }

    public Integer getContext()
    {
        return contextSum;
    }

    @Override
    public String toString()
    {
        return "CellState [state=" + state + ", contextSum=" + contextSum;
    }

}
