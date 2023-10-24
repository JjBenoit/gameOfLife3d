package graphixx;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GridFramePaint extends JFrame {

    public int tailleEcranX = 1024;

    public int tailleEcranY = 768;

    private MoteurGraphique2D moteurGraphique2D;

    private GameInfos gameInfos;

    private PanelCells panelCells;

    private PanelInfos panelInfos;

    private static final Logger LOGGER = LogManager.getLogger(GridFramePaint.class);

    public GridFramePaint(GameInfos gameInfos) {

	setSize(tailleEcranX, tailleEcranY);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setIgnoreRepaint(true);
	setVisible(true);

	addKeyListener(new KeyBordListener(gameInfos));

	this.gameInfos = gameInfos;
	this.gameInfos.setSizeScreen(((Graphics2D) getGraphics()).getDeviceConfiguration().getBounds());
	this.gameInfos.setDimCellule(new Dimension(5, 5));

	moteurGraphique2D = new MoteurGraphique2D();

	panelCells = new PanelCells(gameInfos);
	add(panelCells, BorderLayout.CENTER);
	moteurGraphique2D.addRender(panelCells);

	panelInfos = new PanelInfos(gameInfos);
	add(panelInfos, BorderLayout.SOUTH);
	moteurGraphique2D.addRender(panelInfos);

    }

    public void startRender() {

	moteurGraphique2D.go();

    }

}
