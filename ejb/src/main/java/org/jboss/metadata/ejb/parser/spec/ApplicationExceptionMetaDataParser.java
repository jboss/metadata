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

import org.jboss.metadata.ejb.spec.ApplicationExceptionMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * @author <a href="mailto:carlo@redhat.com">Carlo de Wolf</a>
 */
public class ApplicationExceptionMetaDataParser extends AbstractIdMetaDataParser<ApplicationExceptionMetaData> {
    public static final ApplicationExceptionMetaDataParser INSTANCE = new ApplicationExceptionMetaDataParser();

    /**
     * Parses and creates InterceptorsMetaData out of the interceptors element
     *
     * @param reader
     * @return
     * @throws javax.xml.stream.XMLStreamException
     *
     */
    @Override
    public ApplicationExceptionMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        ApplicationExceptionMetaData metaData = new ApplicationExceptionMetaData();
        processAttributes(metaData, reader, this);
        this.processElements(metaData, reader, propertyReplacer);
        return metaData;
    }

    /**
     * Parses the child elements of the &lt;application-exception&gt; element and updates the passed {@link
     * org.jboss.metadata.ejb.spec.ApplicationExceptionMetaData} accordingly.
     *
     * @param applicationExceptionMetaData
     * @param reader
     * @throws javax.xml.stream.XMLStreamException
     *
     */
    @Override
    protected void processElement(ApplicationExceptionMetaData applicationExceptionMetaData, XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        // get the element to process
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case EXCEPTION_CLASS:
                applicationExceptionMetaData.setExceptionClass(super.getElementText(reader, propertyReplacer));
                break;

            case INHERITED:
                applicationExceptionMetaData.setInherited(Boolean.valueOf(super.getElementText(reader, propertyReplacer)));
                break;

            case ROLLBACK:
                applicationExceptionMetaData.setRollback(Boolean.valueOf(super.getElementText(reader, propertyReplacer)));
                break;

            default:
                throw unexpectedElement(reader);
        }
    }
}
