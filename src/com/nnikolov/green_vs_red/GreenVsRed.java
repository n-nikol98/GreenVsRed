package com.nnikolov.green_vs_red;

import com.nnikolov.green_vs_red.exception.grid.InvalidGridCellMatrixCellCoordinatesException;
import com.nnikolov.green_vs_red.input.InputArgumentsReader;
import com.nnikolov.green_vs_red.model.TargetGridGeneration;
import com.nnikolov.green_vs_red.util.RectangularCellMatrixUtil;
import com.nnikolov.green_vs_red.util.VariableActionUtil;
import com.nnikolov.green_vs_red.model.Cell;
import com.nnikolov.green_vs_red.model.Grid;
import com.nnikolov.green_vs_red.util.GridGenerationStepper;

import java.util.List;
import java.util.function.Consumer;

/**
 * Green Vs Red
 *
 * @author Nedko Nikolov / nnikolov
 * @version 1.0.1
 *
 * A game that determines how many times a Cell within a Grid, containing a rectangular cell matrix, has a green color
 * over the progression of the different generations of the Grids cell matrix, up to a certain targeted Grid generation.
 *
 * Some naming conventions and the corresponding terminology:
 *
 *      - cellMatrix: A 2D List<List<Cell>> that contains Cells with a specific color. A generic cellMatrix may not
 *      have a rectangular shape.
 *          - rectangular cellMatrix: A cellMatrix that has a rectangular shape when presented on a 2D plane.
 *
 *      - Grid: A cellMatrix that conforms to the shape constraint:
 *      cellMatrixAxisSizeHorizontal <= cellMatrixAxisSizeVertical < 1000
 *
 * A description of the Grid generation progression algorithm:
 *
 *      NOTE: The neighbours of a Cell also include the ones that are diagonal to it, therefore each Cell can have up
 *      to 8 neighbours.
 *
 *      - If a red color Cell is surrounded by 3 or 6 green Cells, it will become green in the next generation.
 *      - If a red color Cell is surrounded by 1, 2, 4, 5, 7 or 8 green Cells, it will remain red in the next
 *      generation.
 *      - If a green color Cell is surrounded by 1, 4, 5, 7 or 8 green Cells, it will become red in the next generation.
 *      - If a green color Cell is surrounded by 2, 3 or 6 green Cells, it will remain green in the next generation.
 *
 * Expected inputs from an user:
 *
 *      (1) A String that conforms to the pattern: X, Y; Where X & Y are numbers that can be stored in a 'short'
 *      primitive type. X & Y respectively represent the horizontal and vertical sizes of the intended cell matrix,
 *      which will be stored in a Grid.
 *
 *      (2) For the next Z lines (where Z is equal to the vertical size of the cell matrix defined in input step (1)):
 *
 *          - A String that conforms to the pattern: XXX...; Where each X is a number that can be stored in a 'short'
 *          primitive type. The amount of X numbers corresponds to the horizontal size of the cell matrix defined in
 *          input step (1). These numbers represent the intended colors of the Cells in the cell matrix that will be
 *          stored in a Grid.
 *
 *      (3) A String that conforms to the pattern: X, Y, N; Where X & Y are numbers that can be stored in a 'short'
 *      primitive type. N is a number that can be stored in a 'long' primitive type. X & Y respectively represent the
 *      horizontal and vertical coordinates of a Cell in the defined cell matrix (via input steps (1) & (2)) that will
 *      have its color tracked over N generations.
 *
 * */
public final class GreenVsRed {

    /**
     * Initialise a static context message output String consumer.
     * For the purposes of the lifetime of this application, this consumer will feed all incoming Strings to the
     * standard console output function: System.out::println.
     *
     * */
    private final static Consumer<String> messageOutputStringConsumer = System.out::println;

    /**
     * Initialise a static context InputArgumentsReader for the purposes of the lifetime of this application that
     * accepts an InputStream in the form of: System.in and an output String Consumer in the
     * form of the static context messageOutputStringConsumer.
     *
     * */
    private final static InputArgumentsReader inputArgumentsReader =
            new InputArgumentsReader(System.in, messageOutputStringConsumer);

