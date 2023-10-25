package game.executor.turn;

import java.util.concurrent.ForkJoinPool;

import game.object.Grid;
import game.rules.EvolutionCellRule;

public class MultiThreadTurnExecutor implements TurnExecutor {

    private EvolutionCellRule evolutionRule;

    private ForkJoinPool executor;

    public MultiThreadTurnExecutor(int nbThread, EvolutionCellRule evolutionRule) {
	super();
	this.evolutionRule = evolutionRule;
	executor = new ForkJoinPool(nbThread);

    }

    @Override
    public void playOneTurn(Grid grid) {

	executor.submit(
		() -> grid.getBagOfCells().parallelStream().forEach((cell) -> evolutionRule.evolutionCell(cell)))
		.join();

    }

}
