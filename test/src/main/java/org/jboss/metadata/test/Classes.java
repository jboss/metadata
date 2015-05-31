package org.jboss.metadata.test;

import java.lang.reflect.Method;

/**
 * A collection of <code>Class</code> utilities.
 *
 * @author <a href="mailto:jason@planet57.com">Jason Dillon</a>
 * @author <a href="mailto:scott.stark@jboss.org">Scott Stark</a>
 * @author <a href="mailto:dimitris@jboss.org">Dimitris Andreadis<a/>
 * @version <tt>$Revision: 2787 $</tt>
 */
@SuppressWarnings("unchecked")
public final class Classes {


    /////////////////////////////////////////////////////////////////////////
    //                               Primitives                            //
    /////////////////////////////////////////////////////////////////////////


    /**
     * Map of primitive types to their wrapper classes
     */
    private static final Class[] PRIMITIVE_WRAPPER_MAP = {
            Boolean.TYPE, Boolean.class,
            Byte.TYPE, Byte.class,
            Character.TYPE, Character.class,
            Double.TYPE, Double.class,
            Float.TYPE, Float.class,
            Integer.TYPE, Integer.class,
            Long.TYPE, Long.class,
            Short.TYPE, Short.class,
    };

    /**
     * Get the wrapper class for the given primitive type.
     *
     * @param type Primitive class.
     * @return Wrapper class for primitive.
     * @throws IllegalArgumentException Type is not a primitive class
     */
    public static Class getPrimitiveWrapper(final Class type) {
        if (!type.isPrimitive()) {
            throw new IllegalArgumentException("type is not a primitive class");
        }

        for (int i = 0; i < PRIMITIVE_WRAPPER_MAP.length; i += 2) {
            if (type.equals(PRIMITIVE_WRAPPER_MAP[i])) { return PRIMITIVE_WRAPPER_MAP[i + 1]; }
        }

        // should never get here, if we do then PRIMITIVE_WRAPPER_MAP
        // needs to be updated to include the missing mapping
        throw new IllegalStateException();
    }


    /**
     * Check if the given class is a primitive wrapper class.
     *
     * @param type Class to check.
     * @return True if the class is a primitive wrapper.
     */
    public static boolean isPrimitiveWrapper(final Class type) {
        for (int i = 0; i < PRIMITIVE_WRAPPER_MAP.length; i += 2) {
            if (type.equals(PRIMITIVE_WRAPPER_MAP[i + 1])) {
                return true;
            }
        }

        return false;
    }


    /**
     * Returns attribute's getter method. If the method not found then NoSuchMethodException will be thrown.
     *
     * @param cls  the class the attribute belongs too
     * @param attr the attribute's name
     * @return attribute's getter method
     * @throws NoSuchMethodException if the getter was not found
     */
    public static Method getAttributeGetter(Class cls, String attr) throws NoSuchMethodException {
        StringBuilder buf = new StringBuilder(attr.length() + 3);
        buf.append("get");
        if (Character.isLowerCase(attr.charAt(0))) {
            buf.append(Character.toUpperCase(attr.charAt(0)))
                    .append(attr.substring(1));
        } else {
            buf.append(attr);
        }

        try {
            return cls.getMethod(buf.toString(), (Class[]) null);
        } catch (NoSuchMethodException e) {
            buf.replace(0, 3, "is");
            return cls.getMethod(buf.toString(), (Class[]) null);
        }
    }

}
