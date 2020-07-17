package com.nnikolov.green_vs_red.input;

import com.nnikolov.green_vs_red.model.Cell;
import com.nnikolov.green_vs_red.model.TargetGridGeneration;
import com.nnikolov.green_vs_red.util.VariableActionUtil;
import com.nnikolov.green_vs_red.util.StringUtil;

import java.io.InputStream;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * This class provides functions for reading the application-defined standard input arguments from a User-defined
 * InputStream that will be scanned for Strings. Any potential Exception messages that occur due input Strings not
 * being correctly formatted or due to non-parsable input arguments are fed to a User-provided String Consumer
 *
 * NOTE: The arguments, which are received from the functions of this class may not be valid for the initialization of
 * the cellMatrix in a Grid, additionally the coordinates for a Cell in the cellMatrix of an existing Grid may be
 * invalid.
 *
 * */
public final class InputArgumentsReader {

    /**
     * Utility function that reads Strings from an internal class Scanner and passes them to a provided String
     * Consumer until no Exceptions are thrown by that same Consumer.
     *
     * Returns the read inputString that has been successfully processed by the provided String Consumer without
     * any Exceptions.
     * */
    private final Function<Consumer<String>, String> readInputStringAndProcessUntilAllExceptionsAreCleared;
    /**
     * Stores a String Consumer where Exception messages from any of the functions in this class will be sent.
     * */
    private final Consumer<String> messageOutputStringConsumer;

    /**
     * Lone constructor that accepts an InputStream, which will be scanned for input Strings and a String Consumer
     * where an potential Exception messages will be sent.
     *
     * @param inputStream The InputStream, which will be scanned for Strings.
     *
     * @param messageOutputStringConsumer The String Consumer, which will receive potential Exception messages.
     *
     * */
    public InputArgumentsReader(final InputStream inputStream,
                                final Consumer<String> messageOutputStringConsumer) {
        //Initialize a new Scanner via the provided inputStream.
        final Scanner inputScanner = new Scanner(inputStream);

        //Store the String Consumer in a class variable.
        this.messageOutputStringConsumer = messageOutputStringConsumer;

        /*
        * Initialize a utility function that will read Strings from the internal class Scanner and pass
        * them to a provided String Consumer until no Exceptions are thrown by that same Consumer.
        * Afterwards, return the read inputString.
        * */
        this.readInputStringAndProcessUntilAllExceptionsAreCleared = (action) -> {
            //Initialize a single-element String array that will hold the User inputString.
            final String[] inputStr = new String[1];

            //Loop until the provided String Consumer throws no Exceptions.
            VariableActionUtil.loopAndOutputErrorMessagesUntilAllActionExceptionsAreCleared
                    /*
                     * Pass the String Consumer, which was provided to this class to the looping function for
                     * Exception message output.
                     * */
                    .accept(messageOutputStringConsumer, () -> {
                        inputStr[0] = inputScanner.nextLine(); //Read the inputString from the internal class Scanner.

                        //Pass the inputString to the String Consumer, which was provided to this function.
                        action.accept(inputStr[0]);
            });

            /*
             * Once we have assured that the read inputString is successfully processed by the provided String
             *  Consumer, return it.
             * */
            return inputStr[0];
        };
    }

    /**
     * Reads 'short' Number type cellMatrix axes Sizes from the provided InputStream and stores them
     * in a two-element array. This function will loop indefinitely until a proper inputString, which contains
     * two 'short' Number types is received.
     *
     * NOTE: The returned 'short' number types may not be fit to initialize a Grid cellMatrix.
     *
     * @return A two-element 'short' Number type array, which includes potential rectangular cellMatrix axes sizes.
     *
     * */
    public Short[] readCellMatrixAxesSizesUntilAcceptableEntry() {
        /*
        * Initialize the two-element 'short' Number type array, which will store the axes sizes of a potential
        * rectangular cellMatrix.
        * */
        final Short[] cellMatrixAxesSizes = new Short[2];

        //Initialize an array that will hold the String representations of the target cellMatrix axes sizes.
        final String[] splitCellMatrixAxesSizesString =
                //Split a provided inputString via the generic application delimiter.
                StringUtil.splitStringByGenericApplicationInputDelimiter(
                        /*
                         * Keep reading input string, until one with a valid format is received, as documented
                         * in the InputStringFormatValidator class.
                         * */
                        readInputStringAndProcessUntilAllExceptionsAreCleared.apply(
                                InputStringFormatValidator::assertCellMatrixDimensionsStringValidity));

        /*
        * Populate the cellMatrixAxesSizes array with the parsed 'short' Number types from the
        * splitCellMatrixAxesSizesString.
        * */
        populateFirstTwoIndexesOfArrWithParsedShortsFromStringArr.accept(
                cellMatrixAxesSizes, splitCellMatrixAxesSizesString);

        /*
        * Return the two-element 'short' Number type array that includes the axes sizes of a potential rectangular
        * cellMatrix.
        * */
        return cellMatrixAxesSizes;
    }

