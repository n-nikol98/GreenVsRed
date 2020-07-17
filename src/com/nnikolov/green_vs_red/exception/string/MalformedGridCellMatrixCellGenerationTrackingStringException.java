package com.nnikolov.green_vs_red.exception.string;

/**
 * A RuntimeException indicating that a Grid cellMatrix Cell generation tracking inputString is malformed due to the
 * fact that it does not correspond to the format: X, Y, N; where X & Y are 'short' type numbers, whereas N is a 'long'
 * type number.
 *
 * */
public final class MalformedGridCellMatrixCellGenerationTrackingStringException extends MalformedInputStringException {

    /**
     * Default initialization constructor that accepts a malformed Grid cellMatrix Cell generation tracking
     * inputString, which will be passed to the superclass.
     *
     * Includes a generic error message.
     *
     * @param inputString The malformed malformed Grid cellMatrix Cell generation tracking inputString.
     *
     * */
    public MalformedGridCellMatrixCellGenerationTrackingStringException(final String inputString) {
        super(inputString, "The provided Grid Cell generation tracking input string is malformed." +
                " Please make sure that it is formatted as follows: X, Y, N;" +
                " where X & Y are the coordinates of the Cell that will be tracked and N " +
                " is the amount of generations that it will be tracked for." +
                " The former two numbers should be of a 'short' type, whereas the latter third one" +
                " should be a 'long' type.");
    }

    /**
     * Advanced initialization constructor that accepts a malformed Grid cellMatrix Cell generation tracking
     * inputString, which will be passed to the superclass.
     *
     * Includes the possibility of adding a custom error message to the Exception, which will also be passed to the
     * superclass.
     *
     * @param inputString The malformed malformed Grid cellMatrix Cell generation tracking inputString.
     *
     * @param message A custom error message that can more accurately describe the issue
     *                that occurred.
     *
     * */
    public MalformedGridCellMatrixCellGenerationTrackingStringException(
            final String inputString, final String message) {
        super(inputString,message);
    }
}
