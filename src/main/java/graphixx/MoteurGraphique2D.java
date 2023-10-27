package graphixx;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import game.object.Cell;

public class MoteurGraphique2D
{

    // boucle d'affichage
    private RenderingThread renderingThread = new RenderingThread();

    private List<GraphicalRender> renders;

    protected int waitInMS = 10;

    private static final Logger LOGGER = LogManager.getLogger(MoteurGraphique2D.class);

    public MoteurGraphique2D()
    {
        renders = new ArrayList<>();
    }

    public void go()
    {
        renderingThread.start();
    }

    public void addRender(GraphicalRender render)
    {
        renders.add(render);
    }

    class RenderingThread extends Thread
    {
        /**
         * Ce thread appelle le rafraichissement de notre fenetre toutes les n milli-secondes
         */
        @Override
        public void run()
        {
            while (true)
            {
                long deb = System.currentTimeMillis();
                try
                {
                    // on appelle notre propre méthode de rafraichissement
                    for (GraphicalRender graphicalRender : renders)
                    {
                        graphicalRender.render();
                    }
                    long delay = System.currentTimeMillis() - deb;
                    if (delay < waitInMS)
                        sleep(waitInMS - delay);

                    if (LOGGER.isDebugEnabled())
                        LOGGER.debug("FPS : " + (1000 / delay));
                }
                catch (Exception e)
                {
                }
            }
        }
    }

}