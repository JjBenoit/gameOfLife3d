package graphixx.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.GameInfos;
import game.GameOflife;
import graphixx.engine.MoteurGraphique2D;
import graphixx.listener.KeyBordListener;

public class GridFramePaint extends JFrame {

    private MoteurGraphique2D moteurGraphique2D;

    private GameInfos gameInfos;

    private PanelCells panelCells;

    private PanelInfos panelInfos;

    private static final Logger LOGGER = LogManager.getLogger(GridFramePaint.class);

    public GridFramePaint(GameInfos gameInfos, GameOflife jeuVie) {

	this.gameInfos = gameInfos;
	this.gameInfos.setDimCellule(new Dimension(5, 5));

	// ((Graphics2D) getGraphics()).getDeviceConfiguration().getBounds()
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setIgnoreRepaint(true);
	setVisible(true);

	addKeyListener(new KeyBordListener(gameInfos));

	moteurGraphique2D = new MoteurGraphique2D();

	panelCells = new PanelCells(gameInfos, jeuVie);
	add(panelCells, BorderLayout.CENTER);
	moteurGraphique2D.addRender(panelCells);

	panelInfos = new PanelInfos(gameInfos);
	add(panelInfos, BorderLayout.SOUTH);
	moteurGraphique2D.addRender(panelInfos);

	pack();

    }

    public void startRender() {

	moteurGraphique2D.go();

    }

}
