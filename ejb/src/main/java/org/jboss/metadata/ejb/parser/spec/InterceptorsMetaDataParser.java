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

import org.jboss.metadata.ejb.spec.InterceptorMetaData;
import org.jboss.metadata.ejb.spec.InterceptorsMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * Parses the &lt;interceptors&gt; element in a ejb-jar.xml and creates metadata out of it.
 * <p/>
 * <p/>
 * Author: Jaikiran Pai
 */
public class InterceptorsMetaDataParser extends AbstractWithDescriptionsParser<InterceptorsMetaData> {
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());
    public static final InterceptorsMetaDataParser INSTANCE = new InterceptorsMetaDataParser();

    /**
     * Parses and creates InterceptorsMetaData out of the interceptors element
     *
     * @param reader
     * @return
     * @throws XMLStreamException
     */
    @Override
    public InterceptorsMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        InterceptorsMetaData interceptors = new InterceptorsMetaData();
        processAttributes(interceptors, reader, ATTRIBUTE_PROCESSOR);
        this.processElements(interceptors, reader, propertyReplacer);
        return interceptors;
    }

    /**
     * Parses the child elements of the &lt;interceptors&gt; element and updates the passed {@link InterceptorsMetaData}
     * accordingly.
     *
     * @param interceptors
     * @param reader
     * @throws XMLStreamException
     */
    @Override
    protected void processElement(InterceptorsMetaData interceptors, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        // get the element to process
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case INTERCEPTOR:
                InterceptorMetaData interceptor = InterceptorMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                interceptors.add(interceptor);
                return;

            default:
                super.processElement(interceptors, reader, propertyReplacer);

        }
    }
}
