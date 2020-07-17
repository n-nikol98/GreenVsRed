package com.nnikolov.green_vs_red.exception.string;

/**
 * A generic RuntimeException indicating that an inputString is malformed.
 * Stores the inputString.
 *
 * */
public class MalformedInputStringException extends RuntimeException {

    /**
     * The malformed inputString.
     *
     * */
    private final String inputString;

    /**
     * Default initialization construct that accepts an inputString, which is malformed.
     * Includes a generic error message.
     *
     * @param inputString The inputString that is malformed.
     *
     * */
    public MalformedInputStringException(final String inputString) {
        super("The provided input string is malformed due to an unspecified issue.");

        this.inputString = inputString;
    }

    /**
     * Advanced initialization construct that accepts an inputString, which is malformed.
     * Includes the possibility of adding a custom error message to the Exception.
     *
     * @param inputString The inputString that is malformed.
     *
     * @param message A custom error message that can more accurately describe the issue
     *                that occurred.
     *
     * */
    public MalformedInputStringException(final String inputString, final String message) {
        super(message);

        this.inputString = inputString;
    }

    /**
     * Get the malformed inputString.
     *
     * @return The malformed inputString.
     *
     * */
    public String getInputString() {
        return inputString;
    }
}
