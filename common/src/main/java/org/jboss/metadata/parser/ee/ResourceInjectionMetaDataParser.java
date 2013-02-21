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
