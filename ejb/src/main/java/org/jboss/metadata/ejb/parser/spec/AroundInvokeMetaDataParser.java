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