    public static void main(final String[] args) {
        //Construct an initial Grid from User input.
        final Grid grid = constructGridFromUserInput();

        //Alter the Cell colors in the cellMatrix of the above-mentioned Grid with ones defined by User input.
        setGridCellMatrixCellColorsFromUserInput(grid);

        /* Grid cellMatrix Cell Generation tracking data START */

        final short[][] targetCellCoordinates = new short[1][2];
        final TargetGridGeneration[] targetGridGeneration = new TargetGridGeneration[1];

        /* Grid cellMatrix Cell Generation tracking data END */

        /*
         * Populate the above-mentioned Grid cellMatrix Cell Generation tracking data elements via User input.
         * The targeted Grid is provided, so that the targetCellCoordinates may be verified against the Grids
         * cellMatrix. This function will loop until valid targetCellCoordinates are provided.
         */
        populateAndVerifyGridCellMatrixCellGenerationTrackingDataFromUserInputUntilValidEntry(
                grid, targetCellCoordinates, targetGridGeneration);

        /*
        * Pass the amount of times a targeted Cell within the cellMatrix of a Grid changes its Color to GREEN
        * up to the targeted Grid generation (including the first ZERO generation) to the message output string
        * consumer.
        * */
        messageOutputStringConsumer.accept(Long.toString(getTargetCellGreenColorChangesUpToTargetGeneration(
                grid, targetCellCoordinates[0], targetGridGeneration[0])));
    }

    /**
     * Internal utility function.
     * Constructs a Grid instance via User input. This function will loop indefinitely until the cellMatrix dimensions
     * provided by the User are suitable for use within a Grid.
     *
     * @return A Grid instance in its initial state (filled only with RED Color Cells).
     *
     * */
    private static Grid constructGridFromUserInput() {
        //Initialise a one-element Grid array
        final Grid[] grid = new Grid[1];

        //Loop until a valid initial-state Grid instance is constructed via User input.
        VariableActionUtil.loopAndOutputErrorMessagesUntilAllActionExceptionsAreCleared.accept(
                //All Exception message String will be fed to the application standard messageOutputStringConsumer
                messageOutputStringConsumer, () -> {
                    /*
                     * Obtain a two-element 'short' array that contains the intended Grid cellMatrix axes sizes from
                     * User input.
                     */
                    final Short[] cellMatrixAxesSizes =
                            inputArgumentsReader.readCellMatrixAxesSizesUntilAcceptableEntry();

                    /*
                     * Attempt to construct a Grid instance via the provided cellMatrix axes sizes. If this fails,
                     * the loop will repeat.
                     */
                    grid[0] = new Grid(cellMatrixAxesSizes[0], cellMatrixAxesSizes[1]);
                });

        //Return the constructed initial-state Grid instance
        return grid[0];
    }

    /**
     * Internal utility function.
     * Changes the colors of all the Cells in a Grids cellMatrix via User input.
     *
     * @param grid A Grid instance. The Colors of the Cells in its cellMatrix will be changed to the ones defined by
     *             User input.
     *
     * */
    private static void setGridCellMatrixCellColorsFromUserInput(final Grid grid) {
        //Loop through the cellMatrixRows of the Grids cellMatrix.
        for (short y = 0; y < grid.getCellMatrixSizeAxisY(); y++) {
            //Obtain a List of all the Cell Colors that the User intended for the current cellMatrixRow.
            final List<Cell.Color> cellColors =
                    inputArgumentsReader.readCellMatrixRowCellColorsUntilAcceptableEntry(
                            //Provide the horizontal size of the Grids cellMatrix.
                            grid.getCellMatrixSizeAxisX());

            //Loop through all of the Cells in the current cellMatrixRow and set their Colors, as defined by the User.
            for (short x = 0; x < grid.getCellMatrixSizeAxisX(); x++) {
                grid.setCellColor(x, y, cellColors.get(x));
            }
        }
    }

