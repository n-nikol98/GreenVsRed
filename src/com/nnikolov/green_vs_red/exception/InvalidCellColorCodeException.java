package com.nnikolov.green_vs_red.exception;

/**
 * A RuntimeException indicating that Cell.Color with the given colorCode does not exist.
 * Stores the inappropriate colorCode.
 *
 * */
public final class InvalidCellColorCodeException extends RuntimeException {

    /**
     * The inappropriate Cell.Color colorCode.
     *
     * */
    private final short colorCode;

    /**
     * Default initialization construct that accepts an inappropriate Cell.Color colorCode.
     * Includes a generic error message.
     *
     * @param colorCode The inappropriate Cell.Color colorCode.
     *
     * */
    public InvalidCellColorCodeException(final short colorCode) {
        super("The Cell color code: " +
                colorCode +
                " is invalid. Please ensure that you use either 0 (RED) or 1 (GREEN).");

        this.colorCode = colorCode;
    }

    /**
     * Advanced initialization construct that accepts an inappropriate Cell.Color colorCode.
     * Includes the possibility of adding a custom error message to the Exception.
     *
     * @param cellColorCode The inappropriate Cell.Color colorCode.
     *
     * @param message A custom error message that can more accurately describe the issue
     *                that occurred.
     *
     * */
    public InvalidCellColorCodeException(final short cellColorCode, final String message) {
        super(message);

        this.colorCode = cellColorCode;
    }

    /**
     * Get the inappropriate Cell.Color colorCode.
     *
     * @return The inappropriate Cell.Color colorCode.
     *
     * */
    public short getCellColorCode() {
        return colorCode;
    }
}
