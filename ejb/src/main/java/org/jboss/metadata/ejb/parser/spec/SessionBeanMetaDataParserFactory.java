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
 * Responsible for returning the correct parser, based on the ejb-jar version, for
 * parsing metadata out of ejb-jar.xml
 * <p/>
 * <p/>
 * Author: Jaikiran Pai
 */
class SessionBeanMetaDataParserFactory {

    /**
     * Returns an appropriate instance of {@link SessionBeanMetaDataParser} for the passed
     * {@link EjbJarVersion ejb-jar version}
     *
     * @param ejbJarVersion The ejb-jar version
     * @return
     */
    static SessionBeanMetaDataParser getParser(EjbJarVersion ejbJarVersion) {
        if (ejbJarVersion == null) {
            throw new IllegalArgumentException("ejb-jar version cannot be null. Can't return a parser");
        }
        switch (ejbJarVersion) {
            case EJB_3_0:
            case EJB_1_1:
            case EJB_2_0:
            case EJB_2_1:
                return new SessionBean30MetaDataParser();

            case EJB_3_1:
                return new SessionBean31MetaDataParser();
            case EJB_3_2:
                return new SessionBean32MetaDataParser();

            default:
                throw new IllegalArgumentException("No parser available for ejb-jar version: " + ejbJarVersion.name());
        }
    }
}
