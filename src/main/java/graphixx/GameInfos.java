package graphixx;

import java.awt.Dimension;

import game.object.Grid;

public class GameInfos {

    private Grid grid;

    private Dimension dimCellule;

    private int selectedZ = 0;

    private boolean paused = false;

    private int waitInMS;

    private int gameNbturn;

    public int getGameNbturn() {
	return gameNbturn;
    }

    public void setGameNbturn(int turn) {
	this.gameNbturn = turn;
    }

    public int getSelectedZ() {
	return selectedZ;
    }

    public void setSelectedZ(int selectedZ) {
	this.selectedZ = selectedZ;
    }

    public boolean isPaused() {
	return paused;
    }

    public void setPaused(boolean paused) {
	this.paused = paused;
    }

    public Grid getGrid() {
	return grid;
    }

    public Dimension getDimCellule() {
	return dimCellule;
    }

    public void setDimCellule(Dimension dimCellule) {
	this.dimCellule = dimCellule;
    }

    public void setGrid(Grid grid) {
	this.grid = grid;
    }

    public int getWaitInMS() {
	return waitInMS;
    }

    public void setWaitInMS(int waitInMS) {
	this.waitInMS = waitInMS;
    }

}