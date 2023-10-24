package graphixx;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import util.GridUtil;

public class KeyBordListener extends KeyAdapter {
    private GameInfos gameInfos;

    private static final Logger LOGGER = LogManager.getLogger(KeyBordListener.class);

    public KeyBordListener(GameInfos gameInfos) {
	this.gameInfos = gameInfos;

	LOGGER.info("Press Up and Down arrows for speed up or slow down");
	LOGGER.info("Press left and right arrows change dimension layer");
	LOGGER.info("Press S to save the state of the game");
	LOGGER.info("Press Space to pause the game");
	LOGGER.info("Press Enter to step by step the game");

    }

    @Override
    public void keyPressed(KeyEvent e) {
	myKeyEvt(e);
    }

    private void myKeyEvt(KeyEvent e) {

	int key = e.getKeyCode();

	if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP) {
	    gameInfos.setWaitInMS(gameInfos.getWaitInMS() + 5);
	} else if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN) {
	    if ((gameInfos.getWaitInMS() - 5) >= 0)
		gameInfos.setWaitInMS(gameInfos.getWaitInMS() - 5);
	} else if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT) {
	    if (gameInfos.getSelectedZ() + 1 <= (gameInfos.getGrid().getGrid().length - 1))
		gameInfos.setSelectedZ(gameInfos.getSelectedZ() + 1);
	} else if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT) {
	    if (gameInfos.getSelectedZ() - 1 >= 0)
		gameInfos.setSelectedZ(gameInfos.getSelectedZ() - 1);
	} else if (key == KeyEvent.VK_SPACE) {
	    if (gameInfos.isPaused())
		gameInfos.setPaused(false);
	    else
		gameInfos.setPaused(true);

	}

	else if (key == KeyEvent.VK_S) {

	    File f = new File("saves");
	    try {
		GridUtil.saveGrid(f, gameInfos.getGrid(), gameInfos.getGameNbturn());
		LOGGER.info("Grid state saved under : " + f.getCanonicalFile());
	    } catch (ParserConfigurationException | TransformerException | IOException e1) {
		LOGGER.error("Error while saving grid state", e1);
	    }

	}

    }

}
