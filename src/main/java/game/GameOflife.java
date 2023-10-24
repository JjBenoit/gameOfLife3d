package game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.executor.gridcontext.GridContextUpdateExecutor;
import game.executor.gridcontext.MultiThreadGridContextUpdateExecutor;
import game.executor.turn.MultiThreadTurnExecutor;
import game.executor.turn.TurnExecutor;
import game.rules.EvolutionCellRule;
import game.rules.StandardRulesEvolutionCell;
import graphixx.GameInfos;
import util.GridUtil;

public class GameOflife {

    private GameInfos gameInfos;

    private EvolutionCellRule evolutionRule;

    private TurnExecutor turnExecutor;

    private GridContextUpdateExecutor gridContextUpdateExecutor;

    private static final Logger LOGGER = LogManager.getLogger(GameOflife.class);

    private static final Logger LOGGER_PRINTGRID = LogManager.getLogger("printGrid");

    public GameOflife(GameInfos gameInfos) {

	this.gameInfos = gameInfos;
	// customisable : les regles d'evolution des cellules
	evolutionRule = new StandardRulesEvolutionCell();

	// customisable : la moteur d'enchainement des tours calculant l'état des
	// celulles
	turnExecutor = new MultiThreadTurnExecutor(20, evolutionRule);

	// customisable : la moteur de mise a jour du context ( environnment des cellule
	// )
	gridContextUpdateExecutor = new MultiThreadGridContextUpdateExecutor(10, gameInfos.getGrid());

	// turnExecutor = new SimpleTurnExecutor(evolutionRule);
	// gridContextUpdateExecutor = new SimpleGridContextUpdateExecutor(grid);

    }

    public void play() {
	while (true) {
	    playOneTurn();
	}
    }

    public void playNumberTurns(int nbTurn) {
	int i = 0;
	while (i < nbTurn) {
	    playOneTurn();
	    i++;
	}
    }

    public void playOneTurn() {

	if (!gameInfos.isPaused()) {
	    gridContextUpdateExecutor.updateContextFromWrtingContext();

	    if (LOGGER_PRINTGRID.isDebugEnabled()) {
		LOGGER_PRINTGRID.debug("Turn:" + gameInfos.getGameNbturn() + "\n" + GridUtil.printGrid(gameInfos.getGrid()));
	    }

	    long time = System.currentTimeMillis();

	    turnExecutor.playOneTurn(gameInfos.getGrid());

	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Grid traité en : " + (System.currentTimeMillis() - time) + " ms");
	    }

	    gameInfos.setGameNbturn(gameInfos.getGameNbturn() + 1);
	}
    }

}
