/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Richeli.vargas
 */
public class ObjectHelper implements Serializable {

    private static final long serialVersionUID = -88421800383487956L;

    public final static boolean isNullOrEmpty(final Object object) {
        return isNull(object) || isEmpty(object);
    }

    public final static boolean isNotNullAndNotEmpty(final Object object) {
        return isNotNull(object) && isNotEmpty(object);
    }

    @SuppressWarnings("null")
    public final static boolean isEmpty(final Object object) {

        if (isNull(object)) {
            throw new IllegalArgumentException("Object is null");
        }

        if (object instanceof Map) {
            return ((Map) object).isEmpty();
        } else if (object instanceof Collection) {
            return ((Collection) object).isEmpty();
        } else if (object instanceof Object[]) {
            return ((Object[]) object).length == 0;
        } else {
            return StringHelper.isEmpty(object.toString());
        }
    }

    public final static boolean isNotEmpty(final Object object) {
        return !isEmpty(object);
    }

    public final static boolean isNull(final Object object) {
        return object == null;
    }

    public final static boolean isNotNull(final Object object) {
        return !isNull(object);
    }
    
    public final static <O> O orDefault(final O object, final O defaultValue) {
        if (isNull(object)) {
            return defaultValue;
        }
        return object;
    }
}
