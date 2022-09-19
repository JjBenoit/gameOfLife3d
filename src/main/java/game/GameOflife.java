package game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.executor.gridcontext.GridContextUpdateExecutor;
import game.executor.gridcontext.MultiThreadGridContextUpdateExecutor;
import game.executor.turn.MultiThreadTurnExecutor;
import game.executor.turn.TurnExecutor;
import game.object.Grid;
import game.rules.EvolutionCellRule;
import game.rules.StandardRulesEvolutionCell;
import util.GridUtil;

public class GameOflife
{

    private Grid grid;

    private int turn;

    private EvolutionCellRule evolutionRule;

    private TurnExecutor turnExecutor;

    private GridContextUpdateExecutor gridContextUpdateExecutor;

    private static final Logger LOGGER = LogManager.getLogger(GameOflife.class);

    private static final Logger LOGGER_PRINTGRID = LogManager.getLogger("printGrid");

    public GameOflife(int sizeX, int sizeY, int sizeZ)
    {
        this(new Grid(sizeZ, sizeY, sizeX));
    }

    public GameOflife(Grid grid)
    {
        this.grid = grid;
        // customisable : les regles d'evolution des cellules
        evolutionRule = new StandardRulesEvolutionCell();

        // customisable : la moteur d'enchainement des tours calculant l'état des celulles
        turnExecutor = new MultiThreadTurnExecutor(50, evolutionRule);

        // customisable : la moteur de mise a jour du context ( environnment des cellule )
        gridContextUpdateExecutor = new MultiThreadGridContextUpdateExecutor(10, grid);

        // turnExecutor = new SimpleTurnExecutor(evolutionRule);
        // gridContextUpdateExecutor = new SimpleGridContextUpdateExecutor(grid);

    }

    public void playNumberTurns(int nbTurn)
    {
        int i = 0;
        while (i < nbTurn)
        {
            playOneTurn();
            i++;
        }
    }

    public void playOneTurn()
    {

        gridContextUpdateExecutor.updateContextFromWrtingContext();

        if (LOGGER_PRINTGRID.isDebugEnabled())
        {
            LOGGER_PRINTGRID.debug("Turn:" + turn + "\n" + GridUtil.printGrid(grid));
        }

        long time = System.currentTimeMillis();

        turnExecutor.playOneTurn(grid);

        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Grid traité en : " + (System.currentTimeMillis() - time) + " ms");
        }

        turn++;
    }

    public Grid getGrid()
    {
        return grid;
    }

    public int getTurn()
    {
        return turn;
    }

}
