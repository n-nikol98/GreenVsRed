package com.nnikolov.green_vs_red.util;

import com.nnikolov.green_vs_red.model.Cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Helper utility class that provides useful static function for use when working with Rectangular cellMatrices.
 *
 * */
public final class RectangularCellMatrixUtil {

    /**
     * Check if a 2D List cellMatrix has a rectangular shape.
     *
     * @param cellMatrix A 2D List cellMatrix. Its shape will be checked, in order to determine if its rectangular.
     *
     * @return A boolean that indicates if a 2D List cellMatrix has a rectangular shape.
     *
     * */
    public static boolean cellMatrixIsRectangular(final List<List<Cell>> cellMatrix) {
        //If a cellMatrix if empty, assume its rectangular!
        if (cellMatrix.isEmpty())
            return true;

        //Obtain the horizontal size of the first row of the provided cellMatrix.
        final short cellMatrixSizeAxisX = (short) cellMatrix.get(0).size();

        /*
        * If any of the horizontal sizes of provided cellMatrix's rows do not match that of the first row,
        * the cellMatrix is not rectangular.
        * */
        for(final List<Cell> cellMatrixRow: cellMatrix)
            if (cellMatrixRow.size() != cellMatrixSizeAxisX)
                return false;

        //The sizes of all of the rows in the provided cellMatrix are equal, therefore the cellMatrix is rectangular.
        return true;
    }

    /**
     * Check if the provided cell coordinates are within the bounds of a 2D List rectangular cellMatrix.
     *
     * @param cellMatrix A 2D List cellMatrix. Its bounds will be obtained. It should be rectangular, in order for this
     *                   function to return a TRUE result.
     *
     * @param cellCoordinateX The horizontal coordinate of the potential cellMatrix Cell.
     *
     * @param cellCoordinateY The vertical coordinate of the potential cellMatrix Cell.
     *
     * @return A boolean that indicates if the provided potential cellMatrix Cell coordinates are within the provided
     * cellMatrix.
     *
     * */
    public static boolean cellCoordinatesAreInBoundsOfRectangularCellMatrix(final List<List<Cell>> cellMatrix,
                                                                            final short cellCoordinateX,
                                                                            final short cellCoordinateY) {
        return !cellMatrix.isEmpty() && //If the cellMatrix is empty, no Cells can be in it.
                /*
                * If the cellMatrix is not rectangular, the condition mandated by this functions name is not met,
                * therefore a Cell cannot be in it.
                 */
                cellMatrixIsRectangular(cellMatrix) &&
                /* *** */
                cellCoordinateX >= 0 &&
                cellCoordinateY >= 0 &&
                cellCoordinateX < cellMatrix.get(0).size() &&
                cellCoordinateY < cellMatrix.size();
    }

    /**
     * An utility wrap-around function that produces an unmodifiable fixed-size rectangular cellMatrix.
     *
     * Accepts the expected RectangularCellMatrixDimensions, an action that consumes a cellMatrixRow and an
     * Integer (the vertical index of the current cellMatrixRow).
     *
     * Return an unmodifiable fixed-size rectangular cellMatrix, which is filled with custom Cells.
     *
     * */
    public static final BiFunction<RectangularCellMatrixDimensions, BiConsumer<Cell[], Integer>, List<List<Cell>>>
            createUnmodifiableShapeRectangularCellMatrix = (cellMatrixSize, action) ->
            //Initialise cellMatrix with a fixed vertical size.
            Collections.unmodifiableList(new ArrayList<>(cellMatrixSize.axisY) {{
                //Loop through each currently empty cellMatrix row
                IntStream.range(0, cellMatrixSize.axisY).forEach((y) -> {
                    //Define an empty cellMatrixRow array with a fixed horizontal size.
                    final Cell[] cellMatrixRow = new Cell[cellMatrixSize.axisX];

                    //Pass the cellMatrixRow and the current vertical cellMatrix index to an optional variable action.
                    action.accept(cellMatrixRow, y);

                    //Construct the cellMatrixRow List with a fixed size and add it to the larger cellMatrix.
                    add(Arrays.asList(cellMatrixRow));
                });
            }});

    /**
     * An utility wrap-around code-block that fills a cellMatrixRow array with Cells that are initialised
     * by the return Cells of an optional function that accepts the current horizontal index of the explored
     * cellMatrixRow.
     *
     * */
    public final static BiConsumer<Cell[], Function<Integer, Cell>>
            fillRectangularCellMatrixRowArrWithCustomInstances = (cellMatrixRow, cellProvider) ->
            //Loop through each Cell of the cellMatrixRow array
            IntStream.range(0, cellMatrixRow.length).forEach((x) ->
                    /* Replace the current Cell within the cellMatrixRow array with a copy of a Cell provided by the
                     * cellProvider function that accepts the current horizontal index within the cellMatrixRow array.
                     * */
                    cellMatrixRow[x] = new Cell(cellProvider.apply(x)));

    /**
     * An utility STRUCT based class that denotes the horizontal & vertical axes dimensions of a rectangular
     * cellMatrix.
     *
     * */
    public static class RectangularCellMatrixDimensions {
        private final short axisX;
        private final short axisY;

        public RectangularCellMatrixDimensions(final short axisX, final short axisY) {
            this.axisX = axisX;
            this.axisY = axisY;
        }
    }
}
