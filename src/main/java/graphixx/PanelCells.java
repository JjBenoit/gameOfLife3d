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

public class PanelCells extends Canvas implements GraphicalRender
{

    private GameInfos gameInfos;

    private GameOflife jeuVie;

    // variable permettant d'utiliser la mémoire VRAM
    private BufferStrategy strategy;

    // buffer mémoire ou les images et les textes sont appliqués
    private Graphics buffer;

    private int selectedZ;

    private List<Cell> listCellsofZ;

    private static final Logger LOGGER = LogManager.getLogger(PanelCells.class);

    public PanelCells(GameInfos gameInfos, GameOflife jeuVie)
    {
        this.gameInfos = gameInfos;
        this.jeuVie = jeuVie;

        setSize(gameInfos.getGrid().getSizeX() * gameInfos.getDimCellule().width,
            gameInfos.getGrid().getSizeY() * gameInfos.getDimCellule().height);

        addMouseListener(new MouseListenerGrid(gameInfos));
        // inhibe la méthode courante d'affichage du composant
        setIgnoreRepaint(true);

    }

    private void activeDBuffering()
    {
        // 2 buffers dans la VRAM donc c'est du double-buffering
        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }

    @Override
    public void render()
    {

        if (strategy == null)
            activeDBuffering();

        if (!gameInfos.isPaused() && isDisplayable())
        {
            buffer = strategy.getDrawGraphics();

            if (listCellsofZ == null || selectedZ != gameInfos.getSelectedZ())
            {
                listCellsofZ = gameInfos.getGrid().getBagOfCellsOfZ(selectedZ);
                selectedZ = gameInfos.getSelectedZ();
            }

            long time = System.currentTimeMillis();

            // Graphics is not thread safe can't be MultiThreaded, so creation of one single thread
            ForkJoinTask task = ForkJoinPool.commonPool().submit(() -> listCellsofZ.stream().forEach((cell) -> update(cell)));

            // this call a method threaded + join
            jeuVie.playOneTurn();

            // we wait until render is finished
            while (!task.isDone())
            {
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                }

            }
            // we swap buffer when the turn is played and the render is finished
            jeuVie.flipCurrentActiveStateBufferIndex();

            buffer.dispose();
            strategy.show();

            if (LOGGER.isDebugEnabled())
                LOGGER.debug("Grid (calcul next turn et affichage ) en : " + (System.currentTimeMillis() - time) + " ms");
        }

    }

    private void update(Cell cell)
    {

        // on ne dessine pas ce qui ne se voit pas
        if (!(cell.getPositionInGrid().x * gameInfos.getDimCellule().width > getWidth())
            && !(cell.getPositionInGrid().y * gameInfos.getDimCellule().height > getHeight()))
        {

            if (cell.getState() == StateLife.DEATH_VALUE)
            {
                buffer.setColor(Color.BLACK);
                buffer.fillRect(cell.getPositionInGrid().x * gameInfos.getDimCellule().width,
                    cell.getPositionInGrid().y * gameInfos.getDimCellule().height, gameInfos.getDimCellule().width,
                    gameInfos.getDimCellule().height);
            }
            else
            {
                buffer.setColor(Color.WHITE);
                buffer.fillRect(cell.getPositionInGrid().x * gameInfos.getDimCellule().width,
                    cell.getPositionInGrid().y * gameInfos.getDimCellule().height, gameInfos.getDimCellule().width,
                    gameInfos.getDimCellule().height);
            }
        }

    }
}
