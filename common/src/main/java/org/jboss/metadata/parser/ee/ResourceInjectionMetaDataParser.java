/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import java.util.HashSet;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;


/**
 * @author Remy Maucherat
 */
public class ResourceInjectionMetaDataParser extends MetaDataElementParser {

    public static boolean parse(XMLStreamReader reader, ResourceInjectionMetaData resourceInjection, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        // Only look at the current element, no iteration
        final Element element = Element.forName(reader.getLocalName());
        switch (element) {
            case LOOKUP_NAME:
                resourceInjection.setLookupName(getElementText(reader, propertyReplacer));
                break;
            case MAPPED_NAME:
                resourceInjection.setMappedName(getElementText(reader, propertyReplacer));
                break;
            case JNDI_NAME:
                resourceInjection.setJndiName(getElementText(reader, propertyReplacer));
                break;
            case IGNORE_DEPENDECY:
                resourceInjection.setIgnoreDependency(new EmptyMetaData());
                break;
            case INJECTION_TARGET:
                Set<ResourceInjectionTargetMetaData> injectionTargets = resourceInjection.getInjectionTargets();
                if (injectionTargets == null) {
                    injectionTargets = new HashSet<ResourceInjectionTargetMetaData>();
                    resourceInjection.setInjectionTargets(injectionTargets);
                }
                injectionTargets.add(ResourceInjectionTargetMetaDataParser.parse(reader, propertyReplacer));
                break;
            default:
                return false;
        }
        return true;
    }

}
