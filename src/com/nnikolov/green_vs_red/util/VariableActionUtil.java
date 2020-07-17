package com.nnikolov.green_vs_red.util;

import com.nnikolov.green_vs_red.exception.InvalidCellColorCodeException;
import com.nnikolov.green_vs_red.exception.IncompatibleGridCellMatrixShapeException;
import com.nnikolov.green_vs_red.exception.InvalidTargetGridGeneration;
import com.nnikolov.green_vs_red.exception.grid.GridException;
import com.nnikolov.green_vs_red.exception.string.MalformedInputStringException;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * An utility class, which provides a static wrap-around code-block for working with actions that could throw
 * one of the Exceptions defined in *.green_vs_red.exception.
 *
 * */
public final class VariableActionUtil {

    /**
     * This wrap-around code-block accepts an optional variable action that will be executed within it for a
     * potentially indefinite amount, until none of the Exceptions defined in *.green_vs_red.exception are thrown.
     * All potential error messages are fed to a provided String Consumer, so that the may be outputted to a preferred
     * channel of the User.
     *
     * */
    public static BiConsumer<Consumer<String>, Runnable> loopAndOutputErrorMessagesUntilAllActionExceptionsAreCleared =
            (messageOutputStringConsumer, action) -> {
                while (true) { //Loop until a break is encountered
                    try {
                        action.run(); //Execute the action, which is provided by the User.

                        break; //Action completed successfully, exit loop and wrap-around code-block.
                    } catch (final GridException | MalformedInputStringException |
                            InvalidCellColorCodeException | IncompatibleGridCellMatrixShapeException |
                            InvalidTargetGridGeneration ex) {
                        /*
                         * An Exception was thrown by the variable action, feed its error message to the provided
                         * String Consumer.*/
                        messageOutputStringConsumer.accept(ex.getMessage());
                    }
                }
            };
}
