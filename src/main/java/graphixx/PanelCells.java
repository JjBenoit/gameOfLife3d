package graphixx;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

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

	setSize(gameInfos.getGrid().getSizeX() * gameInfos.getDimCellule().width,
		gameInfos.getGrid().getSizeY() * gameInfos.getDimCellule().height);

	addMouseListener(new MouseListenerGrid(gameInfos));
	// inhibe la méthode courante d'affichage du composant
	setIgnoreRepaint(true);
	executor = new ForkJoinPool(2);

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

	    // BufferedImage img = new BufferedImage(selectedZ, selectedZ,
	    // BufferedImage.TYPE_INT_RGB);
	    // Pour multiThread : executor.submit(() ->
	    // listCellsofZ.parallstream().forEach((cell) -> update(cell))).join(); mais le
	    // muti thread pose probleme grphic est il tread safe ?

	    executor.submit(() -> listCellsofZ.stream().forEach((cell) -> update(cell))).join();

	    jeuVie.playOneTurn();

	    buffer.dispose();
	    strategy.show();

	    if (LOGGER.isDebugEnabled())
		LOGGER.debug("Grid affichée en : " + (System.currentTimeMillis() - time) + " ms");
	}

    }

    private void update(Cell cell) {

	// on ne dessine pas ce qui ne se voit pas
	if (!(cell.getPositionInGrid().x * gameInfos.getDimCellule().width > getWidth())
		&& !(cell.getPositionInGrid().y * gameInfos.getDimCellule().height > getHeight())) {

	    if (cell.getState() == StateLife.DEATH_VALUE) {
		buffer.setColor(Color.BLACK);
		buffer.fillRect(cell.getPositionInGrid().x * gameInfos.getDimCellule().width,
			cell.getPositionInGrid().y * gameInfos.getDimCellule().height, gameInfos.getDimCellule().width,
			gameInfos.getDimCellule().height);
	    } else {
		buffer.setColor(Color.WHITE);
		buffer.fillRect(cell.getPositionInGrid().x * gameInfos.getDimCellule().width,
			cell.getPositionInGrid().y * gameInfos.getDimCellule().height, gameInfos.getDimCellule().width,
			gameInfos.getDimCellule().height);
	    }
	}

    }
}
