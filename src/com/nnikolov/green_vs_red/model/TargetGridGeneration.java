package com.nnikolov.green_vs_red.model;

import com.nnikolov.green_vs_red.exception.InvalidTargetGridGeneration;

/**
 * Represents a TargetGridGeneration Number, which is specific for this application.
 *
 * An instance of this class can only be obtained if initialized via a positive Long type, else
 * an Exception will be thrown.
 *
 * */
public final class TargetGridGeneration extends Number {

    private final Long numericValue;

    /**
     * Lone constructor. Accepts a positive Long Number type.
     *
     * @param numericValue A Long Number type, which will be stored in this class.
     *
     * @throws InvalidTargetGridGeneration If the provided numericValue is non-positive, an Exception will be thrown.
     *
     * */
    public TargetGridGeneration(final Long numericValue) {
        if (numericValue <= 0)
            throw new InvalidTargetGridGeneration(numericValue);

        this.numericValue = numericValue;
    }

    /**
     * Obtain an Integer representation of the Long Number type, which is stored in this class.
     * Data truncation is possible.
     *
     * @return The numericValue, which is stored in this class, represented as an Integer. Possible data truncation.
     *
     * */
    @Override
    public int intValue() {
        return numericValue.intValue();
    }

    /**
     * The numericValue of this class should be obtained via this method, in order to avoid potential data truncation.
     *
     * @return The numericValue, which is stored in this class. Not truncated.
     *
     * */
    @Override
    public long longValue() {
        return numericValue;
    }

    /**
     * Obtain a Float representation of the Long Number type, which is stored in this class.
     * Data truncation is possible.
     *
     * @return The numericValue, which is stored in this class, represented as a Float. Possible data truncation.
     *
     * */
    @Override
    public float floatValue() {
        return numericValue.floatValue();
    }

    /**
     * Obtain a Double representation of the Long Number type, which is stored in this class.
     * Data truncation is possible.
     *
     * @return The numericValue, which is stored in this class, represented as a Double. Possible data truncation.
     *
     * */
    @Override
    public double doubleValue() {
        return numericValue.doubleValue();
    }
}
