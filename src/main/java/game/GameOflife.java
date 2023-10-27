package game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.executor.turn.MultiThreadTurnExecutor;
import game.executor.turn.TurnExecutor;
import game.object.Cell;
import game.rules.EvolutionCellRule;
import game.rules.StandardRulesEvolutionCell;
import graphixx.GameInfos;
import util.GridUtil;

public class GameOflife
{

    private GameInfos gameInfos;

    private EvolutionCellRule evolutionRule;

    private TurnExecutor turnExecutor;

    private static final Logger LOGGER = LogManager.getLogger(GameOflife.class);

    private static final Logger LOGGER_PRINTGRID = LogManager.getLogger("printGrid");

    public GameOflife(GameInfos gameInfos)
    {

        this.gameInfos = gameInfos;
        // customisable : les regles d'evolution des cellules
        evolutionRule = new StandardRulesEvolutionCell();

        // customisable : la moteur d'enchainement des tours calculant l'état des
        // celulles
        turnExecutor = new MultiThreadTurnExecutor(10, evolutionRule);

    }

    public void play()
    {
        while (true)
        {
            playOneTurn();
            flipCurrentActiveStateBufferIndex();
        }
    }

    public void playNumberTurns(int nbTurn)
    {
        int i = 0;
        while (i < nbTurn)
        {
            playOneTurn();
            flipCurrentActiveStateBufferIndex();
            i++;
        }
    }

    public void playOneTurn()
    {

        if (LOGGER_PRINTGRID.isDebugEnabled())
        {
            LOGGER_PRINTGRID
                .debug("Turn:" + gameInfos.getGameNbturn() + "\n" + GridUtil.printGrid(gameInfos.getGrid()));
        }

        long time = System.currentTimeMillis();

        turnExecutor.playOneTurn(gameInfos.getGrid());

        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Grid traité en : " + (System.currentTimeMillis() - time) + " ms");
        }

        gameInfos.setGameNbturn(gameInfos.getGameNbturn() + 1);

    }

    public void flipCurrentActiveStateBufferIndex()
    {
        // change de buffer pour le prochain tour
        Cell.flipCurrentActiveStateBufferIndex();

    }

}
