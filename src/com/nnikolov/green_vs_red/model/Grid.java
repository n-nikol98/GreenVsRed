package com.nnikolov.green_vs_red.model;

import com.nnikolov.green_vs_red.util.CellMatrixGridCompatibilityValidator;
import com.nnikolov.green_vs_red.exception.IncompatibleGridCellMatrixShapeException;
import com.nnikolov.green_vs_red.exception.grid.InvalidGridCellMatrixCellCoordinatesException;
import com.nnikolov.green_vs_red.util.RectangularCellMatrixUtil;

import java.util.List;

/**
 * Represent a 2D Rectangular cellMatrix, which conforms to a given shape constraint, specifically:
 * 0 < X <= Y < 1000
 *
 * The cellMatrix, which is stored in this Grid is with an unmodifiable shape & size and cannot be directly
 * accessed (instead, its copy may be obtained through the 'getCellMatrix' method). Additionally, the individual
 * Cells within the matrix can only be received as copies through the 'getCell' method.
 *
 * Cells can be directly added to the cellMatrix via the 'addCell' method or the Color of existing Cells
 * can be modified through the 'setCellColorMethod'.
 *
 * Instances of Cells stored in this Grid may be replaced via the 'replaceCell' method.
 *
 * */
public final class Grid {

    /**
     * A 2D List stores the cellMatrix, which is used by this Grid.
     *
     * */
    private final List<List<Cell>> cellMatrix;

    /**
     * Standard constructor, which initialises a new Grid via two 'short' type axes sizes.
     * The constructed Grid will be filled with Cells that only have a RED Color.
     *
     * @param cellMatrixSizeAxisX A 'short' type, which represents the horizontal size of the Grids cellMatrix.
     *
     * @param cellMatrixSizeAxisY A 'short' type, which represents the vertical size of the Grids cellMatrix.
     *
     * @throws IncompatibleGridCellMatrixShapeException If the provided cellMatrix axes sizes do not produce a
     * cellMatrix that conforms the shape constraint: 0 < X <= Y < 1000, an Exception will be thrown.
     *
     * */
    public Grid(final short cellMatrixSizeAxisX, final short cellMatrixSizeAxisY) {
        /*
        * Assert that the  intended cellMatrix conforms to the shape constrain: 0 < X <= Y < 1000, else
        * throw an Exception.
        * */
        assertRectangularCellMatrixGridCompatibility(cellMatrixSizeAxisX, cellMatrixSizeAxisY);

        /*
        * Initialise the 2D cellMatrix with fixed vertical & horizontal dimensions.
        * */
        cellMatrix = RectangularCellMatrixUtil.createUnmodifiableShapeRectangularCellMatrix.apply(
                new RectangularCellMatrixUtil.RectangularCellMatrixDimensions(
                        cellMatrixSizeAxisX, cellMatrixSizeAxisY) /*Define the dimension of the cellMatrix*/,
                (cellMatrixRow, VoidArg0) ->
                        /*Define the pattern, which should be used to generate the rows of the cellMatrix*/
                        RectangularCellMatrixUtil.fillRectangularCellMatrixRowArrWithCustomInstances.accept(
                                //Fill each row with cells that have a RED Color
                                cellMatrixRow, (VoidArg1) -> new Cell(Cell.Color.RED)));
    }

    /**
     * Copy constructor.
     *
     * @param grid An existing Grid instance.
     *
     * */
    public Grid(final Grid grid) {
        //Receive a deep copy of the existing Grid's cellMatrix and assign it to this one.
        cellMatrix = grid.getCellMatrix();
    }

    /**
     * Replace an existing Cell from the Grid with the copy of a new one.
     *
     * @param cellCoordinateX The horizontal coordinate of the existing Cell.
     *
     * @param cellCoordinateY The vertical coordinate of the existing Cell.
     *
     * @param cell The new Cell, which will be copied into the cellMatrix of the Grid at the specified coordinates.
     *
     * @throws InvalidGridCellMatrixCellCoordinatesException If the provided existing Cell coordinates are invalid,
     * an exception will be thrown.
     *
     * */
    public void replaceCell(final short cellCoordinateX, final short cellCoordinateY,
                        final Cell cell) {
        /*
        * Assert that the provided existing Cell coordinates are within the bounds of the Grids cellMatrix,
        * else throw an Exception.
        * */
        assertCellCoordinatesAreInBounds(cellCoordinateX, cellCoordinateY);

        //Get a reference to the List, which contains the targeted existing Cell and replace it with the new one.
        getCellMatrixRowReference(cellCoordinateY).set(cellCoordinateX, new Cell(cell));
    }

