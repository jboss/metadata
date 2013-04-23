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

package org.jboss.metadata.ejb.parser.spec;

import org.jboss.metadata.ejb.spec.EjbJarVersion;

/**
 * User: jpai
 */
class AssemblyDescriptorMetaDataParserFactory {
    static AssemblyDescriptorMetaDataParser getParser(EjbJarVersion ejbJarVersion) {
        if (ejbJarVersion == null) {
            throw new IllegalArgumentException("ejb-jar version cannot be null");
        }
        switch (ejbJarVersion) {
            case EJB_1_1:
            case EJB_2_0:
                // TODO: Parser not yet implemented for EJB 1.x and EJB 2.x versions, fallback to generic
                return new AssemblyDescriptorMetaDataParser();
            case EJB_2_1:
                return new AssemblyDescriptor21MetaDataParser();
            case EJB_3_0:
            case EJB_3_1:
            case EJB_3_2:
                return new AssemblyDescriptor30MetaDataParser();
            default:
                throw new IllegalArgumentException("No parser available for ejb-jar version: " + ejbJarVersion.name());
        }
    }
}
