/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
