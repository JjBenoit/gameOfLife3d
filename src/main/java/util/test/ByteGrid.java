package util.test;

import java.util.Random;

public class ByteGrid {

    // Positions from right to left . java signed int ( default ) so the leftmost
    // bit used to represent the sign
    public final byte[] MASKS = { -0b10000000, 0b01000000, 0b00100000, 0b00010000, 0b00001000, 0b00000100, 0b00000010,
	    0b00000001 };

    int sizeX;
    int sizeY;
    byte[] grid;

    public ByteGrid(int sizeX, int sizeY) {
	super();
	this.sizeX = sizeX;
	this.sizeY = sizeY;
	this.grid = new byte[(sizeX * sizeY / Byte.SIZE)];

    }

    public void genGrid() {

	Random random = new Random(100L);

	// for each byte of the grid
	for (int i = 0; i < grid.length; i++) {
	    // set one byte
	    for (int j = 0; j < MASKS.length; j++) {

		int nb = (random.nextBoolean() == true ? 1 : 0);

		if (nb == 1) {
		    grid[i] = (byte) (grid[i] ^ MASKS[j]);
		    System.out.println(byteToString(grid[i], 0, Byte.SIZE));
		}
	    }
	}
    }

    private void setValue(boolean value, int x, int y) {

	// the number of cells is computed from the top line ( index 0 ) *
	int indexCell = (x * sizeX + y);
	int indexByte = indexCell / Byte.SIZE;
	int indexBitInByte = indexCell % Byte.SIZE;

	if (indexByte < grid.length) {
	    grid[indexByte] = (byte) (grid[indexByte] ^ MASKS[indexBitInByte]);
	}
    }

    public void toStringGrid() {

	StringBuilder buffer = new StringBuilder();
	int nBbytesByLine = sizeX / Byte.SIZE;
	int bitsLeft = sizeX % Byte.SIZE;
	int indexByte = 0;
	int indexByte = 0;

	while (indexByte < grid.length) {

	    // if()
	    {
		buffer.append("\n");

	    }

	}

    }

    private void toStringOneLineGrid(int indexByte, int nBbytes, int indexbit, int nBbitsLeft) {

	StringBuilder buffer = new StringBuilder();

	for (int i = indexByte; i < nBbytes; i++) {
	    buffer.append(grid[indexByte]);
	    indexByte++;
	}

    }

    // TODO add checks on start, end ...
    private StringBuilder byteToString(byte value, int startBitIndex, int endBitIndex) {

	StringBuilder buffer = new StringBuilder();
	for (int j = startBitIndex; j < endBitIndex; j++) {
	    // if after AND the result equals the mask, so the value of the position defined
	    // by the mask is 1
	    buffer.append(MASKS[j] == (value & MASKS[j]) ? 1 : 0);
	}

	return buffer;

    }

    public static void main(String[] args) {
	ByteGrid grid = new ByteGrid(10, 10);
	grid.genGrid();
	grid.setValue(true, 3, 1);
    }

}
