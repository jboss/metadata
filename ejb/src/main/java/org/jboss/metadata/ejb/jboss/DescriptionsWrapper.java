/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import org.jboss.annotation.javaee.Description;
import org.jboss.annotation.javaee.Descriptions;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 65803 $
 */
public class DescriptionsWrapper
        implements Descriptions {
    private static final long serialVersionUID = 1;
    private Description[] value;

    DescriptionsWrapper(Descriptions primary, Descriptions defaults) {
        ArrayList<Description> tmp = new ArrayList<Description>();
        Description[] d = primary != null ? primary.value() : null;
        if (d != null) {
            for (Description desc : d)
                tmp.add(desc);
        }
        d = defaults != null ? defaults.value() : null;
        if (d != null) {
            for (Description desc : d)
                tmp.add(desc);
        }
        value = new Description[tmp.size()];
        tmp.toArray(value);
    }

    public Class<? extends Annotation> annotationType() {
        return Descriptions.class;
    }

    public Description[] value() {
        return value;
    }
}
