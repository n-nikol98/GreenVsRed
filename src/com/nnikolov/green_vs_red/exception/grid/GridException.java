package com.nnikolov.green_vs_red.exception.grid;

import com.nnikolov.green_vs_red.model.Grid;

/**
 * A generic Grid RuntimeException.
 * Stores a reference to a Grid that is related to the issue that occurred.
 *
 * */
public class GridException extends RuntimeException {

    /**
     * The Grid instance, which is related to the cause of the Exception.
     *
     * */
    private final Grid grid;

    /**
     * Default initialization construct that accepts a Grid instance.
     * Includes a generic error message.
     *
     * @param grid The Grid instance that this class will be initialised with.
     *             Should be the Grid that is related to the issue that occurred.
     *
     * */
    public GridException(final Grid grid) {
        super("An unidentified Exception has occurred with this Grid.");

        this.grid = grid;
    }

    /**
     * Advanced initialization construct that accepts a Grid instance.
     * Includes the possibility of adding a custom error message to the Exception.
     *
     * @param grid The Grid instance that this class will be initialised with.
     *             Should be the Grid that is related to the issue that occurred.
     *
     * @param message A custom error message that can more accurately describe the issue
     *                that occurred.
     *
     * */
    public GridException(final Grid grid, final String message) {
        super(message);

        this.grid = grid;
    }

    /**
     * Get the Grid instance, which is related to the cause of this Exception.
     *
     * @return The Grid instance, which is related to the cause of this Exception.
     *
     * */
    public Grid getGrid() {
        return grid;
    }
}
