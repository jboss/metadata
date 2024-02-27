/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.support.WithDescriptions;
import org.jboss.metadata.parser.ee.DescriptionMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public abstract class AbstractWithDescriptionsParser<MD extends WithDescriptions> extends AbstractMetaDataParser<MD> {
    @Override
    protected void processElement(MD metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case DESCRIPTION:
                DescriptionsImpl descriptions = (DescriptionsImpl) metaData.getDescriptions();
                if (descriptions == null) {
                    descriptions = new DescriptionsImpl();
                    metaData.setDescriptions(descriptions);
                }
                descriptions.add(DescriptionMetaDataParser.parse(reader, propertyReplacer));
                break;

            default:
                super.processElement(metaData, reader, propertyReplacer);
        }
    }
}
