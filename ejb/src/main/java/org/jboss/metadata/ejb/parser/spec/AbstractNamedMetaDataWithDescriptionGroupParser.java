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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;
import org.jboss.metadata.parser.ee.Accessor;
import org.jboss.metadata.parser.ee.DescriptionGroupMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public abstract class AbstractNamedMetaDataWithDescriptionGroupParser<MD extends NamedMetaDataWithDescriptionGroup> extends AbstractMetaDataParser<MD>
        implements AttributeProcessor<MD> {
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());

    @Override
    public void processAttribute(MD metaData, XMLStreamReader reader, int i) throws XMLStreamException {
        ATTRIBUTE_PROCESSOR.processAttribute(metaData, reader, i);
    }

    @Override
    protected void processElement(final MD metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        Accessor<DescriptionGroupMetaData> accessor = new Accessor<DescriptionGroupMetaData>() {
            @Override
            public DescriptionGroupMetaData get() {
                DescriptionGroupMetaData descriptionGroupMetaData = metaData.getDescriptionGroup();
                if (descriptionGroupMetaData == null) {
                    descriptionGroupMetaData = new DescriptionGroupMetaData();
                    metaData.setDescriptionGroup(descriptionGroupMetaData);
                }
                return descriptionGroupMetaData;
            }
        };
        if (DescriptionGroupMetaDataParser.parse(reader, accessor))
            return;
        super.processElement(metaData, reader, propertyReplacer);
    }
}
