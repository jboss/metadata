/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.extension;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.parser.jboss.ejb3.AbstractEJBBoundMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class CacheTestParser extends AbstractEJBBoundMetaDataParser<CacheTest> {
    @Override
    public CacheTest parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        CacheTest cacheTest = new CacheTest();
        processElements(cacheTest, reader, propertyReplacer);
        return cacheTest;
    }

    @Override
    protected void processElement(CacheTest metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        if (reader.getNamespaceURI().equals("urn:cache-test")) {
            if (reader.getLocalName().equals("size")) { metaData.setSize(Integer.valueOf(getElementText(reader, propertyReplacer))); } else {
                throw unexpectedElement(reader);
            }
        } else { super.processElement(metaData, reader, propertyReplacer); }
    }
}
