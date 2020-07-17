package com.nnikolov.green_vs_red.exception;

/**
 * A RuntimeException indicating that the provided cellMatrix axes sizes do not produce a shape that is compatible
 * with a Grid.
 * Includes the inappropriate cellMatrix axes sizes.
 *
 * */
public final class IncompatibleGridCellMatrixShapeException extends RuntimeException {

    /**
     * The inappropriate Grid cellMatrix axes sizes.
     *
     * */
    private final short cellMatrixSizeAxisX, cellMatrixSizeAxisY;

    /**
     * Default initialization construct that accepts the inappropriate Grid cellMatrix axesSizes.
     * Includes a generic error message.
     *
     * @param cellMatrixSizeAxisX The inappropriate Grid cellMatrix horizontal axis size.
     *
     * @param cellMatrixSizeAxisY The inappropriate Grid cellMatrix vertical axis size.
     *
     * */
    public IncompatibleGridCellMatrixShapeException(final short cellMatrixSizeAxisX, final short cellMatrixSizeAxisY) {
        super("A Grid with the size: (" +
                cellMatrixSizeAxisX + ", " +
                cellMatrixSizeAxisY +
                        "), on the X and Y axes respectively," +
                        " cannot be created, as it is an invalid shape," +
                        " as per the requirements of this application." +
                        " Please ensure that the following bounds" +
                        " are met: X <= Y < 1000.");

        this.cellMatrixSizeAxisX = cellMatrixSizeAxisX;
        this.cellMatrixSizeAxisY = cellMatrixSizeAxisY;
    }

    /**
     * Advanced initialization constructor that accepts the inappropriate Grid cellMatrix axesSizes.
     * Includes the possibility of adding a custom error message to the Exception.
     *
     * @param cellMatrixSizeAxisX The inappropriate Grid cellMatrix horizontal axis size.
     *
     * @param cellMatrixSizeAxisY The inappropriate Grid cellMatrix vertical axis size.
     *
     * @param message A custom error message that can more accurately describe the issue
     *                that occurred.
     *
     * */
    public IncompatibleGridCellMatrixShapeException(final short cellMatrixSizeAxisX, final short cellMatrixSizeAxisY,
                                                    final String message) {
        super(message);

        this.cellMatrixSizeAxisX = cellMatrixSizeAxisX;
        this.cellMatrixSizeAxisY = cellMatrixSizeAxisY;
    }

    /**
     * Get the inappropriate Grid cellMatrix horizontal axis size.
     *
     * @return The inappropriate Grid cellMatrix horizontal axis size.
     *
     * */
    public short getCellMatrixSizeAxisX() {
        return cellMatrixSizeAxisX;
    }

    /**
     * Get the inappropriate Grid cellMatrix vertical axis size.
     *
     * @return The inappropriate Grid cellMatrix vertical axis size.
     *
     * */
    public short getCellMatrixSizeAxisY() {
        return cellMatrixSizeAxisY;
    }
}
