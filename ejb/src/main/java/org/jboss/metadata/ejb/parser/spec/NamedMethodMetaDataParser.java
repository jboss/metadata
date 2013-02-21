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

import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class NamedMethodMetaDataParser extends AbstractIdMetaDataParser<NamedMethodMetaData> {
    public static final NamedMethodMetaDataParser INSTANCE = new NamedMethodMetaDataParser();

    @Override
    public NamedMethodMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        NamedMethodMetaData namedMethodMetaData = new NamedMethodMetaData();
        processAttributes(namedMethodMetaData, reader, this);
        processElements(namedMethodMetaData, reader, propertyReplacer);
        return namedMethodMetaData;
    }

    @Override
    protected void processElement(NamedMethodMetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case METHOD_NAME:
                metaData.setMethodName(getElementText(reader, propertyReplacer));
                return;

            case METHOD_PARAMS:
                metaData.setMethodParams(MethodParametersMetaDataParser.INSTANCE.parse(reader, propertyReplacer));
                return;
        }
        super.processElement(metaData, reader, propertyReplacer);
    }
}
