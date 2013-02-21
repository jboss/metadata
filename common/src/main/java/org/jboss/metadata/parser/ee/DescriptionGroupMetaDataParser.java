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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.DisplayNamesImpl;
import org.jboss.metadata.javaee.spec.IconsImpl;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;


/**
 * @author Remy Maucherat
 */
public class DescriptionGroupMetaDataParser extends MetaDataElementParser {

    public static boolean parse(XMLStreamReader reader, Accessor<DescriptionGroupMetaData> accessor) throws XMLStreamException {
        return parse(reader, accessor, PropertyReplacers.noop());
    }

    public static boolean parse(XMLStreamReader reader, Accessor<DescriptionGroupMetaData> accessor, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        // Only look at the current element, no iteration
        final Element element = Element.forName(reader.getLocalName());
        switch (element) {
            case DESCRIPTION: {
                DescriptionGroupMetaData descriptionGroup = accessor.get();
                DescriptionsImpl descriptions = (DescriptionsImpl) descriptionGroup.getDescriptions();
                if (descriptions == null) {
                    descriptions = new DescriptionsImpl();
                    descriptionGroup.setDescriptions(descriptions);
                }
                descriptions.add(DescriptionMetaDataParser.parse(reader, propertyReplacer));
                break;
            }
            case ICON: {
                DescriptionGroupMetaData descriptionGroup = accessor.get();
                IconsImpl icons = (IconsImpl) descriptionGroup.getIcons();
                if (icons == null) {
                    icons = new IconsImpl();
                    descriptionGroup.setIcons(icons);
                }
                icons.add(IconMetaDataParser.parse(reader, propertyReplacer));
                break;
            }
            case DISPLAY_NAME: {
                DescriptionGroupMetaData descriptionGroup = accessor.get();
                DisplayNamesImpl displayNames = (DisplayNamesImpl) descriptionGroup.getDisplayNames();
                if (displayNames == null) {
                    displayNames = new DisplayNamesImpl();
                    descriptionGroup.setDisplayNames(displayNames);
                }
                displayNames.add(DisplayNameMetaDataParser.parse(reader, propertyReplacer));
                break;
            }
            default:
                return false;
        }
        return true;
    }

    public static boolean parse(XMLStreamReader reader, final DescriptionGroupMetaData descriptionGroup) throws XMLStreamException {
        return parse(reader, descriptionGroup, PropertyReplacers.noop());
    }

    public static boolean parse(XMLStreamReader reader, final DescriptionGroupMetaData descriptionGroup, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        return parse(reader, new Accessor<DescriptionGroupMetaData>() {
            @Override
            public DescriptionGroupMetaData get() {
                return descriptionGroup;
            }
        }, propertyReplacer);
    }
}
