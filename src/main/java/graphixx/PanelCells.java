package graphixx;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.StateLife;
import game.object.Grid;

public class PanelCells extends Canvas implements GraphicalRender {

    private GameInfos gameInfos;

    // variable permettant d'utiliser la mémoire VRAM
    private BufferStrategy strategy;

    // buffer mémoire ou les images et les textes sont appliqués
    private Graphics buffer;

    private static final Logger LOGGER = LogManager.getLogger(PanelCells.class);

    public PanelCells(GameInfos gameInfos) {
	this.gameInfos = gameInfos;
	setSize(gameInfos.getSizeScreen().width, (gameInfos.getSizeScreen().height - 100));
	addMouseListener(new MouseListenerGrid(gameInfos));
	// inhibe la méthode courante d'affichage du composant
	setIgnoreRepaint(true);
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

	    long time = System.currentTimeMillis();

	    for (int y = 0; y < gameInfos.getGrid().getGrid()[gameInfos.getSelectedZ()].length; y++) {

		try {
		    Thread.ofVirtual().start(new RunnableUpGraphixGrid(buffer, gameInfos.getGrid(),
			    gameInfos.getSelectedZ(), y, gameInfos.getDimCellule(), this)).join();
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

	    }

	    buffer.dispose();
	    strategy.show();

	    if (LOGGER.isDebugEnabled())
		LOGGER.debug("Grid affichée en : " + (System.currentTimeMillis() - time) + " ms");
	}

    }

    record RunnableUpGraphixGrid(Graphics g, Grid grid, int selectedZ, int y, Dimension dimCellule, Canvas panelCells)
	    implements Runnable {

	@Override
	public void run() {

	    for (int x = 0; x < grid.getGrid()[selectedZ][y].length; x++) {

		// on ne dessine pas ce qui ne se voit pas
		if (x * dimCellule.width > panelCells.getWidth())
		    break;

		// on ne dessine pas ce qui ne se voit pas
		if (y * dimCellule.height > panelCells.getHeight())
		    break;

		if (grid.getGrid()[selectedZ][y][x].getState() == StateLife.DEATH_VALUE) {
		    g.setColor(Color.BLACK);
		    g.fillRect(x * dimCellule.width, y * dimCellule.height, dimCellule.width, dimCellule.height);
		} else {
		    g.setColor(Color.WHITE);
		    g.fillRect(x * dimCellule.width, y * dimCellule.height, dimCellule.width, dimCellule.height);
		}

	    }

	}

    }
}