    /**
     * Reads 'short' Number type Cell Color colorCodes from the provided InputStream, parses them and stores them
     * in a List with a size of expectedCellMatrixRowLength. This function will loop indefinitely until a proper
     * inputString, which contains only valid Cell Color colorCodes 'short' Number types is received and their amount
     * corresponds to the provided number expectedCellMatrixRowLength.
     *
     * @param expectedCellMatrixRowLength The amount of Cell.Color types that need to be stored in the returned List.
     *
     * @return A List with a length of expectedCellMatrixRowLength that contains Cell.Colors.
     *
     * */
    public List<Cell.Color> readCellMatrixRowCellColorsUntilAcceptableEntry(final short expectedCellMatrixRowLength) {
        //Initialize a List of Cell.Colors with a length of expectedCellMatrixRowLength.
        final List<Cell.Color> cellMatrixRowCellColors = new ArrayList<>(expectedCellMatrixRowLength);

        /*
        * Loop until every Exception is cleared and a proper List of cellMatrixRowCellColors is constructed with a
        * length of expectedCellMatrixRowLength.
        * */
        VariableActionUtil.loopAndOutputErrorMessagesUntilAllActionExceptionsAreCleared.accept(
                //Pass the provided to the class String consumer for Exception message output.
                messageOutputStringConsumer, () -> {
                    //Clear the List at the start of each potential iteration.
                    cellMatrixRowCellColors.clear();

                    //Obtain a valid format cellMatrixRowString, as described in the InputStringFormatValidator class.
                    final String cellMatrixRowString =
                            readInputStringAndProcessUntilAllExceptionsAreCleared.apply((inputStr) ->
                                    InputStringFormatValidator.assertCellMatrixRowStringValidity(
                                            inputStr, expectedCellMatrixRowLength));

                    //Go through each character of the valid format cellMatrixRowString
                    for (final char cellMatrixRowCellColorCode : cellMatrixRowString.toCharArray())
                        /*
                        * Add a Cell.Color to the cellMatrixRowCellColors List if the current Char represents
                        * a valid colorCode.
                        * */
                        cellMatrixRowCellColors.add(
                                Cell.Color.fromCode(StringUtil.charToShort(cellMatrixRowCellColorCode)));
        });

        //Return a List of Cell.Colors with a length of expectedCellMatrixRowLength, which were parsed from User input.
        return cellMatrixRowCellColors;
    }

    /**
     * Reads 'short' Number type cellMatrix Cell coordinates and a 'long' Number type for a TargetGridGeneration
     * instance. This function will loop indefinitely until a properly formatted inputString
     * (as described in the InputStringFormatValidator class), which contains only two valid 'short' Number type
     * cellMatrix Cell coordinates and a positive 'long' Number type for a TargetGridGeneration, is received.
     *
     * NOTE: The returned 'short' cellMatrix Cell coordinates may not match a Cell within an existing cellMatrix.
     *
     * @return A Number array that contains the coordinates of a Cell in a cellMatrix for generation tracking in the
     * first two indexes and a TargetGridGeneration Number in the third index.
     *
     * */
    public Number[] readGridCellMatrixCellGenerationTrackingDataUntilAcceptableEntry() {
        /*
        * Initialise a Number array, which will hold two 'short' cellMatrix Cell coordinates in the first two indexes
        * and a TargetGridGeneration in the third index.
        * */
        final Number[] gridCellMatrixCellGenerationTrackingData = new Number[3];

        /*
         * Loop until every Exception is cleared and a proper gridCellMatrixCellGenerationTrackingData Number array is
         * constructed.
         * */
        VariableActionUtil.loopAndOutputErrorMessagesUntilAllActionExceptionsAreCleared.accept(
                //Pass the provided to the class String consumer for Exception message output.
                messageOutputStringConsumer, () -> {

            /*
            * Initialize an array that will hold the String representations of the target cellMatrix Cell coordinates
            * and the TargetGridGenerationNumber.
            * */
            final String[] splitGridCellMatrixCellGenerationTrackingString =
                    //Split a provided inputString via the generic application delimiter.
                    StringUtil.splitStringByGenericApplicationInputDelimiter(
                            /*
                             * Keep reading input string, until one with a valid format is received, as documented
                             * in the InputStringFormatValidator class.
                             * */
                            readInputStringAndProcessUntilAllExceptionsAreCleared.apply(
                                    InputStringFormatValidator::
                                            assertGridCellMatrixCellGenerationTrackingStringValidity));

            /*
             * Populate the first two indexes of the gridCellMatrixCellGenerationTrackingData array with the parsed
             * 'short' Number types from the first two indexes of the splitGridCellMatrixCellGenerationTrackingString.
             * */
            populateFirstTwoIndexesOfArrWithParsedShortsFromStringArr.accept(
                    gridCellMatrixCellGenerationTrackingData, splitGridCellMatrixCellGenerationTrackingString);

            /*
            * Populate the third index of the gridCellMatrixCellGenerationTrackingData array with a
            * TargetGridGeneration type that is initialized via a parsed 'long' Number from the third index of the
            * splitGridCellMatrixCellGenerationTrackingString String array.
            * */
            gridCellMatrixCellGenerationTrackingData[2] = new TargetGridGeneration(
                    StringUtil.stringToLong(splitGridCellMatrixCellGenerationTrackingString[2]));

        });

        return gridCellMatrixCellGenerationTrackingData;
    }

    /**
     * Utility wrap-around code-block that populates the first two indexes of a passed Object array with 'short' Number
     * types that are parsed from a passed String array.
     * */
    private final static BiConsumer<Object[], String[]> populateFirstTwoIndexesOfArrWithParsedShortsFromStringArr =
            (arr, stringArr) ->
                    IntStream.range(0, 2).forEach(index ->
                            arr[index] = StringUtil.stringToShort(stringArr[index]));
}
