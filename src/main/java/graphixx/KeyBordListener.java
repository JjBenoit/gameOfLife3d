package graphixx;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.GameOflife;
import util.GridUtil;

public class KeyBordListener extends KeyAdapter
{
    private GridFramePaint framePrincipale;

    private GameOflife jeu;

    private static final Logger LOGGER = LogManager.getLogger(KeyBordListener.class);

    public KeyBordListener(GridFramePaint framePrincipale, GameOflife jeu)
    {
        this.framePrincipale = framePrincipale;
        this.jeu = jeu;

        LOGGER.info("Press Up and Down arrows for speed up or slow down");
        LOGGER.info("Press left and right arrows change dimension layer");
        LOGGER.info("Press S to save the state of the game");
        LOGGER.info("Press Space to pause the game");
        LOGGER.info("Press Enter to step by step the game");

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        myKeyEvt(e);
    }

    private void myKeyEvt(KeyEvent e)
    {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP)
        {
            framePrincipale.waitInMS += 5;
        }
        else if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN)
        {
            if ((framePrincipale.waitInMS - 5) >= 0)
                framePrincipale.waitInMS -= 5;
        }
        else if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT)
        {
            if (framePrincipale.getSelectedZ() + 1 <= (jeu.getGrid().getGrid().length - 1))
                framePrincipale.setSelectedZ(framePrincipale.getSelectedZ() + 1);
        }
        else if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT)
        {
            if (framePrincipale.getSelectedZ() - 1 >= 0)
                framePrincipale.setSelectedZ(framePrincipale.getSelectedZ() - 1);
        }
        else if (key == KeyEvent.VK_SPACE)
        {
            if (framePrincipale.paused)
                framePrincipale.paused = false;
            else
                framePrincipale.paused = true;

        }

        else if (key == KeyEvent.VK_S)
        {

            File f = new File("saves");
            try
            {
                GridUtil.saveGrid(f, jeu.getGrid(), jeu.getTurn());
                LOGGER.info("Grid state saved under : " + f.getCanonicalFile());
            }
            catch (ParserConfigurationException | TransformerException | IOException e1)
            {
                LOGGER.error("Error while saving grid state", e1);
            }

        }

    }

}
