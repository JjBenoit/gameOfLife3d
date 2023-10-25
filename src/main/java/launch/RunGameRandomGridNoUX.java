package launch;

import game.GameOflife;
import game.StateLife;
import game.object.Grid;
import game.visitor.VisitThreeDimensionalArrayCellTor;
import graphixx.GameInfos;
import util.GridUtil;

public class RunGameRandomGridNoUX {

    public static void main(String[] args) {
	GameInfos gameInfos = new GameInfos();

	// Taille de la grille
	int sizeX = 3;
	int sizeY = 3;
	int sizeZ = 1;

	gameInfos
		.setGrid(new Grid(sizeZ, sizeY, sizeX, StateLife.DEATH_VALUE, new VisitThreeDimensionalArrayCellTor()));

	// option pré remplir la gille avec des cellules mortes random
	GridUtil.addRandomDeathCell((int) ((gameInfos.getGrid().getSizeY() * gameInfos.getGrid().getSizeX()) * 0.5),
		StateLife.LIFE_VALUE, gameInfos.getGrid());

	GameOflife jeuVie = new GameOflife(gameInfos);
	jeuVie.playNumberTurns(10);

    }

}
