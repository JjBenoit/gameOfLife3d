package graphixx;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.StateLife;
import game.object.Cell;

public class MouseListenerGrid implements MouseListener
{

    private GridFramePaint framePrincipale;

    private static final Logger LOGGER = LogManager.getLogger(MouseListenerGrid.class);

    public MouseListenerGrid(GridFramePaint framePrincipale)
    {
        this.framePrincipale = framePrincipale;

        LOGGER.info("Click left and bring to life the cell clicked");
        LOGGER.info("Click right and  kill the cell clicked");

    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        Point p = e.getPoint();
        int z = framePrincipale.getSelectedZ();
        int x = p.x / framePrincipale.getDimCellule().width;
        int y = p.y / framePrincipale.getDimCellule().height;

        Cell cell = framePrincipale.getJeu().getGrid().getGrid()[z][y][x];

        if (e.getButton() == MouseEvent.BUTTON1)
            cell.changeState(StateLife.LIFE_VALUE);

        if (e.getButton() == MouseEvent.BUTTON2)
            cell.changeState(StateLife.DEATH_VALUE);

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

}