    /**
     * Set the Color of an existing Cell from the Grid.
     *
     * @param cellCoordinateX The horizontal coordinate of the existing Cell.
     *
     * @param cellCoordinateY The vertical coordinate of the existing Cell.
     *
     * @param color The Color, which needs to be set to the existing Cell.
     *
     * @throws InvalidGridCellMatrixCellCoordinatesException If the provided existing Cell coordinates are invalid,
     * an exception will be thrown.
     *
     * */
    public void setCellColor(final short cellCoordinateX, final short cellCoordinateY, final Cell.Color color) {
        /*
         * Assert that the provided existing Cell coordinates are within the bounds of the Grids cellMatrix,
         * else throw an Exception.
         * */
        assertCellCoordinatesAreInBounds(cellCoordinateX, cellCoordinateY);

        //Get the targeted existing Cell and replace its Color with the provided one.
        getCellMatrixRowReference(cellCoordinateY).get(cellCoordinateX).setColor(color);
    }

    /**
     * Get a copy of an existing Cell from the Grids cellMatrix.
     *
     * @param cellCoordinateX The horizontal coordinate of the existing Cell.
     *
     * @param cellCoordinateY The vertical coordinate of the existing Cell.
     *
     * @throws InvalidGridCellMatrixCellCoordinatesException If the provided existing Cell coordinates are invalid,
     * an exception will be thrown.
     *
     * */
    public Cell getCell(final short cellCoordinateX, final short cellCoordinateY) {
        /*
         * Assert that the provided existing Cell coordinates are within the bounds of the Grids cellMatrix,
         * else throw an Exception.
         * */
        assertCellCoordinatesAreInBounds(cellCoordinateX, cellCoordinateY);

        //Return a deep copy ot the existing Cell from the Grids cellMatrix.
        return new Cell(
                cellMatrix.get(cellCoordinateY)
                        .get(cellCoordinateX));
    }

    /**
     * Return a deep copy of the cellMatrix, which is stored in this Grid, with an unmodifiable shape.
     *
     * @return A deep copy of the cellMatrix, which is stored in the Grid, with an unmodifiable shape.
     *
     * */
    public List<List<Cell>> getCellMatrix() {
        //Get the dimension of the Grids cellMatrix.
        final short cellMatrixSizeAxisX = getCellMatrixSizeAxisX();
        final short cellMatrixSizeAxisY = getCellMatrixSizeAxisY();

        /*
         * Return a 2D cellMatrix with fixed vertical & horizontal dimensions, which are equal to the ones
         * of this Grids cellMatrix.
         * */
        return RectangularCellMatrixUtil.createUnmodifiableShapeRectangularCellMatrix.apply(
                new RectangularCellMatrixUtil.RectangularCellMatrixDimensions(
                        cellMatrixSizeAxisX, cellMatrixSizeAxisY), /*Define the dimension of the new cellMatrix*/
                (cellMatrixRow, y) ->
                        /*Define the pattern, which should be used to generate the rows of the cellMatrix*/
                        RectangularCellMatrixUtil.fillRectangularCellMatrixRowArrWithCustomInstances.accept(
                                //Each Row of the new cellMatrix should mirror the row of the current one.
                                cellMatrixRow, (x) -> cellMatrix.get(y).get(x)));
    }

    /**
     * Get the horizontal size of this Grids cellMatrix.
     *
     * @return The horizontal size of this Grids cellMatrix, represented as a 'short' number type.
     *
     * */
    public short getCellMatrixSizeAxisX() {
        return (short) cellMatrix.get(0).size();
    }

    /**
     * Get the vertical size of this Grids cellMatrix.
     *
     * @return The vertical size of this Grids cellMatrix, represented as a 'short' number type.
     *
     * */
    public short getCellMatrixSizeAxisY() {
        return (short) cellMatrix.size();
    }

