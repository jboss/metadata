/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceType;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;

/**
 * @author Remy Maucherat
 */
public class EJBLocalReferenceMetaDataParser extends MetaDataElementParser {

    public static EJBLocalReferenceMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        return parse(reader, PropertyReplacers.noop());
    }

    public static EJBLocalReferenceMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        EJBLocalReferenceMetaData ejbReference = new EJBLocalReferenceMetaData();

        IdMetaDataParser.parseAttributes(reader, ejbReference);

        DescriptionsImpl descriptions = new DescriptionsImpl();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionsMetaDataParser.parse(reader, descriptions, propertyReplacer)) {
                if (ejbReference.getDescriptions() == null) {
                    ejbReference.setDescriptions(descriptions);
                }
                continue;
            }
            if (ResourceInjectionMetaDataParser.parse(reader, ejbReference, propertyReplacer)) {
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case EJB_REF_NAME:
                    ejbReference.setEjbRefName(getElementText(reader, propertyReplacer));
                    break;
                case EJB_REF_TYPE:
                    ejbReference.setEjbRefType(EJBReferenceType.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case LOCAL_HOME:
                    ejbReference.setLocalHome(getElementText(reader, propertyReplacer));
                    break;
                case LOCAL:
                    ejbReference.setLocal(getElementText(reader, propertyReplacer));
                    break;
                case LOCAL_JNDI_NAME:
                    ejbReference.setLocalJndiName(getElementText(reader, propertyReplacer));
                    break;
                case EJB_LINK:
                    ejbReference.setLink(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return ejbReference;
    }

}