    /**
     * Internal utility function.
     * Populates the Grid cellMatrix Cell Generation tracking data via User data. The validity of the
     * targetCellCoordinates is checked against an existing Grid. This function will loop indefinitely until valid
     * targetCellCoordinates are provided by the User.
     *
     * @param grid A Grid instance. Its cellMatrix will be used, in order to verify if the intended
     *             targetCellCoordinates are within its bounds.
     *
     * @param targetCellCoordinates A vertically one-element multi-dimensional array that will be populated by a
     *                              two-element 'short' Number type array at its first vertical index, which will hold
     *                              the targetCellCoordinates.
     *
     * @param targetGridGeneration A one-element TargetGridGeneration array that will be populated with the
     *                             TargetGridGeneration specified by the User.
     *
     * */
    private static void populateAndVerifyGridCellMatrixCellGenerationTrackingDataFromUserInputUntilValidEntry(
            final Grid grid,
            final short[][] targetCellCoordinates,
            final TargetGridGeneration[] targetGridGeneration) {
        //Loop until valid targetCellCoordinates are provided by the User.
        VariableActionUtil.loopAndOutputErrorMessagesUntilAllActionExceptionsAreCleared.accept(
                //All Exception message String will be fed to the application standard messageOutputStringConsumer.
                messageOutputStringConsumer, () ->
                {
                    /*
                     * Obtain a three-element Number array, containing the Grid cellMatrix Cell Generation tracking
                     * data, which was acquired via User input.
                     * */
                    final Number[] gridCellGenerationTrackingData =
                            inputArgumentsReader.readGridCellMatrixCellGenerationTrackingDataUntilAcceptableEntry();

                    /*
                     * Populate the first vertical index of the targetCellCoordinates array with a 'short' Number type
                     * array that contains the targetCellCoordinates.
                     * */
                    targetCellCoordinates[0] = new short[]{
                            gridCellGenerationTrackingData[0].shortValue(),
                            gridCellGenerationTrackingData[1].shortValue()
                    };

                    //Populate the targetGridGeneration with the TargetGridGeneration specified by the User.
                    targetGridGeneration[0] = (TargetGridGeneration) gridCellGenerationTrackingData[2];

                    final short targetCellCoordinateX = targetCellCoordinates[0][0],
                            targetCellCoordinateY = targetCellCoordinates[0][1];

                    /*
                     * Checks if the targeted Cell can be in the Grids cellMatrix.
                     */
                    if (!RectangularCellMatrixUtil.cellCoordinatesAreInBoundsOfRectangularCellMatrix(
                            grid.getCellMatrix(), targetCellCoordinateX, targetCellCoordinateY))
                        //If the targeted Cell cannot be in the provided Grids cellMatrix, throw an Exception.
                        throw new InvalidGridCellMatrixCellCoordinatesException(
                                grid,
                                targetCellCoordinateX,
                                targetCellCoordinateY);
        });
    }

    /**
     * Internal utility function.
     * Checks if the targeted Cell in a Grids cellMatrix has a GREEN Color.
     *
     * @param grid A Grid instance. Its cellMatrix will be used, in order to obtain the targeted Cell.
     *
     * @param cellCoordinateX The horizontal coordinate of the targeted Cell in the Grids cellMatrix.
     *
     * @param cellCoordinateY The vertical coordinate of the targeted Cell in the Grids cellMatrix.
     *
     * @return A boolean, which indicates whether the targeted Cell in a Grids cellMatrix has a GREEN Color.
     *
     * */
    private static boolean gridCellMatrixCellIsGreen(final Grid grid,
                                                     final short cellCoordinateX,
                                                     final short cellCoordinateY) {
        return grid
                .getCell(cellCoordinateX, cellCoordinateY)
                .getColor()
                .isGreen();
    }

    /**
     * Internal utility function.
     * Calculates the amount of times a targeted Cell within a Grids cellMatrix will have a GREEN Color, up to a
     * certain targetGridGeneration (including its initial state in the Grids cellMatrix; the ZERO Generation).
     *
     * @param grid A Grid instance. It will be used to initialize a GridGenerationStepper, so that the
     *             next generation states of its cellMatrix can be calculated.
     *
     * @param targetCellCoordinates A 'short' Number type array, which holds the horizontal & vertical coordinates of
     *                             the targeted Cell in the Grids cellMatrix.
     *
     * @param targetGridGeneration The targeted final Grid generation.
     *
     * @return A 'long' Number type, which indicates how many times the targeted Cell in the Grids cellMatrix had a
     * GREEN Color, up to the targetGridGeneration (including the ZERO generation).
     *
     * */
    private static long getTargetCellGreenColorChangesUpToTargetGeneration(final Grid grid,
                                                                           final short[] targetCellCoordinates,
                                                                           final TargetGridGeneration targetGridGeneration) {
        /*
         * Since the ZERO generation target Cell Color is included in this functions calculation, check to see if it
         * has a GREEN Color, in order to determine this counters initial state.
         * */
        long targetCellGreenColorChanges =
                gridCellMatrixCellIsGreen(grid, targetCellCoordinates[0], targetCellCoordinates[1]) ? 1 : 0;

        //Initialize a new GridGenerationStepper via the provided Grid instance.
        final GridGenerationStepper gridGenerationStepper = new GridGenerationStepper(grid);

        //Loop up to the targetGridGeneration.
        for (long x = 0; x < targetGridGeneration.longValue(); x++) {
            //Progress to the next generation of the provided Grid.
            gridGenerationStepper.stepToNext();

            //Determine if the targeted Cell has a GREEN Color in the next generation of the provided Grid.
            if (gridGenerationStepper.getGrid().getCell(
                    targetCellCoordinates[0], targetCellCoordinates[1])
                    .getColor().isGreen())
                /*
                 * If the targeted Cell has a GREEN Color in the next generation of the provided Grid, increment the
                 * counter.
                 * */
                targetCellGreenColorChanges++;
        }

        /*
        * Return the amount of times the targeted Cell had a GREEN Color up to the
        * TargetGridGeneration (including the ZERO Generation).
        */
        return targetCellGreenColorChanges;
    }
}