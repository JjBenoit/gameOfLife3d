package game.executor.gridcontext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import game.object.Cell;
import game.object.Grid;

public class MultiThreadGridContextUpdateExecutor implements GridContextUpdateExecutor
{

    private ExecutorService executor;

    private Grid grid;

    public MultiThreadGridContextUpdateExecutor(int nbThread, Grid grid)
    {
        super();
        executor = Executors.newFixedThreadPool(nbThread);
        this.grid = grid;
    }

    @Override
    public void updateContextFromWrtingContext()
    {

        List<Future> futures = new ArrayList<>();

        // les y , les lignes
        for (int y = 0; y < grid.getGrid().length; y++)
        {
            for (int x = 0; x < grid.getGrid()[y].length; x++)
            {
                futures.add(evolve(grid.getGrid()[y][x]));
            }
        }

        boolean finished = false;

        while (!finished)
        {
            finished = true;
            for (Future future : futures)
            {
                if (!future.isDone())
                    finished = false;
            }
        }

    }

    private Future evolve(Cell[] grid)
    {
        return executor.submit(() -> {

            for (int x = 0; x < grid.length; x++)
            {
                grid[x].updateContextFromWrtingContext();
            }
        });
    }

}
