package game.object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.geometry.Position3d;

public class Cell
{
    private int state;

    private int[] context = new int[2];

    private List<Cell> connectedCells;

    private Position3d position;

    private static final Logger LOGGER = LogManager.getLogger(Cell.class);

    public Cell(int state, Position3d position)
    {
        this.state = state;
        this.position = position;
        this.connectedCells = new ArrayList<>();

    }

    public void addConnectedCells(Cell cell)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER
                .debug(
                    "Cell: " + this.getPosition() + " adding cell :  " + cell.getPosition() + " with state = " + cell.getState());
        }

        this.connectedCells.add(cell);
        contextChanged(cell.getState());

    }

    // Recoit d'une cellule voisine ( connectée) appelante un changement d'état. Met a jour le context de la celulle appelée
    private synchronized void contextChanged(int delta)
    {
        int oldContext = getWritingContext();

        setWritingContext(delta);

        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug(
                "Cell: " + this.getPosition() + " Context  modified (" + oldContext + " + " + delta + ") = " + this.getWritingContext());
        }
    }

    // changement d'état de la cellule ( morte / vivante ) suite à écoulement du temps ( itération )
    public void changeState(int state)
    {

        int oldState = getState();

        // si il y a chnagement 0=>1 =1; si 1=> 0 =-1
        if (this.state != state)
        {
            int valueOfChange = (state == 0 ? -1 : 1);
            informConnectedCells(valueOfChange);
            this.state = state;

        }

        if (LOGGER.isDebugEnabled())
        {
            LOGGER
                .debug(
                    "Cell:" + getPosition() + " old state = " + oldState + " new state = " + getState() + " context = "
                        + getContext());
        }
    }

    // previens les cellules voisines ( connectées ) du changement de l'état de cette cellule
    private void informConnectedCells(int delta)
    {
        for (Cell cell : connectedCells)
        {
            cell.contextChanged(delta);
        }

    }

    public void setWritingContext(int delta)
    {
        context[1] = (delta + context[1] < 0) ? 0 : (delta + context[1]);
    }

    public int getWritingContext()
    {
        return context[1];
    }

    public int getContext()
    {
        return context[0];
    }

    public void updateContextFromWrtingContext()
    {
        context[0] = context[1];
    }

    public int getState()
    {
        return state;
    }

    public Position3d getPosition()
    {
        return position;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cell other = (Cell) obj;
        if (position == null)
        {
            if (other.position != null)
                return false;
        }
        else if (!position.equals(other.position))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Cell [state=" + state + ", context=" + Arrays.toString(context) + ", connectedCells="
            + connectedCells + ", position=" + position + "]";
    }

}
