package com.nnikolov.green_vs_red.util;

/**
 * Utility class that allows checking of potential cellMatrix shapes via their horizontal & vertical dimensions,
 * in order to determine if they are valid for use within a Grid, as per the requirements of this application.
 *
 * */
public final class CellMatrixGridCompatibilityValidator {

    /**
     * Static function that determines if the horizontal & vertical dimensions of a rectangular cellMatrix produce a
     * shape that fits the constraint: 0 < X <= Y < 1000.
     *
     * @param cellMatrixSizeAxisX A 'short' type, which represents the horizontal size of the Grids cellMatrix.
     *
     * @param cellMatrixSizeAxisY A 'short' type, which represents the vertical size of the Grids cellMatrix.
     *
     * @return A boolean, which indicates if a cellMatrix has a proper shape that fits the above-mentioned constraint,
     * thus allowing its use in a Grid.
     *
     * */
    public static boolean isCompatibleShape(final short cellMatrixSizeAxisX, final short cellMatrixSizeAxisY) {
        return  cellMatrixSizeAxisX > 0 &&
                cellMatrixSizeAxisX <= cellMatrixSizeAxisY &&
                cellMatrixSizeAxisY < 1000;
    }
}
