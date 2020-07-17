package com.nnikolov.green_vs_red.util;

/**
 * An Utility class that provides commonly used by this application static functions, which work with Strings and
 * derive various return results from them.
 * 
 * */
public final class StringUtil {

    /**
     * This function splits a String via the input delimiter ", ", which is used by this application.
     *
     * @param string The String, which will be split via the above-mentioned delimiter.
     *
     * @return A String array that contains substrings, derived from the original input String after is splitting.
     *
     * */
    public static String[] splitStringByGenericApplicationInputDelimiter(final String string) {
        return string.split(", ");
    }

    /**
     * This function converts a String to a 'short' Number type.
     * NOTE: Strings that do not contain a 'short' Number type will result in an Exception being thrown by this method.
     *
     * @param string The string, which will be converted to a 'short' Number type.
     *
     * @return A 'short' Number type, which is acquired from the provided String.
     *
     * */
    public static short stringToShort(final String string) {
        return Short.parseShort(string);
    }

    /**
     * This function converts a Char to a 'short' Number type.
     * NOTE: Characters that do not represent a 'short' Number type will result in an Exception being thrown by this
     * method.
     *
     * @param character The Char, which will be converted to a 'short' Number type.
     *
     * @return A 'short' Number type, which is acquired from the provided Char.
     *
     * */
    public static short charToShort(final char character) {
        return stringToShort(String.valueOf(character) /* Convert the Char to a String */);
    }

    /**
     * This function converts a String to a 'long' Number type.
     * NOTE: Strings that do not contain a 'long' Number type will result in an Exception being thrown by this method.
     *
     * @param string The string, which will be converted to a 'long' Number type.
     *
     * @return A 'long' Number type, which is acquired from the provided String.
     *
     * */
    public static long stringToLong(final String string) {
        return Long.parseLong(string);
    }
}
