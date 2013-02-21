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

import org.jboss.metadata.javaee.support.IdMetaData;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
class IdMetaDataAttributeProcessor<MD extends IdMetaData> extends AbstractChainedAttributeProcessor<MD> {
    IdMetaDataAttributeProcessor(final AttributeProcessor<? super MD> next) {
        super(next);
    }

    @Override
    public void processAttribute(MD metaData, XMLStreamReader reader, int i) throws XMLStreamException {
        final String value = reader.getAttributeValue(i);
        final EjbJarAttribute ejbJarAttribute = EjbJarAttribute.forName(reader.getAttributeLocalName(i));
        switch (ejbJarAttribute) {
            case ID: {
                metaData.setId(value);
                break;
            }
            default:
                super.processAttribute(metaData, reader, i);
        }
    }
}
