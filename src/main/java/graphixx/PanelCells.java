package graphixx;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.GameOflife;
import game.StateLife;
import game.object.Cell;

public class PanelCells extends Canvas implements GraphicalRender {

    private GameInfos gameInfos;

    private GameOflife jeuVie;

    // variable permettant d'utiliser la mémoire VRAM
    private BufferStrategy strategy;

    // buffer mémoire ou les images et les textes sont appliqués
    private Graphics buffer;

    private int selectedZ;

    private List<Cell> listCellsofZ;

    private ForkJoinPool executor;

    private static final Logger LOGGER = LogManager.getLogger(PanelCells.class);

    public PanelCells(GameInfos gameInfos, GameOflife jeuVie) {
	this.gameInfos = gameInfos;
	this.jeuVie = jeuVie;
	setSize(gameInfos.getSizeScreen().width, (gameInfos.getSizeScreen().height - 100));
	addMouseListener(new MouseListenerGrid(gameInfos));
	// inhibe la méthode courante d'affichage du composant
	setIgnoreRepaint(true);
	executor = new ForkJoinPool(10);

    }

    public void activeDBuffering() {

	// 2 buffers dans la VRAM donc c'est du double-buffering
	createBufferStrategy(2);
	strategy = getBufferStrategy();
    }

    @Override
    public void render() {

	if (strategy == null)
	    activeDBuffering();

	buffer = strategy.getDrawGraphics();

	if (!gameInfos.isPaused() && isDisplayable()) {

	    if (listCellsofZ == null || selectedZ != gameInfos.getSelectedZ()) {
		listCellsofZ = gameInfos.getGrid().getBagOfCellsOfZ(selectedZ);
		selectedZ = gameInfos.getSelectedZ();
	    }

	    long time = System.currentTimeMillis();

	    ForkJoinTask task = executor.submit(() -> listCellsofZ.parallelStream().forEach((cell) -> update(cell)));

	    jeuVie.playOneTurn();

	    while (!task.isDone())
		try {
		    Thread.sleep(10);
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

	    buffer.dispose();
	    strategy.show();

	    if (LOGGER.isDebugEnabled())
		LOGGER.debug("Grid affichée en : " + (System.currentTimeMillis() - time) + " ms");
	}

    }

    private void update(Cell cell) {

	// on ne dessine pas ce qui ne se voit pas
	if (!(cell.getPosition().x * gameInfos.getDimCellule().width > getWidth())
		&& !(cell.getPosition().y * gameInfos.getDimCellule().height > getHeight())) {

	    if (cell.getState() == StateLife.DEATH_VALUE) {
		buffer.setColor(Color.BLACK);
		buffer.fillRect(cell.getPosition().x * gameInfos.getDimCellule().width,
			cell.getPosition().y * gameInfos.getDimCellule().height, gameInfos.getDimCellule().width,
			gameInfos.getDimCellule().height);
	    } else {
		buffer.setColor(Color.WHITE);
		buffer.fillRect(cell.getPosition().x * gameInfos.getDimCellule().width,
			cell.getPosition().y * gameInfos.getDimCellule().height, gameInfos.getDimCellule().width,
			gameInfos.getDimCellule().height);
	    }
	}

    }
}
