/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.jbossweb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.parser.ee.ParamValueMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.jboss.ValveMetaData;

/**
 * @author Remy Maucherat
 */
public class ValveMetaDataParser extends MetaDataElementParser {

    public static ValveMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        ValveMetaData valve = new ValveMetaData();

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case CLASS_NAME:
                    valve.setValveClass(getElementText(reader, propertyReplacer));
                    break;
                case MODULE:
                    valve.setModule(getElementText(reader, propertyReplacer));
                    break;
                case PARAM:
                    List<ParamValueMetaData> params = valve.getParams();
                    if (params == null) {
                        params = new ArrayList<ParamValueMetaData>();
                        valve.setParams(params);
                    }
                    params.add(ParamValueMetaDataParser.parse(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return valve;
    }

}
