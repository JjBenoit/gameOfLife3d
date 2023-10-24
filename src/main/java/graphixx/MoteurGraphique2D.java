package graphixx;

import java.util.ArrayList;
import java.util.List;

public class MoteurGraphique2D {

    // boucle d'affichage
    private RenderingThread renderingThread = new RenderingThread();

    private List<GraphicalRender> renders;

    protected int waitInMS = 20;

    public MoteurGraphique2D() {
	renders = new ArrayList<>();
    }

    public void go() {
	renderingThread.start();
    }

    public void addRender(GraphicalRender render) {
	renders.add(render);
    }

    class RenderingThread extends Thread {
	/**
	 * Ce thread appelle le rafraichissement de notre fenetre toutes les n
	 * milli-secondes
	 */
	@Override
	public void run() {
	    while (true) {
		try {
		    // on appelle notre propre méthode de rafraichissement

		    for (GraphicalRender graphicalRender : renders) {
			graphicalRender.render();
		    }
		    sleep(waitInMS);

		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}
    }

}