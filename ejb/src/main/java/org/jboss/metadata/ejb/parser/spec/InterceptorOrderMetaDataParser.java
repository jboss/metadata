/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.InterceptorOrderMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * Parses the &lt;interceptor-order&gt; element in a ejb-jar.xml and creates metadata out of it.
 * <p/>
 * <p/>
 * Author: Stuart Douglas
 */
public class InterceptorOrderMetaDataParser extends AbstractIdMetaDataParser<InterceptorOrderMetaData> {
    public static final InterceptorOrderMetaDataParser INSTANCE = new InterceptorOrderMetaDataParser();

    /**
     * Parses and creates InterceptorsMetaData out of the interceptors element
     *
     * @param reader
     * @return
     * @throws javax.xml.stream.XMLStreamException
     *
     */
    @Override
    public InterceptorOrderMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        InterceptorOrderMetaData interceptors = new InterceptorOrderMetaData();
        processAttributes(interceptors, reader, this);
        this.processElements(interceptors, reader, propertyReplacer);
        return interceptors;
    }

    /**
     * Parses the child elements of the &lt;interceptors&gt; element and updates the passed {@link org.jboss.metadata.ejb.spec.InterceptorsMetaData}
     * accordingly.
     *
     * @param interceptors
     * @param reader
     * @throws javax.xml.stream.XMLStreamException
     *
     */
    @Override
    protected void processElement(InterceptorOrderMetaData interceptors, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        // get the element to process
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case INTERCEPTOR_CLASS:
                interceptors.add(getElementText(reader, propertyReplacer));
                return;

            default:
                throw unexpectedElement(reader);

        }
    }
}