    /**
     * Replace all of the Cells in this Grids cellMatrix with those from an existing cellMatrix.
     * Every cell is deep copied.
     * 
     * NOTE: If the provided cellMatrix has a proper shape, but does not have the exact size as the 
     * one in this Grid, its Cells will still be copied.
     *
     * @param cellMatrix An existing 2D List cellMatrix. It's Cells will replace the ones that are
     *                   stored in this Grids cellMatrix.
     *
     * @throws IncompatibleGridCellMatrixShapeException An Exception, which will be thrown if the provided cellMatrix
     * is not rectangular or if its shape does not conform to the constraint: 0 < X <= Y < 1000.
     *
     * */
    public void populateFromCellMatrix(final List<List<Cell>> cellMatrix) {
        //Assure that the provided cellMatrix is rectangular
        if (!RectangularCellMatrixUtil.cellMatrixIsRectangular(cellMatrix))
            //If the provided cellMatrix is not rectangular, throw an Exception.
            throw new IncompatibleGridCellMatrixShapeException(
                    (short) -1, (short) -1,
                    "The provided cellMatrix is not rectangular and" +
                            " cannot be used for the initialization of a Grid.");

        /*
        * Obtain the dimension of the provided cellMatrix.
        * If the cellMatrix is empty, its horizontal size will always be 0.
        */
        final short cellMatrixSizeAxisX =
                        cellMatrix.isEmpty() ? 0 : (short) cellMatrix.get(0).size(),
                cellMatrixSizeAxisY =
                            (short) cellMatrix.size();

        /*
         * Assert that the provided cellMatrix conforms to the shape constrain: 0 < X <= Y < 1000, else
         * throw an Exception.
         * */
        assertRectangularCellMatrixGridCompatibility(cellMatrixSizeAxisX, cellMatrixSizeAxisY);

        /*
        * Deep copy all of the cells from the provided cellMatrix into the one, which is stored in this Grid.
        * 
        * NOTE: If the provided cellMatrix has a proper shape, but does not
        * have the exact size as the one in this Grid, its Cells will still be copied.
         */
        for (short y = 0; y < cellMatrixSizeAxisY; y++)
            for (short x = 0; x < cellMatrixSizeAxisX; x++)
                replaceCell(x, y, cellMatrix.get(y).get(x));
    }

    /**
     * Internal utility function.
     * Get a reference to the List row of the internal cellMatrix via a provided vertical coordinate.
     *
     * @param coordinateY The vertical coordinate of the targeted cellMatrix List row.
     *
     * @return A reference to the targeted List row of the internal cellMatrix.
     * */
    private List<Cell> getCellMatrixRowReference(final short coordinateY) {
        return cellMatrix.get(coordinateY);
    }

    /**
     * Internal utility function.
     *
     * Assert that the shape of a potential Grid cellMatrix conforms to the constraint: 0 < X <= Y < 1000.
     *
     * @param cellMatrixSizeAxisX A 'short' type, which represents the horizontal size of the cellMatrix.
     *
     * @param cellMatrixSizeAxisY A 'short' type, which represents the vertical size of the cellMatrix.
     *
     * @throws IncompatibleGridCellMatrixShapeException If the provided cellMatrix axes sizes do not produce a
     * cellMatrix that conforms to the shape constraint: 0 < X <= Y < 1000, an Exception will be thrown.
     *
     * */
    private static void assertRectangularCellMatrixGridCompatibility(final short cellMatrixSizeAxisX,
                                                                     final short cellMatrixSizeAxisY) {
        //Validate that the shape of the potential cellMatrix Grid conforms to the above-mentioned constraint.
        if (!CellMatrixGridCompatibilityValidator.isCompatibleShape(cellMatrixSizeAxisX, cellMatrixSizeAxisY))
            /*
            * If the size of the potential cellMatrix Grid does not conform to the above-mentioned constraint,
            * throw an Exception.
            */
            throw new IncompatibleGridCellMatrixShapeException(cellMatrixSizeAxisX, cellMatrixSizeAxisY);
    }

    /**
     * Internal utility function.
     *
     * Assert that the coordinates of a potential cellMatrix Cell are within the bounds of the cellMatrix stored
     * in this Grid.
     *
     * @param cellCoordinateX A 'short' type, which represents the horizontal coordinate of the potential
     *                       cellMatrix Cell.
     *
     * @param cellCoordinateY A 'short' type, which represents the vertical coordinate of the potential
     *                       cellMatrix Cell.
     *
     * @throws InvalidGridCellMatrixCellCoordinatesException If the provided Cell coordinates are not within the bounds
     * of the cellMatrix stored in this Grid, an Exception will be thrown.
     *
     * */
    private void assertCellCoordinatesAreInBounds(final short cellCoordinateX, final short cellCoordinateY) {
        //Validate that the provided Cell coordinates are within the bounds of the cellMatrix stored in this Grid.
        if (!RectangularCellMatrixUtil.cellCoordinatesAreInBoundsOfRectangularCellMatrix(
                cellMatrix, cellCoordinateX, cellCoordinateY))
            /*
            * If the provided Cell coordinate are not within the bounds of the cellMatrix stored in this Grid,
            * throw an Exception.
            * */
            throw new InvalidGridCellMatrixCellCoordinatesException(this, cellCoordinateX, cellCoordinateY);
    }
}
