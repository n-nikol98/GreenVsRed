package com.nnikolov.green_vs_red.input;

import com.nnikolov.green_vs_red.exception.string.MalformedGridCellMatrixCellGenerationTrackingStringException;
import com.nnikolov.green_vs_red.exception.string.MalformedCellMatrixRowStringException;
import com.nnikolov.green_vs_red.exception.string.MalformedCellMatrixDimensionsStringException;
import com.nnikolov.green_vs_red.util.StringUtil;

import java.util.function.Predicate;

/**
 * An utility class that asserts the validity of the three different possible input Strings that this application
 * expects.
 *
 * NOTE: The functions offered by this class only verify that the provided inputStrings have a valid (potentially
 * splittable) format and that they contain parsable Long / Short Number types. Whether the aforementioned Number
 * types can be used to construct valid Cell Colors, Grids and / or TargetGridGenerations is not determined. As such,
 * only MalformedInputStringException throws can be expected.
 *
 * */
final class InputStringFormatValidator {

    /**
     * A function, which asserts that a given inputString follows the format: X, Y; where X & Y are 'short' Number
     * types.
     *
     * @param inputString The inputString, which will be tested for a valid format.
     *
     * @throws MalformedCellMatrixDimensionsStringException Thrown if the provided inputString does not follow the
     * above-mentioned format.
     *
     * */
    static void assertCellMatrixDimensionsStringValidity(final String inputString) {
        //Split the inputString by the delimiter ", "
        final String[] splitCellMatrixDimensionStr =
                StringUtil.splitStringByGenericApplicationInputDelimiter(inputString);

        //If the length of the splitGridSizeStr array is not 2, we do not have a valid format.
        if (splitCellMatrixDimensionStr.length != 2 ||
                //Assure that both of the Strings in the splitGridSizeStr array are 'short' Number types.
                stringIsNotAShort(splitCellMatrixDimensionStr[0]) ||
                stringIsNotAShort(splitCellMatrixDimensionStr[1])) {
            //If the inputString does not follow the above-mentioned format, throw an Exception.
            throw new MalformedCellMatrixDimensionsStringException(inputString);
        }
    }

    /**
     * A function, which asserts that a given inputString follows the format: XXX...; where X is a 'short' Number
     * type. The amount of numbers corresponds to the provided expectedCellMatrixRowLength.
     *
     * @param inputString The inputString, which will be tested for a valid format.
     *
     * @param expectedCellMatrixRowLength The expected length of the cellMatrixRow
     *
     * @throws MalformedCellMatrixRowStringException Thrown if the provided inputString does not follow the
     * above-mentioned format.
     *
     * */
    static void assertCellMatrixRowStringValidity(final String inputString, final short expectedCellMatrixRowLength) {
        //The inputString is currently valid if its length equals the expectedCellMatrixRowLength.
        boolean validCellMatrixRowString = expectedCellMatrixRowLength == inputString.length();

        //If the inputString is still valid, proceed to check if all of its characters are 'short' Number types.
        if (validCellMatrixRowString) {
            //Convert the inputString to a 'char' array.
            final char[] gridRowStrChars = inputString.toCharArray();

            //If any of the inputStrings characters is not a 'short' the entire String is not valid.
            for (char gridRowStrChar : gridRowStrChars)
                if (stringIsNotAShort(String.valueOf(gridRowStrChar)))
                    validCellMatrixRowString = false;
        }

        //If the inputString does not follow the above-mentioned format, throw an Exception.
        if (!validCellMatrixRowString)
            throw new MalformedCellMatrixRowStringException(inputString, expectedCellMatrixRowLength);
    }

    /**
     * A function, which asserts that a given inputString follows the format: X, Y, N; The former two numbers must be
     * of a 'short' Number type, whereas the latter third one must be a 'long' Number type.
     *
     * @param inputString The inputString, which will be tested for a valid format.
     *
     * @throws MalformedGridCellMatrixCellGenerationTrackingStringException Thrown if the provided inputString does not
     * follow the above-mentioned format.
     *
     * */
    static void assertGridCellMatrixCellGenerationTrackingStringValidity(final String inputString) {
        //Split the inputString by the delimiter ", "
        final String[] splitGridCellMatrixCellGenerationTrackingStr =
                StringUtil.splitStringByGenericApplicationInputDelimiter(inputString);

        //If the length of the splitGridCellGenerationTrackingStr array is not 3, we do not have a valid format.
        if (splitGridCellMatrixCellGenerationTrackingStr.length != 3 ||
                /*
                 * Assure that the first two of the Strings in the splitGridCellGenerationTrackingStr array are 'short'
                 * Number types.*/
                stringIsNotAShort(splitGridCellMatrixCellGenerationTrackingStr[0]) ||
                stringIsNotAShort(splitGridCellMatrixCellGenerationTrackingStr[1]) ||
                /*
                 * Assure that the third String in the splitGridCellGenerationTrackingStr array is a 'long' Number
                 * type.*/
                stringIsNotALong(splitGridCellMatrixCellGenerationTrackingStr[2]))
            //If the inputString does not follow the above-mentioned format, throw an Exception.
            throw new MalformedGridCellMatrixCellGenerationTrackingStringException(inputString);
    }

    /**
     * Internal utility function that determines if a String does not represent a 'short' Number type.
     *
     * @param string The String to be checked.
     *
     * @return A boolean, which indicated if the provided string represents a 'short' Number type.
     *
     * */
    private static boolean stringIsNotAShort(final String string) {
        /*
         * Pass a stringToShort parsing action to a wrap-around predicate,
         * in order to determine if the provided String is a 'short' Number type.
         * */
        return !noNumberFormatException.test(() -> StringUtil.stringToShort(string));
    }

    /**
     * Internal utility function that determines if a String does not represent a 'long' Number type.
     *
     * @param string The String to be checked.
     *
     * @return A boolean, which indicated if the provided string represents a 'long' Number type.
     *
     * */
    private static boolean stringIsNotALong(final String string) {
        /*
         * Pass a stringToLong parsing action to a wrap-around predicate,
         * in order to determine if the provided String is a 'long' Number type.
         * */
        return !noNumberFormatException.test(() -> StringUtil.stringToLong(string));
    }

    /**
     * Internal utility predicate, which accepts an action (intended for stringToNumeric parse operations)
     * and determines if it throws a NumberFormatException.
     *
     * Returns a boolean that signals whether the passed action threw a NumberFormatException.
     *
     * */
    private static final Predicate<Runnable> noNumberFormatException = (action) -> {
        try {
            action.run();
        } catch (NumberFormatException ignored) {
            return false;
        }

        return true;
    };
}
