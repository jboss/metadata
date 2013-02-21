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
package org.jboss.metadata.javaee.jboss;

// $Id: StubPropertyMetaData.java 84989 2009-03-02 11:40:52Z alex.loubyansky@jboss.com $

import org.jboss.metadata.javaee.spec.ParamValueMetaData;

/**
 * A remapping of ParamValueMetaData to support prop-name, prop-value elements
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */

public class StubPropertyMetaData extends ParamValueMetaData {
    private static final long serialVersionUID = 1;

    public String getPropName() {
        return super.getParamName();
    }

    public void setPropName(String name) {
        super.setParamName(name);
    }

    public String getPropValue() {
        return super.getParamValue();
    }

    public void setPropValue(String value) {
        super.setParamValue(value);
    }
}
