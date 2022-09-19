package game.executor.turn;

import game.object.Grid;
import game.rules.EvolutionCellRule;

public class SimpleTurnExecutor implements TurnExecutor
{

    private EvolutionCellRule evolutionRule;

    public SimpleTurnExecutor(EvolutionCellRule evolutionRule)
    {
        super();
        this.evolutionRule = evolutionRule;
    }

    @Override
    public void playOneTurn(Grid grid)
    {

        for (int z = 0; z < grid.getGrid().length; z++)
        {
            for (int y = 0; y < grid.getGrid()[z].length; y++)
            {
                for (int x = 0; x < grid.getGrid()[z][y].length; x++)
                {
                    evolutionRule.evolutionCell(grid.getGrid()[z][y][x]);
                }
            }
        }

    }

}
