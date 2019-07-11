package com.makesystem.pidgey.formatation.printf;

import java.math.BigInteger;

import com.makesystem.pidgey.formatation.printf.bit.Bit;
import com.makesystem.pidgey.formatation.printf.bit.BitVector;
import com.makesystem.pidgey.formatation.printf.bit.BitVectorBuffer;

/**
 * Static methods which are useful for a variety of {@link ConversionFormatter}
 * objects.
 *
 * @author Richeli Vargas
 */
final class PrintfUtils {

    /**
     * Returns the width in bits of the specified Object. Supported types
     * include {@link Boolean} and most classes implementing {@link Number}.
     *
     * @param arg the object to check
     * @return the number of bits required by the type of <code>arg</code>
     * @throws InvalidFormatSpecException if <code>arg</code> is not a supported
     * type
     */
    public static final int getWidth(final Object arg) {
        final Class c = arg.getClass();
        if (c == Bit.class || c == Boolean.class) {
            return 1;
        } else if (c == Byte.class) {
            return 8;
        } else if (c == Short.class) {
            return 16;
        } else if (c == Integer.class || arg instanceof Enum< ?>) {
            return 32;
        } else if (c == Long.class) {
            return 64;
        } else if (c == BitVectorBuffer.class) {
            return ((BitVectorBuffer) arg).length();
        } else if (c == BitVector.class) {
            return ((BitVector) arg).length();
        } else if (c == BigInteger.class) {
            BigInteger bigInt = (BigInteger) arg;
            boolean isNegative = bigInt.signum() < 0;
            return bigInt.bitLength() + (isNegative ? 1 : 0);
        } else {
            throw new InvalidFormatSpecException("Unsupported numeric type: "
                    + c);
        }
    }
}
