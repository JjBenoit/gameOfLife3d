package graphixx;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public abstract class MoteurGraphique2D extends JFrame
{

    public int tailleEcranX = 1024;

    public int tailleEcranY = 768;

    // boucle d'affichage
    private RenderingThread renderingThread = new RenderingThread();

    // variable permettant d'utiliser la mémoire VRAM
    protected BufferStrategy strategy;

    // buffer mémoire ou les images et les textes sont appliqués
    protected Graphics buffer;

    protected int waitInMS = 100;

    public MoteurGraphique2D()
    {

        init();
    }

    private void init()
    {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(tailleEcranX, tailleEcranY);
        setVisible(true);

        // inhibe la méthode courante d'affichage du composant
        setIgnoreRepaint(true);

        // 2 buffers dans la VRAM donc c'est du double-buffering
        createBufferStrategy(2);

        // récupère les buffers graphiques dans la mémoire VRAM
        strategy = getBufferStrategy();
        buffer = strategy.getDrawGraphics();

    }

    public void go()
    {
        renderingThread.start();
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
                try
                {
                    // on appelle notre propre méthode de rafraichissement
                    graphicalRender();

                    sleep(waitInMS);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    protected abstract void graphicalRender();

}