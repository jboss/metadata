/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.AroundTimeoutMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * Parses and creates metadata out of &lt;around-timeout&gt; element in ejb-jar.xml
 * <p/>
 * <p/>
 * Author: Jaikiran Pai
 */
public class AroundTimeoutMetaDataParser extends AbstractMetaDataParser<AroundTimeoutMetaData> {

    public static final AroundTimeoutMetaDataParser INSTANCE = new AroundTimeoutMetaDataParser();

    /**
     * Parses and creates AroundInvokeMetaData out of the around-invoke element
     *
     * @param reader
     * @return
     * @throws javax.xml.stream.XMLStreamException
     *
     */
    @Override
    public AroundTimeoutMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        AroundTimeoutMetaData aroundTimeoutMetaData = new AroundTimeoutMetaData();
        this.processElements(aroundTimeoutMetaData, reader, propertyReplacer);
        return aroundTimeoutMetaData;
    }

    /**
     * Parses the child elements in the around-invoke element and updates the passed {@link org.jboss.metadata.ejb.spec.AroundInvokeMetaData}
     * accordingly.
     *
     * @param aroundTimeout
     * @param reader
     * @throws javax.xml.stream.XMLStreamException
     *
     */
    @Override
    protected void processElement(AroundTimeoutMetaData aroundTimeout, XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        // get the element to process
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case CLASS:
                String klass = getElementText(reader, propertyReplacer);
                aroundTimeout.setClassName(klass);
                return;

            case METHOD_NAME:
                String methodName = getElementText(reader, propertyReplacer);
                aroundTimeout.setMethodName(methodName);
                return;

            default:
                throw unexpectedElement(reader);
        }
    }

}

