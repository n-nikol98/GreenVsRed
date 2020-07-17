package com.nnikolov.green_vs_red.util;

import com.nnikolov.green_vs_red.model.Cell;
import com.nnikolov.green_vs_red.model.Grid;

import java.util.List;

/**
 * A class, which accepts a Grid instance and progressively steps up its generations (as per the requirements
 * of this application). This process can continue indefinitely.
 *
 * The internal Grid instance is initially deep-copied from a provided Grid instance, as such, this class insures
 * that it will independently modify a Grid without affecting the original one.
 *
 * Additionally, when requested by the User (at any generation), the internal Grid instance is provided as a deep-copy,
 * therefore assuring that the operation of the instance of this class cannot be interrupted.
 *
 * */
public final class GridGenerationStepper {

    /**
     * Internal Grid instance
     *
     * */
    private final Grid grid;

    /**
     * Lone constructor.
     * Deep copies an existing Grid instance into the internal Grid variable.
     *
     * @param grid An existing Grid instance.
     *
     * */
    public GridGenerationStepper(final Grid grid) {
        this.grid = new Grid(grid);
    }

    /**
     * Steps up the internal Grid instance to its next generation.
     *
     * */
    public void stepToNext() {
        //Obtain the internal Grids cellMatrix.
        final List<List<Cell>> cellMatrix = grid.getCellMatrix();

        //Obtain the dimension of the internal Grids cellMatrix.
        final short
                cellMatrixSizeAxisX = (short) cellMatrix.get(0).size(),
                cellMatrixSizeAxisY = (short) cellMatrix.size();

        //Initialise a new cellMatrix, which will hold the Cells of the next generation of the internal Grid.
        final List<List<Cell>> nextGenerationMatrix = RectangularCellMatrixUtil
                 //Initialise the 2D cellMatrix with fixed vertical & horizontal dimensions.
                .createUnmodifiableShapeRectangularCellMatrix.apply(
                        new RectangularCellMatrixUtil.RectangularCellMatrixDimensions(
                                cellMatrixSizeAxisX, cellMatrixSizeAxisY) /*Define the dimension of the cellMatrix*/,
                        (VoidArg0, VoidArg1) -> {}); //Fill each row of the modified cellMatrix with NULLs.

        //Loop through all of the NULL Cells of the new modified cellMatrix.
        for (short y = 0; y < cellMatrixSizeAxisY; y++)
            for (short x = 0; x < cellMatrixSizeAxisX; x++)
                //Replace each NULL cell of the new modified cellMatrix with is next generation variant.
                nextGenerationMatrix.get(y).set(x, new Cell(getNextGenerationCellColor(x, y)));

        //Replace the cellMatrix of the internal Grid instance with the next Grid generation cellMatrix.
        grid.populateFromCellMatrix(nextGenerationMatrix);
    }

    /**
     * Get a deep-copy of the internal Grid instance.
     *
     * @return A deep-copy of the internal Grid instance.
     *
     * */
    public Grid getGrid() {
        return new Grid(grid);
    }

    /**
     * Internal utility function.
     * Calculate the next generation Color of an existing Cell in the internal Grids cellMatrix.
     *
     * @param cellCoordinateX The horizontal coordinate of the existing Cell in the internal Grids cellMatrix.
     *
     * @param cellCoordinateY The vertical coordinate of the existing Cell in the internal Grids cellMatrix.
     *
     * @return The next generation Color of the existing Cell in the internal Grids cellMatrix.
     *
     * */
    private Cell.Color getNextGenerationCellColor(final short cellCoordinateX, final short cellCoordinateY) {
        //Obtain the internal Grids cellMatrix.
        final List<List<Cell>> cellMatrix = grid.getCellMatrix();

        //Counter for the GREEN Color neighbours of the existing Cell in the internal Grids cellMatrix.
        long greenNeighboursCount = 0;

        /*
        * Scan all of the coordinates in the immediate vicinity of the target existing
        * Cell (including its own coordinates).*/
        for (short x = (short) (cellCoordinateX - 1); x <= cellCoordinateX + 1; x++) {
            for (short y = (short) (cellCoordinateY - 1); y <= cellCoordinateY + 1; y++) {
                /*
                * If our current coordinates equal the target existing Cell, continue with the next iteration
                * of the loop.
                * */
                if (x == cellCoordinateX && y == cellCoordinateY)
                    continue;

                /*
                 * Assure that the current target Cell neighbour coordinates are within the bounds of the internal
                 * Grids cellMatrix.
                 * */
                if (RectangularCellMatrixUtil.cellCoordinatesAreInBoundsOfRectangularCellMatrix(cellMatrix, x, y))
                    //Check if the target Cell neighbouring Cell has a GREEN Color.
                    if (cellMatrix.get(y).get(x).getColor().isGreen())
                        //Increment GREEN neighbours counter.
                        greenNeighboursCount++;
            }
        }

        //Obtain the Color of the targeted internal Grid cellMatrix Cell.
        final Cell.Color targetCellColor = cellMatrix.get(cellCoordinateY).get(cellCoordinateX).getColor();

        /* Depending on the amount of GREEN neighbours the targeted internal Grid cellMatrix Cell has and its
         * current Color, determine its next Grid generation Color within the cellMatrix.
         */
        if (targetCellColor.isRed())
            return greenNeighboursCount == 3 ||
                    greenNeighboursCount == 6 ? Cell.Color.GREEN : targetCellColor;
        else
            return greenNeighboursCount == 2 ||
                    greenNeighboursCount == 3 ||
                    greenNeighboursCount == 6 ? targetCellColor : Cell.Color.RED;
    }
}
