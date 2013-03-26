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

import org.jboss.metadata.ejb.spec.AbstractGenericBeanMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * EJB 3.2 version specific ejb-jar.xml parser for session beans
 * <p/>
 * Author: Jaikiran Pai
 */
public class SessionBean32MetaDataParser extends SessionBean31MetaDataParser {
    /**
     * Parses EJB 3.2 specific ejb-jar.xml elements and updates the passed {@link org.jboss.metadata.ejb.spec.SessionBean32MetaData ejb metadata} appropriately
     *
     * @param sessionBean The metadat to be updated during parsing
     * @param reader      The XMLStreamReader
     * @throws javax.xml.stream.XMLStreamException
     *
     */
    protected void processElement(AbstractGenericBeanMetaData sessionBean, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        // get the element to process
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case PASSIVATION_CAPABLE:
                final String passviationCapable = getElementText(reader, propertyReplacer);
                sessionBean.setPassivationCapable(Boolean.parseBoolean(passviationCapable));
                return;
            default:
                super.processElement(sessionBean, reader, propertyReplacer);
                return;

        }
    }
}
