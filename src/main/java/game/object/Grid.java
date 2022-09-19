package game.object;

public class Grid
{
    // Z,Y,X, grid en cours ( profondeur Z, puis liste lignes de colonnes , 2 dimen les lignes (Y) , 3 dimension les données
    // de chaque
    // pour chaque niveau de Z , une grille Y,X
    private Cell[][][] grid;

    public Grid(int sizeZ, int sizeY, int sizeX)
    {
        grid = (new Cell[sizeZ][sizeY][sizeX]);
    }

    public Grid(int sizeZ)
    {
        grid = (new Cell[sizeZ][][]);
    }

    public Cell[][][] getGrid()
    {
        return grid;
    }

}
