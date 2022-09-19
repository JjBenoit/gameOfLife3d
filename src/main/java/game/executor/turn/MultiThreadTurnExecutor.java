package game.executor.turn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import game.object.Cell;
import game.object.Grid;
import game.rules.EvolutionCellRule;

public class MultiThreadTurnExecutor implements TurnExecutor
{

    private EvolutionCellRule evolutionRule;

    private ExecutorService executor;

    public MultiThreadTurnExecutor(int nbThread, EvolutionCellRule evolutionRule)
    {
        super();
        this.evolutionRule = evolutionRule;
        executor = Executors.newFixedThreadPool(nbThread);
    }

    @Override
    public void playOneTurn(Grid grid)
    {

        List<Future> futures = new ArrayList<>();

        // les y , les lignes
        for (int z = 0; z < grid.getGrid().length; z++)
        {
            for (int y = 0; y < grid.getGrid()[z].length; y++)
            {
                futures.add(evolve(grid.getGrid()[z][y], evolutionRule));
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

    public Future evolve(Cell[] grid, EvolutionCellRule evolutionRule)
    {
        return executor.submit(() -> {

            for (int x = 0; x < grid.length; x++)
            {
                // détemerniation de l'état d'une cellule en fonction de ses voisines
                evolutionRule.evolutionCell(grid[x]);
            }
        });
    }

}
