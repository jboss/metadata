/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
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
