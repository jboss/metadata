/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
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

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.jboss.StubPropertyMetaData;
import org.jboss.metadata.javaee.spec.RespectBinding;
import org.jboss.metadata.parser.util.MetaDataElementParser;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public class StubPropertyParser extends MetaDataElementParser {

    public static StubPropertyMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        StubPropertyMetaData stubPropertyMD = new StubPropertyMetaData();

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case PROP_NAME:
                    stubPropertyMD.setPropName(getElementText(reader));
                    break;
                case PROP_VALUE:
                    stubPropertyMD.setPropValue(getElementText(reader));
                    break;
                default: throw unexpectedElement(reader);
            }
        }

        return stubPropertyMD;
    }

    /**
     * Read the element text, with trimming.
     *
     * @param reader the reader
     * @throws XMLStreamException if an error occurs
    */
    protected static String getElementText(final XMLStreamReader reader) throws XMLStreamException {
        return getElementText(reader, true);
    }

}
