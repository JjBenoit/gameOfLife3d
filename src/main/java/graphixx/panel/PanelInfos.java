package graphixx.panel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.GameInfos;
import graphixx.engine.GraphicalRender;

public class PanelInfos extends JPanel implements GraphicalRender {

    private GameInfos gameInfos;

    private JLabel nbTurn;

    private JLabel vitesse;

    private static final Logger LOGGER = LogManager.getLogger(PanelInfos.class);

    public PanelInfos(GameInfos gameInfos) {
	this.gameInfos = gameInfos;

	setBorder(new LineBorder(Color.BLACK));
	setLayout(new FlowLayout(FlowLayout.LEFT));

	nbTurn = new JLabel();
	nbTurn.setFont(new Font("Verdana", 1, 20));
	add(nbTurn);

	vitesse = new JLabel();
	vitesse.setFont(new Font("Verdana", 1, 20));
	add(vitesse);

	// inhibe la méthode courante d'affichage du composant
	setIgnoreRepaint(true);

    }

    @Override
    public void render() {

	long time = System.currentTimeMillis();

	upDateInfo();
	repaint();

	if (LOGGER.isDebugEnabled())
	    LOGGER.debug("Infos affichées en : " + (System.currentTimeMillis() - time) + " ms");
    }

    private void upDateInfo() {
	nbTurn.setText("Nb turn :" + gameInfos.getGameNbturn() + "." + " Z :" + gameInfos.getSelectedZ());
	vitesse.setText(gameInfos.isPaused() ? " Game in pause--> Press 'space bar' to resume" : "");
    }

}
