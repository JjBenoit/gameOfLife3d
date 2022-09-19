package game.executor.gridcontext;

import game.object.Grid;

public class SimpleGridContextUpdateExecutor implements GridContextUpdateExecutor
{
    private Grid grid;

    public SimpleGridContextUpdateExecutor(Grid grid)
    {
        super();
        this.grid = grid;
    }

    @Override
    public void updateContextFromWrtingContext()
    {

        for (int z = 0; z < grid.getGrid().length; z++)
        {
            for (int y = 0; y < grid.getGrid()[z].length; y++)
            {
                for (int x = 0; x < grid.getGrid()[z][y].length; x++)
                {
                    grid.getGrid()[z][y][x].updateContextFromWrtingContext();
                }
            }
        }

    }

}
