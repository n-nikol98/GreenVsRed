package com.nnikolov.green_vs_red.exception.string;

/**
 * A RuntimeException indicating that a cellMatrix row inputString is malformed due to the fact that it does not
 * correspond to the format: XXX...; where X is a 'short' type number; The amount of expected numbers corresponds to
 * the expectedCellMatrixRowLength, which is stored in this class.
 *
 * */
public final  class MalformedCellMatrixRowStringException extends MalformedInputStringException {

    /**
     * The expected cellMatrix row length, which represents the length the inputString should be.
     *
     * */
    private final short expectedCellMatrixRowLength;

    /**
     * Default initialization constructor that accepts a malformed cellMatrix row inputString, which will be passed
     * to the superclass. Additionally, the constructor accepts an expectedCellMatrixRowLength number, which
     * represents the length the inputString should be.
     *
     * Includes a generic error message.
     *
     * @param inputString The malformed malformed cellMatrix row inputString.
     *
     * @param expectedCellMatrixRowLength A 'short' number type, which indicates the expected length of the
     *                                    malformed cellMatrix row inputString.
     *
     * */
    public MalformedCellMatrixRowStringException(final String inputString, final short expectedCellMatrixRowLength) {
        super(inputString, "The provided Grid row input string is malformed." +
                " Please make sure that it is formatted as follows: XXX...; where X is a 'short' type number." +
                " The amount of numbers corresponds to the horizontal size of your Grid, specifically: " +
                expectedCellMatrixRowLength);

        this.expectedCellMatrixRowLength = expectedCellMatrixRowLength;
    }

    /**
     * Advanced initialization constructor that accepts a malformed cellMatrix row inputString, which will be passed
     * to the superclass. Additionally, the constructor accepts an expectedCellMatrixRowLength number, which
     * represents the length the inputString should be.
     *
     * Includes the possibility of adding a custom error message to the Exception, which will also be passed to the
     * superclass.
     *
     * @param inputString The malformed malformed cellMatrix row inputString.
     *
     * @param expectedCellMatrixRowLength A 'short' number type, which indicates the expected length of the
     *                                    malformed cellMatrix row inputString.
     *
     * @param message A custom error message that can more accurately describe the issue
     *                that occurred.
     *
     * */
    public MalformedCellMatrixRowStringException(final String inputString,
                                                 final short expectedCellMatrixRowLength,
                                                 final String message) {
        super(inputString,message);

        this.expectedCellMatrixRowLength = expectedCellMatrixRowLength;
    }

    /**
     * Get the expected cellMatrix row length, which represents the length the inputString should be.
     *
     * @return The expected cellMatrix row length.
     *
     * */
    public short getExpectedCellMatrixRowLength() {
        return expectedCellMatrixRowLength;
    }
}
