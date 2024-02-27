/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.jboss.ejb3;

import java.util.HashSet;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.EjbJarElement;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * jboss-assembly-descriptor-bean-entryType
 *
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public abstract class AbstractEJBBoundMetaDataParser<MD extends AbstractEJBBoundMetaData> extends AbstractMetaDataParser<MD> {
    @Override
    protected void processElement(MD metaData, XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        final Namespace namespace = Namespace.forUri(reader.getNamespaceURI());
        switch (namespace) {
            case SPEC:
            case SPEC_7_0:
            case JAKARTAEE:
                final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
                switch (ejbJarElement) {
                    case DESCRIPTION:
                        // TODO: implement
                        reader.getElementText();
                        return;
                    case EJB_NAME:
                        if(metaData.getEjbName() != null) {
                            throw unexpectedElement(reader);
                        }
                        metaData.setEjbName(getElementText(reader, propertyReplacer));
                        return;
                }
        }
        super.processElement(metaData, reader, propertyReplacer);
    }

    @Override
    protected void processElements(MD metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer)
            throws XMLStreamException {
        super.processElements(metaData, reader, propertyReplacer);
        if (metaData.getEjbName() == null) {
            Set<EjbJarElement> required = new HashSet<EjbJarElement>();
            required.add(EjbJarElement.EJB_NAME);
            throw missingRequiredElement(reader, required);
        }
    }

}
