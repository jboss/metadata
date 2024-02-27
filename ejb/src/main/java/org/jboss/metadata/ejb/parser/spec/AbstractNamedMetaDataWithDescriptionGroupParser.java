/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
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
