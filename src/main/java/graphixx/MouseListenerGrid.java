package graphixx;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.StateLife;
import game.object.Cell;

public class MouseListenerGrid implements MouseListener {

    private GameInfos gameInfos;

    private static final Logger LOGGER = LogManager.getLogger(MouseListenerGrid.class);

    public MouseListenerGrid(GameInfos gameInfos) {
	this.gameInfos = gameInfos;

	LOGGER.info("Click left and bring to life the cell clicked");
	LOGGER.info("Click right and  kill the cell clicked");

    }

    @Override
    public void mouseClicked(MouseEvent e) {
	Point p = e.getPoint();
	int z = gameInfos.getSelectedZ();
	int x = p.x / gameInfos.getDimCellule().width;
	int y = p.y / gameInfos.getDimCellule().height;

	Cell cell = gameInfos.getGrid().getGrid()[z][y][x];

	if (e.getButton() == MouseEvent.BUTTON1)
	    cell.setNextState(StateLife.LIFE_VALUE);

	if (e.getButton() == MouseEvent.BUTTON2)
	    cell.setNextState(StateLife.DEATH_VALUE);

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}