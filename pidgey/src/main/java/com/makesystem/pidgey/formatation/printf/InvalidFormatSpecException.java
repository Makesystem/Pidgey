package com.makesystem.pidgey.formatation.printf;

/**
 * This exception is thrown when an invalid format is detected by the printf
 * implementation.
 *
 * @author Richeli Vargas
 */
public class InvalidFormatSpecException
        extends RuntimeException {

    private static final long serialVersionUID = 3256720693289956408L;

    /**
     * @param message
     * @see RuntimeException#RuntimeException(java.lang.String)
     */
    public InvalidFormatSpecException(final String message) {
        super(message);
    }

    /**
     * @param e
     * @see RuntimeException#RuntimeException(java.lang.Throwable)
     */
    public InvalidFormatSpecException(final Throwable e) {
        super(e);
    }
}
