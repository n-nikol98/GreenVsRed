package com.nnikolov.green_vs_red.model;

import com.nnikolov.green_vs_red.exception.InvalidCellColorCodeException;

/**
 * Represents an individual Grid cellMatrix Cell, which has a specific color.
 *
 * This is a wrapper class for a Color, therefore its contents can be freely modified.
 *
 * */
public final class Cell {

    private Color color;

    /**
     *
     * Standard constructor for the initialization of a new Cell.
     *
     * @param color A Color enumeration, which will be stored in the Cell.
     *
     * */
    public Cell(final Color color) {
        this.color = color;
    }

    /**
     * Copy constructor
     *
     * @param cell An existing Cell instance.
     *
     * */
    public Cell(final Cell cell) {
        this.color = cell.getColor();
    }

    /**
     * @return Get the specific Color, which is stored in this Cell.
     *
     * */
    public Color getColor() {
        return color;
    }


    /**
     * Set the Color of this Cell.
     *
     * @param color A Color enumeration, which will be stored in the Cell.
     *
     * */
    public void setColor(final Color color) {
        this.color = color;
    }

    /**
     *
     * Cell Color enumeration nested type.
     *
     * Possible values are: RED & GREEN.
     *
     * */
    public enum Color {
        RED,
        GREEN;

        /**
         * Utility function to determine if the current Color is RED.
         *
         * @return A boolean value.
         *
         * */
        public boolean isRed() {
            return this == RED;
        }

        /**
         * Utility function to determine if the current Color is GREEN.
         *
         * @return A boolean value.
         *
         * */
        public boolean isGreen() {
            return this == GREEN;
        }

        /**
         *
         * Provide a 'short' primitive type colorCode and receive a matching Color enumeration.
         *
         * @param code Possible values are 0 (RED) & 1 (GREEN).
         *
         * @throws InvalidCellColorCodeException If the User has provided a colorCode, which is not 0 or 1,
         * an exception will be thrown.
         *
         **/
        public static Color fromCode(final short code) {
            if (code != 0 && code != 1)
                throw new InvalidCellColorCodeException(code);

            return Color.values()[code];
        }
    }
}
