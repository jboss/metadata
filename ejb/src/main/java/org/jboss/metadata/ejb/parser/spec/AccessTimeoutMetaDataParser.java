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

import java.util.concurrent.TimeUnit;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.AccessTimeoutMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class AccessTimeoutMetaDataParser extends AbstractMetaDataParser<AccessTimeoutMetaData> {
    public static final AccessTimeoutMetaDataParser INSTANCE = new AccessTimeoutMetaDataParser();

    @Override
    public AccessTimeoutMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        AccessTimeoutMetaData accessTimeoutMetaData = new AccessTimeoutMetaData();
        processElements(accessTimeoutMetaData, reader, propertyReplacer);
        return accessTimeoutMetaData;
    }

    @Override
    protected void processElement(AccessTimeoutMetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case TIMEOUT:
                metaData.setTimeout(Long.valueOf(getElementText(reader, propertyReplacer)));
                return;

            case UNIT:
                metaData.setUnit(TimeUnit.valueOf(getElementText(reader, propertyReplacer).toUpperCase()));
                return;
        }
        super.processElement(metaData, reader, propertyReplacer);
    }
}
