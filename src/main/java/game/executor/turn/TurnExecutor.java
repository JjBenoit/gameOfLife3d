package game.executor.turn;

import game.object.Grid;

public interface TurnExecutor
{
    public abstract void playOneTurn(Grid grid);
}
