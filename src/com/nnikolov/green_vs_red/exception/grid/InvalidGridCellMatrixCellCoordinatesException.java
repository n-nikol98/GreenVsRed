package com.nnikolov.green_vs_red.exception.grid;

import com.nnikolov.green_vs_red.model.Grid;

/**
 * A RuntimeException indicating that the coordinates of a Cell within a Grids cellMatrix are invalid.
 * Includes the incorrect Cell coordinates. The instance of the Grid that is related to this Exception can be accessed
 * via the superclass GridException.
 *
 * */
public final class InvalidGridCellMatrixCellCoordinatesException extends GridException {

    /**
     * The incorrect Cell coordinates within the provided Grids cellMatrix.
     *
     * */
    private final short cellCoordinateX, cellCoordinateY;

    /**
     * Default initialization constructor that accepts a Grid instance and the incorrect cellMatrix Cell coordinates.
     * Includes a generic error message.
     *
     * @param grid The Grid instance that the superclass will be initialised with.
     *             Should be the Grid that is related to the issue that occurred.
     *
     * @param cellCoordinateX The incorrect horizontal coordinate within the provided Grids cellMatrix.
     *
     * @param cellCoordinateY The incorrect vertical coordinate within the provided Grids cellMatrix.
     *
     * */
    public InvalidGridCellMatrixCellCoordinatesException(final Grid grid,
                                                     final short cellCoordinateX,
                                                     final short cellCoordinateY) {
        super(grid, "The cell coordinates (" +
                cellCoordinateX +
                ", " +
                cellCoordinateY +
                ") are invalid for this grid.");

        this.cellCoordinateX = cellCoordinateX;
        this.cellCoordinateY = cellCoordinateY;
    }

    /**
     * Advanced initialization construct that accepts a Grid instance and the incorrect cellMatrix Cell coordinates.
     * Includes the possibility of adding a custom error message to the Exception.
     *
     * @param grid The Grid instance that the superclass will be initialised with.
     *             Should be the Grid that is related to the issue that occurred.
     *
     * @param cellCoordinateX The incorrect horizontal coordinate within the provided Grids cellMatrix.
     *
     * @param cellCoordinateY The incorrect vertical coordinate within the provided Grids cellMatrix.
     *
     * @param message A custom error message that can more accurately describe the issue
     *                that occurred.
     *
     * */
    public InvalidGridCellMatrixCellCoordinatesException(final Grid grid,
                                                     final short cellCoordinateX,
                                                     final short cellCoordinateY,
                                                     final String message) {
        super(grid, message);

        this.cellCoordinateX = cellCoordinateX;
        this.cellCoordinateY = cellCoordinateY;
    }

    /**
     * Get the incorrect horizontal coordinate within the provided Grids cellMatrix.
     *
     * @return The incorrect horizontal coordinate within the provided Grids cellMatrix.
     *
     * */
    public short getCellCoordinateX() {
        return cellCoordinateX;
    }

    /**
     * Get the incorrect vertical coordinate within the provided Grids cellMatrix.
     *
     * @return The incorrect vertical coordinate within the provided Grids cellMatrix.
     *
     * */
    public short getCellCoordinateY() {
        return cellCoordinateY;
    }
}
