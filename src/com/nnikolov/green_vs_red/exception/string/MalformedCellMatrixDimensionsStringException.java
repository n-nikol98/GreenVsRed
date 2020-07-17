package com.nnikolov.green_vs_red.exception.string;

/**
 * A RuntimeException indicating that a cellMatrix dimensions inputString is malformed due to the fact that it
 * does not correspond to the format: X, Y; where both X & Y are 'short' type numbers.
 *
 * */
public final class MalformedCellMatrixDimensionsStringException extends MalformedInputStringException {

    /**
     * Default initialization constructor that accepts a malformed cellMatrix dimensions inputString,
     * which will be passed to the superclass.
     *
     * Includes a generic error message.
     *
     * @param inputString The malformed cellMatrix dimensions inputString.
     *
     * */
    public MalformedCellMatrixDimensionsStringException(final String inputString) {
        super(inputString, "The provided Grid size input string is malformed." +
                " Please make sure that it is formatted as follows: X, Y; where " +
                "both X & Y are 'short' type numbers.");
    }

    /**
     * Advanced initialization constructor that accepts a malformed cellMatrix dimensions inputString,
     * which will be passed to the superclass.
     *
     * Includes the possibility of adding a custom error message to the Exception, which will also be passed to the
     * superclass.
     *
     * @param inputString The malformed cellMatrix dimensions inputString.
     *
     * @param message A custom error message that can more accurately describe the issue
     *                that occurred.
     *
     * */
    public MalformedCellMatrixDimensionsStringException(final String inputString, final String message) {
        super(inputString,message);
    }
}
