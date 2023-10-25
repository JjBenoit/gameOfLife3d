package game.executor.turn;

import game.object.Grid;
import game.rules.EvolutionCellRule;

public class SimpleTurnExecutor implements TurnExecutor {

    private EvolutionCellRule evolutionRule;

    public SimpleTurnExecutor(EvolutionCellRule evolutionRule) {
	super();
	this.evolutionRule = evolutionRule;
    }

    @Override
    public void playOneTurn(Grid grid) {

	grid.getBagOfCells().stream().forEach((cell) -> evolutionRule.evolutionCell(cell));
    }

}
