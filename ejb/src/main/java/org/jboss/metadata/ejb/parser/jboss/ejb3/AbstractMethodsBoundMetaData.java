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
package org.jboss.metadata.ejb.parser.jboss.ejb3;

import org.jboss.metadata.ejb.spec.MethodsMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class AbstractMethodsBoundMetaData extends IdMetaDataImplWithDescriptions {
    /**
     * The methods
     */
    private MethodsMetaData methods;

    /**
     * Get the methods.
     *
     * @return the methods.
     */
    public MethodsMetaData getMethods() {
        return methods;
    }

    /**
     * Set the methods.
     *
     * @param methods the methods.
     * @throws IllegalArgumentException for a null methods
     */
    public void setMethods(MethodsMetaData methods) {
        if (methods == null)
            throw new IllegalArgumentException("Null methods");
        this.methods = methods;
    }
}
