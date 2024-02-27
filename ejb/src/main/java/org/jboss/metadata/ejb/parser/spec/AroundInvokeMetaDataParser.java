/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.AroundInvokeMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * Parses and creates metadata out of &lt;around-invoke&gt; element in ejb-jar.xml
 * <p/>
 * <p/>
 * Author: Jaikiran Pai
 */
public class AroundInvokeMetaDataParser extends AbstractMetaDataParser<AroundInvokeMetaData> {

    public static final AroundInvokeMetaDataParser INSTANCE = new AroundInvokeMetaDataParser();

    /**
     * Parses and creates AroundInvokeMetaData out of the around-invoke element
     *
     * @param reader
     * @return
     * @throws XMLStreamException
     */
    @Override
    public AroundInvokeMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        AroundInvokeMetaData aroundInvoke = new AroundInvokeMetaData();
        this.processElements(aroundInvoke, reader, propertyReplacer);
        return aroundInvoke;
    }

    /**
     * Parses the child elements in the around-invoke element and updates the passed {@link AroundInvokeMetaData}
     * accordingly.
     *
     * @param aroundInvoke
     * @param reader
     * @throws XMLStreamException
     */
    @Override
    protected void processElement(AroundInvokeMetaData aroundInvoke, XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        // get the element to process
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case CLASS:
                String klass = getElementText(reader, propertyReplacer);
                aroundInvoke.setClassName(klass);
                return;

            case METHOD_NAME:
                String methodName = getElementText(reader, propertyReplacer);
                aroundInvoke.setMethodName(methodName);
                return;

            default:
                throw unexpectedElement(reader);
        }
    }

}

