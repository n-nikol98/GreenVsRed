package com.nnikolov.green_vs_red.exception;

/**
 * A RuntimeException indicating that a TargetGridGeneration cannot be represented with the provided
 * numericValue. Stores the inappropriate numericValue.
 *
 * */
public final class InvalidTargetGridGeneration extends RuntimeException {

    /**
     * The inappropriate TargetGridGeneration numericValue.
     *
     * */
    private final long numericValue;

    /**
     * Default initialization construct that accepts an inappropriate TargetGridGeneration numericValue.
     * Includes a generic error message.
     *
     * @param numericValue The inappropriate TargetGridGeneration numericValue.
     *
     * */
    public InvalidTargetGridGeneration(final long numericValue) {
        super("The provided target generation for a grid is invalid." +
                " Please ensure that your input contains a number that is greater than 0.");

        this.numericValue = numericValue;
    }

    /**
     * Advanced initialization construct that accepts an inappropriate TargetGridGeneration numericValue.
     * Includes the possibility of adding a custom error message to the Exception.
     *
     * @param numericValue The inappropriate TargetGridGeneration numericValue.
     *
     * @param message A custom error message that can more accurately describe the issue
     *                that occurred.
     *
     * */
    public InvalidTargetGridGeneration(final long numericValue, final String message) {
        super(message);

        this.numericValue = numericValue;
    }

    /**
     * Get the inappropriate TargetGridGeneration numericValue.
     *
     * @return The inappropriate TargetGridGeneration numericValue.
     *
     * */
    public long getNumericValue() {
        return numericValue;
    }
}
